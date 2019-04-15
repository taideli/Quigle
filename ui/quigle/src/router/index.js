import Vue from 'vue'
import Router from 'vue-router'
import Quigle from '@/components/Quigle'
import About from '@/components/About'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/quigle',
      name: 'Quigle',
      component: Quigle
    },
    {
      path: '/about',
      name: 'About',
      component: About
    }
  ]
})
