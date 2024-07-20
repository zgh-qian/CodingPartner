import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useQuestionStore = defineStore(
  'question',
  () => {
    // 标签页
    const questionViewTabsKey = ref()
    // 设置标签页
    const setQuestionViewTabsKey = (key: string) => {
      questionViewTabsKey.value = key
    }
    return {
      questionViewTabsKey,
      setQuestionViewTabsKey
    }
  },
  {
    persist: false
  }
)
