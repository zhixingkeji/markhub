package com.zhixingkeji.api.sys.service;

import com.zhixingkeji.api.sys.model.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author astupidcoder
 * @since 2021-11-11
 */
public interface RoleService extends IService<Role> {
    List<Role> listRolesByUserId(Long userId);
}
