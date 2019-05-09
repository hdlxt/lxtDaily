package com.mbyte.easy.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mbyte.easy.common.entity.BaseEntity;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 照片表
 * </p>
 *
 * @author 
 * @since 2019-05-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_photos")
public class Photos extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;

    /**
     * 照片
     */
    private String img;

    /**
     * 所属相册
     */
    private Long albumId;

    /**
     * 创建时间
     */
    private LocalDateTime creTime;

    /**
     * 背景
     */
    private Long backId;

    /**
     * 标记时间
     */
    private LocalDateTime tagTime;

    /**
     * 标记内容
     */
    private String tagContent;


}
