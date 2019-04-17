package com.mbyte.easy.admin.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.Apply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2019-04-14
 */
public interface ApplyMapper extends BaseMapper<Apply> {

    @Select("<script>" +
            "select a.*,u1.name as applyName,u2.name as applyedName,u3.name as userName,ft.name as typeName from t_apply a" +
            " left join sys_user u1 on a.apply_id = u1.id" +
            " left join sys_user u2 on a.applyed_id = u2.id" +
            " left join sys_user u3 on a.user_id = u3.id" +
            " left join t_fen_type ft on a.type_id = ft.id" +
            " where 1 = 1 " +
            "<if test='apply.applyName != null'>" +
            " and u1.name like '%${apply.applyName}%'" +
            "</if>" +
            "<if test='apply.applyedName != null'>" +
            " and u2.name like '%${apply.applyedName}%'" +
            "</if>" +
            "<if test='apply.status != null'>" +
            " and a.status = ${apply.status}" +
            "</if>" +
            "<if test='apply.applyId != null'>" +
            " and (a.apply_id = ${apply.applyId} or a.applyed_id = ${apply.applyedId})" +
            "</if>" +
            "</script>")
    List<Apply> listPage(@Param("apply") Apply apply, Page<Apply> page);
}
