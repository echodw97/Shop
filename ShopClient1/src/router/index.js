import Vue from 'vue'
import Router from 'vue-router'
import Login from '../components/Login.vue'
import Home from '../components/Home.vue'
import Welcome from '../components/Welcome.vue'
import Users from '../components/user/Users.vue'
import Roles from '../components/authority/Roles.vue'
import Authoritys from '../components/authority/Authoritys.vue'
import Commodity from '../components/commodity/Commodity.vue'
import Parameter from '../components/commodity/Parameter.vue'
import Categories from '../components/commodity/Categories.vue'
import Orders from '../components/order/Orders.vue'
import Register from '../components/Register.vue'

Vue.use(Router)

const router = new Router({
  mode: 'history',
  routes: [
    { path: '/', redirect: '/login' },
    { path: '/login', component: Login },
    {
      path: '/home',
      component: Home,
      redirect: '/welcome',
      children: [
        { path: '/welcome', component: Welcome },
        { path: '/users', component: Users },
        { path: '/roles', component: Roles },
        { path: '/authoritys', component: Authoritys },
        { path: '/commodity', component: Commodity },
        { path: '/parameter', component: Parameter },
        { path: '/categories', component: Categories },
        { path: '/orders', component: Orders }
      ]
    },
    { path: '/register', component: Register }
  ]
})

// // 挂载路由导航守卫
// router.beforeEach((to, from, next) => {
//   // to将要访问的路径，from从哪个路径来，next 放行函数
//   if (to.path === '/login') return next()
//   // 获取token
//   const tokenStr = window.sessionStorage.getItem('token')
//   if (!tokenStr) return next('/login')
//   next()
// })

export default router
