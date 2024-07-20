import USER_ROLE from '@/constant/userRole'

/**
 * 检查是否有足够的权限
 * @param loginUser
 * @param needAccess
 * @return boolean 有无权限
 */
const checkAccess = (user: any, needAccess = USER_ROLE.NOT_LOGIN) => {
  // 登录用户权限
  const userAccess = user?.role ?? USER_ROLE.NOT_LOGIN
  // 如果不用登录
  if (needAccess === USER_ROLE.NOT_LOGIN) {
    return true
  }
  // 如果需要用户权限并且没有登录
  if (needAccess === USER_ROLE.USER && userAccess === USER_ROLE.NOT_LOGIN) {
    return false
  }
  // 如果需要管理员权限并且不是管理员
  if (needAccess === USER_ROLE.ADMIN && userAccess !== USER_ROLE.ADMIN) {
    return false
  }
  return true
}

export default checkAccess
