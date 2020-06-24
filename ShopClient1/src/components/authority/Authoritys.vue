<template>
    <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>权限管理</el-breadcrumb-item>
      <el-breadcrumb-item>权限列表</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图 -->
    <el-card>
        <!-- 搜索区 -->
      <el-row :gutter="40">
        <el-col :span="8">
          <el-input placeholder="请输入内容" v-model="queryInfo.query">
            <el-button slot="append" icon="el-icon-search" @click="getAuthoritysListLike()"></el-button>
          </el-input>
        </el-col>
      </el-row>
       <!-- 权限列表区域 -->
      <el-table :data="rightsList" border stripe>
        <el-table-column label="序号" type="index"></el-table-column>
        <el-table-column label="名字" prop="name"></el-table-column>
        <el-table-column label="路径" prop="path"></el-table-column>
        <el-table-column label="权限" prop="rights">
            <template slot-scope="scope">
                <el-tag v-if="scope.row.rights === 1">一级</el-tag>
                <el-tag type="success" v-else>二级</el-tag>
            </template>
        </el-table-column>
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
      rightsList: [],
      total: 0
    }
  },
  created () {
    this.getRightList()
  },
  methods: {

    // 监听pageSize改变事件
    handleSizeChange (newSize) {
      this.queryInfo.size = newSize
      this.getRightList()
    },
    // 监听页面值改变事件
    handleCurrentChange (newPage) {
      this.queryInfo.page = newPage
      this.getRightList()
    },
    // 获取所有的权限
    async getRightList () {
      const { data: res } = await this.$http.get('rights/list/' + this.queryInfo.page + '/' + this.queryInfo.size)
      if (res.meta.status !== 200) {
        return this.$message.error('请求失败')
      }
      this.rightsList = res.data.records
      this.total = res.data.total
    },
    // 模糊查询匹配权限列表
    async getAuthoritysListLike () {
      const { data: res } = await this.$http.get(
        'rights/' +
          this.queryInfo.page +
          '/' +
          this.queryInfo.size +
          '/' +
          this.queryInfo.query
      )
      if (res.meta.status !== 200) {
        return this.$message.error('获取权限列表失败')
      }
      this.rightsList = res.data.records
      this.total = res.data.total
    }
  }
}
</script>

<style lang="less" scoped>

</style>
