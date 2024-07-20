import USER_ROLE from '@/constant/userRole'
import { defineStore } from 'pinia'
import { ref } from 'vue'
import { User } from '@/api'

export const useUserStore = defineStore(
  'user',
  () => {
    // 角色
    const user = ref<User>({
      // 默认没有登录
      role: USER_ROLE.NOT_LOGIN
    })
    const setUser = (loginUser: User) => {
      user.value = loginUser
    }
    return {
      user,
      setUser
    }
  },
  {
    persist: false
  }
)
