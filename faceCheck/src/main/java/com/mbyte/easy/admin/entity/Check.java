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
 * @since 2019-04-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_check")
public class Check extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    private String title;

    /**
     * 合照
     */
    private String img;

    /**
     * 创建时间
     */
    private LocalDateTime creTime;


}
