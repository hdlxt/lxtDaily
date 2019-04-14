package com.mbyte.easy.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.Knowledge;
import com.mbyte.easy.admin.service.IKnowledgeService;
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
@RequestMapping("/admin/knowledge")
public class KnowledgeController extends BaseController  {

    private String prefix = "admin/knowledge/";

    @Autowired
    private IKnowledgeService knowledgeService;

    /**
    * 查询列表
    *
    * @param model
    * @param pageNo
    * @param pageSize
    * @param knowledge
    * @return
    */
    @RequestMapping
    public String index(Model model,@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,@RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize, String creTimeSpace, Knowledge knowledge) {
        Page<Knowledge> page = new Page<Knowledge>(pageNo, pageSize);
        QueryWrapper<Knowledge> queryWrapper = new QueryWrapper<Knowledge>();

        if(knowledge.getTitle() != null  && !"".equals(knowledge.getTitle() + "")) {
            queryWrapper = queryWrapper.like("title",knowledge.getTitle());
         }


        if(knowledge.getAuthor() != null  && !"".equals(knowledge.getAuthor() + "")) {
            queryWrapper = queryWrapper.like("author",knowledge.getAuthor());
         }


        if(knowledge.getCreTime() != null  && !"".equals(knowledge.getCreTime() + "")) {
            queryWrapper = queryWrapper.like("cre_time",knowledge.getCreTime());
         }


        if(knowledge.getContent() != null  && !"".equals(knowledge.getContent() + "")) {
            queryWrapper = queryWrapper.like("content",knowledge.getContent());
         }

        IPage<Knowledge> pageInfo = knowledgeService.page(page, queryWrapper);
        model.addAttribute("creTimeSpace", creTimeSpace);
        model.addAttribute("searchInfo", knowledge);
        model.addAttribute("pageInfo", new PageInfo(pageInfo));
        return prefix+"knowledge-list";
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
    * @param knowledge
    * @return
    */
    @PostMapping("add")
    @ResponseBody
    public AjaxResult add(Knowledge knowledge){
        return toAjax(knowledgeService.save(knowledge));
    }
    /**
    * 添加跳转页面
    * @return
    */
    @GetMapping("editBefore/{id}")
    public String editBefore(Model model,@PathVariable("id")Long id){
        model.addAttribute("knowledge",knowledgeService.getById(id));
        return prefix+"edit";
    }
    /**
    * 添加
    * @param knowledge
    * @return
    */
    @PostMapping("edit")
    @ResponseBody
    public AjaxResult edit(Knowledge knowledge){
        return toAjax(knowledgeService.updateById(knowledge));
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @GetMapping("delete/{id}")
    @ResponseBody
    public AjaxResult delete(@PathVariable("id") Long id){
        return toAjax(knowledgeService.removeById(id));
    }
    /**
    * 批量删除
    * @param ids
    * @return
    */
    @PostMapping("deleteAll")
    @ResponseBody
    public AjaxResult deleteAll(@RequestBody List<Long> ids){
        return toAjax(knowledgeService.removeByIds(ids));
    }

}

