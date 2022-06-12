package com.saucesubfresh.job.core.collector;

/**
 * @author lijunping on 2022/1/19
 */
public interface OpenJobHandler {

    /**
     * Execute jobHandler
     *
     * @param params params
     */
    void handler(String params);
}
