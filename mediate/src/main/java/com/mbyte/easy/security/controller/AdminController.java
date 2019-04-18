package com.mbyte.easy.security.controller;

import com.mbyte.easy.entity.SysResource;
import com.mbyte.easy.entity.SysUser;
import com.mbyte.easy.entity.SysUserRoles;
import com.mbyte.easy.mapper.SysResourceMapper;
import com.mbyte.easy.mapper.SysRoleMapper;
import com.mbyte.easy.mapper.SysUserMapper;
import com.mbyte.easy.mapper.SysUserRolesMapper;
import com.mbyte.easy.util.Constants;
import com.mbyte.easy.util.Utility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/")
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	@Autowired
	private SysUserMapper userMapper;
	@Autowired
	private SysResourceMapper resourceMapper;
    @Autowired
    private SysRoleMapper roleMapper;
    @Autowired
    private SysUserRolesMapper userRolesMapper;

	/**
	 * 登录用户信息
	 *
	 * @return
	 * @Description:
	 */
	@ModelAttribute("user")
	public SysUser User() {
		String loginUserName = Utility.getCurrentUsername();
		SysUser loginUser = userMapper.selectByUsername(loginUserName);
		return loginUser;
	}

	/**
	 * 页面加载方法
	 *
	 * @param model
	 * @return
	 * @Description:
	 */
	@RequestMapping
	public String welcome(Model model, HttpServletRequest req) {
		// set集合保存的是引用不同地址的对象这里用于去掉重复的资源权限     !!!!!!LinkedHashSet去除重复同时保证排序
		Set<SysResource> sysMenu = new LinkedHashSet<SysResource>();
		String loginUserName = Utility.getCurrentUsername();
		// 加载左侧菜单列表
	    List<SysResource> menuList = resourceMapper.selectByUsername(loginUserName);
		sysMenu.addAll(menuList);
		model.addAttribute("loginUserName", loginUserName);
		model.addAttribute("sysMenu", sysMenu);
		// 菜单加载完成
		return "index";
	}

	@RequestMapping("/welco")
	public String welco() {
		return "welcome";
	}

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping("/register/add")
    public String registerAdd(Model model,SysUser user) {
	    if(StringUtils.isBlank(user.getName())){
            model.addAttribute("msg","请输入姓名");
            return "register";
        }
        if(StringUtils.isBlank(user.getUsername())){
            model.addAttribute("msg","请输入用户名");
            return "register";
        }
        if(StringUtils.isBlank(user.getPassword())){
            model.addAttribute("msg","请输入密码");
            return "register";
        }
        if(!user.getPassword().equals(user.getPassword1())){
            model.addAttribute("msg","密码不一致");
            return "register";
        }
        SysUserRoles sysUserRoles = new SysUserRoles();
        SysUser dbUser = userMapper.selectByUsername(user.getUsername());
        // 用户名已存在
        if (dbUser != null) {
            model.addAttribute("msg","用户名已存在");
            return "register";
        }
        if (user != null && user.getUsername() != null && !"".equals(user.getUsername())) {
            user.setPassword(Utility.QuickPassword(user.getPassword()));
            user.setCreatetime(new Date());
            user.setUpdatetime(new Date());
            user.setAvailable(true);
            userMapper.insert(user);
            user = userMapper.selectByUsername(user.getUsername());
            sysUserRoles.setRolesId(Constants.ROLE_USER);
            sysUserRoles.setSysUserId(user.getId());
            userRolesMapper.insert(sysUserRoles);
        }
        model.addAttribute("msg","注冊成功！");
        return "register";
    }



	@RequestMapping("/foo")
	public String foo(Map<String, Object> model) {
		throw new RuntimeException("Foo");
	}

//	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping("/404")
	public String test() {
		logger.info("您没有如此的访问权限");
		return "404";
	}

}


