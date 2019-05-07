package com.mbyte.easy.admin.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.App;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mbyte.easy.admin.entity.UserScore;
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
public interface AppMapper extends BaseMapper<App> {

    @Select("<script>SELECT  a.*,us.id as scoreId FROM t_app a" +
            " left join t_user_score us on  a.id = us.app_id and us.user_id = ${app.userId} " +
            " where 1=1 " +
            "<if test='app.name != null'> " +
            " and a.name = ${app.name}" +
            "</if>" +
            "<if test='app.type != null'> " +
            " and a.type = ${app.type}" +
            "</if>" +
            "</script>")
    List<App> listPage(App app, Page<App> page);
}
