package com.saucesubfresh.job;

import com.saucesubfresh.rpc.client.annotation.EnableOpenRpcClient;
import com.saucesubfresh.starter.security.annotation.EnableSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author lijunping on 2022/2/24
 */
@EnableSecurity
@EnableScheduling
@EnableOpenRpcClient
@SpringBootApplication
public class JobDashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobDashboardApplication.class, args);
    }
}
