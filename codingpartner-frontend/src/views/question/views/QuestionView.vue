<!-- 题目查看页面 -->
<template>
  <div id="questionView">
    <a-scrollbar style="max-height: 69vh; overflow: auto">
      <!-- 题目标题 -->
      <a-card v-if="question" :title="question.title">
        <template #extra>
          <a-space wrap>
            <!-- 难度 -->
            <a-tag :color="getDifficultyColor(question.difficulty as any)">
              {{ question.difficulty }}
            </a-tag>
            <!-- 状态 -->
            <a-tag :color="getDifficultyColor(question.difficulty as any)">
              未作答
            </a-tag>
          </a-space>
        </template>
        <!-- 标签 -->
        <a-space wrap>
          <a-tag
            v-for="(tag, index) of JSON.parse(question.tags as any) || []"
            :key="index"
            :color="getTagColor(tag)"
          >
            {{ tag }}
          </a-tag>
        </a-space>
        <!-- 内容 -->
        <MdViewer :value="question?.content || ''" />
        <!-- 判题信息 -->
        <a-descriptions title="判题信息" :column="{ xs: 1, md: 2, lg: 3 }">
          <!-- 时间限制 -->
          <a-descriptions-item label="时间限制">
            {{ question.judgeConfig?.timeLimit ?? 0 }}
          </a-descriptions-item>
          <!-- 内存限制 -->
          <a-descriptions-item label="内存限制">
            {{ question.judgeConfig?.memoryLimit ?? 0 }}
          </a-descriptions-item>
          <!-- 堆栈限制 -->
          <a-descriptions-item label="堆栈限制">
            {{ question.judgeConfig?.stackLimit ?? 0 }}
          </a-descriptions-item>
        </a-descriptions>
      </a-card>
    </a-scrollbar>
  </div>
</template>
<script setup lang="ts">
import { ref, withDefaults, defineProps, onMounted } from 'vue'
import message from '@arco-design/web-vue/es/message'
import MdViewer from '@/components/MdViewer.vue'
import { getDifficultyColor, getTagColor } from '@/utils/index'
import { Service, QuestionVO } from '@/api'

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
  getQuestionVO()
})
</script>
<style \>
#questionView {
  max-width: 1400px;
}
#questionView .arco-space-horizontal .arco-space-item {
  margin-bottom: 0 !important;
}
</style>
