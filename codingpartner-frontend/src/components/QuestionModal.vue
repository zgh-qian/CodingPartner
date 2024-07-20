<template>
  <a-modal
    v-model:visible="visible"
    @ok="handleOk"
    @cancel="handleCancel"
    fullscreen
    okText="创建"
    cancelText="取消"
  >
    <template #title> 创建题目 </template>
    <div id="questionModal">
      <a-form :model="questionForm" layout="horizontal" class="form">
        <!-- 题目标题 -->
        <a-form-item field="title" label="题目标题">
          <a-input v-model="questionForm.title" placeholder="请输入题目标题" />
        </a-form-item>
        <!-- 题目标签 -->
        <a-form-item field="tags" label="题目标签">
          <a-input-tag
            v-model="questionForm.tags"
            placeholder="请输入题目标签"
            allow-clear
          ></a-input-tag>
        </a-form-item>
        <!-- 题目难度 -->
        <a-form-item label="题目难度">
          <a-select
            placeholder="请输入题目难度"
            v-model="questionForm.difficulty"
          >
            <a-option>简单</a-option>
            <a-option>中等</a-option>
            <a-option>困难</a-option>
          </a-select>
        </a-form-item>
        <!-- 题目支持的语言 -->
        <a-form-item field="language" label="题目语言">
          <a-select
            v-model="questionForm.language"
            placeholder="请输入题目支持的语言"
            multiple
          >
            <a-option
              v-for="item in languageList"
              :key="item.id"
              :tag-props="{ color: getLanguageColor(item.language) }"
              >{{ item.language }}</a-option
            >
          </a-select>
        </a-form-item>
        <!-- 题目内容 -->
        <a-form-item field="content" label="题目内容">
          <MdEditor
            :value="questionForm.content"
            :handle-change="onChangeContent"
            class="md"
          />
        </a-form-item>
        <!-- 题目判题配置 -->
        <a-form-item field="judgeConfig.timeLimit" label="时间限制(ms)">
          <a-input-number
            v-model="judgeConfig.timeLimit"
            placeholder="请输入时间限制(ms)"
            :min="0"
          />
        </a-form-item>
        <a-form-item field="judgeConfig.memoryLimit" label="内存限制(kb)">
          <a-input-number
            v-model="judgeConfig.memoryLimit"
            placeholder="请输入内存限制(kb)"
            :min="0"
          />
        </a-form-item>
        <a-form-item field="judgeConfig.stackLimit" label="堆栈限制(kb)">
          <a-input-number
            v-model="judgeConfig.stackLimit"
            placeholder="请输入堆栈限制(kb)"
            :min="0"
          />
        </a-form-item>
        <!-- 题目答案 -->
        <a-form-item field="answer" label="题目答案">
          <MdEditor
            :value="questionForm.answer"
            :handle-change="onChangeAnswer"
            class="md"
          />
        </a-form-item>
        <!-- 题目测试用例 -->
        <a-form-item label="测试用例"></a-form-item>
        <a-form-item
          v-for="(judgeCaseItem, index) of judgeCaseArray"
          :label="`测试用例 ${index + 1}`"
          :key="index"
          no-style
        >
          <a-space direction="vertical">
            <a-form-item
              :field="`form.judgeCase[${index}].input`"
              :label="`测试用例 ${index + 1}`"
              :key="index"
            >
              <a-textarea
                v-model="judgeCaseItem.input"
                placeholder="请输入题目输入用例"
                auto-size
              />
            </a-form-item>
            <a-form-item>
              <a-textarea
                v-model="judgeCaseItem.output"
                placeholder="请输入题目输出用例"
                auto-size
              />
            </a-form-item>
            <a-form-item>
              <a-button @click="handleDelete(index)" status="danger"
                >删除</a-button
              >
            </a-form-item>
          </a-space>
        </a-form-item>
      </a-form>
      <!-- 测试用例相关按钮 -->
      <div class="judgeCaseBtn">
        <a-button @click="handleAdd" type="primary">添加测试用例</a-button>
        <a-button @click="handleClear" status="danger">清空测试用例</a-button>
      </div>
    </div>
  </a-modal>
