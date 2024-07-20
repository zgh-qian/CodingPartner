<!-- 登录注册页面 -->
<template>
  <a-layout id="authLayout">
    <a-layout-header><GlobalHeader /></a-layout-header>
    <a-layout-content class="content">
      <router-view />
    </a-layout-content>
    <a-layout-footer class="footer"><GlobalFooter /></a-layout-footer>
  </a-layout>
</template>
<script setup lang="ts">
import GlobalHeader from '@/components/GlobalHeader.vue'
import GlobalFooter from '@/components/GlobalFooter.vue'
</script>
<style scoped>
#authLayout .content {
  margin-top: 80px;
  display: flex;
  justify-content: center;
  align-items: center;
}
#authLayout .footer {
  position: fixed;
  bottom: 0;
  width: 100%;
}
</style>
