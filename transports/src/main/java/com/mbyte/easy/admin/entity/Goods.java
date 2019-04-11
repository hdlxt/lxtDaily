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
@TableName("t_goods")
public class Goods extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final int INIT = 0;
    public static final int ING = 1;
    public static final int END = 2;

    /**
     * 批次号
     */
    private String batchCode;

    /**
     * 货物名称
     */
    private String name;

    /**
     * 重量
     */
    private Double loadM;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 编号
     */
    private String code;

    /**
     * 注意事项
     */
    private String remark;

    /**
     * 货物内容
     */
    private String content;


}
