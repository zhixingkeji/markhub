package com.zhixingkeji.api.sys.service;

import com.zhixingkeji.api.sys.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author astupidcoder
 * @since 2021-11-11
 */
public interface UserService extends IService<User> {

    User getByUsername(String username);


    boolean register(User user);

    String getUserAuthorityInfo(Long userId);

    void clearUserAuthorityInfo(String username);

    void clearUserAuthorityInfoByRoleId(Long roleId);

    void clearUserAuthorityInfoByMenuId(Long menuId);
}
