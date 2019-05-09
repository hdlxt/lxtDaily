package com.mbyte.easy.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.App;
import com.mbyte.easy.admin.mapper.AppMapper;
import com.mbyte.easy.admin.service.IAppService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 *
 * @since 2019-04-26
 */
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements IAppService {

    @Override
    public Page<App> listPage(App app, Page<App> page) {
        return page.setRecords(this.baseMapper.listPage(app,page));
    }
}
