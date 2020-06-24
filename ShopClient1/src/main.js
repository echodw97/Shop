import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './plugins/element.js'
import TreeTable from 'vue-table-with-tree-grid'

/* **导入全局样式表** */
import './assets/css/global.css'

/* **导入字体图标** */
import './assets/fonts/iconfont.css'

import axios from 'axios'
// 配置请求根路径
axios.defaults.baseURL = 'http://localhost:8090/api/private/v1/'
axios.interceptors.request.use(config => {
  console.log(config)
  config.headers.Authorization = window.sessionStorage.getItem('token')
  return config
})
Vue.prototype.$http = axios

Vue.config.productionTip = false
Vue.component('tree-table', TreeTable)

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
