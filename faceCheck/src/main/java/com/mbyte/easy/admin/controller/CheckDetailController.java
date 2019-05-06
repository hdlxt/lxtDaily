package com.mbyte.easy.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.CheckDetail;
import com.mbyte.easy.admin.service.ICheckDetailService;
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
@RequestMapping("/admin/checkDetail")
public class CheckDetailController extends BaseController  {

    private String prefix = "admin/checkDetail/";

    @Autowired
    private ICheckDetailService checkDetailService;

    /**
    * 查询列表
    *
    * @param model
    * @param pageNo
    * @param pageSize
    * @param checkDetail
    * @return
    */
    @RequestMapping
    public String index(Model model,@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,@RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize, CheckDetail checkDetail) {
        Page<CheckDetail> page = new Page<CheckDetail>(pageNo, pageSize);
        QueryWrapper<CheckDetail> queryWrapper = new QueryWrapper<CheckDetail>();

        if(checkDetail.getCheckId() != null  && !"".equals(checkDetail.getCheckId() + "")) {
            queryWrapper = queryWrapper.like("check_id",checkDetail.getCheckId());
         }


        if(checkDetail.getUserId() != null  && !"".equals(checkDetail.getUserId() + "")) {
            queryWrapper = queryWrapper.like("user_id",checkDetail.getUserId());
         }


        if(checkDetail.getStatus() != null  && !"".equals(checkDetail.getStatus() + "")) {
            queryWrapper = queryWrapper.like("status",checkDetail.getStatus());
         }

        IPage<CheckDetail> pageInfo = checkDetailService.page(page, queryWrapper);
        model.addAttribute("searchInfo", checkDetail);
        model.addAttribute("pageInfo", new PageInfo(pageInfo));
        return prefix+"checkDetail-list";
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
    * @param checkDetail
    * @return
    */
    @PostMapping("add")
    @ResponseBody
    public AjaxResult add(CheckDetail checkDetail){
        return toAjax(checkDetailService.save(checkDetail));
    }
    /**
    * 添加跳转页面
    * @return
    */
    @GetMapping("editBefore/{id}")
    public String editBefore(Model model,@PathVariable("id")Long id){
        model.addAttribute("checkDetail",checkDetailService.getById(id));
        return prefix+"edit";
    }
    /**
    * 添加
    * @param checkDetail
    * @return
    */
    @PostMapping("edit")
    @ResponseBody
    public AjaxResult edit(CheckDetail checkDetail){
        return toAjax(checkDetailService.updateById(checkDetail));
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @GetMapping("delete/{id}")
    @ResponseBody
    public AjaxResult delete(@PathVariable("id") Long id){
        return toAjax(checkDetailService.removeById(id));
    }
    /**
    * 批量删除
    * @param ids
    * @return
    */
    @PostMapping("deleteAll")
    @ResponseBody
    public AjaxResult deleteAll(@RequestBody List<Long> ids){
        return toAjax(checkDetailService.removeByIds(ids));
    }

}

