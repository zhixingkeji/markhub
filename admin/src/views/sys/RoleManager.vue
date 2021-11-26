<template>
  <div class="RoleManager">
    <!--    行内表单-->
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item label="">
        <el-input v-model="formInline.name" placeholder="请输入名称"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button round>查询</el-button>
        <el-button type="primary" round @click="newMenu()">新增</el-button>
        <el-button type="danger" round>批量删除</el-button>
      </el-form-item>
    </el-form>


    <!--    表格展示-->
    <el-table
        :data="tableData"
        style="width: 100%;margin-bottom: 20px;"
        row-key="id"
        border
        stripe
        default-expand-all
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >


      <!--      名称-->
      <el-table-column prop="name" label="名称"  width="180">
      </el-table-column>

      <!--      标识-->
      <el-table-column prop="code" label="标识"  width="180">
      </el-table-column>

      <!--      描述-->
      <el-table-column prop="remark" label="描述"  >
      </el-table-column>

      <!--      时间-->
      <el-table-column prop="time" label="时间"  width="150">
        <el-tooltip placement="top">
          <template #content> 创建时间: 2020-11-17 20:20:59<br/>修改时间: 2020-11-20 20:20:59</template>
          <i class="el-icon-timer" style="font-size: 30px"/>
        </el-tooltip>
      </el-table-column>


      <!--      状态-->
      <el-table-column prop="statu" label="状态"  width="150">
        <template #default="scope">
          <el-tag type="success" v-if="scope.row.statu === 1">正常</el-tag>
          <el-tag type="danger" v-else-if="scope.row.statu === 0">禁用</el-tag>
        </template>
      </el-table-column>


      <!--操作-->
      <el-table-column prop="edit" label="操作"  width="280">
        <template #default="scope">
          <el-button type="primary" icon="el-icon-edit" @click="editMenu(scope.row)"/>
          <el-button type="success" icon="el-icon-set-up"/>
          <el-popconfirm
              title="确定删除吗？"
              @confirm="confirmEvent"
              @cancel="cancelEvent">
            <template #reference>
              <el-button type="danger" icon="el-icon-delete"></el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>


  </div>
</template>

<script lang="js">
import {reactive, ref, toRefs} from "vue";
import { getRoleTable} from "@/api/http";

export default {
  name: "RoleManager",
  components: {},
  setup() {
    let data = reactive({})
    let formInline = ref({
      name: "",

    })

    //新增,编辑按钮的数据模型
    let editFrom = ref(
        {
          id: '',
          name: '',
          code: "",
          remark: "",
          statu: "",
        })

    let tableData =ref([])


    //获取角色的表格数据
    async function getRoleTableData () {
      const {data} = await getRoleTable()
      tableData.value = data.records
      // console.log(data)
      // console.log(data.records)
      // console.log(tableData.value)
    }
    getRoleTableData()



    return {
      ...toRefs(data),
      formInline,
      editFrom,
      tableData

    }
  },

}
</script>

<style scoped lang="scss">
.RoleManager {

}
</style>
