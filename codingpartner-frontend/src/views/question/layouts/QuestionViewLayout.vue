<!-- 题目查看页面 -->
<template>
  <div id="questionViewLayout">
    <a-tabs
      type="rounded"
      class="tabs"
      :activeKey="activeKey"
      @change="handleTabChange"
      v-if="question"
    >
      <a-tab-pane key="1">
        <template #title> <icon-question /> 题目 </template>
        <router-view />
      </a-tab-pane>
      <a-tab-pane key="2">
        <template #title> <icon-file />评论 </template>
        <router-view />
      </a-tab-pane>
      <a-tab-pane key="3">
        <template #title> <icon-book />答案 </template>
        <router-view />
      </a-tab-pane>
      <a-tab-pane key="4">
        <template #title> <icon-user />提交记录 </template>
        <router-view />
      </a-tab-pane>
    </a-tabs>
    <GlobalButton
      v-if="activeKey !== '2' && activeKey !== '3' && activeKey !== '4'"
      :isThumb="false"
      :isFavour="false"
      :thumbCount="question?.thumbCount"
      :favourCount="question?.favourCount"
      :commentCount="question?.commentCount"
      :doThumb="doThumb"
      :doComment="doComment"
      :doFavour="doFavour"
      class="questionButton"
    />
  </div>
</template>
<script setup lang="ts">
import { ref, withDefaults, defineProps, onMounted } from 'vue'
import message from '@arco-design/web-vue/es/message'
import {
  IconQuestion,
  IconFile,
  IconBook,
  IconUser
} from '@arco-design/web-vue/es/icon'
import GlobalButton from '@/components/GlobalButton.vue'
import { computed } from 'vue'
import { Service, QuestionVO } from '@/api'
import { useQuestionStore } from '@/stores/modules/question'
import { useRouter, useRoute } from 'vue-router'

const route = useRoute()
const router = useRouter()
const questionStore = useQuestionStore()
// 标签页
const handleTabChange = (key: string | number) => {
  questionStore.setQuestionViewTabsKey(key.toString())
  if (key === 1 || key === '1') {
    router.push(`/question/${props.id}`)
  } else if (key === 2 || key === '2') {
    router.push(`/question/${props.id}/comment`)
  } else if (key === 3 || key === '3') {
    router.push(`/question/${props.id}/answer`)
  } else if (key === 4 || key === '4') {
    router.push(`/question/${props.id}/submissions`)
  }
}
const activeKey = computed(() => questionStore.questionViewTabsKey)
// 获取Props
interface Props {
  id: string
}
const props = withDefaults(defineProps<Props>(), {
  id: () => ''
})
const question = ref<QuestionVO>()
// 获取题目
const getQuestionVO = async () => {
  if (props.id !== null && props.id !== '') {
    const res = await Service.getQuestionByIdUsingGet(props.id as any)
    if (res.code === 200) {
      question.value = res.data as QuestionVO
    } else {
      message.error('获取题目失败，' + res.message)
    }
  }
}
onMounted(() => {
  const fullPath = route.fullPath
  if (fullPath.includes('comment')) {
    questionStore.setQuestionViewTabsKey('2')
  } else if (fullPath.includes('answer')) {
    questionStore.setQuestionViewTabsKey('3')
  } else if (fullPath.includes('submissions')) {
    questionStore.setQuestionViewTabsKey('4')
  } else {
    questionStore.setQuestionViewTabsKey('1')
  }
  router.push(fullPath)
  getQuestionVO()
})
// 点赞
const doThumb = () => {
  console.log('点赞')
}
// 评论
const doComment = () => {
  questionStore.setQuestionViewTabsKey('2')
  router.push(`/question/${props.id}/comment`)
}
// 收藏
const doFavour = () => {
  console.log('收藏')
}
</script>
<style \>
#questionViewLayout {
  max-width: 1400px;
  max-height: 60vh;
}
#questionViewLayout .tabs {
  max-width: 700px;
  margin: 0 auto;
}
#questionViewLayout .arco-space-horizontal .arco-space-item {
  margin-bottom: 0 !important;
}
#questionViewLayout .questionButton {
  max-width: 700px;
  margin: 0 auto;
}
</style>
