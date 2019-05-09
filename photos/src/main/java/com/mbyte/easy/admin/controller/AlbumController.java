package com.mbyte.easy.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.Album;
import com.mbyte.easy.admin.service.IAlbumService;
import com.mbyte.easy.common.controller.BaseController;
import com.mbyte.easy.common.web.AjaxResult;
import com.mbyte.easy.util.FileUtil;
import com.mbyte.easy.util.PageInfo;
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
* @author 
* @since 2019-03-11
*/
@Controller
@RequestMapping("/admin/album")
public class AlbumController extends BaseController  {

    private String prefix = "admin/album/";

    @Autowired
    private IAlbumService albumService;

    /**
    * 查询列表
    *
    * @param model
    * @param pageNo
    * @param pageSize
    * @param album
    * @return
    */
    @RequestMapping
    public String index(Model model,@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,@RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize, String creTimeSpace, Album album) {
        Page<Album> page = new Page<Album>(pageNo, pageSize);
        QueryWrapper<Album> queryWrapper = new QueryWrapper<Album>();

        if(album.getName() != null  && !"".equals(album.getName() + "")) {
            queryWrapper = queryWrapper.like("name",album.getName());
         }


        if(album.getTypeName() != null  && !"".equals(album.getTypeName() + "")) {
            queryWrapper = queryWrapper.like("type_name",album.getTypeName());
         }


        if(album.getCreTime() != null  && !"".equals(album.getCreTime() + "")) {
            queryWrapper = queryWrapper.like("cre_time",album.getCreTime());
         }


        if(album.getCode() != null  && !"".equals(album.getCode() + "")) {
            queryWrapper = queryWrapper.like("code",album.getCode());
         }


        if(album.getImg() != null  && !"".equals(album.getImg() + "")) {
            queryWrapper = queryWrapper.like("img",album.getImg());
         }


        if(album.getBackId() != null  && !"".equals(album.getBackId() + "")) {
            queryWrapper = queryWrapper.like("back_id",album.getBackId());
         }

        IPage<Album> pageInfo = albumService.page(page, queryWrapper);
        model.addAttribute("creTimeSpace", creTimeSpace);
        model.addAttribute("searchInfo", album);
        model.addAttribute("pageInfo", new PageInfo(pageInfo));
        return prefix+"album-list";
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
    * @param album
    * @return
    */
    @PostMapping("add")
    @ResponseBody
    public AjaxResult add(Album album, MultipartFile fileFeng){
        album.setImg(FileUtil.uploadSuffixPath+FileUtil.uploadFile(fileFeng));
        album.setCreTime(LocalDateTime.now());
        return toAjax(albumService.save(album));
    }
    /**
    * 添加跳转页面
    * @return
    */
    @GetMapping("editBefore/{id}")
    public String editBefore(Model model,@PathVariable("id")Long id){
        model.addAttribute("album",albumService.getById(id));
        return prefix+"edit";
    }
    /**
    * 添加
    * @param album
    * @return
    */
    @PostMapping("edit")
    @ResponseBody
    public AjaxResult edit(Album album,@RequestParam(required = false) MultipartFile fileFeng){
        if(fileFeng != null){
            album.setImg(FileUtil.uploadSuffixPath+FileUtil.uploadFile(fileFeng));
        }
        return toAjax(albumService.updateById(album));
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @GetMapping("delete/{id}")
    @ResponseBody
    public AjaxResult delete(@PathVariable("id") Long id){
        return toAjax(albumService.removeById(id));
    }
    /**
    * 批量删除
    * @param ids
    * @return
    */
    @PostMapping("deleteAll")
    @ResponseBody
    public AjaxResult deleteAll(@RequestBody List<Long> ids){
        return toAjax(albumService.removeByIds(ids));
    }

}

