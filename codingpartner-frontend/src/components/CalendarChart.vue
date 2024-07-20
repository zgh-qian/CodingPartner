<!-- eslint-disable @typescript-eslint/no-explicit-any -->
<script lang="ts" setup>
import * as echarts from 'echarts/core'
import {
  TitleComponent,
  TitleComponentOption,
  CalendarComponent,
  CalendarComponentOption,
  TooltipComponent,
  TooltipComponentOption,
  VisualMapComponent,
  VisualMapComponentOption
} from 'echarts/components'
import { HeatmapChart, HeatmapSeriesOption } from 'echarts/charts'
import { CanvasRenderer } from 'echarts/renderers'
import { onMounted, defineProps, withDefaults } from 'vue'

echarts.use([
  TitleComponent,
  CalendarComponent,
  TooltipComponent,
  VisualMapComponent,
  HeatmapChart,
  CanvasRenderer
])

type EChartsOption = echarts.ComposeOption<
  | TitleComponentOption
  | CalendarComponentOption
  | TooltipComponentOption
  | VisualMapComponentOption
  | HeatmapSeriesOption
>
// 定义props
interface Props {
  // 标题
  title?: string
  // 长宽
  area?: { width: number; height: number }
  // 可视域
  visualMap?: { min: number; max: number; pieces: any[]; show: boolean }
  // 日历设置
  calendar?: { cellsize: number; range: string }
  // 数据
  data: any[]
}

// 默认props
const props = withDefaults(defineProps<Props>(), {
  title: () => 'Calendar',
  area: () => {
    return { width: 600, height: 300 }
  },
  visualMap: () => {
    return {
      min: 0,
      max: 1000,
      pieces: [
        { lte: 1, color: '#ffffff' },
        { lte: 3, color: '#CBEAD1' },
        { lte: 5, color: '#ABE087' },
        { lte: 7, color: '#73C038' },
        { lte: 7, color: '#397D54' },
        { lte: 9, color: '#235D3A' },
        { gte: 10, color: '#FF0000' }
      ],
      show: false
    }
  },
  calendar: () => {
    const currentYear = new Date().getFullYear()
    return {
      cellsize: 13,
      range: currentYear.toString()
    }
  },
  data: () => {
    return []
  }
})
const drawInit = () => {
  var chartDom = document.getElementById('main')
  var myChart = echarts.init(chartDom, null, {
    width: props.area.width,
    height: props.area.height
  })
  var option: EChartsOption = {
    title: {
      top: 50,
      left: 'center',
      text: props.title
    },
    tooltip: {},
    visualMap: {
      min: props.visualMap.min,
      max: props.visualMap.max,
      pieces: props.visualMap.pieces,
      show: props.visualMap.show
    },
    calendar: {
      top: 120,
      left: 30,
      right: 30,
      cellSize: ['auto', props.calendar.cellsize],
      range: props.calendar.range,
      itemStyle: {
        borderWidth: 0.5
      },
      yearLabel: {
        show: false
      },
      dayLabel: {
        firstDay: 0,
        nameMap: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat']
      }
    },
    series: {
      type: 'heatmap',
      coordinateSystem: 'calendar',
      data: props.data
    }
  }
  option && myChart.setOption(option)
}
onMounted(() => {
  drawInit()
})
</script>
<template>
  <div id="main"></div>
</template>
<style scoped></style>
