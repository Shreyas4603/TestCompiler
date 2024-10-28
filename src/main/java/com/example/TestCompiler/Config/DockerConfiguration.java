package com.example.TestCompiler.Config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.transport.DockerHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.dockerjava.okhttp.OkDockerHttpClient;
@Configuration
public class DockerConfiguration {
    @Bean
    DockerClient dockerClient() {
        DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .build();

        DockerHttpClient httpClient = new OkDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .build();

        DockerClient dockerClient = DockerClientBuilder.getInstance(config)
                .withDockerHttpClient(httpClient)
                .build();
        return dockerClient;
    }
}
