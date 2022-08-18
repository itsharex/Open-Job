package com.saucesubfresh.job.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.saucesubfresh.job.admin.entity.OpenJobReportDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lijunping on 2022/4/11
 */
@Repository
public interface OpenJobReportMapper extends BaseMapper<OpenJobReportDO> {

    default List<OpenJobReportDO> queryList(){
        return selectList(Wrappers.lambdaQuery());
    }
}
