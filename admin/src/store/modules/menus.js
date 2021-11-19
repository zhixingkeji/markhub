

export default ({
    state: {
        //菜单列表
        menuList: JSON.parse(sessionStorage.getItem("menuList")),

        //权限列表
        permList: [],

        //是否获取了菜单
        hasRoute: sessionStorage.getItem("hasRoute"),

        //标签页的索引
        editableTabsValue: 'Index',

        //标签页详情
        editableTabs: [
            {
                title: '首页展示',
                name: 'Index',
            }
        ]
    },
    mutations: {

        //获取菜单
        setMenuList(state,menus){
            state.menuList = menus
            sessionStorage.setItem("menuList",JSON.stringify(menus))
        },

        //获取角色
        setPermList(state,perms){
            state.permList = perms
            sessionStorage.setItem("permList",perms)
        },

        //不重复获取路由
        changeRouterStatus(state,hasRoute){
            state.hasRoute = hasRoute
            sessionStorage.setItem("hasRoute",hasRoute)
        },

        //标签页添加菜单
        addTab(state,tab){
            //判断标签是否已经添加过
            let index = state.editableTabs.findIndex(e => e.name === tab.name)
            if (index === -1 ){
                state.editableTabs.push({
                    title: tab.title,
                    name: tab.name,
                })
            }
            state.editableTabsValue = tab.name
        }


    },

    actions: {

    },

    modules: {

    }
})
