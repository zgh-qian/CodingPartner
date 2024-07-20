<!-- 题目详情页面 -->
<template>
  <a-layout id="questionLayout">
    <a-layout-header><GlobalHeader /></a-layout-header>
    <a-layout-content style="max-height: 80vh">
      <a-row>
        <a-col :span="12">
          <div><QuestionView :id="props.id" /></div>
        </a-col>
        <a-col :span="12">
          <div><QuestionEdit :id="props.id" /></div>
        </a-col>
      </a-row>
    </a-layout-content>
    <a-layout-footer style="position: fixed; bottom: 0; width: 100%"
      ><GlobalFooter
    /></a-layout-footer>
  </a-layout>
</template>
<script setup lang="ts">
import GlobalHeader from '@/components/GlobalHeader.vue'
import QuestionView from '@/views/question/layouts/QuestionViewLayout.vue'
import QuestionEdit from '@/views/question/layouts/QuestionEditLayout.vue'
import GlobalFooter from '@/components/GlobalFooter.vue'
import { withDefaults, defineProps } from 'vue'
/**
 * 获取Props
 */
interface Props {
  id: string
}
const props = withDefaults(defineProps<Props>(), {
  id: () => ''
})
</script>
<style scoped></style>
