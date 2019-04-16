package com.mbyte.easy.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.FenType;
import com.mbyte.easy.admin.service.IFenTypeService;
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
@RequestMapping("/admin/fenType")
public class FenTypeController extends BaseController  {

    private String prefix = "admin/fenType/";

    @Autowired
    private IFenTypeService fenTypeService;

    /**
    * 查询列表
    *
    * @param model
    * @param pageNo
    * @param pageSize
    * @param fenType
    * @return
    */
    @RequestMapping
    public String index(Model model,@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,@RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize, FenType fenType) {
        Page<FenType> page = new Page<FenType>(pageNo, pageSize);
        QueryWrapper<FenType> queryWrapper = new QueryWrapper<FenType>();

        if(fenType.getName() != null  && !"".equals(fenType.getName() + "")) {
            queryWrapper = queryWrapper.like("name",fenType.getName());
         }

        IPage<FenType> pageInfo = fenTypeService.page(page, queryWrapper);
        model.addAttribute("searchInfo", fenType);
        model.addAttribute("pageInfo", new PageInfo(pageInfo));
        return prefix+"fenType-list";
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
    * @param fenType
    * @return
    */
    @PostMapping("add")
    @ResponseBody
    public AjaxResult add(FenType fenType){
        return toAjax(fenTypeService.save(fenType));
    }
    /**
    * 添加跳转页面
    * @return
    */
    @GetMapping("editBefore/{id}")
    public String editBefore(Model model,@PathVariable("id")Long id){
        model.addAttribute("fenType",fenTypeService.getById(id));
        return prefix+"edit";
    }
    /**
    * 添加
    * @param fenType
    * @return
    */
    @PostMapping("edit")
    @ResponseBody
    public AjaxResult edit(FenType fenType){
        return toAjax(fenTypeService.updateById(fenType));
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @GetMapping("delete/{id}")
    @ResponseBody
    public AjaxResult delete(@PathVariable("id") Long id){
        return toAjax(fenTypeService.removeById(id));
    }
    /**
    * 批量删除
    * @param ids
    * @return
    */
    @PostMapping("deleteAll")
    @ResponseBody
    public AjaxResult deleteAll(@RequestBody List<Long> ids){
        return toAjax(fenTypeService.removeByIds(ids));
    }

}

