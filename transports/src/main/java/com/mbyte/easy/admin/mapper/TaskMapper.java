package com.mbyte.easy.admin.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.Task;
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
 * @since 2019-04-10
 */
public interface TaskMapper extends BaseMapper<Task> {
    @Select("<script>" +
            " select t.*,cs.name as startCity,ce.name as endCity,c.code as code  from t_task t" +
            " left join t_city_start cs on t.start_city_id = cs.id" +
            " left join t_city_end ce on t.start_city_id = ce.id" +
            " left join t_car c on t.car_id = c.id" +
            " where 1=1 " +
            " <if test='task.startCity != null'>" +
            " and cs.name like '%${task.startCity}%'" +
            "</if>" +
            " <if test='task.endCity != null'>" +
            " and ce.name like '%${task.endCity}%'" +
            "</if>" +
            " <if test='task.name != null'>" +
            " and t.name like '%${task.name}%'" +
            "</if>" +
            " order by task_date desc</script>")
    List<Task> listPage(@Param("task") Task task, Page<Task> page);
}
