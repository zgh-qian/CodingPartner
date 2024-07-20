<!-- eslint-disable @typescript-eslint/no-empty-function -->
<!--使用示例：
import MdEditor from '@/components/MdEditor.vue'
import { ref } from 'vue'
const value = ref('')
const onChange = (v: string) => {
    console.log('parent: ', v)
}

<MdEditor :value="value" :handle-change="onChange" />
 -->
<!-- 坑： vue3+ts+setup语法糖这里存在问题，需要绑定函数，否则会不生效 -->
<script setup lang="ts">
import gfm from '@bytemd/plugin-gfm'
import highlight from '@bytemd/plugin-highlight'
import { Editor } from '@bytemd/vue-next'
import { withDefaults, defineProps, computed } from 'vue'

// 定义组件属性类型
interface Props {
  value: string
  mode?: string
  handleChange: (v: string) => void
}
// 插件列表
const plugins = [gfm(), highlight()]
// 定义父子组件属性
const props = withDefaults(defineProps<Props>(), {
  value: () => '',
  mode: () => 'split',
  handleChange: () => {}
})
/**
 * 父组件传值
 */
const value = computed(() => {
  return props.value
})
const handleChange = props.handleChange
</script>
<template>
  <Editor
    :value="value"
    :mode="mode"
    :plugins="plugins"
    @change="handleChange"
  />
</template>
<style>
/* 在前端页面发现可以隐藏部分图标，这里隐藏的是GitHub的图标，注意style不能是scoped，否则不生效 */
.bytemd-toolbar-icon.bytemd-tippy.bytemd-tippy-right:last-child {
  display: none;
}
</style>
