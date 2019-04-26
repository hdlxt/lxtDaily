package com.mbyte.easy.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.App;
import com.mbyte.easy.admin.service.IAppService;
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
@RequestMapping("/admin/app")
public class AppController extends BaseController  {

    private String prefix = "admin/app/";

    @Autowired
    private IAppService appService;

    /**
    * 查询列表
    *
    * @param model
    * @param pageNo
    * @param pageSize
    * @param app
    * @return
    */
    @RequestMapping
    public String index(Model model,@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,@RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize, App app) {
        Page<App> page = new Page<App>(pageNo, pageSize);
        QueryWrapper<App> queryWrapper = new QueryWrapper<App>();

        if(app.getName() != null  && !"".equals(app.getName() + "")) {
            queryWrapper = queryWrapper.like("name",app.getName());
         }


        if(app.getImg() != null  && !"".equals(app.getImg() + "")) {
            queryWrapper = queryWrapper.like("img",app.getImg());
         }


        if(app.getType() != null  && !"".equals(app.getType() + "")) {
            queryWrapper = queryWrapper.like("type",app.getType());
         }


        if(app.getRemark() != null  && !"".equals(app.getRemark() + "")) {
            queryWrapper = queryWrapper.like("remark",app.getRemark());
         }

        IPage<App> pageInfo = appService.page(page, queryWrapper);
        model.addAttribute("searchInfo", app);
        model.addAttribute("pageInfo", new PageInfo(pageInfo));
        return prefix+"app-list";
    }

    /**
    * 添加跳转页面
    * @return
    */
    @GetMapping("addBefore")
    public String addBefore(){
        return prefix+"add";
    }
    /**
    * 添加
    * @param app
    * @return
    */
    @PostMapping("add")
    @ResponseBody
    public AjaxResult add(App app){
        return toAjax(appService.save(app));
    }
    /**
    * 添加跳转页面
    * @return
    */
    @GetMapping("editBefore/{id}")
    public String editBefore(Model model,@PathVariable("id")Long id){
        model.addAttribute("app",appService.getById(id));
        return prefix+"edit";
    }
    /**
    * 添加
    * @param app
    * @return
    */
    @PostMapping("edit")
    @ResponseBody
    public AjaxResult edit(App app){
        return toAjax(appService.updateById(app));
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @GetMapping("delete/{id}")
    @ResponseBody
    public AjaxResult delete(@PathVariable("id") Long id){
        return toAjax(appService.removeById(id));
    }
    /**
    * 批量删除
    * @param ids
    * @return
    */
    @PostMapping("deleteAll")
    @ResponseBody
    public AjaxResult deleteAll(@RequestBody List<Long> ids){
        return toAjax(appService.removeByIds(ids));
    }

}

