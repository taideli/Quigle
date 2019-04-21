<template>
  <el-container>
    <el-header class="header" height="100px">
      <h3>我的网站</h3>
      <p>Vue小模块之用户登陆功能</p>
    </el-header>
    <el-main>
      <router-view />
    </el-main>
  </el-container>
</template>

<script>
export default {
  name: 'App'
}
</script>
<!--https://www.cnblogs.com/taylorchen/p/6083099.html-->
<style>
.header {
  background-color: #409EFF;
  color: white;
}
</style>
