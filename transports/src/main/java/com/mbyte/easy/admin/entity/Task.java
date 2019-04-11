package com.mbyte.easy.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mbyte.easy.common.entity.BaseEntity;
import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 *
 * @since 2019-04-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_task")
public class Task extends BaseEntity {

    private static final long serialVersionUID = 1L;
    public static final int INIT = 0;
    public static final int ING = 1;
    public static final int END = 2;

    /**
     * 起点城市
     */
    private Long startCityId;
    @TableField(exist = false)
    private String startCity;

    /**
     * 终点城市
     */
    private Long endCityId;
    @TableField(exist = false)
    private String endCity;

    /**
     * 任务日期
     */
    private LocalDate taskDate;

    /**
     * 货物id
     */
    private String goodsBatch;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 使用车辆
     */
    private Long carId;
    @TableField(exist = false)
    private String code;


}
