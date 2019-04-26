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
 * @since 2019-04-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_user_score")
public class UserScore extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户
     */
    private Long userId;

    /**
     * app
     */
    private Long appId;

    /**
     * 评分
     */
    private Integer userScore;

    /**
     * 备注
     */
    private String remark;


}
