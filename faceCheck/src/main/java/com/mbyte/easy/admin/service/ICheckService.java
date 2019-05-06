package com.mbyte.easy.admin.service;

import com.mbyte.easy.admin.entity.Check;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 *
 * @since 2019-04-21
 */
public interface ICheckService extends IService<Check> {

    void insertCheck(Check check);
}
