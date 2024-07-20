<!--使用示例：
import MdViewer from '@/components/MdViewer.vue'
import { ref } from 'vue'
const value = ref('')

<MdViewer :value="value" />
 -->
<script setup lang="ts">
import gfm from '@bytemd/plugin-gfm'
import highlight from '@bytemd/plugin-highlight'
import { Viewer } from '@bytemd/vue-next'
import { withDefaults, defineProps, computed } from 'vue'

// 定义组件属性类型
interface Props {
  value: string
}
// 插件列表
const plugins = [gfm(), highlight()]
// 定义父子组件属性
const props = withDefaults(defineProps<Props>(), {
  value: () => ''
})
/**
 * 父组件传值
 */
const value = computed(() => {
  return props.value
})
</script>
<template>
  <Viewer :value="value" :plugins="plugins" />
</template>
<style></style>
