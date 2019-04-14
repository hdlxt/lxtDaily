package com.mbyte.easy.admin.service.impl;

import com.mbyte.easy.admin.entity.Message;
import com.mbyte.easy.admin.mapper.MessageMapper;
import com.mbyte.easy.admin.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 留言表 服务实现类
 * </p>
 *
 * @author 
 * @since 2019-04-14
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

}
