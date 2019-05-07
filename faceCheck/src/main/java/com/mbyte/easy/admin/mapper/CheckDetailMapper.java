package com.mbyte.easy.admin.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.CheckDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 *
 * @since 2019-04-21
 */
public interface CheckDetailMapper extends BaseMapper<CheckDetail> {
    @Select("<script>SELECT cd.*,c.title as title,u.username as username FROM t_check_detail cd" +
            " left join t_check c on  cd.check_id = c.id" +
            " left join sys_user u on  cd.user_id = u.id" +
            " where 1=1" +
            "<if test='checkDetail.userName != null'> " +
            " and u.username like '%${checkDetail.userName}%'" +
            "</if>" +
            "<if test='checkDetail.title != null'> " +
            " and c.title like '%${checkDetail.title}%'" +
            "</if>" +
            "</script>")
    List<CheckDetail> listPage(CheckDetail checkDetail, Page<CheckDetail> page);

}
