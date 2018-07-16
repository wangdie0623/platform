import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import helper from './HttpHelper'
import VueUi from '@vue/ui'
import '@vue/ui/dist/vue-ui.css'


Vue.config.productionTip = false;

Vue.use(helper);
Vue.use(VueUi);
new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
