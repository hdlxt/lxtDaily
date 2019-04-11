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
 * @since 2019-04-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_city_end")
public class CityEnd extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 城市名
     */
    private String name;

    /**
     * 唯一编号
     */
    private String code;

    /**
     * 备注
     */
    private String remark;


}
