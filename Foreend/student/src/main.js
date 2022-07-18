import Vue from 'vue'
import App from './App.vue'
import { router } from './router'
import store from './store'
import 'normalize.css/normalize.css'
import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import '@/styles/index.scss' // global css
import './icons' // icon
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style

// 设置反向代理，前端请求默认发送到 http://localhost:8080
var axios = require('axios')
axios.defaults.baseURL = 'http://localhost:8001'
// 全局注册，之后可在其他组件中通过 this.$axios 发送数据
Vue.prototype.$axios = axios

Vue.use(Element, {
  size: 'medium' // set element-ui default size
})

Vue.config.productionTip = false

NProgress.configure({ showSpinner: false }) // NProgress Configuration

router.beforeEach(async (to, from, next) => {
  // start progress bar
  NProgress.start()
  if (to.meta.title !== undefined) {
    document.title = to.meta.title
  } else {
    document.title = '\u200E'
  }

  if (to.meta.bodyBackground !== undefined) {
    document.querySelector('body').setAttribute('style', 'background: ' + to.meta.bodyBackground)
  } else {
    document.querySelector('body').removeAttribute('style')
  }

  if (to.path) {
    // eslint-disable-next-line no-undef
    _hmt.push(['_trackPageview', '/#' + to.fullPath])
  }
  next()
})

router.afterEach((to, from, next) => {
  // finish progress bar
  NProgress.done()
})

Vue.prototype.$$router = router

new Vue({
  router: router,
  store: store,
  render: h => h(App)
}).$mount('#app')
