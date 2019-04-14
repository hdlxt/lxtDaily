package com.mbyte.easy.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.Message;
import com.mbyte.easy.admin.service.IMessageService;
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
@RequestMapping("/admin/message")
public class MessageController extends BaseController  {

    private String prefix = "admin/message/";

    @Autowired
    private IMessageService messageService;

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
        QueryWrapper<Message> queryWrapper = new QueryWrapper<Message>();

        if(message.getUserId() != null  && !"".equals(message.getUserId() + "")) {
            queryWrapper = queryWrapper.like("user_id",message.getUserId());
         }


        if(message.getMessage() != null  && !"".equals(message.getMessage() + "")) {
            queryWrapper = queryWrapper.like("message",message.getMessage());
         }


        if(message.getReply() != null  && !"".equals(message.getReply() + "")) {
            queryWrapper = queryWrapper.like("reply",message.getReply());
         }


        if(message.getReplyUserId() != null  && !"".equals(message.getReplyUserId() + "")) {
            queryWrapper = queryWrapper.like("reply_user_id",message.getReplyUserId());
         }


        if(message.getCreTime() != null  && !"".equals(message.getCreTime() + "")) {
            queryWrapper = queryWrapper.like("cre_time",message.getCreTime());
         }


        if(message.getUpTime() != null  && !"".equals(message.getUpTime() + "")) {
            queryWrapper = queryWrapper.like("up_time",message.getUpTime());
         }

        IPage<Message> pageInfo = messageService.page(page, queryWrapper);
        model.addAttribute("creTimeSpace", creTimeSpace);
        model.addAttribute("upTimeSpace", upTimeSpace);
        model.addAttribute("searchInfo", message);
        model.addAttribute("pageInfo", new PageInfo(pageInfo));
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
        return toAjax(messageService.save(message));
    }
    /**
    * 添加跳转页面
    * @return
    */
    @GetMapping("editBefore/{id}")
    public String editBefore(Model model,@PathVariable("id")Long id){
        model.addAttribute("message",messageService.getById(id));
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

