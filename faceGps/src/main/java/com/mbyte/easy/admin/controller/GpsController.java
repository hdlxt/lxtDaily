package com.mbyte.easy.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.Person;
import com.mbyte.easy.admin.service.IPersonService;
import com.mbyte.easy.common.controller.BaseController;
import com.mbyte.easy.common.web.AjaxResult;
import com.mbyte.easy.util.BaiDuUtil;
import com.mbyte.easy.util.FileUtil;
import com.mbyte.easy.util.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
* <p>
* 前端控制器
* </p>

* @since 2019-03-11
*/
@Controller
@RequestMapping("/admin/gps")
public class GpsController extends BaseController  {

    private String prefix = "admin/";

    @Autowired
    private IPersonService personService;
    /**
    * 查询列表
    *
    * @return
    */
    @RequestMapping
    public String index(Model model,@RequestParam(name = "file",required = false)MultipartFile file) {
        model.addAttribute("flag","1000");
        if(file != null){
            String filePath = FileUtil.uploadFile(file);
            if(!BaiDuUtil.detect(FileUtil.uploadLocalPath+filePath)){
                model.addAttribute("flag","1001");
            }else{
                model.addAttribute("flag","1002");
                //匹配人脸
                List<Long> userIdList = BaiDuUtil.search(FileUtil.uploadLocalPath+filePath);
                if(userIdList.size() == 0){
                    model.addAttribute("users",new ArrayList<>());
                }else{
                    model.addAttribute("users",personService.list(new QueryWrapper<Person>().lambda().in(Person::getId,userIdList)));
                }
            }
        }
        model.addAttribute("persons",personService.list());
        return prefix+"gps";
    }

}

