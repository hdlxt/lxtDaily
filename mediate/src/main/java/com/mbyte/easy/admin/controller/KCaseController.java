package com.mbyte.easy.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.KCase;
import com.mbyte.easy.admin.service.IKCaseService;
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
* @author 
* @since 2019-03-11
*/
@Controller
@RequestMapping("/admin/kCase")
public class KCaseController extends BaseController  {

    private String prefix = "admin/kCase/";

    @Autowired
    private IKCaseService kCaseService;

    /**
    * 查询列表
    *
    * @param model
    * @param pageNo
    * @param pageSize
    * @param kCase
    * @return
    */
    @RequestMapping
    public String index(Model model,@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,@RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize, String creTimeSpace, KCase kCase) {
        Page<KCase> page = new Page<KCase>(pageNo, pageSize);
        QueryWrapper<KCase> queryWrapper = new QueryWrapper<KCase>();

        if(kCase.getTitle() != null  && !"".equals(kCase.getTitle() + "")) {
            queryWrapper = queryWrapper.like("title",kCase.getTitle());
         }


        if(kCase.getAuthor() != null  && !"".equals(kCase.getAuthor() + "")) {
            queryWrapper = queryWrapper.like("author",kCase.getAuthor());
         }


        if(kCase.getCreTime() != null  && !"".equals(kCase.getCreTime() + "")) {
            queryWrapper = queryWrapper.like("cre_time",kCase.getCreTime());
         }


        if(kCase.getContent() != null  && !"".equals(kCase.getContent() + "")) {
            queryWrapper = queryWrapper.like("content",kCase.getContent());
         }

        IPage<KCase> pageInfo = kCaseService.page(page, queryWrapper);
        model.addAttribute("creTimeSpace", creTimeSpace);
        model.addAttribute("searchInfo", kCase);
        model.addAttribute("pageInfo", new PageInfo(pageInfo));
        return prefix+"kCase-list";
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
    * @param kCase
    * @return
    */
    @PostMapping("add")
    @ResponseBody
    public AjaxResult add(KCase kCase){
        return toAjax(kCaseService.save(kCase));
    }
    /**
    * 添加跳转页面
    * @return
    */
    @GetMapping("editBefore/{id}")
    public String editBefore(Model model,@PathVariable("id")Long id){
        model.addAttribute("kCase",kCaseService.getById(id));
        return prefix+"edit";
    }
    /**
    * 添加
    * @param kCase
    * @return
    */
    @PostMapping("edit")
    @ResponseBody
    public AjaxResult edit(KCase kCase){
        return toAjax(kCaseService.updateById(kCase));
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @GetMapping("delete/{id}")
    @ResponseBody
    public AjaxResult delete(@PathVariable("id") Long id){
        return toAjax(kCaseService.removeById(id));
    }
    /**
    * 批量删除
    * @param ids
    * @return
    */
    @PostMapping("deleteAll")
    @ResponseBody
    public AjaxResult deleteAll(@RequestBody List<Long> ids){
        return toAjax(kCaseService.removeByIds(ids));
    }

}

