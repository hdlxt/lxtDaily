package com.mbyte.easy.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.Task;
import com.mbyte.easy.admin.mapper.TaskMapper;
import com.mbyte.easy.admin.service.ITaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public Page<Task> listPage(Task task, Page<Task> page) {
        return page.setRecords(this.baseMapper.listPage(task,page));
    }
}
