import axios from "axios"
import { ElLoading,ElMessage } from 'element-plus'
import router from "@/router";


//封装axios
const $http = axios.create({
    baseURL: 'http://localhost:8090',
    timeout: 5000,
    headers: {
        'Content-Type': "application/json; charset=utf-8"
    }
})


//等待动画
let loading;
const startLoading=()=>{
    const options = {
        lock: true,
        text: "加载中。。。",
        background: 'rgba(0,0,0,0.7)'
    }
    loading = ElLoading.service(options);
}
const endLoading = () =>{
    loading.close();
}


//请求拦截
$http.interceptors.request.use(config=>{
    startLoading();

    //每次请求携带jwt的token
    config.headers['Authorization'] = localStorage.getItem("token")

    return config;
})

//响应拦截
$http.interceptors.response.use(response=>{
    endLoading();
    let res = response.data;

    if(res.code === 200){
        ElMessage.success("请求成功")
        return res;
    }else {
        ElMessage.error(!res.msg ? "系统异常" : res.msg)
        return Promise.reject(response.data.msg)
    }

},error =>{
    endLoading();

    //如果出现错误
    if(error.response.data){
        error.message = error.response.data.msg;
    }

    //如果没有权限
    if(error.response.status === 401){
        router.push("/login")
    }

    ElMessage.error(error.message)
    return Promise.reject(error);
})


export default $http;
