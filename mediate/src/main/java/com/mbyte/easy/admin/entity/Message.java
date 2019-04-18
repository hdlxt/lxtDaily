package com.mbyte.easy.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mbyte.easy.common.entity.BaseEntity;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 留言表
 * </p>
 *
 * @author 
 * @since 2019-04-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_message")
public class Message extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 留言者主键
     */
    private Long userId;
    @TableField(exist = false)
    private String userName;

    /**
     * 留言内容
     */
    private String message;
    private String mTitle;


    /**
     * 回复内容
     */
    private String reply;

    /**
     * 回复者主键
     */
    private Long replyUserId;
    @TableField(exist = false)
    private String replyUserName;

    /**
     * 留言时间
     */
    private LocalDateTime creTime;

    /**
     * 回复时间
     */
    private LocalDateTime upTime;


}
