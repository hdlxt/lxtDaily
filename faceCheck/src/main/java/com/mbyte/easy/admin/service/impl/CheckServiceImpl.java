package com.mbyte.easy.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.Check;
import com.mbyte.easy.admin.entity.CheckDetail;
import com.mbyte.easy.admin.mapper.CheckMapper;
import com.mbyte.easy.admin.service.ICheckService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 *
 * @since 2019-04-21
 */
@Service
public class CheckServiceImpl extends ServiceImpl<CheckMapper, Check> implements ICheckService {

    @Override
    public void insertCheck(Check check) {
        this.baseMapper.insertCheck(check);
    }


}
