package com.mbyte.easy.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.Law;
import com.mbyte.easy.admin.service.ILawService;
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
@RequestMapping("/admin/law")
public class LawController extends BaseController  {

    private String prefix = "admin/law/";

    @Autowired
    private ILawService lawService;

    /**
    * 查询列表
    *
    * @param model
    * @param pageNo
    * @param pageSize
    * @param law
    * @return
    */
    @RequestMapping
    public String index(Model model,@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,@RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize, String creTimeSpace, Law law) {
        Page<Law> page = new Page<Law>(pageNo, pageSize);
        QueryWrapper<Law> queryWrapper = new QueryWrapper<Law>();

        if(law.getTitle() != null  && !"".equals(law.getTitle() + "")) {
            queryWrapper = queryWrapper.like("title",law.getTitle());
         }


        if(law.getAuthor() != null  && !"".equals(law.getAuthor() + "")) {
            queryWrapper = queryWrapper.like("author",law.getAuthor());
         }


        if(law.getCreTime() != null  && !"".equals(law.getCreTime() + "")) {
            queryWrapper = queryWrapper.like("cre_time",law.getCreTime());
         }


        if(law.getContent() != null  && !"".equals(law.getContent() + "")) {
            queryWrapper = queryWrapper.like("content",law.getContent());
         }

        IPage<Law> pageInfo = lawService.page(page, queryWrapper);
        model.addAttribute("creTimeSpace", creTimeSpace);
        model.addAttribute("searchInfo", law);
        model.addAttribute("pageInfo", new PageInfo(pageInfo));
        return prefix+"law-list";
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
    * @param law
    * @return
    */
    @PostMapping("add")
    @ResponseBody
    public AjaxResult add(Law law){
        return toAjax(lawService.save(law));
    }
    /**
    * 添加跳转页面
    * @return
    */
    @GetMapping("editBefore/{id}")
    public String editBefore(Model model,@PathVariable("id")Long id){
        model.addAttribute("law",lawService.getById(id));
        return prefix+"edit";
    }
    /**
    * 添加
    * @param law
    * @return
    */
    @PostMapping("edit")
    @ResponseBody
    public AjaxResult edit(Law law){
        return toAjax(lawService.updateById(law));
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @GetMapping("delete/{id}")
    @ResponseBody
    public AjaxResult delete(@PathVariable("id") Long id){
        return toAjax(lawService.removeById(id));
    }
    /**
    * 批量删除
    * @param ids
    * @return
    */
    @PostMapping("deleteAll")
    @ResponseBody
    public AjaxResult deleteAll(@RequestBody List<Long> ids){
        return toAjax(lawService.removeByIds(ids));
    }

}

