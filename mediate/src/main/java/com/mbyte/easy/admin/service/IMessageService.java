package com.mbyte.easy.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 留言表 服务类
 * </p>
 *
 * @author 
 * @since 2019-04-14
 */
public interface IMessageService extends IService<Message> {
    Page<Message> listPage(@Param("message") Message message, Page<Message> page);
}
