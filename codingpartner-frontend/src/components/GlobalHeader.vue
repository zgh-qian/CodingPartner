<!-- 全局导航栏 -->
<template>
  <a-row id="globalHeader" align="center" :wrap="false">
    <a-col flex="auto">
      <a-menu
        mode="horizontal"
        :selected-keys="selectedKeys"
        @menu-item-click="doMenuClick"
      >
        <a-menu-item key="logoImg" class="logoImg" disabled>
          <img src="../assets/cp.png" />
        </a-menu-item>
        <a-menu-item key="logoFont" class="logoFont" disabled>
          <img src="../assets/CodingPartner.png" />
        </a-menu-item>
        <a-menu-item v-for="item in visibleRoutes" :key="item.path">{{
          item.name
        }}</a-menu-item>
      </a-menu>
    </a-col>
    <a-space @click="doUserClick" class="user">
      <a-col class="avatar">
        <a-avatar v-if="user.avatar === null" :size="35">
          {{ user?.username ? user.username[0].toUpperCase() : '未登录' }}
        </a-avatar>
        <!-- <a-avatar :size="35" :src=""/> -->
      </a-col>
      <a-col class="nickname"
        ><a-link>{{ user.nickname ?? NOT_LOGIN }}</a-link></a-col
      >
      <a-col class="level">{{ user.level ?? '' }}</a-col>
    </a-space>
    <a-link
      v-if="user.role === USER_ROLE.ADMIN && !route.fullPath.includes('/admin')"
      href="/admin"
      >后台系统</a-link
    >
    <a-link
      v-if="user.role === USER_ROLE.ADMIN && route.fullPath.includes('/admin')"
      href="/"
      >前台系统</a-link
    >
  </a-row>
</template>
<script setup lang="ts">
import { computed, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '../stores/modules/user'
import USER_ROLE from '@/constant/userRole'
import { NOT_LOGIN } from '@/constant/user'

// 引入路由
const router = useRouter()
// 获取当前路由
const route = useRoute()
// 当前选中的菜单
const selectedKeys = ref([route.path])
// 点击菜单事件
const doMenuClick = (key: string) => {
  router.push({
    path: key
  })
  selectedKeys.value = [key]
}
// 展示在菜单中的路由数组
const visibleRoutes = computed(() => {
  // const user = userStore()
  return router.getRoutes().filter((item) => {
    // 管理员不显示用户页面
    if (
      route.fullPath.includes('/admin') &&
      item.meta?.access !== USER_ROLE.ADMIN &&
      user.role !== USER_ROLE.USER
    ) {
      return false
    }
    // 用户不显示管理员页面
    if (
      !route.fullPath.includes('/admin') &&
      item.meta?.access === USER_ROLE.ADMIN
    ) {
      return false
    }
    // 显示展示的路由
    if (item.meta.showInMenu) {
      return true
    }
  })
})
// 点击用户跳转用户界面
const doUserClick = () => {
  //   console.log('跳转用户界面')
  router.push('/user/info')
}
//
const user = useUserStore().user
</script>
<style scoped>
#globalHeader {
  max-height: 10vh;
  max-width: 1200px;
  margin: 0 auto;
}
#globalHeader .logo {
  display: flex;
}
#globalHeader .logo .logoImg {
  margin-left: 10px;
}
#globalHeader .user {
  margin-right: 100px;
}
</style>
