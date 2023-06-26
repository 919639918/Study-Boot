<script setup>

import router from "@/router";
import {ElMessage} from "element-plus";
import {get} from "@/net";

const logout = () => {

  get('api/auth/logout', (message) => {

    ElMessage.success(message)
    router.push('/')
  })

}

</script>

<template>

  <div style="font-family: 楷体,monospace;font-size: 60px;margin-top: 150px; text-align: center">欢迎来到主页面!</div>
  <div style="margin-top: 100px;text-align: center;">

    <el-button @click="logout()" type="success" plain>退出</el-button>
  </div>

</template>

<style scoped>

</style>