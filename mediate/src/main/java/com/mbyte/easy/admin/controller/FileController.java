package com.mbyte.easy.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.File;
import com.mbyte.easy.admin.service.IFileService;
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
@RequestMapping("/admin/file")
public class FileController extends BaseController  {

    private String prefix = "admin/file/";

    @Autowired
    private IFileService fileService;

    /**
    * 查询列表
    *
    * @param model
    * @param pageNo
    * @param pageSize
    * @param file
    * @return
    */
    @RequestMapping
    public String index(Model model,@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,@RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize, File file) {
        Page<File> page = new Page<File>(pageNo, pageSize);
        QueryWrapper<File> queryWrapper = new QueryWrapper<File>();

        if(file.getName() != null  && !"".equals(file.getName() + "")) {
            queryWrapper = queryWrapper.like("name",file.getName());
         }


        if(file.getPath() != null  && !"".equals(file.getPath() + "")) {
            queryWrapper = queryWrapper.like("path",file.getPath());
         }

        IPage<File> pageInfo = fileService.page(page, queryWrapper);
        model.addAttribute("searchInfo", file);
        model.addAttribute("pageInfo", new PageInfo(pageInfo));
        return prefix+"file-list";
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
    * @param file
    * @return
    */
    @PostMapping("add")
    @ResponseBody
    public AjaxResult add(File file){
        return toAjax(fileService.save(file));
    }
    /**
    * 添加跳转页面
    * @return
    */
    @GetMapping("editBefore/{id}")
    public String editBefore(Model model,@PathVariable("id")Long id){
        model.addAttribute("file",fileService.getById(id));
        return prefix+"edit";
    }
    /**
    * 添加
    * @param file
    * @return
    */
    @PostMapping("edit")
    @ResponseBody
    public AjaxResult edit(File file){
        return toAjax(fileService.updateById(file));
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @GetMapping("delete/{id}")
    @ResponseBody
    public AjaxResult delete(@PathVariable("id") Long id){
        return toAjax(fileService.removeById(id));
    }
    /**
    * 批量删除
    * @param ids
    * @return
    */
    @PostMapping("deleteAll")
    @ResponseBody
    public AjaxResult deleteAll(@RequestBody List<Long> ids){
        return toAjax(fileService.removeByIds(ids));
    }

}

