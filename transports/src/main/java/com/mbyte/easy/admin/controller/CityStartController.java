package com.mbyte.easy.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.CityStart;
import com.mbyte.easy.admin.service.ICityStartService;
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
@RequestMapping("/admin/cityStart")
public class CityStartController extends BaseController  {

    private String prefix = "admin/cityStart/";

    @Autowired
    private ICityStartService cityStartService;

    /**
    * 查询列表
    *
    * @param model
    * @param pageNo
    * @param pageSize
    * @param cityStart
    * @return
    */
    @RequestMapping
    public String index(Model model,@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,@RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize, CityStart cityStart) {
        Page<CityStart> page = new Page<CityStart>(pageNo, pageSize);
        QueryWrapper<CityStart> queryWrapper = new QueryWrapper<CityStart>();

        if(cityStart.getName() != null  && !"".equals(cityStart.getName() + "")) {
            queryWrapper = queryWrapper.like("name",cityStart.getName());
         }


        if(cityStart.getCode() != null  && !"".equals(cityStart.getCode() + "")) {
            queryWrapper = queryWrapper.like("code",cityStart.getCode());
         }


        if(cityStart.getRemark() != null  && !"".equals(cityStart.getRemark() + "")) {
            queryWrapper = queryWrapper.like("remark",cityStart.getRemark());
         }

        IPage<CityStart> pageInfo = cityStartService.page(page, queryWrapper);
        model.addAttribute("searchInfo", cityStart);
        model.addAttribute("pageInfo", new PageInfo(pageInfo));
        return prefix+"cityStart-list";
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
    * @param cityStart
    * @return
    */
    @PostMapping("add")
    @ResponseBody
    public AjaxResult add(CityStart cityStart){
        if(cityStartService.getOne(new QueryWrapper<CityStart>().lambda().eq(CityStart::getCode,cityStart.getCode())) != null){
            return error("唯一编号已存在！");
        }
        return toAjax(cityStartService.save(cityStart));
    }
    /**
    * 添加跳转页面
    * @return
    */
    @GetMapping("editBefore/{id}")
    public String editBefore(Model model,@PathVariable("id")Long id){
        model.addAttribute("cityStart",cityStartService.getById(id));
        return prefix+"edit";
    }
    /**
    * 添加
    * @param cityStart
    * @return
    */
    @PostMapping("edit")
    @ResponseBody
    public AjaxResult edit(CityStart cityStart){
        CityStart cityStartDb = cityStartService.getById(cityStart.getId());
        if(!cityStartDb.getCode().equals(cityStart.getCode()) && cityStartService.getOne(new QueryWrapper<CityStart>().lambda().eq(CityStart::getCode,cityStart.getCode())) != null){
            return error("唯一编号已存在！");
        }
        return toAjax(cityStartService.updateById(cityStart));
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @GetMapping("delete/{id}")
    @ResponseBody
    public AjaxResult delete(@PathVariable("id") Long id){
        return toAjax(cityStartService.removeById(id));
    }
    /**
    * 批量删除
    * @param ids
    * @return
    */
    @PostMapping("deleteAll")
    @ResponseBody
    public AjaxResult deleteAll(@RequestBody List<Long> ids){
        return toAjax(cityStartService.removeByIds(ids));
    }

}

