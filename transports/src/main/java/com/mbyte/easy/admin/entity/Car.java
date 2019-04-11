package com.mbyte.easy.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mbyte.easy.common.entity.BaseEntity;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 黄润宣
 * @since 2019-04-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_car")
public class Car extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 车辆编号
     */
    private String code;

    /**
     * 车辆类型
     */
    private String type;

    /**
     * 载重量
     */
    private Double loadMax;

    /**
     * 牌照
     */
    private String pai;

    /**
     * 上牌时间
     */
    private LocalDateTime paiTime;

    /**
     * 司机姓名
     */
    private String name;

    /**
     * 联系手机
     */
    private String tel;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 使用状态
     */
    private Integer status;

    /**
     * 已载重
     */
    private Double loadUse;


}
