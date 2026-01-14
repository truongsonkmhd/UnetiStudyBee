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

@Service
@RequiredArgsConstructor
public class JudgeServiceImpl implements JudgeService {
    private final UserService userService;

    private final ExerciseTestCaseMapper exerciseTestCaseMapper;

    private final ExerciseTestCaseRepository exerciseTestCaseRepository;

    @Override
    public JudgeRunResponseDTO runUserCode(JudgeRequestDTO request, String language) {

        String userName = UserContext.getUsername();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        String folderName = userName + "-ex" + request.getExerciseID() + "-" + timestamp;

        Path workingDir = Paths.get("Code_Dir", folderName);

        try {
            // Tạo folder lưu code tạm Code_Dir nếu chưa tồn tại
            Files.createDirectories(Paths.get("Code_Dir"));
            Files.createDirectories(workingDir);

            // Tạo và ghi file Main.java
            Path sourceFile = workingDir.resolve("Main.java");
            Files.writeString(sourceFile, request.getSourceCode());

            // Biên dịch sử dụng Docker
            DockerCodeExecutionUtil.compileInContainer(workingDir , language);

            // Tạo output để trả về alert kết quả testcase bên client
            StringBuilder output = new StringBuilder();

            // Kiểm tra xem bài tập có test case không, nếu không có thì không cần nhập testcase
            if (getListExerciseTestCase(request).isEmpty()) {
                output.append(DockerCodeExecutionUtil.runInContainer(workingDir,language, ""));
            }

            boolean allPassed = true;

            for (ExerciseTestCasesDTO testCase : getListExerciseTestCase(request)) {
                // Bỏ qua test case ẩn (public = false)
                if (!testCase.getIsPublic())
                    continue;

                // Chạy với input của testcase public trong Docker
                String outputRun = DockerCodeExecutionUtil.runInContainer(
                        workingDir,language,
                        testCase.getInput()
                );

                // So sánh output thực tế và output mong đợi
                String expected = testCase.getExpectedOutput().trim();
                String actual = outputRun.trim();

                boolean passed = expected.equals(actual);
                if (!passed)
                    allPassed = false;

                // Ghi nhận kết quả từng test case
                output.append("Test case: \n")
                        .append("Input:\n").append(testCase.getInput()).append("\n")
                        .append("Expected:\n").append(expected).append("\n")
                        .append("Actual:\n").append(actual).append("\n")
                        .append(passed ? "✅ Passed\n\n" : "❌ Failed\n\n");
            }

            String status = allPassed ? "SUCCESS" : "FAIL";
            return new JudgeRunResponseDTO(output.toString(), status, "");

        } catch (IOException | InterruptedException e) {
            return new JudgeRunResponseDTO("", "ERROR", e.getMessage());
        } catch (DockerCodeExecutionUtil.CompilationException e) {
            return new JudgeRunResponseDTO(e.getOutput(), "COMPILATION_ERROR", "Lỗi biên dịch");
        } catch (RuntimeException e) {
            return new JudgeRunResponseDTO("", "ERROR", e.getMessage());
        } finally {
            // Dọn dẹp thư mục
            try {
                DockerCodeExecutionUtil.deleteDirectoryRecursively(workingDir);
            } catch (Exception e) {
                // Bỏ qua lỗi khi dọn dẹp
            }
        }
    }

    private Set<ExerciseTestCasesDTO> getListExerciseTestCase(JudgeRequestDTO request) {
        return exerciseTestCaseMapper.toDto(exerciseTestCaseRepository.getExerciseTestCasesDTOByExerciseID(request.getExerciseID()));
    }

    @Override
    public CodingSubmissionResponseDTO submitUserCode(JudgeRequestDTO request, String language) {
        Set<ExerciseTestCasesDTO> exerciseTestCases = getListExerciseTestCase(request);

        String userName = UserContext.getUsername();
        UUID userId = userService.findUserIDByUserName(userName);

        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        String folderName = userName + "-ex" + request.getExerciseID() + "-" + timestamp;

        Path workingDir = Paths.get("Code_Dir", folderName);

        // default response values
        int passed = 0;
        int total = (exerciseTestCases == null) ? 0 : exerciseTestCases.size();
        int score = 0;

        // Nếu bạn chưa đo runtime/memory thật, có thể để null (hoặc 0)
        Integer runtimeMs = null;
        Integer memoryKb = null;

        SubmissionVerdict verdict = SubmissionVerdict.PENDING;

        try {
            Files.createDirectories(Paths.get("Code_Dir"));
            Files.createDirectories(workingDir);

            // TODO: tuỳ ngôn ngữ mà file name khác nhau (Main.java, main.py, main.cpp...)
            Path sourceFile = workingDir.resolve(resolveSourceFileName(language));
            Files.writeString(sourceFile, request.getSourceCode());

            DockerCodeExecutionUtil.compileInContainer(workingDir, language);

            // Không có test case -> chạy 1 lần
            if (total == 0) {
                DockerCodeExecutionUtil.runInContainer(workingDir, language, "");
                verdict = SubmissionVerdict.ACCEPTED; // hoặc tuỳ logic bạn muốn
                score = 0;
            } else {
                boolean allPassed = true;

                for (ExerciseTestCasesDTO tc : exerciseTestCases) {
                    String outputRun = DockerCodeExecutionUtil.runInContainer(
                            workingDir,
                            language,
                            tc.getInput()
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
            } catch (Exception ignored) {}
        }

        //  build DTO khớp entity
        return CodingSubmissionResponseDTO.builder()
                .exerciseID(request.getExerciseID())
                .userID(userId)
                .code(request.getSourceCode())
                .language(language)
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

    /**
     * Tùy language mà map ra đúng file name.
     * Nếu hiện tại bạn chỉ hỗ trợ Java thì cứ return "Main.java".
     */
    private static String resolveSourceFileName(String language) {
        if (language == null) return "Main.java";
        return switch (language.toLowerCase()) {
            case "java" -> "Main.java";
            case "cpp", "c++" -> "main.cpp";
            case "c" -> "main.c";
            case "python", "py" -> "main.py";
            case "javascript", "js" -> "main.js";
            default -> "Main.java";
        };
    }

}