package com.mbyte.easy.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mbyte.easy.admin.entity.Person;
import com.mbyte.easy.admin.service.IPersonService;
import com.mbyte.easy.common.controller.BaseController;
import com.mbyte.easy.util.BaiDuUtil;
import com.mbyte.easy.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
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
    public String index(Model model,@RequestParam(name = "file",required = false)MultipartFile file) throws InterruptedException {
        model.addAttribute("flag","1000");
        List<Person> personList = personService.list(new QueryWrapper<Person>().lambda().orderByAsc(Person::getCreTime));
        if(file != null){
            String filePath = FileUtil.uploadFile(file);
            if(!BaiDuUtil.detect(FileUtil.uploadLocalPath+filePath)){
                model.addAttribute("flag","1001");
            }else{
                model.addAttribute("flag","1002");
                //删除
                BaiDuUtil.deleteUser();
                //睡眠1s
                Thread.sleep(1000L);
                //注册人脸
                String faceToken = BaiDuUtil.addUser(FileUtil.uploadLocalPath+filePath);
                /**
                 * 进行M：1人脸搜索
                 */
                List<Person> persons = new ArrayList<>();
                int count = 0;
                for (Person person : personList) {
                    if(BaiDuUtil.searchMul(FileUtil.uploadLocalPath+person.getPhoto().substring(FileUtil.uploadSuffixPath.length()))){
                        count++;
                        person.setName("序号："+count);
                        persons.add(person);
                    }
}
                model.addAttribute("users",persons);
                //删除人脸照片
//                BaiDuUtil.delete(faceToken);
            }
        }
        model.addAttribute("persons",personList);
        return prefix+"gps";
    }

}

