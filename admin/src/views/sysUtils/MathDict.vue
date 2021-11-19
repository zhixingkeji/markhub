<template>
  <div class="mathDict">
    这里是数字字典
    测试专用
    <button @click="gourl">测试按钮</button>
  </div>
</template>

<script lang="js">
import {reactive, toRefs} from "vue";
import {gettest} from "@/api/http";

export default {
  name: "MathDict",
  components: {},
  setup() {
    let data = reactive({})
    let gourl = () => {
      gettest()
    }



    return {
      ...toRefs(data),
      gourl
    }
  },

}
</script>

<style scoped lang="scss">
.mathDict {

}
</style>
