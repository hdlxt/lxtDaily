package com.mbyte.easy.security.auth;

import com.mbyte.easy.entity.SysResource;
import com.mbyte.easy.entity.SysRoleResources;
import com.mbyte.easy.entity.SysUser;
import com.mbyte.easy.entity.SysUserRoles;
import com.mbyte.easy.mapper.SysResourceMapper;
import com.mbyte.easy.mapper.SysRoleResourcesMapper;
import com.mbyte.easy.mapper.SysUserMapper;
import com.mbyte.easy.mapper.SysUserRolesMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义校验方式
 *
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
	private final String VALIDATECODE_ERROR = "validatecode_error";//session存储验证码是否错误的key值
	@Value("${application.messsage.error.username:用户名错误}")
	private String usernameErrorMessage = "用户名错误";
	@Value("${application.messsage.error.password:密码错误}")
	private String passwordErrorMessage = "密码错误";
	@Value("${application.messsage.error.available:账号未被启用}")
	private String availableErrorMessage = "账号未被启用";
	
	@Autowired
	private SysUserMapper userMapper;

	@Autowired
	private SysUserRolesMapper userRolesMapper;
	
	@Autowired
	private SysRoleResourcesMapper roleResourcesMapper;
	
	@Autowired
	private SysResourceMapper resourceMapper;
	
	@Autowired
	private HttpSession session;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		if(StringUtils.isBlank(username)){
			session.setAttribute(VALIDATECODE_ERROR, this.usernameErrorMessage);
			throw new BadCredentialsException("Username not found.");
		}
        String password = authentication.getCredentials() != null ? authentication.getCredentials().toString() : null;
		if(StringUtils.isBlank(password)){
			session.setAttribute(VALIDATECODE_ERROR, this.passwordErrorMessage);
			throw new BadCredentialsException("密码不正确");
		}
        SysUser user =  new SysUser();
        user.setUsername(username);
        List<SysUser> users = userMapper.selectByUser(user);
        if(users.size() > 0) {
        	user = users.get(0);
        }else {
        	user = null;
        }
        
        if(user == null){
        	session.setAttribute(VALIDATECODE_ERROR, this.usernameErrorMessage);
            throw new BadCredentialsException("Username not found.");
        }
        if(!user.getAvailable()) {
        	session.setAttribute(VALIDATECODE_ERROR, this.availableErrorMessage);
        	throw new BadCredentialsException("账号未被启用");
        }
        if(!BCrypt.checkpw(password, user.getPassword())){
        	session.setAttribute(VALIDATECODE_ERROR, this.passwordErrorMessage);
        	throw new BadCredentialsException("密码不正确");
        }

        List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
       
		List<SysUserRoles> userRoles = userRolesMapper.selectByUserId(user.getId());
		for(SysUserRoles role : userRoles){
			List<SysRoleResources> roleResourcesList = roleResourcesMapper.selectByRoleId(role.getRolesId());
			for(SysRoleResources roleResources : roleResourcesList){
				SysResource resource = resourceMapper.selectByPrimaryKey(roleResources.getResourcesId());
				if(resource != null ){
					if(resource.getPermission() != null && !"".equals(resource.getPermission())){
						auths.add(new SimpleGrantedAuthority(resource.getPermission()));//将用户的角色名作为权限
					}
				}
			}
		}
		
        return new UsernamePasswordAuthenticationToken(user, password, auths);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true;
	}

}
