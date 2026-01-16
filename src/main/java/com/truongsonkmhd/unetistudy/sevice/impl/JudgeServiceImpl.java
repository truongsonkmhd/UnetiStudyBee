package com.truongsonkmhd.unetistudy.sevice.impl;

import com.truongsonkmhd.unetistudy.common.SubmissionVerdict;
import com.truongsonkmhd.unetistudy.context.UserContext;
import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.JudgeRequestDTO;
import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.JudgeRunResponseDTO;
import com.truongsonkmhd.unetistudy.dto.CodingSubmission.CodingSubmissionResponseDTO;
import com.truongsonkmhd.unetistudy.dto.ExerciseTestCasesDTO.ExerciseTestCasesDTO;
import com.truongsonkmhd.unetistudy.mapper.coding_submission.ExerciseTestCaseMapper;
import com.truongsonkmhd.unetistudy.repository.ExerciseTestCaseRepository;
import com.truongsonkmhd.unetistudy.sevice.JudgeService;
import com.truongsonkmhd.unetistudy.sevice.UserService;
import com.truongsonkmhd.unetistudy.utils.DockerCodeExecutionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.UUID;

import static com.truongsonkmhd.unetistudy.utils.DockerCodeExecutionUtil.*;

@Service
@RequiredArgsConstructor
public class JudgeServiceImpl implements JudgeService {

    private final UserService userService;
    private final ExerciseTestCaseMapper exerciseTestCaseMapper;
    private final ExerciseTestCaseRepository exerciseTestCaseRepository;

    private static final String ENV_CONTAINER = "JUDGE_WORKDIR_CONTAINER";
    private static final String ENV_HOST = "JUDGE_WORKDIR_HOST";

    /**
     * Đường dẫn "nhìn từ trong container judge" (nếu util của bạn cần).
     * Nếu bạn chỉ truyền workingDir (host) vào util và util tự map mount thì có thể không cần dùng.
     */
    private Path containerBaseDir() {
        String v = System.getenv(ENV_CONTAINER);
        return Paths.get(v != null && !v.isBlank() ? v : "Code_Dir");
    }

    /**
     * Đường dẫn thực tế nơi app đang chạy và ghi file (host path đối với docker mount).
     * Nếu app của bạn chạy trong container, "hostBaseDir" vẫn là path trong container app,
     * nhưng phải đúng với volume mount mà container judge dùng.
     */
    private Path hostBaseDir() {
        String v = System.getenv(ENV_HOST);
        return Paths.get(v != null && !v.isBlank() ? v : "Code_Dir");
    }

    @Override
    public JudgeRunResponseDTO runUserCode(JudgeRequestDTO request) {

        String userName = safeFilePart(UserContext.getUsername());
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        String folderName = userName + "-ex" + request.getExerciseID() + "-" + timestamp;

        // luôn dùng base dir (đừng hard-code "Code_Dir")
        Path workingDir = hostBaseDir().resolve(folderName);

        try {
            // (giữ để debug nếu bạn cần)
            Path containerDir = containerBaseDir().resolve(folderName);

            // Tạo folder base + folder bài làm
            Files.createDirectories(hostBaseDir());
            Files.createDirectories(workingDir);

            // Tạo và ghi file source theo ngôn ngữ
            Path sourceFile = workingDir.resolve(resolveSourceFileName(request.getLanguage()));
            Files.writeString(sourceFile, request.getSourceCode());

            // Biên dịch trong Docker
            DockerCodeExecutionUtil.compileInContainer(workingDir, request.getLanguage());

            // Lấy testcases 1 lần
            Set<ExerciseTestCasesDTO> testCases = getListExerciseTestCase(request);

            StringBuilder output = new StringBuilder();

            // Không có test case -> chạy 1 lần (không input)
            if (testCases == null || testCases.isEmpty()) {
                output.append(DockerCodeExecutionUtil.runInContainer(workingDir, request.getLanguage(), ""));
                return new JudgeRunResponseDTO(output.toString(), "SUCCESS", "");
            }

            boolean allPassed = true;

            for (ExerciseTestCasesDTO testCase : testCases) {
                // Bỏ qua test case ẩn (public = false) khi "run"
                if (Boolean.FALSE.equals(testCase.getIsPublic())) continue;

                String outputRun = DockerCodeExecutionUtil.runInContainer(
                        workingDir,
                        request.getLanguage(),
                        safeString(testCase.getInput())
                );

                String expected = safeTrim(testCase.getExpectedOutput());
                String actual = safeTrim(outputRun);

                boolean passed = expected.equals(actual);
                if (!passed) allPassed = false;

                output.append("Test case:\n")
                        .append("Input:\n").append(safeString(testCase.getInput())).append("\n")
                        .append("Expected:\n").append(expected).append("\n")
                        .append("Actual:\n").append(actual).append("\n")
                        .append(passed ? "✅ Passed\n\n" : "❌ Failed\n\n");
            }

            String status = allPassed ? "SUCCESS" : "FAIL";
            return new JudgeRunResponseDTO(output.toString(), status, "");

        } catch (DockerCodeExecutionUtil.CompilationException e) {
            return new JudgeRunResponseDTO(e.getOutput(), "COMPILATION_ERROR", "Lỗi biên dịch");
        } catch (IOException | InterruptedException e) {
            return new JudgeRunResponseDTO("", "ERROR", e.getMessage());
        } catch (RuntimeException e) {
            return new JudgeRunResponseDTO("", "ERROR", e.getMessage());
        } finally {
            try {
                DockerCodeExecutionUtil.deleteDirectoryRecursively(workingDir);
            } catch (Exception ignored) {
            }
        }
    }

