import {createRouter, createWebHistory} from 'vue-router'
import store from "@/store";
import axios from "axios";
import $http from "@/api";


const routes = [
    {
        path: '/home',
        name: 'Home',
        component: () => import('@/views/Home.vue'),
        children: [
        ]

    },
    //  重定向登录
    {
        path: '/',
        redirect: 'login'
    },
    {
        path: '/about',
        name: 'About',
        component: () => import('../views/About.vue')
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('@/views/Login.vue')
    },


]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})


router.beforeEach((to, from, next) => {
    let hasRoute = store.state.menus.hasRoute
    let token = localStorage.getItem("token")

    //如果是登录请求直接下一步
    if (to.path === '/login') {
        next()
    } else if (!token) {
        //如果没有token,则要跳转到登录页面
        next({path: '/login'})
    } else if (token && !hasRoute) {
        //如果token存在但是菜单未获取
        $http.get("/sys/menu/nav", {}).then(res => {
            console.log("返回的菜单数据")
            const {data} = res
            // console.log(data.nav)
            store.commit("setMenuList", data.nav)
            routerPackag(data.nav);

            console.log(router.options.routes)

            store.commit("changeRouterStatus", true)
        })
    }


    next()
})


//路由封装递归方法
const routerPackag = nav => {
    let newRoutes = router.options.routes;
    nav.forEach(menu => {
        // console.log("1级目录")
        // console.log(menu)
        menu.children.forEach(e => {
            // console.log("2级菜单")
            // console.log(e)
            let route = {
                // name: e.name,
                path: e.path,
                meta: {
                    icon: e.icon,
                    title: e.title
                }
            }
            route.component = () => import('@/views/' + e.component +'.vue')
            // newRoutes[0].children.push(route)
            router.addRoute("Home",route)
        })
    })


    // router.addRoute("Home", {
    //     path: `${itemRouter.path}`,
    //     name: itemRouter.name,
    //     component: () => import(`@/${itemRouter.component}`)
    // });
    //
    // // 是否存在子集
    // if (itemRouter.children && itemRouter.children.length) {
    //     routerPackag(itemRouter.children);
    // }

};


//导出router
export default router
