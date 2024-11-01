package com.example.TestCompiler.Services;

import com.example.TestCompiler.Models.CodeSubmission;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@Service
public class RunService {

    private final DockerClient dockerClient;

    @Autowired
    public RunService(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    public String compileAndRun(CodeSubmission submission) {
        String language = submission.getLanguage().toLowerCase();
        String code = submission.getCode();
        String testCases = "20"; // Assuming you have test cases in your CodeSubmission model
        String imageId;
        String codeFileName;
        String inputFileName = "input.txt"; // For test case inputs

        // Set Docker image and file name based on language
        switch (language) {
            case "python":
                imageId = "python-compiler";
                codeFileName = "main.py";
                break;
            case "cpp":
                imageId = "cpp-compiler";
                codeFileName = "main.cpp";
                break;
            case "java":
                imageId = "java-compiler";
                codeFileName = "Main.java";
                break;
            default:
                throw new IllegalArgumentException("Unsupported language: " + language);
        }

        File codeFile = null;
        File inputFile = null;
        try {
            // Save code and input test cases to files
            codeFile = saveToFile(codeFileName, code);


            // Create HostConfig with binds for code and input files
            HostConfig hostConfig = HostConfig.newHostConfig()
                    .withBinds(
                            Bind.parse(codeFile.getAbsolutePath() + ":/app/" + codeFileName)
                    );

            // Create and start the container
            CreateContainerResponse container = dockerClient.createContainerCmd(imageId)
                    .withHostConfig(hostConfig)
                    .exec();

            dockerClient.startContainerCmd(container.getId()).exec();

            // Get logs (output from container execution)


            // Stop and remove container
            dockerClient.stopContainerCmd(container.getId()).exec();
            String result = getContainerLogs(dockerClient, container.getId());
            dockerClient.removeContainerCmd(container.getId()).exec();

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error executing code: " + e.getMessage();
        }
    }

    private File saveToFile(String fileName, String content) throws IOException {
        // Specify the base directory path explicitly, for example, using "Documents/docker_code_submissions"
        Path baseDir = Paths.get(System.getProperty("user.home"), "docker_code_submissions"); //for linux
        Files.createDirectories(baseDir); // Ensure the base directory exists

        Path tempDir = Files.createTempDirectory(baseDir, "code_submission");
        Path filePath = tempDir.resolve(fileName);

        System.out.println("file path : "+filePath);
        File file = filePath.toFile();
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        }
        catch (Exception e){
            System.out.printf("Error occured"+e.toString());
        }
        return file;
    }
    private String getContainerLogs(DockerClient dockerClient, String containerId) {
        StringBuilder logBuilder = new StringBuilder();
        try {
            dockerClient.logContainerCmd(containerId)
                    .withStdOut(true)
                    .withStdErr(true)
                    .exec(new ResultCallback.Adapter<Frame>() {
                        @Override
                        public void onNext(Frame frame) {
                            logBuilder.append(new String(frame.getPayload()));
                        }
                    }).awaitCompletion();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Log retrieval interrupted.";
        }
        return logBuilder.toString();
    }
}
