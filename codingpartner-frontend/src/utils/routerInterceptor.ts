import router from '@/router'
import USER_ROLE from '@/constant/userRole'
import checkAccess from './checkAccess'
import { useUserStore } from '@/stores/modules/user'
import { Service, User } from '@/api'

// 路由拦截器
router.beforeEach(async (to, from, next) => {
  let user = useUserStore().user
  // 如果未登录
  if (!user || user.role === USER_ROLE.NOT_LOGIN) {
    // 则获取登录用户信息
    const res = await Service.getLoginUserVoUsingGet()
    if (res.code === 200) {
      useUserStore().setUser(res.data as User)
      user = res.data as User
    }
  }
  const needAccess = (to.meta?.access as string) ?? USER_ROLE.NOT_LOGIN
  // 如果需要登录
  if (needAccess !== USER_ROLE.NOT_LOGIN) {
    // 如果未登录
    if (!user || !user.role || user.role === USER_ROLE.NOT_LOGIN) {
      // 则跳转登录页
      next(`/auth/login?redirect=${to.path}`)
      return
    }
    // 如果没有权限
    if (!checkAccess(user, needAccess)) {
      // 则跳转404
      next('/404')
      return
    }
  }
  // 如果路由存在，则跳转，不存在则跳转404
  if (router.hasRoute(to.name as string)) {
    next()
    return
  } else {
    next('/404')
    return
  }
})
