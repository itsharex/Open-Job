package org.open.job.starter.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;
import org.open.job.starter.server.handler.GRpcMessageHandler;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 爬虫服务端
 * 默认使用 gRpc 通信，如果有兴趣可更改为其他通信方式，例如 netty
 * @author lijunping on 2022/1/24
 */
@Slf4j
@Component
public class CrawlerServer implements InitializingBean, DisposableBean {

    private static final ExecutorService RPC_CRAWLER_EXECUTOR = Executors.newFixedThreadPool(1);

    private final ServerConfiguration configuration;

    private final GRpcMessageHandler bindableService;
    /**
     * The grpc server instance
     */
    private Server rpcServer;

    public CrawlerServer(ServerConfiguration configuration, GRpcMessageHandler bindableService){
        this.configuration = configuration;
        this.bindableService = bindableService;
    }

    /**
     * Build the grpc {@link Server} instance
     */
    private void buildServer() {
        ServerBuilder<?> serverBuilder = ServerBuilder.forPort(this.configuration.getServerPort());
        if (Objects.nonNull(this.bindableService)){
            serverBuilder.addService(this.bindableService);
        }
        this.rpcServer = serverBuilder.build();
    }

    /**
     * Startup grpc {@link Server}
     */
    public void startup() {
        try {
            this.rpcServer.start();
            log.info("CrawlerServer bind port : {}, startup successfully.", configuration.getServerPort());
            this.rpcServer.awaitTermination();
        } catch (Exception e) {
            log.error("CrawlerServer startup failed.", e);
        }
    }

    /**
     * Shutdown grpc {@link Server}
     */
    public void shutdown() {
        try {
            log.info("CrawlerServer shutting down.");
            this.rpcServer.shutdown();
            long waitTime = 100;
            long timeConsuming = 0;
            while (!this.rpcServer.isShutdown()) {
                log.info("CrawlerServer stopping....，total time consuming：{}", timeConsuming);
                timeConsuming += waitTime;
                Thread.sleep(waitTime);
            }
            log.info("CrawlerServer stop successfully.");
        } catch (Exception e) {
            log.error("CrawlerServer shutdown failed.", e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.buildServer();
        RPC_CRAWLER_EXECUTOR.execute(this::startup);
    }

    @Override
    public void destroy() throws Exception {
        this.shutdown();
        RPC_CRAWLER_EXECUTOR.shutdown();
    }
}
