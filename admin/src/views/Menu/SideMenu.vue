<template>
  <div class="SideMenu">
    <el-menu
        :uniqueOpened="true"
        :default-active="store.state.menus.editableTabsValue"
        class="el-menu-vertical-demo"
        @open="handleOpen"
        @close="handleClose"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b"
    >
      <!--  1级菜单-->
      <el-submenu :index="menu.id" v-for="menu in menuList" style="width: 200px">

        <template #title>
          <i :class="menu.icon"></i>
          <span>{{ menu.title }}</span>
        </template>
        <!--        2级菜单-->
        <router-link :to="item.path" v-for="item in menu.children">
          <el-menu-item
              :index="item.id"
              style="width: 200px"
              @click="selectMenu(item)">
            <template #title>
              <i :class="item.icon"></i>
              <span>{{ item.title }}</span>
            </template>
          </el-menu-item>
        </router-link>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script lang="js">
import {computed, reactive, ref, toRefs} from "vue";
import {useStore} from "vuex";


export default {
  name: "SideMenu",
  components: {},
  setup() {
    const store = useStore()


    //动态监测
    let menuList = computed({
      get() {
        return store.state.menus.menuList
      }
    })


    let data = reactive({})

    const handleOpen = (key, keyPath) => {
      console.log(key, keyPath);
    };
    const handleClose = (key, keyPath) => {
      console.log(key, keyPath);
    };


    const selectMenu = (item)=>{
      store.commit("addTab",item)
    }


    return {
      ...toRefs(data),
      handleOpen,
      handleClose,
      menuList,
      selectMenu,
      store
    }
  },

}
</script>

<style scoped lang="scss">
.SideMenu {

}
</style>
