package com.mbyte.easy.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.Goods;
import com.mbyte.easy.admin.entity.Task;
import com.mbyte.easy.admin.service.*;
import com.mbyte.easy.common.controller.BaseController;
import com.mbyte.easy.common.web.AjaxResult;
import com.mbyte.easy.util.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
* <p>
* 前端控制器
* </p>
*
* @since 2019-03-11
*/
@Controller
@RequestMapping("/admin/task")
public class TaskController extends BaseController  {

    private String prefix = "admin/task/";

    @Autowired
    private ITaskService taskService;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private ICarService carService;
    @Autowired
    private ICityStartService cityStartService;
    @Autowired
    private ICityEndService cityEndService;
    /**
    * 查询列表
    *
    * @param model
    * @param pageNo
    * @param pageSize
    * @param task
    * @return
    */
    @RequestMapping
    public String index(Model model,@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,@RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize, Task task) {
        Page<Task> page = new Page<Task>(pageNo, pageSize);
        model.addAttribute("searchInfo", task);
        model.addAttribute("pageInfo", new PageInfo(taskService.listPage(task, page)));
        return prefix+"task-list";
    }

    /**
    * 添加跳转页面
    * @return
    */
    @GetMapping("addBefore")
    public String addBefore(Model model){
        model.addAttribute("cityStarts",cityStartService.list());
        model.addAttribute("cityEnds",cityEndService.list());
        model.addAttribute("goods",goodsService.list(new QueryWrapper<Goods>().lambda().eq(Goods::getStatus,0).groupBy(Goods::getBatchCode)));
        return prefix+"add";
    }
    /**
    * 添加
    * @param task
    * @return
    */
    @PostMapping("add")
    @ResponseBody
    public AjaxResult add(Task task){
        return toAjax(taskService.save(task));
    }
    /**
    * 添加跳转页面
    * @return
    */
    @GetMapping("editBefore/{id}")
    public String editBefore(Model model,@PathVariable("id")Long id){
        model.addAttribute("task",taskService.getById(id));
        model.addAttribute("cityStarts",cityStartService.list());
        model.addAttribute("cityEnds",cityEndService.list());
        model.addAttribute("goods",goodsService.list(new QueryWrapper<Goods>().lambda().eq(Goods::getStatus,0).groupBy(Goods::getBatchCode)));
        return prefix+"edit";
    }
    /**
    * 添加
    * @param task
    * @return
    */
    @PostMapping("edit")
    @ResponseBody
    public AjaxResult edit(Task task){
        return toAjax(taskService.updateById(task));
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @GetMapping("delete/{id}")
    @ResponseBody
    public AjaxResult delete(@PathVariable("id") Long id){
        return toAjax(taskService.removeById(id));
    }

    /**
     * 调度操作
     * @param id
     * @param status
     * @return
     */
    @GetMapping("diao/{id}/{status}")
    @ResponseBody
    public AjaxResult diao(@PathVariable("id") Long id,@PathVariable("status") int status){
        return toAjax(taskService.update(new UpdateWrapper<Task>().lambda().eq(Task::getId,id).set(Task::getStatus,status)));
    }
    /**
    * 批量删除
    * @param ids
    * @return
    */
    @PostMapping("deleteAll")
    @ResponseBody
    public AjaxResult deleteAll(@RequestBody List<Long> ids){
        return toAjax(taskService.removeByIds(ids));
    }


}

