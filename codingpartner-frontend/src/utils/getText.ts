const getQuestionSubmitStatusText = (status: number) => {
  if (status === 0) {
    return '未判题'
  } else if (status === 1) {
    return '判题失败'
  } else if (status === 2) {
    return '判题成功'
  } else {
    return '未知'
  }
}
export { getQuestionSubmitStatusText }
