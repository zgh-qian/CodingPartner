<template>
  <div id="questionDetailList">
    <!-- 搜索条件 -->
    <a-form :model="searchParams" layout="inline" class="search">
      <!-- 创建题目 -->
      <a-form-item>
        <a-button type="primary" @click="createQuestion">
          <template #icon>
            <icon-plus />
          </template>
        </a-button>
      </a-form-item>
      <QuestionModal ref="questionModalRef" />
      <!-- 难度 -->
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
      <!-- 题目名称 -->
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
      <!-- 标签 -->
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
        <a href="/admin/question" class="link">
          {{ record.title }}
        </a>
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
      <template #optional="{ record }">
        <a-space>
          <a-button type="primary" @click="doEdit(record.id)">
            <template #icon>
              <icon-edit />
            </template>
          </a-button>
          <a-button status="danger" @click="doDelete(record.id)">
            <template #icon>
              <icon-delete />
            </template>
          </a-button>
        </a-space>
      </template>
    </a-table>
  </div>
</template>
<script setup lang="ts">
import { onMounted, ref, watchEffect } from 'vue'
import { QuestionQueryRequest, Service } from '@/api'
import message from '@arco-design/web-vue/es/message'
import { getDifficultyColor, getTagColor } from '@/utils/getColor.ts'
import { IconPlus, IconEdit, IconDelete } from '@arco-design/web-vue/es/icon'
import QuestionModal from '@/components/QuestionModal.vue'

/**
 * 数据列
 */
const questionColumns = ref([
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
  },
  {
    title: '操作',
    slotName: 'optional',
    align: 'center',
    width: 80
  }
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
const questionModalRef = ref()
// 创建题目
const createQuestion = () => {
  // console.log('创建题目')
  questionModalRef.value.open(null)
}
// 编辑题目
const doEdit = async (id: number) => {
  const res = await Service.getQuestionByIdUsingGet(id)
  if (res.code === 200) {
    questionModalRef.value.open(res.data)
  } else {
    message.error('获取题目失败，' + res.message)
  }
}
// 删除题目
const doDelete = async (id: number) => {
  const res = await Service.deleteQuestionUsingDelete([id])
  if (res.code === 200) {
    message.success('删除成功')
    loadDataList()
  } else {
    message.error('删除失败，' + res.message)
  }
}
</script>
<style scoped>
#questionDetailList {
  max-width: 1000px;
  min-height: 100vh;
  margin: 0 auto;
}
#questionDetailList .search {
  margin-top: 15px;
  margin-bottom: 15px;
  padding: 10px;
}
#questionDetailList .table {
  max-height: 100vh;
  margin-bottom: 50px;
}
#questionDetailList .link {
  text-decoration: none;
  color: #000;
}
#questionDetailList .link:hover {
  color: blue;
}
</style>
