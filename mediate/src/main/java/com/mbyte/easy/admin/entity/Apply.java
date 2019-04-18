package com.mbyte.easy.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mbyte.easy.common.entity.BaseEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 调节申请表
 * </p>
 *
 * @author 
 * @since 2019-04-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_apply")
public class Apply extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 申请人主键
     */
    private Long applyId;

    /**
     * 被申请人主键
     */
    private Long applyedId;

    /**
     * 纠纷类型
     */
    private Integer typeId;

    /**
     * 申请时间
     */
    private LocalDateTime creTime;

    /**
     * 申请调解日期
     */
    private LocalDate applyTime;

    /**
     * 调解员主键
     */
    private Long userId;

    /**
     * 进度百分比
     */
    private Integer  percent;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 申请资料
     */
    private String filePath;

    /**
     * 处理信息资料
     */
    private String replyPath;


}
