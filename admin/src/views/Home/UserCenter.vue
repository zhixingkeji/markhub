<template>
  <div class="UserCenter">
    这里可以修改个人信息
  </div>
</template>

<script lang="js">
import {reactive, toRefs} from "vue";

export default {
  name: "UserCenter",
  components: {},
  setup() {
    let data = reactive({})


    return {
      ...toRefs(data)
    }
  },

}
</script>

<style scoped lang="scss">
.UserCenter {

}
</style>
