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

    /**
     * Biên dịch code (nếu cần) trong Docker container
     */
    public static void compileInContainer(Path workingDir, String language) throws IOException, InterruptedException {
        List<String> dockerCommand = new ArrayList<>();
        String dockerImage = getDockerImage(language);

        switch (language.toLowerCase()) {
            case "java":
                dockerCommand = Arrays.asList("docker", "run", "--rm",
                        "--network", "none",
                        "--memory=" + MEMORY_LIMIT_MB + "m",
                        "--cpus=0.5",
                        "-v", workingDir.toAbsolutePath() + ":/app:Z",
                        "-w", "/app",
                        dockerImage,
                        "javac", "Main.java");
                break;
            case "cpp":
                dockerCommand = Arrays.asList("docker", "run", "--rm",
                        "--network", "none",
                        "--memory=" + MEMORY_LIMIT_MB + "m",
                        "--cpus=0.5",
                        "-v", workingDir.toAbsolutePath() + ":/app:Z",
                        "-w", "/app",
                        dockerImage,
                        "g++", "main.cpp", "-o", "main");
                break;
            case "csharp":
                dockerCommand = Arrays.asList("docker", "run", "--rm",
                        "--network", "none",
                        "--memory=" + MEMORY_LIMIT_MB + "m",
                        "--cpus=0.5",
                        "-v", workingDir.toAbsolutePath() + ":/app:Z",
                        "-w", "/app",
                        dockerImage,
                        "mcs", "-out:Main.exe", "Main.cs");
                break;
            case "go":
                dockerCommand = Arrays.asList("docker", "run", "--rm",
                        "--network", "none",
                        "--memory=" + MEMORY_LIMIT_MB + "m",
                        "--cpus=0.5",
                        "-v", workingDir.toAbsolutePath() + ":/app:Z",
                        "-w", "/app",
                        dockerImage,
                        "go", "build", "-o", "main", "main.go");
                break;
            case "python":
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

        List<String> command = new ArrayList<>();
        switch (language.toLowerCase()) {
            case "java":
                command = Arrays.asList("java", "Main");
                break;
            case "cpp":
            case "go":
                command = Arrays.asList("./main");
                break;
            case "csharp":
                command = Arrays.asList("mono", "Main.exe");
                break;
            case "python":
                command = Arrays.asList("python3", "main.py");
                break;
            default:
                throw new RuntimeException("Ngôn ngữ chưa được hỗ trợ: " + language);
        }

        List<String> dockerCommand = new ArrayList<>(Arrays.asList(
                "docker", "run", "--rm", "--name", containerId,
                "--network", "none",
                "--memory=" + MEMORY_LIMIT_MB + "m",
                "--cpus=0.5",
                "-v", workingDir.toAbsolutePath() + ":/app:Z",
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
            return "⏰ Chạy quá thời gian (" + EXECUTION_TIMEOUT_SECONDS + " giây)";
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    /**
     * Xác định Docker image cho từng ngôn ngữ
     */
    private static String getDockerImage(String language) {
        switch (language.toLowerCase()) {
            case "java":
                return "java-runner:latest";
            case "python":
                return "python-runner:latest";
            case "cpp":
                return "cpp-runner:latest";
            case "csharp":
                return "csharp-runner:latest";
            case "go":
                return "go-runner:latest";
            default:
                throw new RuntimeException("Chưa có image cho ngôn ngữ: " + language);
        }
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
