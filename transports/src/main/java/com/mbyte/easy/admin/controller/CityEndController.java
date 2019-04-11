package com.mbyte.easy.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.CityEnd;
import com.mbyte.easy.admin.entity.CityStart;
import com.mbyte.easy.admin.service.ICityEndService;
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
@RequestMapping("/admin/cityEnd")
public class CityEndController extends BaseController  {

    private String prefix = "admin/cityEnd/";

    @Autowired
    private ICityEndService cityEndService;

    /**
    * 查询列表
    *
    * @param model
    * @param pageNo
    * @param pageSize
    * @param cityEnd
    * @return
    */
    @RequestMapping
    public String index(Model model,@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,@RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize, CityEnd cityEnd) {
        Page<CityEnd> page = new Page<CityEnd>(pageNo, pageSize);
        QueryWrapper<CityEnd> queryWrapper = new QueryWrapper<CityEnd>();

        if(cityEnd.getName() != null  && !"".equals(cityEnd.getName() + "")) {
            queryWrapper = queryWrapper.like("name",cityEnd.getName());
         }


        if(cityEnd.getCode() != null  && !"".equals(cityEnd.getCode() + "")) {
            queryWrapper = queryWrapper.like("code",cityEnd.getCode());
         }


        if(cityEnd.getRemark() != null  && !"".equals(cityEnd.getRemark() + "")) {
            queryWrapper = queryWrapper.like("remark",cityEnd.getRemark());
         }

        IPage<CityEnd> pageInfo = cityEndService.page(page, queryWrapper);
        model.addAttribute("searchInfo", cityEnd);
        model.addAttribute("pageInfo", new PageInfo(pageInfo));
        return prefix+"cityEnd-list";
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
    * @param cityEnd
    * @return
    */
    @PostMapping("add")
    @ResponseBody
    public AjaxResult add(CityEnd cityEnd){
        if(cityEndService.getOne(new QueryWrapper<CityEnd>().lambda().eq(CityEnd::getCode,cityEnd.getCode())) != null){
            return error("唯一编号已存在！");
        }
        return toAjax(cityEndService.save(cityEnd));
    }
    /**
    * 添加跳转页面
    * @return
    */
    @GetMapping("editBefore/{id}")
    public String editBefore(Model model,@PathVariable("id")Long id){
        model.addAttribute("cityEnd",cityEndService.getById(id));
        return prefix+"edit";
    }
    /**
    * 添加
    * @param cityEnd
    * @return
    */
    @PostMapping("edit")
    @ResponseBody
    public AjaxResult edit(CityEnd cityEnd){
        CityEnd cityEndDb = cityEndService.getById(cityEnd.getId());
        if(!cityEndDb.getCode().equals(cityEnd.getCode()) && cityEndService.getOne(new QueryWrapper<CityEnd>().lambda().eq(CityEnd::getCode,cityEnd.getCode())) != null){
            return error("唯一编号已存在！");
        }
        return toAjax(cityEndService.updateById(cityEnd));
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @GetMapping("delete/{id}")
    @ResponseBody
    public AjaxResult delete(@PathVariable("id") Long id){
        return toAjax(cityEndService.removeById(id));
    }
    /**
    * 批量删除
    * @param ids
    * @return
    */
    @PostMapping("deleteAll")
    @ResponseBody
    public AjaxResult deleteAll(@RequestBody List<Long> ids){
        return toAjax(cityEndService.removeByIds(ids));
    }

}

