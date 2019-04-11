package com.mbyte.easy.admin.service.impl;

import com.mbyte.easy.admin.entity.Task;
import com.mbyte.easy.admin.mapper.TaskMapper;
import com.mbyte.easy.admin.service.ITaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 *
 * @since 2019-04-10
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements ITaskService {

}
