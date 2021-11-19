// import Mock from 'mockjs'
//
//
// const Random = Mock.Random
//
// let Result = {
//     code: 200,
//     msg: "操作成功",
//     data: null,
// }
//
// //登录页面获取验证码图片
// Mock.mock("/api/captcha", "get", () => {
//     Result.data = {
//         randomCode: Random.string(32),
//         captchaImg: Random.dataImage("70x40", "p4n3")
//     }
//     return Result
// })
//
// //得到用户头像
// Mock.mock("/api/getUserinfo", "get", () => {
//     Result.data = {
//         id: "11",
//         username: "root",
//         avatar: "https://z3.ax1x.com/2021/10/31/ISTUS0.jpg",
//     }
//     return Result
// })
//
// //退出
// Mock.mock("/api/logout", "post", () => {
//     return Result
// })
//
// //得到左侧菜单栏
// Mock.mock("/api/menulist", "get", () => {
//
//     let menulist = [
//         {
//             "id": 1,
//             "parentId": "",
//             "title": "首页展示",
//             "url": "",
//             "icon": "el-icon-house",
//             "component": "",
//             "orderNum": 1,
//             "open": 1,
//             "disabled": false,
//             "perms": null,
//             "type": 0,
//             "children": [
//                 {
//                     "id": 101,
//                     "parentId": "1",
//                     "title": "首页展示",
//                     "name": "Index",
//                     "url": "/index",
//                     "icon": "el-icon-house",
//                     "component": "@/views/Home/Index.vue",
//                     "orderNum": 2,
//                     "open": "",
//                     "disabled": false,
//                     "perms": null,
//                     "type": 0,
//
//                 },
//                 {
//                     "id": 102,
//                     "parentId": "1",
//                     "title": "个人中心",
//                     "name": "UserCenter",
//                     "url": "/usercenter",
//                     "icon": "el-icon-house",
//                     "component": "@/views/Home/UserCenter.vue",
//                     "orderNum": 1,
//                     "open": 1,
//                     "disabled": false,
//                     "perms": null,
//                     "type": 0,
//
//                 },
//             ]
//         },
//         {
//             "id": 2,
//             "parentId": "",
//             "title": "系统管理",
//             "url": "",
//             "icon": "el-icon-coin",
//             "component": "",
//             "orderNum": 1,
//             "open": 1,
//             "disabled": false,
//             "perms": null,
//             "type": 0,
//             "children": [
//                 {
//                     "id": 201,
//                     "parentId": "2",
//                     "title": "用户管理",
//                     "name": "UserManager",
//                     "url": "/usermanager",
//                     "icon": "el-icon-user",
//                     "component": "@/views/sys/UserManager.vue",
//                     "orderNum": 1,
//                     "open": 1,
//                     "disabled": false,
//                     "perms": null,
//                     "type": 0,
//                     "children": []
//                 },
//                 {
//                     "id": 202,
//                     "parentId": "2",
//                     "title": "角色管理",
//                     "name": "RoleManager",
//                     "url": "/rolemanager",
//                     "icon": "el-icon-female",
//                     "component": "@/views/sys/RoleManager.vue",
//                     "orderNum": 1,
//                     "open": 1,
//                     "disabled": false,
//                     "perms": null,
//                     "type": 0,
//                     "children": []
//                 },
//                 {
//                     "id": 203,
//                     "parentId": "2",
//                     "title": "菜单管理",
//                     "name": "MenuManager",
//                     "url": "/menumanager",
//                     "icon": "el-icon-s-unfold",
//                     "component": "@/views/sys/MenuManager.vue",
//                     "orderNum": 1,
//                     "open": 1,
//                     "disabled": false,
//                     "perms": null,
//                     "type": 0,
//                     "children": []
//                 }
//             ]
//         },
//         {
//             "id": 3,
//             "parentId": "",
//             "title": "系统工具",
//             "url": "/index",
//             "icon": "el-icon-s-open",
//             "component": "",
//             "orderNum": 1,
//             "open": 1,
//             "disabled": false,
//             "perms": null,
//             "type": 0,
//             "children": [
//                 {
//                     "id": 301,
//                     "parentId": "",
//                     "title": "数字字典",
//                     "name": "MathDict",
//                     "url": "/mathdict",
//                     "icon": "el-icon-document",
//                     "component": "@/views/sysUtils/MathDict.vue",
//                     "orderNum": 1,
//                     "open": 1,
//                     "disabled": false,
//                     "perms": null,
//                     "type": 0,
//                     "children": []
//                 },
//             ]
//         },
//     ]
//     let author = []
//
//
//     Result.data = {
//         menulist: menulist,
//         author: author
//     }
//
//     return Result
// })
//
// //菜单管理页面数据
// Mock.mock("/api/getMenuTable", "get", () => {
//     Result.data =
//         [
//             {
//                 id: 1,
//                 name: '系统管理',
//                 label: "系统管理",
//                 icon: "el-icon-coin",
//                 status: 1,
//                 type: 0,
//                 children: [
//                     {
//                         id: 101,
//                         value: 101,
//                         name: '用户管理',
//                         label: "用户管理",
//                         icon: "el-icon-user",
//                         status: 1,
//                         type: 1,
//                     },
//                     {
//                         id: 102,
//                         name: '角色管理',
//                         label: "角色管理",
//                         icon: "el-icon-female",
//                         status: 1,
//                         type: 1,
//                     },
//                     {
//                         id: 101,
//                         name: '菜单管理',
//                         label: "菜单管理",
//                         icon: "el-icon-s-unfold",
//                         status: 1,
//                         type: 1,
//                     }
//                 ]
//             }
//         ]
//
//     return Result
// })
//
// //用户管理页面数据
// Mock.mock("/api/getUserTable", "get", () => {
//     Result.data =
//         [
//             {
//                 name: "maikelei",
//                 avater: "仓鼠头像",
//                 hasRoles: ["admin","normal"],
//                 email: "19723551775@qq.com",
//                 phoneNum: "18612854561",
//                 status: 1,
//             },
//             {
//                 name: "dota",
//                 avater: "仓鼠头像",
//                 hasRoles: ["normal"],
//                 email: "30125891402@qq.com",
//                 phoneNum: "13612854561",
//                 status: 1,
//             },
//             {
//                 name: "maikelei",
//                 avater: "仓鼠头像",
//                 hasRoles: ["admin"],
//                 email: "30125891402@qq.com",
//                 phoneNum: "15212854561",
//                 status: 1,
//             },
//
//         ]
//
//     return Result
// })
//
// //角色管理页面数据
// Mock.mock("/api/getRoleTable", "get", () => {
//     Result.data =
//         [
//
//             {
//                 id: 101,
//                 name: '普通用户',
//                 role: "normal",
//                 remark: "只有基本的查看功能",
//                 status: 1,
//             },
//             {
//                 id: 102,
//                 name: '管理员',
//                 role: "admin",
//                 remark: "拥有normal和编辑修改的功能",
//                 status: 1,
//             },
//             {
//                 id: 103,
//                 name: '超级管理员',
//                 role: "root",
//                 remark: "拥有admin和删除功能",
//                 status: 0,
//             },
//
//         ]
//
//     return Result
// })
//
