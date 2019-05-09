package com.mbyte.easy.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.Apply;
import com.mbyte.easy.admin.service.IApplyService;
import com.mbyte.easy.admin.service.IFenTypeService;
import com.mbyte.easy.common.controller.BaseController;
import com.mbyte.easy.common.web.AjaxResult;
import com.mbyte.easy.entity.SysUser;
import com.mbyte.easy.mapper.SysUserMapper;
import com.mbyte.easy.util.Constants;
import com.mbyte.easy.util.FileUtil;
import com.mbyte.easy.util.PageInfo;
import com.mbyte.easy.util.Utility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
* <p>
* 前端控制器
* </p>
* @author 
* @since 2019-03-11
*/
@Controller
@RequestMapping("/admin/apply")
public class ApplyController extends BaseController  {

    private String prefix = "admin/apply/";

    @Autowired
    private IApplyService applyService;
    @Autowired
    private IFenTypeService fenTypeService;
    @Autowired
    private SysUserMapper sysUserMapper;

    /**
    * 查询列表
    *
    * @param model
    * @param pageNo
    * @param pageSize
    * @param apply
    * @return
    */
    @RequestMapping
    public String index(Model model,@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo
            ,@RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize
            , String creTimeSpace, Apply apply,@RequestParam(defaultValue = "1000",required = false)String flag) {
        Page<Apply> page = new Page<Apply>(pageNo, pageSize);
        model.addAttribute("searchInfo", apply);
        model.addAttribute("flag", flag);
        if("1001".equals(flag)){
            apply.setStatus(Constants.APPLY_YES);
        }
        Long role = Utility.getRole();
        if(Constants.ROLE_USER.equals(role)){
            apply.setApplyId(Utility.getUserId());
            apply.setApplyedId(Utility.getUserId());
        }
        model.addAttribute("role",role);
        model.addAttribute("pageInfo", new PageInfo(applyService.listPage(apply,page)));
        return prefix+"apply-list";
    }

    /**
    * 添加跳转页面
    * @return
    */
    @GetMapping("addBefore")
    public String addBefore(Model model){
        List<SysUser> sysUserList = sysUserMapper.selectByUser(new SysUser());
        List<SysUser> userList = sysUserList.stream()
                .filter(sysUser -> sysUser.getRoles().get(0).getId().equals(Constants.ROLE_USER)).collect(Collectors.toList());
        model.addAttribute("userList",userList);
        model.addAttribute("cUser", Utility.getCurrentUser());
        model.addAttribute("role",Utility.getRole());
        model.addAttribute("fenTypes",fenTypeService.list());
        return prefix+"add";
    }
    /**
    * 添加
    * @param apply
    * @return
    */
    @PostMapping("add")
    @ResponseBody
    public AjaxResult add(Apply apply, @RequestParam("applyFile")MultipartFile applyFile){
        if(apply.getApplyedId().equals(apply.getApplyId())){
            return error("申请人和被申请不可是同一人！");
        }
        if(Constants.ROLE_USER.equals(Utility.getRole())){
            apply.setApplyId(Utility.getUserId());
        }
        apply.setFilePath(FileUtil.uploadFile(applyFile));
        apply.setFilePathSuffix(FileUtil.getSuffix(applyFile.getOriginalFilename()));
        apply.setCreTime(LocalDateTime.now());
        return toAjax(applyService.save(apply));
    }
    /**
    * 添加跳转页面
    * @return
    */
    @GetMapping("editBefore/{id}")
    public String editBefore(Model model,@PathVariable("id")Long id){
        model.addAttribute("apply",applyService.getById(id));
        List<SysUser> sysUserList = sysUserMapper.selectByUser(new SysUser());
        List<SysUser> userList = sysUserList.stream()
                .filter(sysUser -> sysUser.getRoles().get(0).getId().equals(Constants.ROLE_USER)).collect(Collectors.toList());
        List<SysUser> tiaoList = sysUserList.stream()
                .filter(sysUser -> sysUser.getRoles().get(0).getId().equals(Constants.ROLE_TIAO)).collect(Collectors.toList());
        model.addAttribute("userList",userList);
        model.addAttribute("tiaoList",tiaoList);
        model.addAttribute("fenTypes",fenTypeService.list());
        model.addAttribute("cUser", Utility.getCurrentUser());
        model.addAttribute("role",Utility.getRole());
        return prefix+"edit";
    }
    /**
    * 添加
    * @param apply
    * @return
    */
    @PostMapping("edit")
    @ResponseBody
    public AjaxResult edit(Apply apply
            , @RequestParam(value = "applyFile",required = false)MultipartFile applyFile
            , @RequestParam(value ="replyFile",required = false)MultipartFile replyFile){
        if(apply.getApplyedId().equals(apply.getApplyId())){
            return error("申请人和被申请不可是同一人！");
        }
        if(applyFile != null){
            apply.setFilePath(FileUtil.uploadFile(applyFile));
            apply.setFilePathSuffix(FileUtil.getSuffix(applyFile.getOriginalFilename()));
        }
        if(replyFile != null){
            apply.setReplyPath(FileUtil.uploadFile(replyFile));
            apply.setReplyPathSuffix(FileUtil.getSuffix(replyFile.getOriginalFilename()));
        }
        if(Constants.ROLE_TIAO.equals(Utility.getRole()) && Constants.APPLY_YES == apply.getStatus()){
            apply.setUserId(Utility.getUserId());
        }
        return toAjax(applyService.updateById(apply));
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @GetMapping("delete/{id}")
    @ResponseBody
    public AjaxResult delete(@PathVariable("id") Long id){
        return toAjax(applyService.removeById(id));
    }
    /**
    * 批量删除
    * @param ids
    * @return
    */
    @PostMapping("deleteAll")
    @ResponseBody
    public AjaxResult deleteAll(@RequestBody List<Long> ids){
        return toAjax(applyService.removeByIds(ids));
    }


    /**
     * 下载文件
     * @param filePath
     * @param fileName
     * @param response
     */
    @PostMapping("downloadFile")
    public void downloadFile(String filePath,String fileName, HttpServletResponse response){
        FileUtil.downloadFile(filePath,fileName,response);
    }

}

