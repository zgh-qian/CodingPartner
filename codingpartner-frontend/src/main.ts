import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ArcoVue from '@arco-design/web-vue'
import '@arco-design/web-vue/dist/arco.css'
import pinia from '@/stores'
import '@/utils/routerInterceptor'
import '@/plugins/axios'
import 'bytemd/dist/index.css'

// use(ArcoVue) 完整导入 Arco Design
// use(pinia) 导入 pinia 和 pinia-plugin-persistedstate
createApp(App).use(router).use(ArcoVue).use(pinia).mount('#app')
