package com.mbyte.easy.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.Back;
import com.mbyte.easy.admin.service.IBackService;
import com.mbyte.easy.common.controller.BaseController;
import com.mbyte.easy.common.web.AjaxResult;
import com.mbyte.easy.util.FileUtil;
import com.mbyte.easy.util.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
@RequestMapping("/admin/back")
public class BackController extends BaseController  {

    private String prefix = "admin/back/";

    @Autowired
    private IBackService backService;

    /**
    * 查询列表
    *
    * @param model
    * @param pageNo
    * @param pageSize
    * @param back
    * @return
    */
    @RequestMapping
    public String index(Model model,@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,@RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize, String creTimeSpace, Back back) {
        Page<Back> page = new Page<Back>(pageNo, pageSize);
        QueryWrapper<Back> queryWrapper = new QueryWrapper<Back>();

        if(back.getName() != null  && !"".equals(back.getName() + "")) {
            queryWrapper = queryWrapper.like("name",back.getName());
         }


        if(back.getImg() != null  && !"".equals(back.getImg() + "")) {
            queryWrapper = queryWrapper.like("img",back.getImg());
         }


        if(back.getCreTime() != null  && !"".equals(back.getCreTime() + "")) {
            queryWrapper = queryWrapper.like("cre_time",back.getCreTime());
         }

        IPage<Back> pageInfo = backService.page(page, queryWrapper);
        model.addAttribute("creTimeSpace", creTimeSpace);
        model.addAttribute("searchInfo", back);
        model.addAttribute("pageInfo", new PageInfo(pageInfo));
        return prefix+"back-list";
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
    * @param back
    * @return
    */
    @PostMapping("add")
    @ResponseBody
    public AjaxResult add(Back back,@RequestParam(required = false) MultipartFile file){
        if(file != null){
            back.setImg(FileUtil.uploadSuffixPath+FileUtil.uploadFile(file));
        }
        back.setCreTime(LocalDateTime.now());
        return toAjax(backService.save(back));
    }
    /**
    * 添加跳转页面
    * @return
    */
    @GetMapping("editBefore/{id}")
    public String editBefore(Model model,@PathVariable("id")Long id){
        model.addAttribute("back",backService.getById(id));
        return prefix+"edit";
    }
    /**
    * 添加
    * @param back
    * @return
    */
    @PostMapping("edit")
    @ResponseBody
    public AjaxResult edit(Back back,@RequestParam(required = false) MultipartFile file){
        if(file != null){
            back.setImg(FileUtil.uploadSuffixPath+FileUtil.uploadFile(file));
        }
        return toAjax(backService.updateById(back));
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @GetMapping("delete/{id}")
    @ResponseBody
    public AjaxResult delete(@PathVariable("id") Long id){
        return toAjax(backService.removeById(id));
    }
    /**
    * 批量删除
    * @param ids
    * @return
    */
    @PostMapping("deleteAll")
    @ResponseBody
    public AjaxResult deleteAll(@RequestBody List<Long> ids){
        return toAjax(backService.removeByIds(ids));
    }

}

