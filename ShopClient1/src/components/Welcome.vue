<template>
    <div>
        <h3>欢迎<span style="color:red">{{username}}</span>来到商城后台管理系统</h3>
    </div>
</template>

<script>
export default {
  data () {
    return {
      username: ''
    }
  },
  created () {
    this.username = window.sessionStorage.getItem('username')
  }
}
</script>

<style scoped>

</style>
