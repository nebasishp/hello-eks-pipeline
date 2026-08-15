package com.example;

import com.example.service.RdsSecretService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final RdsSecretService rdsSecretService;

    public HelloController(RdsSecretService rdsSecretService) {
        this.rdsSecretService = rdsSecretService;
    }

    @GetMapping("/")
    public String hello() {
        return "CANARY VERSION";
    }

    @GetMapping("/secret-test")
    public String secretTest() {
        rdsSecretService.getSecret();
        return "Secrets Manager access SUCCESS...";
    }
}