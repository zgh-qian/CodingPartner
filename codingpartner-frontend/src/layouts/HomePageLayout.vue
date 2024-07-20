<template>
  <a-layout id="homePageLayout">
    <a-layout-header>
      <GlobalHeader />
    </a-layout-header>
    <a-layout>
      <a-layout-content>
        <a-row>
          <a-col :span="6">
            <div>Sider</div>
          </a-col>
          <a-col :span="18">
            <router-view />
          </a-col>
        </a-row>
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
