package com.example.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;

@Service
public class RdsSecretService {

    public String getSecret() {

        try (SecretsManagerClient client = SecretsManagerClient.builder()
                .region(Region.AP_SOUTH_1)
                .build()) {

            return client.getSecretValue(
                    GetSecretValueRequest.builder()
                            .secretId("hello-eks/rds")
                            .build()
            ).secretString();
        }
    }
}