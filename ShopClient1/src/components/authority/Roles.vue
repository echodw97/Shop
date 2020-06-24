<template>
    <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>权限管理</el-breadcrumb-item>
      <el-breadcrumb-item>角色列表</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图 -->
    <el-card>
       <!-- 搜索区 -->
      <el-row :gutter="40">
        <el-col :span="8">
          <el-input placeholder="请输入内容" v-model="queryInfo.query">
            <el-button slot="append" icon="el-icon-search" @click="getRoleListLike()"></el-button>
          </el-input>
        </el-col>
      </el-row>
       <!-- 权限列表区域 -->
      <el-table :data="roleList" border stripe>
        <el-table-column label="序号" type="index"></el-table-column>
        <el-table-column label="姓名" prop="username"></el-table-column>
        <el-table-column label="角色" prop="roleName"></el-table-column>
      </el-table>
      <!-- 分页区域 -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.page"
        :page-sizes="[1, 2, 5, 10]"
        :page-size="queryInfo.size"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
        </el-pagination>
    </el-card>
    </div>
</template>

<script>
export default {
  data () {
    return {
      // 获取用户列表的参数对象
      queryInfo: {
        query: '',
        page: 1,
        size: 10
      },
      // 权限列表
      roleList: [],
      total: 0
    }
  },
  created () {
    this.getRoleList()
  },
  methods: {

    // 监听pageSize改变事件
    handleSizeChange (newSize) {
      this.queryInfo.size = newSize
      this.getRoleList()
    },
    // 监听页面值改变事件
    handleCurrentChange (newPage) {
      this.queryInfo.page = newPage
      this.getRoleList()
    },
    // 获取所有的权限
    async getRoleList () {
      const { data: res } = await this.$http.get('role/list/' + this.queryInfo.page + '/' + this.queryInfo.size)
      if (res.meta.status !== 200) {
        return this.$message.error('请求失败')
      }
      console.log(res)
      this.roleList = res.data.records
      this.total = res.data.total
    },
    // 模糊查询匹配角色列表
    async getRoleListLike () {
      const { data: res } = await this.$http.get(
        'role/' +
          this.queryInfo.page +
          '/' +
          this.queryInfo.size +
          '/' +
          this.queryInfo.query
      )
      if (res.meta.status !== 200) {
        return this.$message.error('获取角色列表失败')
      }
      this.roleList = res.data.records
      this.total = res.data.total
    }
  }
}
</script>

<style lang="less" scoped>

</style>
