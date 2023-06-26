<script setup>

import {EditPen, Lock, Message, User} from '@element-plus/icons-vue'
import {reactive, ref} from "vue";
import router from "@/router";
import {ElMessage} from "element-plus";
import {post} from "@/net";

const form = reactive(
    {
      username: '',
      password: '',
      re_password: '',
      email: '',
      code: ''
    }
)

const validateUserName = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('用户名不能为空!'))
  } else if (!/^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(value)) {
    callback(new Error("用户名只能包含中文、英文、数字"))
  } else {
    callback()
  }
}

const validatePassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== form.password) {
    callback(new Error("两次输入密码不一致!"))
  } else {
    callback()
  }
}

const rules = {
  username: [
    {validator: validateUserName, trigger: ['blur', 'change']},
    {min: 2, max: 8, message: '长度要在2-8个字符之间', trigger: ['blur', 'change']},
  ],
  password: [
    {required: true, message: '请输入密码!', trigger: ['blur']},
    {min: 6, max: 12, message: '长度要在6-12个字符之间', trigger: ['blur', 'change']}
  ],
  re_password: [
    {validator: validatePassword, trigger: ['blur', 'change']}
  ],
  email: [
    {required: true, message: '请输入电子邮件地址!', trigger: ['blur']},
    {type: 'email', message: '请输入正确电子邮件地址', trigger: ['blur', 'change']}
  ],
  code: [
    {required: true, message: '请输入验证码!', trigger: ['blur']}
  ]
}

const formRef = ref()
const isEmailValid = ref(false);
const onValidate = (prop, isValid) => {
  if (prop === 'email')
    isEmailValid.value = isValid
}

const register = () => {
  formRef.value.validate((isValid) => {
    if (isValid) {

    } else {
      ElMessage.warning('请把信息填写完整!')
    }
  })
}

const validateEmail = () => {
  post("/api/auth/valid-email",
      {
        email: form.email
      },
      (message) => {
        ElMessage.success(message)
      }
  )

}


</script>

<template>

  <div style="text-align: center;margin: 0 20px">

    <div style="margin-top: 150px">
      <div style="font-family: 黑体,serif; font-weight: bold;font-size: 40px">注册</div>
    </div>

    <div style="margin-top: 30px">

      <el-form :model="form" :rules="rules" @validate="onValidate" ref="formRef">

        <el-form-item prop="username">
          <el-input v-model="form.username" type="text" placeholder="用户名">
            <template #prefix>
              <el-icon>
                <User/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>

        <el-form-item prop="password">
          <el-input v-model="form.password" type="password" placeholder="密码">
            <template #prefix>
              <el-icon>
                <Lock/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>

        <el-form-item prop="re_password">
          <el-input v-model="form.re_password" type="password" placeholder="重复密码">
            <template #prefix>
              <el-icon>
                <Lock/>
              </el-icon>
            </template>
          </el-input>

        </el-form-item>

        <el-form-item prop="email">
          <el-input type="email" v-model="form.email" placeholder="电子邮箱">
            <template #prefix>
              <el-icon>
                <Message/>
              </el-icon>
            </template>
          </el-input>

        </el-form-item>

        <el-form-item prop="code">
          <el-row :gutter="4" style="width: 100%">
            <el-col :span="16">
              <el-input type="text" v-model="form.code" placeholder="验证码">
                <template #prefix>
                  <el-icon>
                    <EditPen/>
                  </el-icon>
                </template>
              </el-input>
            </el-col>
            <el-col :span="8">
              <el-button @click="validateEmail" :disabled="!isEmailValid" style="width: 115px">获取验证码</el-button>
            </el-col>
          </el-row>
        </el-form-item>

      </el-form>

    </div>

    <div style="margin-top: 30px">
      <el-button style=" width: 250px" type="primary" @click="register" plain>注册</el-button>
    </div>
    <div style="font-size: 13px;margin-top: 30px">已有账号?
      <el-link @click="router.push('/')" type="primary" style="translate: 0 -2px">立即登录</el-link>
    </div>

  </div>

</template>

<style scoped>

</style>