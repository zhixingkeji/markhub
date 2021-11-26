<template>
  <div class="MenuManager">
    <!--    行内表单-->
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item label="名称">
        <el-input v-model="formInline.name" placeholder="请输入名称"></el-input>
      </el-form-item>
      <el-form-item label="类型">
        <el-select v-model="formInline.type" placeholder="请选择类型">
          <el-option label="目录" value="dir"></el-option>
          <el-option label="菜单" value="menu"></el-option>
          <el-option label="按钮" value="button"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" >查询</el-button>
        <el-button type="primary" @click="newMenu()">新增</el-button>

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
      <el-table-column prop="name" label="名称" sortable width="180">
      </el-table-column>

      <!--      权限-->
      <el-table-column prop="perms" label="权限" sortable width="180">
      </el-table-column>

      <!--      图标-->
      <el-table-column prop="icon" label="图标">
        <template #default="scope">
          <i :class="scope.row.icon"></i>
          <span>{{"\xa0"+"\xa0"+"\xa0" + scope.row.icon}}</span>
        </template>
      </el-table-column>

      <!--      类型-->
      <el-table-column prop="type" label="类型">
        <template #default="scope">
          <el-tag type="info" v-if="scope.row.type===0">目录</el-tag>
          <el-tag v-if="scope.row.type===1">菜单</el-tag>
          <el-tag type="warning" v-if="scope.row.type === 2">按钮</el-tag>
        </template>
      </el-table-column>

      <!--      链接-->
      <el-table-column prop="path" label="接口">
      </el-table-column>

      <!--      排序-->
      <el-table-column prop="ordernum" label="排序">
      </el-table-column>

      <!--      时间-->
      <el-table-column prop="time" label="时间">
        <el-tooltip placement="top">
          <template #content> 创建时间: 2020-11-17 20:20:59<br />修改时间: 2020-11-20 20:20:59 </template>
          <i class="el-icon-timer" style="font-size: 30px"/>
        </el-tooltip>
      </el-table-column>


      <!--      状态-->
      <el-table-column prop="statu" label="状态">
        <template #default="scope">
          <el-tag type="success" v-if="scope.row.statu === 1">正常</el-tag>
          <el-tag type="danger" v-else-if="scope.row.statu === 0">禁用</el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="edit" label="操作">
        <template #default="scope">
          <el-button type="primary" icon="el-icon-edit" @click="editMenu(scope.row)"/>
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


    <!--    新增按钮的对话框-->
    <el-dialog
        title="菜单管理"
        v-model="dialogVisible"
        width="30%"
        :before-close="handleClose"
        @open="onOpen"
    >
      <el-form
          :model="editFrom"
          ref="editRef"
          label-width="100px"
          class="demo-ruleForm"
      >
        <el-row :gutter="10">
         <el-col :span="24">
           <el-form-item label="上级菜单">
             <el-cascader
                 v-model="value"
                 :options="tableData"
                 @change="cascaderHandleChange"
                 :props="{ checkStrictly: true }"
                 placeholder="请选择目录或菜单"
                 clearable
             ></el-cascader>
           </el-form-item>
         </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="名称">
              <el-input v-model="editFrom.name" placeholder="输入名称"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="权限">
              <el-input v-model="editFrom.perm" placeholder="输入权限"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="图标">
              <el-input v-model="editFrom.icon" placeholder="输入图标"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="接口">
              <el-input v-model="editFrom.url" placeholder="输入接口"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="类型">
          <el-radio-group v-model="editFrom.type">
            <el-radio label="目录"></el-radio>
            <el-radio label="菜单"></el-radio>
            <el-radio label="按钮"></el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="状态">
          <el-switch v-model="editFrom.statu"></el-switch>
        </el-form-item>

        <el-form-item label="排序">
          <el-input-number
              v-model="editFrom.order"
              @change="handleChange"
              :min="1"
              :max="10"
              label="描述文字"
          ></el-input-number>
        </el-form-item>

      </el-form>


      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="js">
import {defineComponent, getCurrentInstance, reactive, ref, toRefs, watch} from "vue";
import {ElMessageBox} from 'element-plus';
import {getMenuTable} from '@/api/http'

export default defineComponent({
  name: "MenuManager",
  components: {},
  setup() {
    let value = ""

    //新增按钮的数据模型
    let editFrom = ref({
      id: "",
      parentId: "",
      time: {
        create_time: "",
        modify_time: "",
      },
      name: "",
      perm: "",
      icon: "",
      url: "",
      type: 0,
      statu: 0,
      order: "",

    })

    //弹窗变量
    let dialogVisible = ref(false)

    //弹窗关闭


    //表单数据
    let formInline = ref({
      name: '',
      type: '',
    })

    //新增按钮点击
    const newMenu = ()=>{
      dialogVisible.value = true
      editFrom.value = {
        id: "",
        parentId: "",
        time: {
          create_time: "",
          modify_time: "",
        },
        name: "",
        perm: "",
        icon: "",
        url: "",
        type: 0,
        status: 0,
        order: "",

      }
    }

    //表单打开回调
    const onOpen = ()=>{

    }

    //提交按钮
    const onSubmit = () => {
      console.log('submit!')
    }

    //表格数据
    let tableData = ref([])

    //排序改变
    const handleChange = (value) => {
      console.log(value)

    }

    //菜单编辑
    const editMenu = (item)=>{
      dialogVisible.value = true
      editFrom.value = {
        name: item.name,
        id: item.id,
        parentId: item.parentId,
        time: {
          create_time: item.create_time,
          modify_time: item.modify_time,
        },

        perm: item.perm,
        icon: item.icon,
        url: item.url,
        type: item.type,
        status: item.status,
        order: item.order,
      }




    }

    //删除确定
    const confirmEvent = () => {
      console.log('confirm!')
    }

    //删除取消
    const cancelEvent = () => {
      console.log('cancel!')
    }

    //获取菜单的表格数据
    async function getMenuTableData () {
      const {data} = await getMenuTable()
      tableData.value = data
      console.log(tableData)
    }
    getMenuTableData()


    return {
      onSubmit,
      formInline,
      tableData,
      dialogVisible,
      // handleClose,
      onOpen,
      handleChange,
      editFrom,
      value,
      confirmEvent,cancelEvent,
      editMenu,
      newMenu

    }
  },

})
</script>

<style scoped lang="scss">
.MenuManager {

}
</style>
