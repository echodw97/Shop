/* eslint-disable no-unused-vars */
<template>
  <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
      <el-breadcrumb-item>商品列表</el-breadcrumb-item>
    </el-breadcrumb>

    <!--卡片内容区  -->
    <el-card>
      <!-- 搜索与添加区 -->
      <el-row :gutter="40">
        <el-col>
           <span>选择商品分类:</span>
           <!-- 选择商品分类的级联选择框 -->
        <el-cascader expand-trigger="hover" :options="cateList" :props="cateProps" v-model="selectedCateKeys" @change="handleChange"></el-cascader>
        </el-col>
        <el-col :span="8" class="coltop">
          <el-input placeholder="请输入内容" v-model="queryInfo.query">
            <el-button slot="append" icon="el-icon-search" @click="getCommodityListLike()"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4" class="coltop">
          <el-button type="primary" @click="addDialogVisible = true">添加商品</el-button>
        </el-col>
      </el-row>
      <!-- 商品列表区域 -->
      <el-table :data="commodityList" border stripe>
        <el-table-column label="序号" type="index"></el-table-column>
        <el-table-column label="商品名" prop="name"></el-table-column>
        <el-table-column label="价格" prop="price"></el-table-column>
        <el-table-column label="创建时间" prop="createTime"></el-table-column>
        <el-table-column label="操作" width="230px">
          <template slot-scope="scope">
            <!-- 修改按钮 -->
            <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row.id)">修改</el-button>
            <!-- 删除按钮 -->
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeCommodityById(scope.row.id)">删除</el-button>
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
        :total="total"
      ></el-pagination>
    </el-card>

    <!-- 添加用户对话框 -->
    <el-dialog title="添加商品" :visible.sync="addDialogVisible" width="50%" @close="addDialogClosed">
      <el-form
        :model="addForm"
        :rules="addFormRules"
        ref="addFormRef"
        label-width="80px">
        <el-form-item label="商品名" prop="name">
          <el-input v-model="addForm.name"></el-input>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="addForm.price"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addCommodity">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 修改用户对话框 -->
    <el-dialog title="修改用户" :visible.sync="editDialogVisible" width="50%" @close="editDialogClosed">
      <el-form
        :model="editForm"
        :rules="editFormRules"
        ref="editFormRef"
        label-width="80px">
        <el-form-item label="商品名">
          <el-input v-model="editForm.name" disabled></el-input>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="editForm.price"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editCommodityInfo">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      // 商品分类列表
      cateList: [],
      //   级联选择框的配置对象
      cateProps: {
        value: 'catId',
        label: 'catName',
        children: 'children'
      },
      //  级联选择框双向绑定到的数组
      selectedCateKeys: [],
      queryInfo: {
        query: '',
        page: 1,
        size: 10
      },
      // 获取商品列表的参数对象
      commodityList: [],
      total: 0,
      catesId: 1,
      // 控制添加商品对话框的显示与隐藏
      addDialogVisible: false,
      // 添加商品的表单数据
      addForm: {
        name: '',
        price: ''
      },
      // 添加表单的验证规则
      addFormRules: {
        name: [
          { required: true, message: '请输入商品名', trigger: 'blur' },
          { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ],
        price: [
          { required: true, message: '请输入价格', trigger: 'blur' },
          { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ]
      },
      editDialogVisible: false,
      // 查询到的商品信息对象
      editForm: {
        name: '',
        price: ''
      },
      // 编辑商品规则
      editFormRules: {
        price: [
          { required: true, message: '请输入价格', trigger: 'blur' },
          { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.getCateList()
  },
  methods: {
    async getCateList () {
      const { data: res } = await this.$http.get('categories/list')
      if (res.meta.status !== 200) {
        return this.$message.error('获取商品分类失败')
      }
      this.cateList = res.data
    },
    async handleChange () {
      this.getId()
      // 根据分类id获取相应的商品
      const { data: res } = await this.$http.get('categories/commodity/' + this.catesId + '/' + this.queryInfo.page + '/' + this.queryInfo.size)

      if (res.meta.status !== 200) {
        return this.$message.error('获取商品列表失败')
      }
      this.commodityList = res.data.records
      this.total = res.data.total
    },
    getId () {
      this.catesId = this.selectedCateKeys[1]
    },
    // 模糊查询匹配商品列表
    async getCommodityListLike () {
      const { data: res } = await this.$http.get(
        'commodity/' +
          this.queryInfo.page +
          '/' +
          this.queryInfo.size +
          '/' +
          this.queryInfo.query
      )
      if (res.meta.status !== 200) {
        return this.$message.error('获取商品列表失败')
      }
      this.commodityList = res.data.records
      this.total = res.data.total
    },
    // 监听pageSize改变事件
    handleSizeChange (newSize) {
      this.queryInfo.size = newSize
      this.handleChange()
    },
    // 监听页面值改变事件
    handleCurrentChange (newPage) {
      this.queryInfo.page = newPage
      this.handleChange()
    },
    // 当关闭添加商品对话框后重置表单数据
    addDialogClosed () {
      this.$refs.addFormRef.resetFields()
    },
    // 添加新商品，并在添加前实现预验证
    addCommodity () {
      this.$refs.addFormRef.validate(async valid => {
        if (!valid) return
        const { data: res } = await this.$http.get('addCommodity/' + this.addForm.name + '/' + this.addForm.price + '/' + this.catesId)
        if (res.meta.status !== 201) {
          return this.$message.error('添加商品失败')
        }
        this.$message.success('添加商品成功')
        this.addDialogVisible = false
        this.handleChange()
      })
    },
    // 展示修改商品的对话框
    async showEditDialog (id) {
      const { data: res } = await this.$http.post('show/commodity/' + id)
      if (res.meta.status !== 200) {
        return this.$message.error('获取商品信息失败')
      }
      this.editForm = res.data
      this.editDialogVisible = true
    },
    // 当关闭修改商品对话框后重置表单数据
    editDialogClosed () {
      this.$refs.editFormRef.resetFields()
    },
    // 修改商品信息并提交，并在提交前实现预验证
    editCommodityInfo () {
      this.$refs.editFormRef.validate(async valid => {
        if (!valid) return
        const { data: res } = await this.$http.post('commodity/' + this.editForm.price + '/' + this.editForm.id)
        if (res.meta.status !== 201) {
          return this.$message.error('修改商品失败')
        }
        this.$message.success('修改商品成功')
        this.editDialogVisible = false
        this.handleChange()
      })
    },
    // 根据id值来删除用户
    async removeCommodityById (id) {
      const confirmResult = await this.$confirm('此操作将永久删除该商品, 是否继续?', '提示', {
        confirmButtonText: '确定',
        type: 'warning'
      }).catch(err => err)
      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消删除')
      }
      const { data: res } = await this.$http.delete('commodity/' + id)
      if (res.meta.status !== 200) {
        return this.$message.error('删除商品失败')
      }
      this.$message.success('删除商品成功')
      this.handleChange()
    }
  }
}
</script>

<style lang="less" scoped>
.coltop{
  margin-top: 40px;
}
.el-cascader{
  margin-left: 20px;
}
</style>
