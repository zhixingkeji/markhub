<template>
  <div class="UserManager">
    <!--    行内表单-->
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item label="">
        <el-input v-model="formInline.name" placeholder="搜索用户名"></el-input>
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

      <!--      头像-->
      <el-table-column prop="avater" label="头像"  width="180">
      </el-table-column>

      <!--      拥有角色-->
      <el-table-column prop="hasRoles" label="拥有角色"  >
      </el-table-column>

      <!--      邮箱-->
      <el-table-column prop="email" label="邮箱"  width="180">
      </el-table-column>

      <!--      手机号-->
      <el-table-column prop="phoneNum" label="手机号"  width="180">
      </el-table-column>

      <!--      时间-->
      <el-table-column prop="time" label="时间"  width="150">
        <el-tooltip placement="top">
          <template #content> 创建时间: 2020-11-17 20:20:59<br/>修改时间: 2020-11-20 20:20:59</template>
          <i class="el-icon-timer" style="font-size: 30px"/>
        </el-tooltip>
      </el-table-column>


      <!--      状态-->
      <el-table-column prop="status" label="状态"  width="150">
        <template #default="scope">
          <el-tag type="success" v-if="scope.row.status === 1">正常</el-tag>
          <el-tag type="danger" v-else-if="scope.row.status === 0">禁用</el-tag>
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
import { getUserTable} from "@/api/http";

export default {
  name: "UserManager",
  components: {},
  setup() {
    let data = reactive({})

    //表单数据模型
    let formInline = ref({
      name: "",
    })

    //编辑数据模型
    let editFrom = ref(
        {
          name: '',
          avater: '',
          hasRoles: "",
          email: "",
          phoneNum: "",
          status: "",
        })

    //表格数据模型
    let tableData =ref([])

    //获取角色的表格数据
    async function getUserTableData () {
      const {data} = await getUserTable()
      tableData.value = data
      console.log(tableData)
    }
    getUserTableData()


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
.UserManager {

}
</style>
