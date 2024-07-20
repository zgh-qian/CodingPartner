/**
 * 数字转带单位
 * @param num   数字
 * @returns    返回带单位
 */
const getNumStr = (num: number) => {
  const kDivider = 1000
  const mDivider = 1000000
  // 数值小于1000时，直接返回数字字符串
  if (num < kDivider) {
    return num.toString()
  } else if (kDivider < num && num < mDivider) {
    // 计算以千为单位的数值
    const kValue = num / kDivider
    // 返回形如 "x.yk" 的字符串，其中 x 是整数部分，y 是小数部分
    return `${kValue.toFixed(1)} k`
  } else {
    const mValue = num / mDivider
    return `${mValue.toFixed(1)} m`
  }
}

export { getNumStr }
