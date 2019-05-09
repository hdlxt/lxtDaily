package com.mbyte.easy.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2019-04-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_app")
public class App extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;
    @TableField(exist = false)
    private Long scoreId;
    @TableField(exist = false)
    private Long userId;

    /**
     * 图标
     */
    private String img;

    /**
     * 类型
     */
    private String type;

    /**
     * 备注
     */
    private String remark;


}
