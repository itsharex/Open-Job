package com.saucesubfresh.job;

import com.saucesubfresh.rpc.server.annotation.EnableOpenRpcServer;
import com.saucesubfresh.starter.security.annotation.EnableSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lijunping on 2022/2/24
 */
@EnableSecurity
@EnableOpenRpcServer
@SpringBootApplication
public class JobAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobAdminApplication.class, args);
    }
}