<template>
  <div class="Login">
    <el-row class="row-bg" justify="center">
      <el-col :span=11>
        <div class="grid-content" style='margin-left: 60%'>
          <h2>markhub后台管理系统</h2>
          <img
            src='@/assets/img/loginlogo.jpeg'
            alt='扫描二维码登录'
            style='height: 300px;width: 200px;'

          >
        </div>
      </el-col>

      <el-col :span="1">
        <div class="grid-content bg-purple-light">
          <el-divider direction="vertical"
                      style='height: 300px;'
          ></el-divider>
        </div>
      </el-col>

      <el-col :span="11" >
        <div class="grid-content" style='margin-right: 60%'>

          <el-form
            :model="ruleForm"
            :rules="rules"
            ref="formRef"
            label-width="100px"
            class="demo-ruleForm"
          >
            <el-form-item label="账号" prop="username" style='width: 380px;'>
              <el-input v-model="ruleForm.username" placeholder="请输入账号"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password" style='width: 380px;'>
              <el-input v-model="ruleForm.password" placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-form-item label="验证码" prop="verifyCode" style='width: 380px;'>
              <el-input v-model="ruleForm.verifyCode" placeholder="请输入验证码" style='float: left;width: 150px'></el-input>
              <img :src='catchImg' alt='验证码' class='yanzhengma' style='width: 120px'>
            </el-form-item>

            <el-form-item style='width: 380px;'>
              <el-button type="primary" @click="submitForm()"
              >立即创建
              </el-button
              >
              <el-button @click="resetForm()">重置</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-col>
    </el-row>

  </div>
</template>

<script lang="js">
import {getCurrentInstance, onMounted, reactive, ref, toRefs} from "vue";
import {getImg} from "@/api/http";
import {useRouter} from "vue-router";
import qs from "qs";
import {useStore} from "vuex";
import axios from "axios";


export default {
  name: "Login",
  components: {

  },


  setup() {

    const router = useRouter()
    const store = useStore()

    const formRef = ref(null)
    let catchImg = ref(null)
    const ruleForm = reactive({
      username: "admin",
      password: "111111",
      verifyCode: "",
      randomCode: ""
    })

    const submitForm = () => {
      formRef.value.validate((valid) => {
        if (valid) {
          axios.post('http://localhost:8090/login?'+ qs.stringify(ruleForm)).then( res => {
            console.log("登录成功后的结果:")
            console.log(res)
            const jwt = res.headers.authorization
            console.log("返回的jwt:")
            console.log(jwt)
            store.commit('SET_TOKEN', jwt)
            router.push("/home")
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
    const resetForm = () => {
      formRef.value.resetFields()
    }



    async function getCatchImg(){
      // 结构请求来的data
      const {data} = await getImg()
      ruleForm.randomCode = data.randomCode
      catchImg.value = data.captchaImg
      console.log(data)

    }

    getCatchImg()

    const rules =  {
      username: [
        { required: true, message: '账号不能为空', trigger: 'blur' },
        {
          min: 4,
          max: 20,
          message: '长度在 4 到 20 个字符',
          trigger: 'blur',
        }
      ],
      password: [
        { required: true, message: '密码不能为空', trigger: 'blur' },
        {
          min: 4,
          max: 20,
          message: '长度在 4 到 20 个字符',
          trigger: 'blur',
        }
      ],
      verifyCode: [
        { required: true, message: '验证码不能为空', trigger: 'blur' },
        {
          min: 4,
          max: 4,
          message: '验证码不正确',
          trigger: 'blur',
        }
      ]
    }




    return {

      ruleForm,
      rules,
      submitForm,
      resetForm,
      formRef,
      catchImg,
      getCatchImg,
    }
  },

}
</script>

<style scoped lang="scss">
html{
  background-color: #fafafa;
  height: 100%;
  width: 100%;
}

.Login {
  background-color: #fafafa;
  height: 100%;
  width: 100%;


  .el-row{
    background-color: #fafafa;
    height: 100%;
    width: 100%;
    display: flex;

    //垂直居中
    align-items: center;

    //文字居中
    text-align: center;

  }
}

.yanzhengma{
  width: 70px;
  height: 40px;
  float: left;
  margin-left: 7px;
}

</style>
