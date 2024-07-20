<template>
  <div id="questionSubmit">
    <a-scrollbar style="max-height: 69vh; overflow: auto">
      <a-link @click="doSubmissions">
        全部提交记录
        <template #icon>
          <icon-arrow-right />
        </template>
      </a-link>
    </a-scrollbar>
  </div>
</template>
<script setup lang="ts">
import { withDefaults, defineProps } from 'vue'
import { IconArrowRight } from '@arco-design/web-vue/es/icon'
import { useRouter } from 'vue-router'
// 获取Props
interface Props {
  id: string
  questionSubmitId: string
}
const props = withDefaults(defineProps<Props>(), {
  id: () => '',
  questionSubmitId: () => ''
})
const router = useRouter()
const doSubmissions = () => {
  router.push(`/question/${props.id}/submissions`)
}
</script>
<style scoped>
#questionSubmit {
  margin: 0 auto;
}
#questionSubmit .search {
  margin-top: 15px;
  margin-bottom: 15px;
  padding: 10px;
}
#questionSubmit .table {
  margin-bottom: 50px;
}
#questionSubmit .link {
  text-decoration: none;
  color: #000;
}
#questionSubmit .link:hover {
  color: blue;
}
</style>
