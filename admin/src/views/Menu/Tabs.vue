<template>
  <div class="Tabs">
    <!--    <el-button size="small" @click="addTab(editableTabsValue)">-->
    <!--      add tab-->
    <!--    </el-button>-->
    <el-tabs
        v-model="editableTabsValue"
        type="card"
        closable
        @tab-remove="removeTab"
        @tab-click="clickTab"
    >
      <el-tab-pane
          v-for="(item, index) in editableTabs"
          :key="item.name"
          :label="item.title"
          :name="item.name"
      >

      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script lang="js">
import {computed, reactive, shallowRef, toRefs} from "vue";
import {useStore} from "vuex";
import {useRouter} from "vue-router";

export default {
  name: "Tabs",
  components: {},
  setup() {
    let data = reactive({})
    const store = useStore()
    const router = useRouter()


    //动态监听当前被激活的标签页
    let editableTabsValue = computed({
          get() {
            return store.state.menus.editableTabsValue
          },
          set(val) {
            store.state.menus.editableTabsValue = val
          }
        }
    )

    //标签页属性
    let editableTabs = computed({
          get() {
            return store.state.menus.editableTabs
          },
          set(val) {
            store.state.menus.editableTabs = val
          }
        }
    )

    //删除标签
    const removeTab = (targetName) => {
      let tabs = JSON.parse(JSON.stringify(store.state.menus.editableTabs))
      if(tabs.length>1){

        let activeName = store.state.menus.editableTabsValue
        console.log(activeName)

        if (activeName === targetName) {
          tabs.forEach((tab, index) => {
            if (tab.name === targetName) {
              let nextTab = tabs[index + 1] || tabs[index - 1]
              if (nextTab) {
                activeName = nextTab.name
              }
            }
          })
        }

        store.state.menus.editableTabsValue = activeName
        store.state.menus.editableTabs = tabs.filter((tab) => tab.name !== targetName)
        router.push({name: activeName})
      }
    }

    //标签点击
    const clickTab = (target) => {
      let targetName = store.state.menus.editableTabsValue
      console.log(store.state.menus.editableTabsValue)

      router.push({name: targetName})
    }

    return {
      ...toRefs(data),

      editableTabsValue,
      editableTabs,
      removeTab,
      clickTab


    }
  },

}
</script>

<style scoped lang="scss">
.Tabs {

}
</style>
