import Vue from 'vue'
import App from './App.vue'
import router from './routes';
import store from './store';
import filters from './filters';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

 //引入echart
 import echarts from 'echarts'
 //将echarts引入到vue的原型中
 Vue.prototype.$echarts = echarts
 

// 使用 elementUI
Vue.use(ElementUI);

// import './routes/getUserMenu'

// 按需引入iview组件库
import 'iview/dist/styles/iview.css';
import iview from '@/iview';
import {Message,Modal} from 'iview'
Vue.prototype.$Message = Message
Vue.prototype.$Modal = Modal
Vue.use(iview)
// 全局挂载 filter

Object.keys(filters).forEach((key, index) => {
  Vue.filter(key, filters[key]);
});

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
