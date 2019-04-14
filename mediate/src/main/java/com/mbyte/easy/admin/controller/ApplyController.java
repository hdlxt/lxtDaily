package com.mbyte.easy.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.Apply;
import com.mbyte.easy.admin.service.IApplyService;
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
@RequestMapping("/admin/apply")
public class ApplyController extends BaseController  {

    private String prefix = "admin/apply/";

    @Autowired
    private IApplyService applyService;

    /**
    * 查询列表
    *
    * @param model
    * @param pageNo
    * @param pageSize
    * @param apply
    * @return
    */
    @RequestMapping
    public String index(Model model,@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,@RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize, String creTimeSpace, Apply apply) {
        Page<Apply> page = new Page<Apply>(pageNo, pageSize);
        QueryWrapper<Apply> queryWrapper = new QueryWrapper<Apply>();

        if(apply.getApplyId() != null  && !"".equals(apply.getApplyId() + "")) {
            queryWrapper = queryWrapper.like("apply_id",apply.getApplyId());
         }


        if(apply.getApplyedId() != null  && !"".equals(apply.getApplyedId() + "")) {
            queryWrapper = queryWrapper.like("applyed_id",apply.getApplyedId());
         }


        if(apply.getTypeId() != null  && !"".equals(apply.getTypeId() + "")) {
            queryWrapper = queryWrapper.like("type_id",apply.getTypeId());
         }


        if(apply.getCreTime() != null  && !"".equals(apply.getCreTime() + "")) {
            queryWrapper = queryWrapper.like("cre_time",apply.getCreTime());
         }


        if(apply.getApplyTime() != null  && !"".equals(apply.getApplyTime() + "")) {
            queryWrapper = queryWrapper.like("apply_time",apply.getApplyTime());
         }


        if(apply.getUserId() != null  && !"".equals(apply.getUserId() + "")) {
            queryWrapper = queryWrapper.like("user_id",apply.getUserId());
         }




        if(apply.getStatus() != null  && !"".equals(apply.getStatus() + "")) {
            queryWrapper = queryWrapper.like("status",apply.getStatus());
         }


        if(apply.getFilePath() != null  && !"".equals(apply.getFilePath() + "")) {
            queryWrapper = queryWrapper.like("file_path",apply.getFilePath());
         }


        if(apply.getReplyPath() != null  && !"".equals(apply.getReplyPath() + "")) {
            queryWrapper = queryWrapper.like("reply_path",apply.getReplyPath());
         }

        IPage<Apply> pageInfo = applyService.page(page, queryWrapper);
        model.addAttribute("creTimeSpace", creTimeSpace);
        model.addAttribute("searchInfo", apply);
        model.addAttribute("pageInfo", new PageInfo(pageInfo));
        return prefix+"apply-list";
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
    * @param apply
    * @return
    */
    @PostMapping("add")
    @ResponseBody
    public AjaxResult add(Apply apply){
        return toAjax(applyService.save(apply));
    }
    /**
    * 添加跳转页面
    * @return
    */
    @GetMapping("editBefore/{id}")
    public String editBefore(Model model,@PathVariable("id")Long id){
        model.addAttribute("apply",applyService.getById(id));
        return prefix+"edit";
    }
    /**
    * 添加
    * @param apply
    * @return
    */
    @PostMapping("edit")
    @ResponseBody
    public AjaxResult edit(Apply apply){
        return toAjax(applyService.updateById(apply));
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @GetMapping("delete/{id}")
    @ResponseBody
    public AjaxResult delete(@PathVariable("id") Long id){
        return toAjax(applyService.removeById(id));
    }
    /**
    * 批量删除
    * @param ids
    * @return
    */
    @PostMapping("deleteAll")
    @ResponseBody
    public AjaxResult deleteAll(@RequestBody List<Long> ids){
        return toAjax(applyService.removeByIds(ids));
    }

}

