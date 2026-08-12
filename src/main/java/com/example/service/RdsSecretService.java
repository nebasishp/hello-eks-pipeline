package com.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;

@Service
public class RdsSecretService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonNode getSecret() {

        try (SecretsManagerClient client =
                     SecretsManagerClient.builder()
                             .region(Region.AP_SOUTH_1)
                             .build()) {

            String secretString = client.getSecretValue(
                    GetSecretValueRequest.builder()
                            .secretId("hello-eks/rds")
                            .build()
            ).secretString();

            return objectMapper.readTree(secretString);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Unable to read RDS secret from Secrets Manager", e);
        }
    }
}