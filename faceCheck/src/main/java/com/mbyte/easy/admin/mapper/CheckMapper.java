package com.mbyte.easy.admin.mapper;

import com.mbyte.easy.admin.entity.Check;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectKey;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 *
 * @since 2019-04-21
 */
public interface CheckMapper extends BaseMapper<Check> {

    @Insert("insert into t_check(title,img,cre_time) " +
            " value(#{title},#{img},#{creTime})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = int.class)
    void insertCheck(Check check);
}
