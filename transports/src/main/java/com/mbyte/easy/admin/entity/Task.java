package com.mbyte.easy.admin.entity;

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

    /**
     * 起点城市
     */
    private Long startCityId;

    /**
     * 终点城市
     */
    private Long endCityId;

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


}
