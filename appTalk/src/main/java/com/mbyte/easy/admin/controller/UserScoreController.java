package com.mbyte.easy.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.UserScore;
import com.mbyte.easy.admin.service.IUserScoreService;
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
@RequestMapping("/admin/userScore")
public class UserScoreController extends BaseController  {

    private String prefix = "admin/userScore/";

    @Autowired
    private IUserScoreService userScoreService;

    /**
    * 查询列表
    *
    * @param model
    * @param pageNo
    * @param pageSize
    * @param userScore
    * @return
    */
    @RequestMapping
    public String index(Model model,@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,@RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize, UserScore userScore) {
        Page<UserScore> page = new Page<UserScore>(pageNo, pageSize);
        QueryWrapper<UserScore> queryWrapper = new QueryWrapper<UserScore>();

        if(userScore.getUserId() != null  && !"".equals(userScore.getUserId() + "")) {
            queryWrapper = queryWrapper.like("user_id",userScore.getUserId());
         }


        if(userScore.getAppId() != null  && !"".equals(userScore.getAppId() + "")) {
            queryWrapper = queryWrapper.like("app_id",userScore.getAppId());
         }


        if(userScore.getUserScore() != null  && !"".equals(userScore.getUserScore() + "")) {
            queryWrapper = queryWrapper.like("user_score",userScore.getUserScore());
         }


        if(userScore.getRemark() != null  && !"".equals(userScore.getRemark() + "")) {
            queryWrapper = queryWrapper.like("remark",userScore.getRemark());
         }

        IPage<UserScore> pageInfo = userScoreService.page(page, queryWrapper);
        model.addAttribute("searchInfo", userScore);
        model.addAttribute("pageInfo", new PageInfo(pageInfo));
        return prefix+"userScore-list";
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
    * @param userScore
    * @return
    */
    @PostMapping("add")
    @ResponseBody
    public AjaxResult add(UserScore userScore){
        return toAjax(userScoreService.save(userScore));
    }
    /**
    * 添加跳转页面
    * @return
    */
    @GetMapping("editBefore/{id}")
    public String editBefore(Model model,@PathVariable("id")Long id){
        model.addAttribute("userScore",userScoreService.getById(id));
        return prefix+"edit";
    }
    /**
    * 添加
    * @param userScore
    * @return
    */
    @PostMapping("edit")
    @ResponseBody
    public AjaxResult edit(UserScore userScore){
        return toAjax(userScoreService.updateById(userScore));
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @GetMapping("delete/{id}")
    @ResponseBody
    public AjaxResult delete(@PathVariable("id") Long id){
        return toAjax(userScoreService.removeById(id));
    }
    /**
    * 批量删除
    * @param ids
    * @return
    */
    @PostMapping("deleteAll")
    @ResponseBody
    public AjaxResult deleteAll(@RequestBody List<Long> ids){
        return toAjax(userScoreService.removeByIds(ids));
    }

}

