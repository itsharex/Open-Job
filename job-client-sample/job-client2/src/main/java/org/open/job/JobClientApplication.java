package org.open.job;

import org.open.job.starter.client.annotation.EnableJobClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lijunping on 2022/2/25
 */
@EnableJobClient
@SpringBootApplication
public class JobClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobClientApplication.class, args);
    }
}
