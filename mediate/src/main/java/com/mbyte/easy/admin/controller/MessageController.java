package com.mbyte.easy.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.Message;
import com.mbyte.easy.admin.service.IMessageService;
import com.mbyte.easy.common.controller.BaseController;
import com.mbyte.easy.common.web.AjaxResult;
import com.mbyte.easy.entity.SysUser;
import com.mbyte.easy.mapper.SysUserMapper;
import com.mbyte.easy.util.PageInfo;
import com.mbyte.easy.util.Utility;
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
@RequestMapping("/admin/message")
public class MessageController extends BaseController  {

    private String prefix = "admin/message/";

    @Autowired
    private IMessageService messageService;
    @Autowired
    private SysUserMapper sysUserMapper;

    /**
    * 查询列表
    *
    * @param model
    * @param pageNo
    * @param pageSize
    * @param message
    * @return
    */
    @RequestMapping
    public String index(Model model,@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,@RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize, String creTimeSpace, String upTimeSpace, Message message) {
        Page<Message> page = new Page<Message>(pageNo, pageSize);
        model.addAttribute("creTimeSpace", creTimeSpace);
        model.addAttribute("upTimeSpace", upTimeSpace);
        if(StringUtils.isBlank(message.getMTitle())){
            message.setMTitle(null);
        }
        if(StringUtils.isBlank(message.getUserName())){
            message.setUserName(null);
        }
        if(StringUtils.isBlank(message.getReplyUserName())){
            message.setReplyUserName(null);
        }
        model.addAttribute("searchInfo", message);
        model.addAttribute("pageInfo", new PageInfo(messageService.listPage(message,page)));
        return prefix+"message-list";
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
    * @param message
    * @return
    */
    @PostMapping("add")
    @ResponseBody
    public AjaxResult add(Message message){
        message.setUserId(((SysUser)Utility.getCurrentUser()).getId());
        message.setCreTime(LocalDateTime.now());
        return toAjax(messageService.save(message));
    }
    /**
    * 添加跳转页面
    * @return
    */
    @GetMapping("editBefore/{id}")
    public String editBefore(Model model,@PathVariable("id")Long id){
        Message message = messageService.getById(id);
        model.addAttribute("message",message);
        model.addAttribute("user",sysUserMapper.selectByPrimaryKey(message.getUserId()));
        model.addAttribute("replyUser",sysUserMapper.selectByPrimaryKey(message.getReplyUserId()));
        return prefix+"edit";
    }
    /**
    * 添加
    * @param message
    * @return
    */
    @PostMapping("edit")
    @ResponseBody
    public AjaxResult edit(Message message){
        return toAjax(messageService.updateById(message));
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @GetMapping("delete/{id}")
    @ResponseBody
    public AjaxResult delete(@PathVariable("id") Long id){
        return toAjax(messageService.removeById(id));
    }
    /**
    * 批量删除
    * @param ids
    * @return
    */
    @PostMapping("deleteAll")
    @ResponseBody
    public AjaxResult deleteAll(@RequestBody List<Long> ids){
        return toAjax(messageService.removeByIds(ids));
    }

}

