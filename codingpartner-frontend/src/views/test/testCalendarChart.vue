<template>
  <div>
    <CalendarChart :title="title" :data="data" />
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import CalendarChart from '../../components/CalendarChart.vue'

const title = ref('日历')
const data = ref([
  ['2024-01-01', 0],
  ['2024-01-02', 1],
  ['2024-01-03', 2],
  ['2024-01-04', 3],
  ['2024-01-05', 4],
  ['2024-01-06', 5],
  ['2024-01-07', 6],
  ['2024-01-08', 7],
  ['2024-01-09', 8],
  ['2024-01-10', 9],
  ['2024-01-11', 10],
  ['2024-01-12', 11]
])
</script>
