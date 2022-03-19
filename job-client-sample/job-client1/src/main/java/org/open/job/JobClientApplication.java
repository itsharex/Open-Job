package org.open.job;

import com.lightcode.rpc.client.annotation.EnableLightRpcClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author lijunping on 2022/2/25
 */
@EnableLightRpcClient
@SpringBootApplication
@ComponentScan({"org.open.job.**", "com.lightcode.**"})
public class JobClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobClientApplication.class, args);
    }
}
