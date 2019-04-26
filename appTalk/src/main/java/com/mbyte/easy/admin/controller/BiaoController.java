package com.mbyte.easy.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.Biao;
import com.mbyte.easy.admin.service.IBiaoService;
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
@RequestMapping("/admin/biao")
public class BiaoController extends BaseController  {

    private String prefix = "admin/biao/";

    @Autowired
    private IBiaoService biaoService;

    /**
    * 查询列表
    *
    * @param model
    * @param pageNo
    * @param pageSize
    * @param biao
    * @return
    */
    @RequestMapping
    public String index(Model model,@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,@RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize, Biao biao) {
        Page<Biao> page = new Page<Biao>(pageNo, pageSize);
        QueryWrapper<Biao> queryWrapper = new QueryWrapper<Biao>();

        if(biao.getName() != null  && !"".equals(biao.getName() + "")) {
            queryWrapper = queryWrapper.like("name",biao.getName());
         }

        IPage<Biao> pageInfo = biaoService.page(page, queryWrapper);
        model.addAttribute("searchInfo", biao);
        model.addAttribute("pageInfo", new PageInfo(pageInfo));
        return prefix+"biao-list";
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
    * @param biao
    * @return
    */
    @PostMapping("add")
    @ResponseBody
    public AjaxResult add(Biao biao){
        return toAjax(biaoService.save(biao));
    }
    /**
    * 添加跳转页面
    * @return
    */
    @GetMapping("editBefore/{id}")
    public String editBefore(Model model,@PathVariable("id")Long id){
        model.addAttribute("biao",biaoService.getById(id));
        return prefix+"edit";
    }
    /**
    * 添加
    * @param biao
    * @return
    */
    @PostMapping("edit")
    @ResponseBody
    public AjaxResult edit(Biao biao){
        return toAjax(biaoService.updateById(biao));
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @GetMapping("delete/{id}")
    @ResponseBody
    public AjaxResult delete(@PathVariable("id") Long id){
        return toAjax(biaoService.removeById(id));
    }
    /**
    * 批量删除
    * @param ids
    * @return
    */
    @PostMapping("deleteAll")
    @ResponseBody
    public AjaxResult deleteAll(@RequestBody List<Long> ids){
        return toAjax(biaoService.removeByIds(ids));
    }

}

