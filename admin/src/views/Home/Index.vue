<template>
  <div class="Index">
   这是index页面
  </div>
</template>

<script lang="js">
import {reactive, toRefs, defineComponent} from "vue";

export default defineComponent({
  name: "Index",
  components: {

  },
  setup() {
    let data = reactive({})




    return {
      ...toRefs(data),

    }
  },

})
</script>

<style scoped lang="scss">
</style>
