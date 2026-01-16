package com.truongsonkmhd.unetistudy.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class DockerCodeExecutionUtil {
    private static final int EXECUTION_TIMEOUT_SECONDS = 10;
    private static final int MEMORY_LIMIT_MB = 256;

    private static final String JAVA_RUNNER_IMAGE = "java-runner:latest";
    private static final String PYTHON_RUNNER_IMAGE = "python-runner:latest";
    private static final String CPP_RUNNER_IMAGE = "cpp-runner:latest";
    private static final String CSHARP_RUNNER_IMAGE = "csharp-runner:latest";
    private static final String GO_RUNNER_IMAGE = "go-runner:latest";

    public static final String LANG_JAVA = "java";
    public static final String LANG_PYTHON = "python";
    public static final String LANG_CPP = "cpp";
    public static final String LANG_CSHARP = "csharp";
    public static final String LANG_GO = "go";
    /**
     * Biên dịch code (nếu cần) trong Docker container
     */
    public static void compileInContainer(Path workingDir, String language) throws IOException, InterruptedException {
        List<String> dockerCommand;
        String dockerImage = getDockerImage(language);

        switch (language.toLowerCase()) {
            case LANG_JAVA:
                dockerCommand = Arrays.asList("docker", "run", "--rm",
                        "--network", "none",
                        "--memory=" + MEMORY_LIMIT_MB + "m",
                        "--cpus=0.5",
                        "-v", workingDir.toAbsolutePath() + ":/app",
                        "-w", "/app",
                        dockerImage,
                        "javac", "Main.java");
                break;
            case LANG_CPP:
                dockerCommand = Arrays.asList("docker", "run", "--rm",
                        "--network", "none",
                        "--memory=" + MEMORY_LIMIT_MB + "m",
                        "--cpus=0.5",
                        "-v", workingDir.toAbsolutePath() + ":/app",
                        "-w", "/app",
                        dockerImage,
                        "g++", "main.cpp", "-o", "main");
                break;
            case LANG_CSHARP:
                dockerCommand = Arrays.asList("docker", "run", "--rm",
                        "--network", "none",
                        "--memory=" + MEMORY_LIMIT_MB + "m",
                        "--cpus=0.5",
                        "-v", workingDir.toAbsolutePath() + ":/app",
                        "-w", "/app",
                        dockerImage,
                        "mcs", "-out:Main.exe", "Main.cs");
                break;
            case LANG_GO:
                dockerCommand = Arrays.asList("docker", "run", "--rm",
                        "--network", "none",
                        "--memory=" + MEMORY_LIMIT_MB + "m",
                        "--cpus=0.5",
                        "-v", workingDir.toAbsolutePath() + ":/app",
                        "-w", "/app",
                        dockerImage,
                        "go", "build", "-o", "main", "main.go");
                break;
            case LANG_PYTHON:
                // Python không cần compile
                return;
            default:
                throw new RuntimeException("Ngôn ngữ chưa được hỗ trợ: " + language);
        }

        runDockerProcess(dockerCommand, "Lỗi biên dịch");
    }

    /**
     * Chạy code trong Docker container
     */
    public static String runInContainer(Path workingDir, String language, String input) throws IOException, InterruptedException {
        String containerId = "code-exec-" + UUID.randomUUID().toString().substring(0, 8);
        String dockerImage = getDockerImage(language);

        List<String> command = switch (language.toLowerCase()) {
            case LANG_JAVA -> Arrays.asList("java", "Main");
            case LANG_GO, LANG_CPP -> List.of("./main");
            case LANG_CSHARP-> Arrays.asList("mono", "Main.exe");
            case LANG_PYTHON -> Arrays.asList("python3", "main.py");
            default -> throw new RuntimeException("Ngôn ngữ chưa được hỗ trợ: " + language);
        };

        List<String> dockerCommand = new ArrayList<>(Arrays.asList(
                "docker", "run", "--rm", "--name", containerId,
                "--network", "none",
                "--memory=" + MEMORY_LIMIT_MB + "m",
                "--cpus=0.5",
                "-v", workingDir.toAbsolutePath() + ":/app",
                "-w", "/app",
                "-i",
                dockerImage
        ));
        dockerCommand.addAll(command);

        ProcessBuilder builder = new ProcessBuilder(dockerCommand);
        builder.redirectErrorStream(true);
        Process process = builder.start();

        if (input != null && !input.isEmpty()) {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
                writer.write(input);
                writer.flush();
            }
        }

        if (!process.waitFor(EXECUTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
            try {
                new ProcessBuilder("docker", "kill", containerId).start().waitFor();
            } catch (Exception ignored) {}
            process.destroyForcibly();
            return " Chạy quá thời gian (" + EXECUTION_TIMEOUT_SECONDS + " giây)";
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    /**
     * Xác định Docker image cho từng ngôn ngữ
     */
    private static String getDockerImage(String language) {
        return switch (language.toLowerCase()) {
            case LANG_JAVA-> JAVA_RUNNER_IMAGE;
            case LANG_PYTHON , "py" -> PYTHON_RUNNER_IMAGE;
            case LANG_CPP , "c++" -> CPP_RUNNER_IMAGE;
            case LANG_CSHARP , "cs" -> CSHARP_RUNNER_IMAGE;
            case LANG_GO -> GO_RUNNER_IMAGE;
            default -> throw new RuntimeException("Chưa có image cho ngôn ngữ: " + language);
        };
    }

    /**
     * Chạy docker command (compile)
     */
    private static void runDockerProcess(List<String> dockerCommand, String errorMessage) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder(dockerCommand);
        builder.redirectErrorStream(true);
        Process process = builder.start();

        if (!process.waitFor(EXECUTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
            process.destroyForcibly();
            throw new RuntimeException("⏰ " + errorMessage + " quá " + EXECUTION_TIMEOUT_SECONDS + " giây");
        }

        String output;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            output = reader.lines().collect(Collectors.joining("\n"));
        }

        if (process.exitValue() != 0) {
            throw new CompilationException(output);
        }
    }

    public static void deleteDirectoryRecursively(Path path) {
        try {
            if (Files.exists(path)) {
                Files.walk(path)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            }
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi xóa files", e);
        }
    }

    public static class CompilationException extends RuntimeException {
        private final String output;
        public CompilationException(String output) {
            super(output);
            this.output = output;
        }
        public String getOutput() { return output; }
    }
}
