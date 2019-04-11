package com.mbyte.easy.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.Goods;
import com.mbyte.easy.admin.service.IGoodsService;
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
@RequestMapping("/admin/goods")
public class GoodsController extends BaseController  {

    private String prefix = "admin/goods/";

    @Autowired
    private IGoodsService goodsService;

    /**
    * 查询列表
    *
    * @param model
    * @param pageNo
    * @param pageSize
    * @param goods
    * @return
    */
    @RequestMapping
    public String index(Model model,@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,@RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize, Goods goods) {
        Page<Goods> page = new Page<Goods>(pageNo, pageSize);
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<Goods>();

        if(goods.getBatchCode() != null  && !"".equals(goods.getBatchCode() + "")) {
            queryWrapper = queryWrapper.like("batch_code",goods.getBatchCode());
         }


        if(goods.getName() != null  && !"".equals(goods.getName() + "")) {
            queryWrapper = queryWrapper.like("name",goods.getName());
         }


        if(goods.getLoadM() != null  && !"".equals(goods.getLoadM() + "")) {
            queryWrapper = queryWrapper.like("load_m",goods.getLoadM());
         }


        if(goods.getStatus() != null  && !"".equals(goods.getStatus() + "")) {
            queryWrapper = queryWrapper.like("status",goods.getStatus());
         }


        if(goods.getCode() != null  && !"".equals(goods.getCode() + "")) {
            queryWrapper = queryWrapper.like("code",goods.getCode());
         }


        if(goods.getRemark() != null  && !"".equals(goods.getRemark() + "")) {
            queryWrapper = queryWrapper.like("remark",goods.getRemark());
         }


        if(goods.getContent() != null  && !"".equals(goods.getContent() + "")) {
            queryWrapper = queryWrapper.like("content",goods.getContent());
         }

        IPage<Goods> pageInfo = goodsService.page(page, queryWrapper);
        model.addAttribute("searchInfo", goods);
        model.addAttribute("pageInfo", new PageInfo(pageInfo));
        return prefix+"goods-list";
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
    * @param goods
    * @return
    */
    @PostMapping("add")
    @ResponseBody
    public AjaxResult add(Goods goods){
        return toAjax(goodsService.save(goods));
    }
    /**
    * 添加跳转页面
    * @return
    */
    @GetMapping("editBefore/{id}")
    public String editBefore(Model model,@PathVariable("id")Long id){
        model.addAttribute("goods",goodsService.getById(id));
        return prefix+"edit";
    }
    /**
    * 添加
    * @param goods
    * @return
    */
    @PostMapping("edit")
    @ResponseBody
    public AjaxResult edit(Goods goods){
        return toAjax(goodsService.updateById(goods));
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @GetMapping("delete/{id}")
    @ResponseBody
    public AjaxResult delete(@PathVariable("id") Long id){
        return toAjax(goodsService.removeById(id));
    }
    /**
    * 批量删除
    * @param ids
    * @return
    */
    @PostMapping("deleteAll")
    @ResponseBody
    public AjaxResult deleteAll(@RequestBody List<Long> ids){
        return toAjax(goodsService.removeByIds(ids));
    }

}

