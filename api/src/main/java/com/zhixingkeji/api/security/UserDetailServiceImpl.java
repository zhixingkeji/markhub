package com.zhixingkeji.api.security;


import com.zhixingkeji.api.sys.model.User;
import com.zhixingkeji.api.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userService.getByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("用户名或密码不正确");
		}
		return new AccountUser(user.getId(), user.getUsername(), user.getPassword(), getUserAuthority(user.getId()));
	}

	/**
	 * 获取用户权限信息（角色、菜单权限）
	 * @param userId
	 * @return
	 */


	public List<GrantedAuthority> getUserAuthority(Long userId){

		// 角色(ROLE_admin)、菜单操作权限 sys:user:list
		String authority = userService.getUserAuthorityInfo(userId);  // ROLE_admin,ROLE_normal,sys:user:list,....
		System.out.println("正在获取权限信息,并包装");
		return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);

	}
}