    private Set<ExerciseTestCasesDTO> getListExerciseTestCase(JudgeRequestDTO request) {
        return exerciseTestCaseMapper.toDto(
                exerciseTestCaseRepository.getExerciseTestCasesDTOByExerciseID(request.getExerciseID())
        );
    }

    @Override
    public CodingSubmissionResponseDTO submitUserCode(JudgeRequestDTO request) {

        Set<ExerciseTestCasesDTO> exerciseTestCases = getListExerciseTestCase(request);

        String userName = safeFilePart(UserContext.getUsername());
        UUID userId = userService.findUserIDByUserName(UserContext.getUsername());

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        String folderName = userName + "-ex" + request.getExerciseID() + "-" + timestamp;

        Path workingDir = hostBaseDir().resolve(folderName);

        int passed = 0;
        int total = (exerciseTestCases == null) ? 0 : exerciseTestCases.size();
        int score = 0;

        Integer runtimeMs = null; // TODO đo thật
        Integer memoryKb = null;  // TODO đo thật

        SubmissionVerdict verdict = SubmissionVerdict.PENDING;

        try {
            Files.createDirectories(hostBaseDir());
            Files.createDirectories(workingDir);

            Path sourceFile = workingDir.resolve(resolveSourceFileName(request.getLanguage()));
            Files.writeString(sourceFile, request.getSourceCode());

            DockerCodeExecutionUtil.compileInContainer(workingDir, request.getLanguage());

            // Không có test case -> chạy 1 lần
            if (total == 0) {
                DockerCodeExecutionUtil.runInContainer(workingDir, request.getLanguage(), "");
                verdict = SubmissionVerdict.ACCEPTED;
                score = 0;
            } else {
                boolean allPassed = true;

                // submit thường chấm cả public + hidden (không filter)
                for (ExerciseTestCasesDTO tc : exerciseTestCases) {
                    String outputRun = DockerCodeExecutionUtil.runInContainer(
                            workingDir,
                            request.getLanguage(),
                            safeString(tc.getInput())
                    );

                    String expected = safeTrim(tc.getExpectedOutput());
                    String actual = safeTrim(outputRun);

                    boolean ok = expected.equals(actual);
                    if (!ok) {
                        allPassed = false;
                    } else {
                        passed++;
                        score += (tc.getScore() != null ? tc.getScore() : 0);
                    }
                }

                verdict = allPassed ? SubmissionVerdict.ACCEPTED : SubmissionVerdict.WRONG_ANSWER;
            }

        } catch (DockerCodeExecutionUtil.CompilationException e) {
            verdict = SubmissionVerdict.COMPILATION_ERROR;
        } catch (IOException | InterruptedException | RuntimeException e) {
            verdict = SubmissionVerdict.RUNTIME_ERROR;
        } finally {
            try {
                DockerCodeExecutionUtil.deleteDirectoryRecursively(workingDir);
            } catch (Exception ignored) {
            }
        }


        return CodingSubmissionResponseDTO.builder()
                .exerciseID(request.getExerciseID())
                .userID(userId)
                .code(request.getSourceCode())
                .language(request.getLanguage())
                .verdict(verdict)
                .passedTestcases(passed)
                .totalTestcases(total)
                .runtimeMs(runtimeMs)
                .memoryKb(memoryKb)
                .score(score)
                .submittedAt(Instant.now())
                .build();
    }

    private static String safeTrim(String s) {
        return s == null ? "" : s.trim();
    }

    private static String safeString(String s) {
        return s == null ? "" : s;
    }

    /**
     * Chống username chứa ký tự lạ làm hỏng path.
     */
    private static String safeFilePart(String s) {
        if (s == null || s.isBlank()) return "user";
        return s.replaceAll("[^a-zA-Z0-9._-]", "_");
    }

    /**
     * Tùy language mà map ra đúng file name.
     */
    private static String resolveSourceFileName(String language) {
        if (language == null || language.isBlank()) {
            return "Main.java";
        }

        return switch (language.toLowerCase()) {
            case LANG_JAVA -> "Main.java";
            case LANG_PYTHON -> "main.py";
            case LANG_CPP -> "main.cpp";
            case LANG_CSHARP -> "Main.cs";
            case LANG_GO -> "main.go";
            default -> "Main.java";
        };
    }

}
