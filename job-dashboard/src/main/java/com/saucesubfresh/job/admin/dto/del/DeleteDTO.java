package com.saucesubfresh.job.admin.dto.del;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * 分类表
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-06-22 15:35:38
 */
@Data
public class DeleteDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "要删除的主键id集合不能为空")
    private List<Long> ids;

}
