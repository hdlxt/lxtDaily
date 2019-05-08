package com.mbyte.easy.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.Photos;
import com.mbyte.easy.admin.service.IPhotosService;
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
@RequestMapping("/admin/photos")
public class PhotosController extends BaseController  {

    private String prefix = "admin/photos/";

    @Autowired
    private IPhotosService photosService;

    /**
    * 查询列表
    *
    * @param model
    * @param pageNo
    * @param pageSize
    * @param photos
    * @return
    */
    @RequestMapping
    public String index(Model model,@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,@RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize, String creTimeSpace, String tagTimeSpace, Photos photos) {
        Page<Photos> page = new Page<Photos>(pageNo, pageSize);
        QueryWrapper<Photos> queryWrapper = new QueryWrapper<Photos>();

        if(photos.getName() != null  && !"".equals(photos.getName() + "")) {
            queryWrapper = queryWrapper.like("name",photos.getName());
         }


        if(photos.getImg() != null  && !"".equals(photos.getImg() + "")) {
            queryWrapper = queryWrapper.like("img",photos.getImg());
         }


        if(photos.getAlbumId() != null  && !"".equals(photos.getAlbumId() + "")) {
            queryWrapper = queryWrapper.like("album_id",photos.getAlbumId());
         }


        if(photos.getCreTime() != null  && !"".equals(photos.getCreTime() + "")) {
            queryWrapper = queryWrapper.like("cre_time",photos.getCreTime());
         }


        if(photos.getBackId() != null  && !"".equals(photos.getBackId() + "")) {
            queryWrapper = queryWrapper.like("back_id",photos.getBackId());
         }


        if(photos.getTagTime() != null  && !"".equals(photos.getTagTime() + "")) {
            queryWrapper = queryWrapper.like("tag_time",photos.getTagTime());
         }


        if(photos.getTagContent() != null  && !"".equals(photos.getTagContent() + "")) {
            queryWrapper = queryWrapper.like("tag_content",photos.getTagContent());
         }

        IPage<Photos> pageInfo = photosService.page(page, queryWrapper);
        model.addAttribute("creTimeSpace", creTimeSpace);
        model.addAttribute("tagTimeSpace", tagTimeSpace);
        model.addAttribute("searchInfo", photos);
        model.addAttribute("pageInfo", new PageInfo(pageInfo));
        return prefix+"photos-list";
    }

    /**
     *
     * @return
     */
    @RequestMapping("compare")
    public String compare(@RequestParam(required = false) MultipartFile file1
        ,@RequestParam(required = false) MultipartFile file2) {

        return prefix+"photos-compare";
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
    * @param photos
    * @return
    */
    @PostMapping("add")
    @ResponseBody
    public AjaxResult add(Photos photos,@RequestParam(required = false) MultipartFile file){
        if(file != null){
            photos.setImg(FileUtil.uploadSuffixPath+FileUtil.uploadFile(file));
        }
        photos.setCreTime(LocalDateTime.now());
        if(StringUtils.isNoneBlank(photos.getTagContent())){
            photos.setTagTime(LocalDateTime.now());
        }
        return toAjax(photosService.save(photos));
    }
    /**
    * 添加跳转页面
    * @return
    */
    @GetMapping("editBefore/{id}")
    public String editBefore(Model model,@PathVariable("id")Long id){
        model.addAttribute("photos",photosService.getById(id));
        return prefix+"edit";
    }
    /**
    * 添加
    * @param photos
    * @return
    */
    @PostMapping("edit")
    @ResponseBody
    public AjaxResult edit(Photos photos,@RequestParam(required = false) MultipartFile file){
        if(file != null){
            photos.setImg(FileUtil.uploadSuffixPath+FileUtil.uploadFile(file));
        }
        return toAjax(photosService.updateById(photos));
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @GetMapping("delete/{id}")
    @ResponseBody
    public AjaxResult delete(@PathVariable("id") Long id){
        return toAjax(photosService.removeById(id));
    }
    /**
    * 批量删除
    * @param ids
    * @return
    */
    @PostMapping("deleteAll")
    @ResponseBody
    public AjaxResult deleteAll(@RequestBody List<Long> ids){
        return toAjax(photosService.removeByIds(ids));
    }

}

