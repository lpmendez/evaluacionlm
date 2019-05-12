import Vue from 'vue'
import App from './App.vue'

import VueRouter from 'vue-router'
import {routes} from './routes/routes'
import {store} from './store/store';
import {http} from './axios/common';


Vue.use(VueRouter)
Vue.config.productionTip = false


const router = new VueRouter({
  routes
});

router.beforeEach((to, from, next) =>{
  let reqAuth = to.matched.some(record=> record.meta.requiresAuth);

  // console.log(reqAuth);
  // console.log(to);
  if(reqAuth){
    // console.log("entre");
    http.get(store.state.urls.validate)
    .then(response=>{
      next();
    })
    .catch(error=>{
        console.log(error)
        store.commit('setToken','');
        if(error.response.status == '440'){
            // this.$router.replace('/login');
            next('/login')
        }
        else
        next('/error')
    })
  }
  else next();
  

  // let currentUser = store.state.token;

  // if(reqAuth && !currentUser) next('/login')
  // else next()
})

new Vue({
  render: h => h(App),
  router,
  store
}).$mount('#app')
  
