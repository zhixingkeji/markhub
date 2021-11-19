import $http from './index.js'

//测试
export const test = (data)=>{
    return $http.get('http://jsonplaceholder.typicode.com/posts',data)
}

//登录界面验证码
export const getImg = (data)=>{
    return $http.get('/captcha',data)
}

//得到home页面用户信息
export const getUserInfoImpl = (data)=>{
    return $http.get('/sys/userInfo',data)
}

//退出登录
export const logoutImpl = (data)=>{
    return $http.get('/logout',data)
}


//获取左侧导航菜单
export const getMenuList = (data)=>{
    return $http.get('/sys/menu/nav',data)
}

//获取菜单管理页面的数据

export const getMenuTable = (data)=>{
    return $http.get("/getMenuTable",data)
}


//获取角色管理页面数据
export const getRoleTable = (data)=>{
    return $http.get("/getRoleTable",data)
}

//获取用户管理页面数据
export const getUserTable = (data)=>{
    return $http.get("/getUserTable",data)
}


//测试按钮专用
export const gettest = (data)=>{
    return $http.get("/auth",data)
}
