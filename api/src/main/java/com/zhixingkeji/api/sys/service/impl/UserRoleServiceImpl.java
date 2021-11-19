package com.zhixingkeji.api.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhixingkeji.api.sys.mapper.UserRoleMapper;
import com.zhixingkeji.api.sys.model.UserRole;
import com.zhixingkeji.api.sys.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
