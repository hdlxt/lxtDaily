package com.mbyte.easy.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.Car;
import com.mbyte.easy.admin.service.ICarService;
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
* @author 黄润宣
* @since 2019-03-11
*/
@Controller
@RequestMapping("/admin/car")
public class CarController extends BaseController  {

    private String prefix = "admin/car/";

    @Autowired
    private ICarService carService;

    /**
    * 查询列表
    *
    * @param model
    * @param pageNo
    * @param pageSize
    * @param car
    * @return
    */
    @RequestMapping
    public String index(Model model,@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,@RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize, String paiTimeSpace, Car car) {
        Page<Car> page = new Page<Car>(pageNo, pageSize);
        QueryWrapper<Car> queryWrapper = new QueryWrapper<Car>();

        if(car.getCode() != null  && !"".equals(car.getCode() + "")) {
            queryWrapper = queryWrapper.like("code",car.getCode());
         }


        if(car.getType() != null  && !"".equals(car.getType() + "")) {
            queryWrapper = queryWrapper.like("type",car.getType());
         }


        if(car.getLoadMax() != null  && !"".equals(car.getLoadMax() + "")) {
            queryWrapper = queryWrapper.like("load_max",car.getLoadMax());
         }


        if(car.getPai() != null  && !"".equals(car.getPai() + "")) {
            queryWrapper = queryWrapper.like("pai",car.getPai());
         }


        if(car.getPaiTime() != null  && !"".equals(car.getPaiTime() + "")) {
            queryWrapper = queryWrapper.like("pai_time",car.getPaiTime());
         }


        if(car.getName() != null  && !"".equals(car.getName() + "")) {
            queryWrapper = queryWrapper.like("name",car.getName());
         }


        if(car.getTel() != null  && !"".equals(car.getTel() + "")) {
            queryWrapper = queryWrapper.like("tel",car.getTel());
         }


        if(car.getIdCard() != null  && !"".equals(car.getIdCard() + "")) {
            queryWrapper = queryWrapper.like("id_card",car.getIdCard());
         }


        if(car.getStatus() != null  && !"".equals(car.getStatus() + "")) {
            queryWrapper = queryWrapper.like("status",car.getStatus());
         }


        if(car.getLoadUse() != null  && !"".equals(car.getLoadUse() + "")) {
            queryWrapper = queryWrapper.like("load_use",car.getLoadUse());
         }

        IPage<Car> pageInfo = carService.page(page, queryWrapper);
        model.addAttribute("paiTimeSpace", paiTimeSpace);
        model.addAttribute("searchInfo", car);
        model.addAttribute("pageInfo", new PageInfo(pageInfo));
        return prefix+"car-list";
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
    * @param car
    * @return
    */
    @PostMapping("add")
    @ResponseBody
    public AjaxResult add(Car car){
        return toAjax(carService.save(car));
    }
    /**
    * 添加跳转页面
    * @return
    */
    @GetMapping("editBefore/{id}")
    public String editBefore(Model model,@PathVariable("id")Long id){
        model.addAttribute("car",carService.getById(id));
        return prefix+"edit";
    }
    /**
    * 添加
    * @param car
    * @return
    */
    @PostMapping("edit")
    @ResponseBody
    public AjaxResult edit(Car car){
        return toAjax(carService.updateById(car));
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @GetMapping("delete/{id}")
    @ResponseBody
    public AjaxResult delete(@PathVariable("id") Long id){
        return toAjax(carService.removeById(id));
    }
    /**
    * 批量删除
    * @param ids
    * @return
    */
    @PostMapping("deleteAll")
    @ResponseBody
    public AjaxResult deleteAll(@RequestBody List<Long> ids){
        return toAjax(carService.removeByIds(ids));
    }

}

