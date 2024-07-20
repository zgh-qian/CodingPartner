<template>
  <div id="loginView">
    <a-form
      :model="loginForm"
      ref="loginFormRef"
      layout="horizontal"
      @submit="handleLogin"
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
        <a-input v-model="loginForm.username" placeholder="请输入账号" />
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
          v-model="loginForm.password"
          placeholder="请输入密码"
        />
      </a-form-item>
      <!-- 重复密码 -->
      <a-form-item
        field="rePassword"
        label="重复密码"
        :rules="[
          { required: true, message: '请重复输入密码' },
          { minLength: 6, maxLength: 20, message: '重复密码必须要是6-20位' }
        ]"
        :validate-trigger="['change', 'input']"
      >
        <a-input-password
          v-model="loginForm.rePassword"
          placeholder="请重复输入密码"
        />
      </a-form-item>
      <!-- 登录按钮 -->
      <a-form-item>
        <a-button html-type="submit" type="primary" class="loginBtn"
          >登录</a-button
        >
      </a-form-item>
      <!-- 跳转注册页 -->
      <a-form-item>
        没有账号？立刻
        <a-link href="/auth/register" class="register">注册</a-link>
      </a-form-item>
    </a-form>
  </div>
</template>
<script setup lang="ts">
import { reactive, ref } from 'vue'
import { UserLoginRequest, Service } from '@/api'
import { useUserStore } from '../../stores/modules/user'
import message from '@arco-design/web-vue/es/message'
import { useRouter } from 'vue-router'

const router = useRouter()
const loginFormRef = ref()
/**
 * 登录表单
 */
const loginForm = reactive({
  username: '',
  password: '',
  rePassword: ''
} as UserLoginRequest)
/**
 * 点击登录按钮
 */
const handleLogin = async () => {
  await new Promise((resolve) => {
    //进行验证
    loginFormRef.value.validate(async (r: any) => {
      if (r == void 0) {
        // 验证通过
        // console.log(loginForm)
        const res = await Service.userLoginUsingPost(loginForm)
        if (res.code === 200) {
          const userStore = useUserStore()
          userStore.setUser(res.data)
          message.success('登录成功')
          // 延时跳转
          setTimeout(() => {
            router.push({
              path: '/user',
              replace: true
            })
          }, 1500)
        } else {
          message.error('登录失败,' + res.message)
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
#loginView {
  width: 500px;
}
#loginView .register {
  color: blue;
}
#loginView .loginBtn {
  margin-top: 20px;
  min-width: 150px;
}
.arco-form-item-content-flex {
  justify-content: center;
}
</style>
