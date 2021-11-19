package com.zhixingkeji.api.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhixingkeji.api.sys.model.Menu;
import com.zhixingkeji.api.sys.model.Role;
import com.zhixingkeji.api.sys.model.User;
import com.zhixingkeji.api.sys.mapper.UserMapper;
import com.zhixingkeji.api.sys.service.MenuService;
import com.zhixingkeji.api.sys.service.RoleService;
import com.zhixingkeji.api.sys.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhixingkeji.api.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author astupidcoder
 * @since 2021-11-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    RoleService roleService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    MenuService menuService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User getByUsername(String username) {
        return getOne(new QueryWrapper<User>().eq("username", username));
    }

    @Override
    public boolean register(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userMapper.insert(user) == 1;
    }

    @Override
    public String getUserAuthorityInfo(Long userId) {
        //获取角色
        String authority = "";
        System.out.println("正在获取角色信息");

        User user = userMapper.selectById(userId);

        //如果redis里有该用户的权限信息 则从redis里拿 没有则从数据库查询
        if (redisUtil.hasKey("GrantedAuthority:" + user.getUsername())) {
            System.out.println("从redis里查询权限信息");
            authority = (String) redisUtil.get("GrantedAuthority:" + user.getUsername());
        } else {
            System.out.println("从数据库里查询权限信息");
            List<Role> roles = roleService.list(new QueryWrapper<Role>()
                    .inSql("id", "select role_id from sys_user_role where user_id=" + userId));


            if (roles.size() > 0) {
                String roleCodes = roles.stream().map(r -> "ROLE_" + r.getCode()).collect(Collectors.joining(","));
                authority = roleCodes.concat(",");
            }
            System.out.println("查到的角色列表:" + authority);

            //获取权限
            List<Long> menuIds = userMapper.getNavMenuIds(userId);
            if (menuIds.size() > 0) {

                List<Menu> menus = menuService.listByIds(menuIds);
                String menuPerms = menus.stream().map(m -> m.getPerms()).collect(Collectors.joining(","));

                authority = authority.concat(menuPerms);
            }
            System.out.println("查到的操作权限列表:" + authority);

            // 存到redis里, 一个小时后销毁
            redisUtil.set("GrantedAuthority:" + user.getUsername(), authority, 60 * 60);
        }


        return authority;
    }

    @Override
    public void clearUserAuthorityInfo(String username) {
        redisUtil.del("GrantedAuthority:" + username);
    }

    @Override
    public void clearUserAuthorityInfoByRoleId(Long roleId) {
        List<User> users = this.list(new QueryWrapper<User>()
                .inSql("id", "select user_id from sys_user_role where role_id = " + roleId));

        users.forEach(u -> this.clearUserAuthorityInfo(u.getUsername()));
    }

    @Override
    public void clearUserAuthorityInfoByMenuId(Long menuId) {

        List<User> users = userMapper.listByMenuId(menuId);

        users.forEach(u -> this.clearUserAuthorityInfo(u.getUsername()));
    }
}
