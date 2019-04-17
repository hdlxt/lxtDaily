package com.mbyte.easy.admin.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.Apply;
import com.mbyte.easy.admin.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 留言表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2019-04-14
 */
public interface MessageMapper extends BaseMapper<Message> {


    @Select("<script>" +
            "select m.*,u1.name as userName,u2.name as replyUserName from t_message m" +
            " left join sys_user u1 on m.user_id = u1.id" +
            " left join sys_user u2 on m.reply_user_id = u2.id" +
            " where 1 = 1 " +
            "<if test='message.userName != null'>" +
            " and u1.name like '%${message.userName}%'" +
            "</if>" +
            "<if test='message.replyUserName != null'>" +
            " and u2.name like '%${message.replyUserName}%'" +
            "</if>" +
            "<if test='message.mTitle != null'>" +
            " and m.m_title like '%${message.mTitle}%'" +
            "</if>" +
            "<if test='message.userId != null'>" +
            " and m.user_id = ${message.userId}" +
            "</if>" +
            "</script>")
    List<Message> listPage(@Param("message") Message message, Page<Message> page);
}
