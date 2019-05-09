package com.mbyte.easy.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.UserScore;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 *
 * @since 2019-04-26
 */
public interface IUserScoreService extends IService<UserScore> {
    Page<UserScore> listPage(UserScore userScore, Page<UserScore> page);
}
