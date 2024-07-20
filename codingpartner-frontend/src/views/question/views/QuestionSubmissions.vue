<template>
  <div id="questionSubmissions">
    <a-scrollbar style="height: 69vh; overflow: auto">
      <!-- 搜索条件 -->
      <a-form :model="searchParams" layout="inline" class="search">
        <QuestionModal ref="questionModalRef" />
        <!-- 语言 -->
        <a-form-item field="language" label="编程语言">
          <a-select
            v-model="searchParams.language"
            @change="doSearch"
            style="max-width: 77.6px"
          >
            <a-option>无</a-option>
            <a-option>java</a-option>
            <a-option>cpp</a-option>
            <a-option>go</a-option>
          </a-select>
        </a-form-item>
      </a-form>
      <!-- 题目列表 -->
      <a-table
        class="table"
        :columns="questionSubmitColumns as any[]"
        :data="questionSubmitList"
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
        <template #judgeStatus="{ record }">
          <a-link>
            <a-tag :color="getQuestionSubmitStatusColor(record.judgeStatus)">{{
              getQuestionSubmitStatusText(record.judgeStatus)
            }}</a-tag>
          </a-link>
        </template>
        <template #time="{ record }">
          {{ record.judgeInfo.time }}
        </template>
        <template #memory="{ record }">
          {{ record.judgeInfo.memory }}
        </template>
        <template #message="{ record }">
          <a-tag
            :color="getQuestionSubmitJudgeInfoColor(record.judgeInfo.message)"
            >{{ record.judgeInfo.message }}</a-tag
          >
        </template>
        <!-- <template #createTime="{ record }">
           {{ moment(record.createTime).format('YYYY-MM-DD') }} 
          {{ moment(record.createTime).format('HH:mm:ss') }}
        </template> -->
      </a-table>
    </a-scrollbar>
  </div>
</template>
<script setup lang="ts">
import { onMounted, ref, watchEffect } from 'vue'
import { QuestionSubmitQueryRequest, Service } from '@/api'
import message from '@arco-design/web-vue/es/message'
import QuestionModal from '@/components/QuestionModal.vue'
import moment from 'moment'
import {
  getQuestionSubmitStatusColor,
  getQuestionSubmitJudgeInfoColor
} from '@/utils/index'
import { getQuestionSubmitStatusText } from '@/utils/index'

/**
 * 数据列
 */
const questionSubmitColumns = ref([
  {
    title: '判题状态',
    slotName: 'message',
    align: 'center',
    width: 100
  },
  {
    title: '语言',
    dataIndex: 'language',
    align: 'center',
    width: 100
  },
  {
    title: '时间消耗',
    slotName: 'time',
    align: 'center',
    width: 100
  },
  {
    title: '内存消耗',
    slotName: 'memory',
    align: 'center',
    width: 100
  },
  {
    title: '状态',
    slotName: 'judgeStatus',
    align: 'center',
    width: 100
  },
  {
    title: '提交时间',
    slotName: 'createTime',
    align: 'center',
    width: 100
  }
])
const questionSubmitList = ref([])
// 搜索条件
const total = ref(0)
const searchParams = ref({
  pageSize: 10,
  current: 1,
  isPass: 1
} as QuestionSubmitQueryRequest)
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
  const res = await Service.getAllQuestionSubmitVoUsingPost(searchParams.value)
  if (res.code === 200) {
    questionSubmitList.value = res.data.records
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
  if (searchParams.value.language === '无') {
    searchParams.value.language = ''
  }
}
const questionModalRef = ref()
</script>
<style scoped>
#questionSubmissions {
  margin: 0 auto;
}
#questionSubmissions .search {
  margin-top: 15px;
  margin-bottom: 15px;
  padding: 10px;
}
#questionSubmissions .table {
  margin-bottom: 50px;
}
#questionSubmissions .link {
  text-decoration: none;
  color: #000;
}
#questionSubmissions .link:hover {
  color: blue;
}
</style>
