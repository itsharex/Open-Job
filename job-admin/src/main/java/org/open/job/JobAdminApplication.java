package org.open.job;

import com.lightcode.rpc.server.annotation.EnableLightRpcServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lijunping on 2022/2/24
 */
@EnableLightRpcServer
@SpringBootApplication
public class JobAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobAdminApplication.class, args);
    }
}
