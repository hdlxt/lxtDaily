package com.mbyte.easy.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mbyte.easy.common.entity.BaseEntity;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 相册表
 * </p>
 *
 * @author 
 * @since 2019-05-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_album")
public class Album extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;

    /**
     * 分类名称
     */
    private String typeName;

    /**
     * 创建时间
     */
    private LocalDateTime creTime;

    /**
     * 相册编号
     */
    private String code;

    /**
     * 封面
     */
    private String img;

    /**
     * 背景
     */
    private Long backId;


}
