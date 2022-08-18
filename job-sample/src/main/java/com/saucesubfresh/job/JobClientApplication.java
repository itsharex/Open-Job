package com.saucesubfresh.job;

import com.saucesubfresh.rpc.server.annotation.EnableOpenRpcServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lijunping on 2022/2/25
 */
@EnableOpenRpcServer
@SpringBootApplication
public class JobClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobClientApplication.class, args);
    }
}
