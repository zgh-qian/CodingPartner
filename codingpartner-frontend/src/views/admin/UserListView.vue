<template>
  <div id="UserListView">
    <!-- 搜索条件 -->
    <a-form :model="searchParams" layout="inline" class="search">
      <QuestionModal ref="questionModalRef" />
      <!-- role -->
      <a-form-item field="role" label="角色">
        <a-select
          v-model="searchParams.role"
          @change="doSearch"
          style="max-width: 100px"
        >
          <a-option>无</a-option>
          <a-option>user</a-option>
          <a-option>admin</a-option>
        </a-select>
      </a-form-item>
    </a-form>
    <!-- 题目列表 -->
    <a-table
      class="table"
      :columns="userListColums as any[]"
      :data="userList"
      :bordered="false"
      :pagination="{
        pageSize: searchParams.pageSize,
        total: total,
        showTotal: true,
        showPageSize: true
      }"
      @page-change="onPageChange"
      @page-size-change="onPageSizeChange"
    >
      <template #role="{ record }">
        <a-tag :color="getUserRoleColor(record.role)"> {{ record.role }}</a-tag>
      </template>
      <template #level="{ record }">
        <a-tag :color="getUserLevel(record.level)"> {{ record.level }}</a-tag>
      </template>
      <template #createTime="{ record }">
        {{ moment(record.createTime).format('YYYY-MM-DD') }}
      </template>
      <template #optional="{ record }">
        <div v-if="record.isBanned === 0">
          <a-button type="primary" @click="doUnBanUser(record.id)">
            <template #icon>
              <icon-unlock />
            </template>
          </a-button>
        </div>
        <div v-else>
          <a-button status="danger" @click="doBanUser(record.id)">
            <template #icon>
              <icon-lock />
            </template>
          </a-button>
        </div>
      </template>
    </a-table>
  </div>
</template>
<script setup lang="ts">
import { onMounted, ref, watchEffect } from 'vue'
import { Service, UserQueryRequest } from '@/api'
import message from '@arco-design/web-vue/es/message'
import QuestionModal from '@/components/QuestionModal.vue'
import moment from 'moment'
import { getUserRoleColor, getUserLevel } from '@/utils/index'
import { IconUnlock, IconLock } from '@arco-design/web-vue/es/icon'
/**
 * 数据列
 */
const userListColums = ref([
  {
    title: '用户名',
    dataIndex: 'username',
    align: 'center',
    width: 100
  },
  {
    title: '昵称',
    dataIndex: 'nickname',
    align: 'center',
    width: 100
  },
  {
    title: '角色',
    slotName: 'role',
    align: 'center',
    width: 50
  },
  {
    title: '等级',
    slotName: 'level',
    align: 'center',
    width: 50
  },
  {
    title: '提交数',
    dataIndex: 'submissionCount',
    align: 'center',
    width: 50
  },
  {
    title: '通过数',
    dataIndex: 'acceptedCount',
    align: 'center',
    width: 50
  },
  {
    title: '创建时间',
    slotName: 'createTime',
    align: 'center',
    width: 100
  },
  {
    title: '操作',
    slotName: 'optional',
    align: 'center',
    width: 20
  }
])
const userList = ref([])
// 搜索条件
const total = ref(0)
const searchParams = ref({
  pageSize: 10,
  current: 1,
  isPass: 1
} as UserQueryRequest)
/**
 * 修改页码
 * @param page
 */
const onPageChange = (page: number) => {
  searchParams.value = {
    ...searchParams.value,
    current: page
  }
}
/**
 * 修改页面大小
 * @param pageSize
 */
const onPageSizeChange = (pageSize: number) => {
  searchParams.value = {
    ...searchParams.value,
    pageSize: pageSize
  }
}
// 获取数据列表
const loadDataList = async () => {
  const res = await Service.getUserListUsingPost(searchParams.value)
  if (res.code === 200) {
    userList.value = res.data.records
    total.value = parseInt(res.data.total)
  } else {
    message.error(res.message)
  }
}
// 挂载后加载数据
onMounted(() => {
  loadDataList()
})
// 监听搜索条件变化
watchEffect(() => {
  loadDataList()
})
// 搜索
const doSearch = () => {
  searchParams.value = {
    ...searchParams.value,
    current: 1
  }
  if (searchParams.value.role === '无') {
    searchParams.value.role = ''
  }
}
const questionModalRef = ref()

// 解封用户
const doUnBanUser = async (id: number) => {
  const res = await Service.banUserByIdUsingPut(id)
  if (res.code === 200) {
    message.success('解封成功')
    loadDataList()
  } else {
    message.error('解封失败，' + res.message)
  }
}
// 封禁用户
const doBanUser = async (id: number) => {
  const res = await Service.unbanUserByIdUsingPut(id)
  if (res.code === 200) {
    message.success('封禁成功')
    loadDataList()
  } else {
    message.error('封禁失败，' + res.message)
  }
}
</script>
<style scoped>
#UserListView {
  max-width: 1000px;
  min-height: 100vh;
  margin: 0 auto;
}
#UserListView .search {
  margin-top: 15px;
  margin-bottom: 15px;
  padding: 10px;
}
#UserListView .table {
  max-height: 100vh;
  margin-bottom: 50px;
}
#UserListView .link {
  text-decoration: none;
  color: #000;
}
#UserListView .link:hover {
  color: blue;
}
</style>
