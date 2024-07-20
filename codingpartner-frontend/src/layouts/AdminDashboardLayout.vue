<template>
  <a-layout id="adminDashboardLayout">
    <a-layout-header>
      <GlobalHeader />
    </a-layout-header>
    <a-layout>
      <a-layout-content>
        <router-view />
      </a-layout-content>
    </a-layout>
    <a-layout-footer>
      <GlobalFooter />
    </a-layout-footer>
  </a-layout>
</template>
<script setup lang="ts">
import GlobalHeader from '@/components/GlobalHeader.vue'
import GlobalFooter from '@/components/GlobalFooter.vue'
</script>
<style scoped></style>
