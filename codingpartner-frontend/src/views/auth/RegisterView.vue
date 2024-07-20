<template>
  <div id="registerView">
    <a-form
      :model="registerForm"
      ref="registerFormRef"
      layout="horizontal"
      @submit="handleRegister"
    >
      <!-- 账号 -->
      <a-form-item
        field="username"
        label="账号"
        :rules="[
          { required: true, message: '请输入账号' },
          { minLength: 4, maxLength: 20, message: '账号必须要是4-20位' }
        ]"
        :validate-trigger="['change', 'input']"
      >
        <a-input v-model="registerForm.username" placeholder="请输入账号" />
      </a-form-item>
      <!-- 密码 -->
      <a-form-item
        field="password"
        label="密码"
        :rules="[
          { required: true, message: '请输入密码' },
          { minLength: 6, maxLength: 20, message: '密码必须要是6-20位' }
        ]"
        :validate-trigger="['change', 'input']"
      >
        <a-input-password
          v-model="registerForm.password"
          placeholder="请输入密码"
        />
      </a-form-item>
      <!-- 昵称 -->
      <a-form-item
        field="nickname"
        label="昵称"
        :rules="[
          { required: true, message: '请输入昵称' },
          { minLength: 2, maxLength: 20, message: '昵称必须要是2-20位' }
        ]"
        :validate-trigger="['change', 'input']"
      >
        <a-input v-model="registerForm.nickname" placeholder="请输入昵称" />
      </a-form-item>
      <!-- 邮箱 -->
      <a-form-item
        field="email"
        label="邮箱"
        :rules="[{ required: true, message: '请输入邮箱' }]"
        :validate-trigger="['change', 'input']"
      >
        <a-input v-model="registerForm.email" placeholder="请输入邮箱" />
      </a-form-item>
      <!-- 注册按钮 -->
      <a-form-item>
        <a-button html-type="submit" type="primary" class="registerBtn">
          注册
        </a-button>
      </a-form-item>
      <!-- 跳转登录页 -->
      <a-form-item>
        已有账号？立刻
        <a-link href="/auth/login" class="login">登录</a-link>
      </a-form-item>
    </a-form>
  </div>
</template>
<script setup lang="ts">
import { reactive, ref } from 'vue'
import message from '@arco-design/web-vue/es/message'
import { Service, UserRegisterRequest } from '@/api'
import { useRouter } from 'vue-router'

const router = useRouter()
const registerFormRef = ref()
/**
 * 登录表单
 */
const registerForm = reactive({
  username: '',
  password: '',
  nickname: '',
  email: ''
} as UserRegisterRequest)
/**
 * 点击登录按钮
 */
const handleRegister = async () => {
  await new Promise((resolve) => {
    //进行验证
    registerFormRef.value.validate(async (r: any) => {
      if (r == void 0) {
        // 验证通过
        // console.log(registerForm)
        const res = await Service.userRegisterUsingPost(registerForm)
        if (res.code === 200) {
          message.success('注册成功')
          // 延时跳转
          setTimeout(() => {
            router.push({
              path: '/auth/login',
              replace: true
            })
          }, 1500)
        } else {
          message.error('注册失败，' + res.message)
        }
        resolve(true)
      } else {
        // 验证不通过
        resolve(false)
      }
    })
  })
}
</script>
<style>
#registerView {
  width: 500px;
}
#registerView .login {
  color: blue;
}
#registerView .registerBtn {
  margin-top: 20px;
  min-width: 150px;
}
.arco-form-item-content-flex {
  justify-content: center;
}
</style>
