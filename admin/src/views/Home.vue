<template>
  <div class="Home">
    <el-container style="height: 100%">
      <el-header>
        <strong>MarkHub后台管理系统</strong>
        <div class="header-avater">

          <el-dropdown>
            <span class="el-dropdown-link">
              <!--          头像-->
                <el-avatar
                    :src="userInfo.avatar"
                >
                </el-avatar>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <router-link to="/usercenter">
                  <el-dropdown-item icon="el-icon-plus">个人信息</el-dropdown-item>
                </router-link>

                <el-dropdown-item icon="el-icon-circle-plus" @click="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
          <span class="demonstration" style="margin-left: 20px">{{userInfo.username}}</span>

        </div>
      </el-header>

      <!--页面下半部分-->
      <el-container>
        <!--        左侧菜单栏-->
        <el-aside width="200px">
          <SideMenu></SideMenu>
        </el-aside>

        <!--        右侧内容区-->
        <el-main>

          <!-- 面包屑导航 -->
          <Tabs></Tabs>
          <el-card shadow="never" >
            <router-view></router-view>
          </el-card>
        </el-main>
      </el-container>
    </el-container>

  </div>
</template>

<script lang="js">
import {reactive, toRefs, defineComponent, onMounted, ref} from "vue";
import SideMenu from "@/views/Menu/SideMenu";
import {getUserInfoImpl, logoutImpl} from "@/api/http";
import {useRouter} from "vue-router";
import Tabs from "@/views/Menu/Tabs";
import {useStore} from 'vuex'

export default defineComponent({
  name: "Home",
  components: {
    SideMenu,
    Tabs,
  },
  setup() {
    let data = reactive({})

    //导入路由
    const router = useRouter()
    const store = useStore()

    //导入状态管理


    let userInfo=ref({
      id: "",
      username: "",
      avatar: "",

    })


    //得到用户信息
    async function getUserInfo() {
      const {data} = await getUserInfoImpl()
      userInfo.value = data
      console.log(data)
      console.log(userInfo)
    }

    getUserInfo()


    async function logout() {
      //清除缓存和vuex
      const { data } = await logoutImpl()
      localStorage.clear()
      sessionStorage.clear()
      store.commit("resetState")
      await router.push("/login")
    }




    return {
      ...toRefs(data),
      userInfo,
      logout

    }
  },

})
</script>

<style scoped lang="scss">
.Home {
  height: 100%;

}

.header-avater {
  //向右浮动
  float: right;

  //开启交叉轴
  display: flex;
  //间隔分开
  justify-content: space-between;
  //垂直居中
  align-items: center;

}


.el-header{
  background-color: #b3c0d1;
  color: var(--el-text-color-primary);
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #d3dce6;
  color: var(--el-text-color-primary);
  //text-align: center;
  line-height: 200px;
}

.el-main {
  //background-color: #e9eef3;
  //color: var(--el-text-color-primary);
  //text-align: center;
  //line-height: 160px;
  padding: 0;
}
body > .el-container {
  margin-bottom: 40px;
  height: 100%;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}


//头像下拉指针
.el-dropdown-link {
  cursor: pointer;
  color: #409eff;
}

//菜单样式
.el-menu-vertical-demo {
  text-align: left;
}
</style>
