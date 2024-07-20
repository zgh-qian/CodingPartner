<template>
  <div id="userDashboardLayout">
    <a-layout>
      <a-layout-header><GlobalHeader /></a-layout-header>
      <a-layout>
        <a-layout-sider> <UserSiderView /> </a-layout-sider>
        <a-layout-content> <router-view /> </a-layout-content>
      </a-layout>
      <a-layout-footer><GlobalFooter /></a-layout-footer>
    </a-layout>
  </div>
</template>
<script setup lang="ts">
import GlobalHeader from '../components/GlobalHeader.vue'
import GlobalFooter from '@/components/GlobalFooter.vue'
import UserSiderView from '@/views/user/UserSiderView.vue'
</script>
<style scoped></style>
