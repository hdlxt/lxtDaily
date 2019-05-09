package com.mbyte.easy.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.UserScore;
import com.mbyte.easy.admin.mapper.UserScoreMapper;
import com.mbyte.easy.admin.service.IUserScoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 *
 * @since 2019-04-26
 */
@Service
public class UserScoreServiceImpl extends ServiceImpl<UserScoreMapper, UserScore> implements IUserScoreService {

    @Override
    public Page<UserScore> listPage(UserScore userScore, Page<UserScore> page) {
        return page.setRecords(this.baseMapper.listPage(userScore,page));
    }

    @Override
    public List<UserScore> statistics() {
        return this.baseMapper.statistics();
    }
}
