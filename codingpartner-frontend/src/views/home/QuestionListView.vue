<template>
  <div id="questionListView">
    <!-- 搜索条件 -->
    <a-form :model="searchParams" layout="inline" class="search">
      <a-form-item field="difficulty" label="难度">
        <a-select
          v-model="searchParams.difficulty"
          @change="doSearch"
          style="max-width: 77.6px"
        >
          <a-option>无</a-option>
          <a-option>简单</a-option>
          <a-option>中等</a-option>
          <a-option>困难</a-option>
        </a-select>
      </a-form-item>
      <a-form-item
        field="title"
        label="题目名称"
        class="form-item"
        @change="doSearch"
      >
        <a-input
          v-model="searchParams.title"
          placeholder="请输入题目名称"
          style="min-width: 250px"
        />
      </a-form-item>
      <a-form-item field="tags" label="标签" class="form-item">
        <a-input-tag
          v-model="searchParams.tags"
          placeholder="请输入标签"
          @change="doSearch"
          style="min-width: 250px"
        />
      </a-form-item>

      <!-- <a-form-item field="status" label="状态">
        <a-select
          v-model="searchParams.difficulty"
          @change="doSearch"
          style="max-width: 91.6px"
        >
          <a-option>无</a-option>
          <a-option>未开始</a-option>
          <a-option>已通过</a-option>
          <a-option>未通过</a-option>
        </a-select>
      </a-form-item> -->
    </a-form>
    <!-- 题目列表 -->
    <a-table
      class="table"
      :columns="questionColumns as any[]"
      :data="questionList"
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
      <template #title="{ record }">
        <a-link
          :hoverable="false"
          @click="goToQuestion(record.id)"
          class="link"
        >
          {{ record.title }}
        </a-link>
      </template>
      <template #tags="{ record }">
        <a-space wrap>
          <a-tag
            v-for="(tag, index) of record.tags"
            :key="index"
            :color="getTagColor(tag)"
            >{{ tag }}</a-tag
          >
        </a-space>
      </template>
      <template #difficulty="{ record }">
        <a-tag :color="getDifficultyColor(record.difficulty)">{{
          record.difficulty
        }}</a-tag>
      </template>
    </a-table>
  </div>
</template>
<script setup lang="ts">
import { onMounted, ref, watchEffect } from 'vue'
import { QuestionQueryRequest, Service } from '@/api'
import message from '@arco-design/web-vue/es/message'
import { getDifficultyColor, getTagColor } from '@/utils/getColor.ts'
import router from '@/router'
/**
 * 数据列
 */
const questionColumns = ref([
  {
    title: '状态',
    dataIndex: 'status',
    align: 'center',
    width: 60
  },
  {
    title: '题目',
    slotName: 'title',
    align: 'center',
    width: 400
  },
  {
    title: '标签',
    slotName: 'tags',
    align: 'center',
    width: 200
  },
  {
    title: '难度',
    slotName: 'difficulty',
    align: 'center',
    width: 60
  },
  {
    title: '通过率',
    dataIndex: 'rate',
    align: 'center',
    width: 80
  }
  /*  {
    title: 'Optional',
    slotName: 'optional'
  } */
])
const questionList = ref([])
// 搜索条件
const total = ref(0)
const searchParams = ref({
  pageSize: 10,
  current: 1
} as QuestionQueryRequest)
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
  const res = await Service.getQuestionVoListUsingPost(searchParams.value)
  if (res.code === 200) {
    questionList.value = res.data.records
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
  if (searchParams.value.difficulty === '无') {
    searchParams.value.difficulty = ''
  }
}
// 跳转到题目详情
const goToQuestion = (id: number) => {
  router.push({
    path: `/question/${id}`
  })
}
</script>
<style scoped>
#questionListView {
  max-width: 1000px;
}
#questionListView .search {
  margin-top: 15px;
  margin-bottom: 15px;
  padding: 10px;
}
#questionListView .table {
  max-height: 100vh;
  margin-bottom: 50px;
}
#questionListView .link {
  text-decoration: none;
  color: #000;
}
#questionListView .link:hover {
  color: blue;
}
</style>
