import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import helper from './HttpHelper'


Vue.config.productionTip = false;

Vue.use(helper);


new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
