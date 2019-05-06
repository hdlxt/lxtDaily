package com.mbyte.easy.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mbyte.easy.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 *
 * @since 2019-04-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_check_detail")
public class CheckDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 考勤主键
     */
    private Long checkId;

    /**
     * 学生主键
     */
    private Long userId;

    /**
     * 状态
     */
    private Integer status;


}
