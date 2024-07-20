<!-- 点赞评论收藏 -->
<!-- 示例：
 <GlobalButton
      :isThumb=""
      :isFavour=""
      :thumbCount=""
      :favourCount=""
      :commentCount=""
      :doThumb=""
      :doComment=""
      :doFavour=""
    />
-->
<template>
  <div id="button-group">
    <a-button-group>
      <a-button @click="props.doThumb">
        <template v-if="props.isThumb" #icon>
          <icon-thumb-up-fill />
        </template>
        <template v-else #icon>
          <icon-thumb-up />
        </template>
        {{ getNumStr(props.thumbCount) }}
      </a-button>
      <a-button @click="props.doComment">
        <template #icon><icon-message /></template>
        {{ getNumStr(props.commentCount) }}
      </a-button>
      <a-button @click="props.doFavour">
        <template v-if="props.isFavour" #icon>
          <icon-star-fill />
        </template>
        <template v-else #icon>
          <icon-star />
        </template>
        {{ getNumStr(props.favourCount) }}
      </a-button>
    </a-button-group>
  </div>
</template>
<script setup lang="ts">
import {
  IconThumbUp,
  IconThumbUpFill,
  IconMessage,
  IconStar,
  IconStarFill
} from '@arco-design/web-vue/es/icon'
import { withDefaults, defineProps } from 'vue'
import { getNumStr } from '@/utils/index'

// 获取Props
interface Props {
  // 是否点赞
  isThumb: boolean
  // 是否收藏
  isFavour: boolean
  // 点赞数
  thumbCount: number
  // 评论数
  commentCount: number
  // 收藏数
  favourCount: number
  // 点赞
  doThumb: () => void
  // 评论
  doComment: () => void
  // 收藏
  doFavour: () => void
}
const props = withDefaults(defineProps<Props>(), {
  isThumb: () => false,
  isFavour: () => false,
  thumbCount: () => 0,
  commentCount: () => 0,
  favourCount: () => 0,
  // eslint-disable-next-line @typescript-eslint/no-empty-function
  doThumb: () => {},
  // eslint-disable-next-line @typescript-eslint/no-empty-function
  doComment: () => {},
  // eslint-disable-next-line @typescript-eslint/no-empty-function
  doFavour: () => {}
})
</script>
<style scoped></style>
