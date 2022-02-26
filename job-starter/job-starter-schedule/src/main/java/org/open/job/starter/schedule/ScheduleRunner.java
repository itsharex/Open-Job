package org.open.job.starter.schedule;

import lombok.extern.slf4j.Slf4j;
import org.open.job.starter.schedule.core.ScheduleTaskManage;
import org.open.job.starter.schedule.executor.ScheduleTaskExecutor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author lijunping on 2022/1/21
 */
@Slf4j
@Component
public class ScheduleRunner implements SmartInitializingSingleton, DisposableBean {

    @Autowired
    private ScheduleTaskManage scheduleTaskManage;

    @Autowired
    protected ScheduleTaskExecutor scheduleTaskExecutor;

    private volatile boolean threadToStop = false;
    private static final long INTERVAL_TIME = 1000;
    private Thread thread;

    @Override
    public void afterSingletonsInstantiated() {
        log.info("init schedulerTask success.");
        init();
    }

    @Override
    public void destroy() {
        threadToStop = true;
        stopThread(thread);
        log.info("JobSchedule stop success");
    }

    /**
     * 每次轮训会间隔1s
     */
    private void init(){
        thread = new Thread(() -> {
            while (!threadToStop) {
                threadSleep();
                try {
                    trigger();
                } catch (Exception e) {
                    if (!threadToStop) {
                        log.error("JobSchedule#ringThread error:{}", e.getMessage());
                    }
                }
            }
            log.info("JobSchedule#ringThread stop");
        });
        thread.setDaemon(true);
        thread.setName("JobSchedule#ringThread");
        thread.start();
    }

    private void trigger(){
        List<Long> scheduleTaskList = scheduleTaskManage.getScheduleTaskList();
        if (CollectionUtils.isEmpty(scheduleTaskList)){
            return;
        }
        scheduleTaskExecutor.execute(scheduleTaskList);
    }

    /**
     * 线程随眠
     */
    private void threadSleep(){
        try {
            TimeUnit.MILLISECONDS.sleep(INTERVAL_TIME - System.currentTimeMillis() % 1000);
        } catch (InterruptedException e) {
            if (!threadToStop) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 停止线程
     * @param thread 要停止的线程
     */
    private void stopThread(Thread thread){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }

        if (thread.getState() == Thread.State.TERMINATED){
            return;
        }

        // interrupt and wait
        thread.interrupt();
        try {
            thread.join();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }
}
