package org.open.job;

import com.lightcode.rpc.server.annotation.EnableLightRpcServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author lijunping on 2022/2/24
 */
@EnableLightRpcServer
@SpringBootApplication
@ComponentScan({"com.lightcode.**", "org.open.job.**"})
public class JobAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobAdminApplication.class, args);
    }
}
