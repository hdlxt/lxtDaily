package com.mbyte.easy.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.CheckDetail;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 *
 * @since 2019-04-21
 */
public interface ICheckDetailService extends IService<CheckDetail> {
    Page<CheckDetail> listPage(CheckDetail checkDetail, Page<CheckDetail> page);
}
