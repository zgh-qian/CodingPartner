<!-- 评论组件 -->
<!-- 示例：
<GlobalComment
  :comment-user="commentUser"
  :comment-content="commentContent"
  :user="user"
/>
-->
<template>
  <a-comment
    align="right"
    :author="props.commentUser.username"
    :avatar="props.commentUser.avatar"
    :content="props.commentContent"
    :datetime="props.commentUser.createTime"
  >
    <template #actions>
      <span class="action" @click="doReply"> <IconMessage /> 回复 </span>
    </template>
    <a-comment v-if="visibleReply" align="right" :avatar="props.user.avatar">
      <template #actions>
        <a-button key="0" type="secondary" @click="doReply"> 取消 </a-button>
        <a-button key="1" type="primary" @click="doComment"> 评论 </a-button>
      </template>
      <template #content>
        <a-input
          v-model:model-value="commentContent"
          placeholder="请输入评论内容"
        />
      </template>
    </a-comment>
  </a-comment>
</template>

<script setup lang="ts">
import { withDefaults, defineProps, ref } from 'vue'
import { IconMessage } from '@arco-design/web-vue/es/icon'
import { User, UserVO } from '@/api'

// 获取Props
interface Props {
  commentUser: UserVO
  user: User
  commentContent: string
}
const props = withDefaults(defineProps<Props>(), {
  commentUser: () => ({}),
  user: () => ({}),
  commentContent: () => ''
})
const visibleReply = ref(false)
const doReply = () => {
  visibleReply.value = !visibleReply.value
}
const commentContent = ref('')
const doComment = () => {
  console.log(commentContent.value)
  commentContent.value = ''
}
</script>

<style scoped>
.action {
  display: inline-block;
  padding: 0 4px;
  color: var(--color-text-1);
  line-height: 24px;
  background: transparent;
  border-radius: 2px;
  cursor: pointer;
  transition: all 0.1s ease;
}
.action:hover {
  background: var(--color-fill-3);
}
</style>
