<!-- 题目编辑页面 -->
<template>
  <div id="questionEdit">
    <a-space wrap>
      <a-select
        v-model="codeLanguageSelect"
        :bordered="false"
        class="codeLanguageSelect"
      >
        <a-option>cpp</a-option>
        <a-option>java</a-option>
        <a-option>go</a-option>
        <a-option>python</a-option>
      </a-select>
    </a-space>
    <CodeEditor
      :language="codeLanguageSelect"
      :value="code"
      :handle-change="onChange"
      class="codeEditor"
    />
    <a-space wrap class="codeEditorButton">
      <a-popover position="bottom">
        <a-button :loading="runStatus" @click="doRunCode"> 运行 </a-button>
        <template #content>运行代码 </template>
      </a-popover>
      <a-popover position="bottom">
        <a-button
          status="success"
          :loading="submitStatus"
          @click="doSubmitCode"
        >
          提交
        </a-button>
        <template #content>提交代码 </template>
      </a-popover>
    </a-space>
  </div>
</template>
<script setup lang="ts">
import CodeEditor from '@/components/CodeEditor.vue'
import { ref, withDefaults, defineProps } from 'vue'
import { Service } from '@/api'
import message from '@arco-design/web-vue/es/message'
import { useQuestionStore } from '@/stores/modules/question'
import { useRouter } from 'vue-router'
// 获取Props
interface Props {
  id: string
}
const props = withDefaults(defineProps<Props>(), {
  id: () => ''
})
const codeLanguageSelect = ref('java')
const code = ref('')
const runStatus = ref(false)
const submitStatus = ref(false)
const timerId = ref()
const router = useRouter()
// 运行代码
const doRunCode = () => {
  // runStatus.value = true
}
// 提交代码
const doSubmitCode = async () => {
  submitStatus.value = true
  const res = await Service.addQuestionSubmitUsingPost({
    code: code.value,
    language: codeLanguageSelect.value,
    questionId: props.id as any
  })
  if (res.code === 200) {
    const questionSubmitId = res.data
    // 开启轮询
    timerId.value = setInterval(function () {
      getQuestionSubmitById(questionSubmitId)
    }, 1000) // 间隔时间为1000毫秒
  } else {
    message.error('提交失败，' + res.message)
  }
}
// 查看判题状态
const getQuestionSubmitById = async (questionSubmitId: string) => {
  const res = await Service.getQuestionSubmitVoByIdUsingPost({
    id: questionSubmitId as any
  })
  if (res.code === 200) {
    if (res.data.judgeStatus === 2 || res.data.judgeStatus === 3) {
      submitStatus.value = false
      // 停止轮询
      clearInterval(timerId.value)
      // 跳转提交标签页
      const questionStore = useQuestionStore()
      router.push(`/question/${props.id}/submissions/${questionSubmitId}`)
      questionStore.setQuestionViewTabsKey('4')
    }
  }
}
// 获取代码
const onChange = (value: string) => {
  code.value = value
}
</script>
<style scoped>
#questionEdit {
  width: 95%;
  margin: 0 auto;
}
#questionEdit .codeLanguageSelect {
  width: 100%;
}
#questionEdit .codeEditor {
  min-height: 50vh;
}
#questionEdit .codeEditorButton {
  margin-top: 20px;
}
</style>
