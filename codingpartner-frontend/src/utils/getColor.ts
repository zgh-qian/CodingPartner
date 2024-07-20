/**
 * 根据难度获取颜色
 * @param difficulty 难度
 * @returns 颜色
 */
const getDifficultyColor = (difficulty: string) => {
  if (difficulty === '简单') {
    return 'green'
  } else if (difficulty === '中等') {
    return 'orange'
  } else if (difficulty === '困难') {
    return 'red'
  } else {
    return 'green'
  }
}
/**
 * 根据标签获取颜色
 * @param tag 标签
 * @returns 颜色
 */
const getTagColor = (tag: string) => {
  if (tag.includes('c')) {
    return 'green'
  } else if (tag.includes('java')) {
    return 'red'
  } else if (tag.includes('python')) {
    return 'blue'
  } else if (tag.includes('go')) {
    return 'purple'
  } else if (tag.includes('栈')) {
    return 'gold'
  } else if (tag.includes('队列')) {
    return 'purple'
  } else if (tag.includes('哈希表')) {
    return 'orange'
  } else if (tag.includes('树')) {
    return 'lime'
  } else if (tag.includes('二分')) {
    return 'cyan'
  } else {
    return 'green'
  }
}
/**
 * 根据编程语言获取颜色
 * @param language 编程语言
 * @returns 颜色
 */
const getLanguageColor = (language: string) => {
  if (language === 'c') {
    return 'green'
  } else if (language === 'java') {
    return 'red'
  } else if (language === 'python') {
    return 'blue'
  } else if (language === 'go') {
    return 'purple'
  } else {
    return 'green'
  }
}
/**
 * 根据提交状态获取颜色
 * @param status 题目状态
 * @returns 颜色
 */
const getQuestionSubmitStatusColor = (status: number) => {
  if (status === 0) {
    return 'cyan'
  } else if (status === 1) {
    return 'red'
  } else if (status === 2) {
    return 'green'
  } else {
    return 'green'
  }
}
/**
 * 根据判题信息获取颜色
 * @param status 题目状态
 * @returns 颜色
 */
const getQuestionSubmitJudgeInfoColor = (status: string) => {
  if (status === 'AC') {
    return 'green'
  } else if (status === 'ERROR') {
    return 'red'
  } else if (status === 'TLE') {
    return 'orange'
  } else if (status === 'mle') {
    return 'purple'
  } else if (status === 're') {
    return 'gold'
  } else {
    return 'green'
  }
}
/**
 *  根据用户角色获取颜色
 * @param role  角色
 * @returns  颜色
 */
const getUserRoleColor = (role: string) => {
  if (role === 'admin') {
    return 'red'
  } else if (role === 'user') {
    return 'green'
  } else {
    return 'green'
  }
}
const getUserLevel = (level: string) => {
  if (level === '上忍') {
    return 'red'
  } else if (level === '中忍') {
    return 'orange'
  } else if (level === '下忍') {
    return 'green'
  } else {
    return 'green'
  }
}
export {
  getDifficultyColor,
  getTagColor,
  getLanguageColor,
  getQuestionSubmitStatusColor,
  getQuestionSubmitJudgeInfoColor,
  getUserRoleColor,
  getUserLevel
}
