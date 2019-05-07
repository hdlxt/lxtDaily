package com.mbyte.easy.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.App;
import com.mbyte.easy.admin.entity.Biao;
import com.mbyte.easy.admin.entity.UserScore;
import com.mbyte.easy.admin.service.IAppService;
import com.mbyte.easy.admin.service.IBiaoService;
import com.mbyte.easy.admin.service.IUserScoreService;
import com.mbyte.easy.common.controller.BaseController;
import com.mbyte.easy.common.web.AjaxResult;
import com.mbyte.easy.entity.SysUser;
import com.mbyte.easy.util.FileUtil;
import com.mbyte.easy.util.PageInfo;
import com.mbyte.easy.util.Utility;
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
*
* @since 2019-03-11
*/
@Controller
@RequestMapping("/admin/app")
public class AppController extends BaseController  {

    private String prefix = "admin/app/";

    @Autowired
    private IAppService appService;
    @Autowired
    private IBiaoService biaoService;
    @Autowired
    private IUserScoreService userScoreServic;
    /**
    * 查询列表
    *
    * @param model
    * @param pageNo
    * @param pageSize
    * @param app
    * @return
    */
    @RequestMapping
    public String index(Model model,@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,@RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize, App app) {
        Page<App> page = new Page<App>(pageNo, pageSize);
        app.setUserId(((SysUser)Utility.getCurrentUser()).getId());
        IPage<App> pageInfo = appService.listPage(app,page);
        model.addAttribute("searchInfo", app);
        model.addAttribute("role", ((SysUser)Utility.getCurrentUser()).getRoles().get(0).getId());
        model.addAttribute("pageInfo", new PageInfo(pageInfo));
        return prefix+"app-list";
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
    * @param app
    * @return
    */
    @PostMapping("add")
    @ResponseBody
    public AjaxResult add(App app, MultipartFile file){
        app.setImg(FileUtil.uploadSuffixPath+FileUtil.uploadFile(file));
        return toAjax(appService.save(app));
    }
    /**
    * 添加跳转页面
    * @return
    */
    @GetMapping("editBefore/{id}")
    public String editBefore(Model model,@PathVariable("id")Long id){
        model.addAttribute("app",appService.getById(id));
        return prefix+"edit";
    }
    @GetMapping("talkBefore/{id}")
    public String talkBefore(Model model,@PathVariable("id")Long id){
        model.addAttribute("app",appService.getById(id));
        List<Biao> biaoList = biaoService.list();
        String biaos = "";
        for (int i = 0; i < biaoList.size(); i++) {
            if(i != biaoList.size()-1){
                biaos += biaoList.get(i).getName()+"、";
            }else{
                biaos += biaoList.get(i).getName();
            }
        }
        model.addAttribute("biaos",biaos);
        return prefix+"talk";
    }
    /**
     * 评价
     * @param userScore
     * @return
     */
    @PostMapping("talk")
    @ResponseBody
    public AjaxResult talk(UserScore userScore){
        userScore.setUserId(((SysUser)Utility.getCurrentUser()).getId());
        return toAjax(userScoreServic.save(userScore));
    }
    /**
    * 添加
    * @param app
    * @return
    */
    @PostMapping("edit")
    @ResponseBody
    public AjaxResult edit(App app,@RequestParam(required = false) MultipartFile file){
        if(file != null){
            app.setImg(FileUtil.uploadSuffixPath+FileUtil.uploadFile(file));
        }
        return toAjax(appService.updateById(app));
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @GetMapping("delete/{id}")
    @ResponseBody
    public AjaxResult delete(@PathVariable("id") Long id){
        return toAjax(appService.removeById(id));
    }
    /**
    * 批量删除
    * @param ids
    * @return
    */
    @PostMapping("deleteAll")
    @ResponseBody
    public AjaxResult deleteAll(@RequestBody List<Long> ids){
        return toAjax(appService.removeByIds(ids));
    }

}

