package com.mbyte.easy.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.Task;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 *
 * @since 2019-04-10
 */
public interface ITaskService extends IService<Task> {
    Page<Task> listPage(Task task, Page<Task> page);
}
