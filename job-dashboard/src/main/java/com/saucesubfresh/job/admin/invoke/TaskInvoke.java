package com.saucesubfresh.job.admin.invoke;

import java.util.List;

/**
 * @author lijunping on 2022/8/18
 */
public interface TaskInvoke {

    /**
     * 调用远程服务
     *
     * @param taskList
     */
    void invoke(List<Long> taskList);
}
