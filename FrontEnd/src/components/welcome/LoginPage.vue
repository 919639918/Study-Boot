<script setup>
import {Lock, User} from '@element-plus/icons-vue'
import {reactive} from "vue";
import {ElMessage} from "element-plus";
import {post} from "@/net";
import router from "@/router";

const form = reactive(
    {
      username: '',
      password: '',
      remember: false
    }
)
const login = () => {
  if (!form.username || !form.password)
    ElMessage.warning("用户名或密码错误!")
  else {
    post('/api/auth/login', {
      username: form.username,
      password: form.password,
      remember: form.remember
    }, (message) => {

      ElMessage.success(message)
      router.push('/index')
    });
  }
}


</script>

<template>

  <div style="text-align: center;margin: 0 20px">

    <div style="margin-top: 150px">
      <div style="font-family: 黑体,serif; font-weight: bold;font-size: 40px ">登录</div>
    </div>

    <div style="margin-top: 30px">
      <el-input v-model="form.username" type="text" placeholder="用户名">
        <template #prefix>
          <el-icon>
            <User/>
          </el-icon>
        </template>
      </el-input>
    </div>
    <div style="margin-top: 20px">
      <el-input v-model="form.password" type="password" placeholder="密码">
        <template #prefix>
          <el-icon>
            <Lock/>
          </el-icon>
        </template>
      </el-input>
    </div>

    <el-row>
      <el-col :span="12" style="text-align: left">
        <el-checkbox v-model="form.remember" label="记住密码" size="large"/>
      </el-col>
      <el-col :span="12" style="text-align: right">
        <el-link>忘记密码?</el-link>
      </el-col>
    </el-row>

    <div style="margin-top: 30px">
      <el-button @click="login()" style=" width: 250px" type="primary" plain>登录</el-button>
    </div>

    <el-divider><span style="color: gray;font-size: 1px">没有账号</span></el-divider>

    <div style="margin-top: 10px">
      <el-button @click="router.push('/register')" style=" width: 250px" type="primary" plain>注册</el-button>
    </div>

  </div>

</template>

<style scoped>


</style>