</template>

<script setup lang="ts">
import { ref, defineExpose } from 'vue'
import message from '@arco-design/web-vue/es/message'
import { getLanguageColor } from '@/utils/getColor'
import { JudgeCase, JudgeConfig, QuestionAddRequest, Service } from '@/api'
import MdEditor from '@/components/MdEditor.vue'

// 题目表单
const questionForm = ref<QuestionAddRequest>({})
// 避免在 v-model 中直接使用可选链
const judgeConfig = ref<JudgeConfig>({})
const judgeCaseArray = ref<Array<JudgeCase>>([])
// md编辑器改变事件
const onChangeContent = (v: string) => {
  questionForm.value.content = v
}
const onChangeAnswer = (v: string) => {
  questionForm.value.answer = v
}
// 添加测试用例
const handleAdd = () => {
  judgeCaseArray.value.push({
    input: '',
    output: ''
  })
}
// 删除测试用例
const handleDelete = (index: number) => {
  judgeCaseArray.value.splice(index, 1)
}
// 清空测试用例
const handleClear = () => {
  judgeCaseArray.value = []
}
// 控制对话框的显示和隐藏
const visible = ref(false)
// 打开对话框，可以接收参数
const open = (v: any) => {
  // 清空表单
  clearQuestionForm()
  // 显示对话框
  visible.value = true
  if (v !== null) {
    questionForm.value = v
    // 转换json
    questionForm.value.tags = JSON.parse(v.tags)
    questionForm.value.language = JSON.parse(v.language)
    const parentJudgeConfig: JudgeConfig = JSON.parse(v.judgeConfig)
    judgeConfig.value.memoryLimit = parentJudgeConfig.memoryLimit
    judgeConfig.value.stackLimit = parentJudgeConfig.stackLimit
    judgeConfig.value.timeLimit = parentJudgeConfig.timeLimit
    judgeCaseArray.value = JSON.parse(v.judgeCase)
  }
}
// 确认按钮，提交题目
const handleOk = async () => {
  // 补充参数
  questionForm.value.judgeConfig = judgeConfig.value
  questionForm.value.judgeCase = judgeCaseArray.value
  const res = await Service.addQuestionUsingPost(questionForm.value)
  if (res.code === 200) {
    message.success('创建成功')
    setTimeout(() => {
      visible.value = false
    }, 1500)
  } else {
    message.error('创建失败，' + res.message)
    visible.value = true
  }
}
// 取消按钮
const handleCancel = () => {
  visible.value = false
}
// 清空题目表单
const clearQuestionForm = () => {
  questionForm.value = {}
  judgeConfig.value = {}
  judgeCaseArray.value = []
}
// 暴露 open 方法给父组件
defineExpose({
  open
})
const languageList = ref([
  {
    id: 1,
    language: 'cpp',
    color: 'red'
  },
  {
    id: 2,
    language: 'java',
    color: 'blue'
  },
  {
    id: 3,
    language: 'go',
    color: 'green'
  },
  {
    id: 4,
    language: 'python',
    color: 'yellow'
  },
  {
    id: 5,
    language: 'javascript',
    color: 'orange'
  },
  {
    id: 6,
    language: 'html',
    color: 'purple'
  }
])
</script>
<style scoped>
#questionModal {
  min-width: 600px;
  justify-content: center;
  align-items: center;
}
#questionModal .form {
  width: 1000px;
  margin: 0 auto;
}
#questionModal .form .md {
  width: 1000px;
}
#questionModal .judgeCaseBtn {
  display: flex;
  min-width: 600px;
  max-width: 1000px;
  margin: 0 auto;
  justify-content: space-around;
  margin-top: 20px;
}
</style>
