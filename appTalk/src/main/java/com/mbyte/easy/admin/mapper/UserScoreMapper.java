package com.mbyte.easy.admin.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.UserScore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 *
 * @since 2019-04-26
 */
public interface UserScoreMapper extends BaseMapper<UserScore> {
    @Select("<script>SELECT us.*,a.name as appName,u.username as userName FROM t_user_score us" +
            " left join t_app a on  us.app_id = a.id" +
            " left join sys_user u on  us.user_id = u.id" +
            " where 1=1" +
            "<if test='userScore.userName != null'> " +
            " and u.username = ${userScore.userName}" +
            "</if>" +
            "<if test='userScore.appName != null'> " +
            " and a.name = ${userScore.appName}" +
            "</if>" +
            "<if test='userScore.userId != null'> " +
            " and us.user_id = ${userScore.userId}" +
            "</if>" +
            "</script>")
    List<UserScore> listPage(UserScore userScore, Page<UserScore> page);
}
