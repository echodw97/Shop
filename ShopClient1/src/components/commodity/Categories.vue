<template>
  <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
      <el-breadcrumb-item>商品分类</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区 -->
    <el-card>
        <!-- 搜索与添加区 -->
      <el-row :gutter="40">
        <el-col :span="8">
          <el-input placeholder="请输入内容" v-model="queryInfo.query">
            <el-button slot="append" icon="el-icon-search" @click="getCateListLike()"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
            <el-button type="primary" @click="showAddCateDialog">添加分类</el-button>
        </el-col>
      </el-row>

      <!-- 表格 -->
      <tree-table
        class="treeTable"
        :data="cateList"
        :columns="columns"
        :selection-type="false"
        :expand-type="false"
        :show-index="true"
        border
        :show-row-hover="false"
      >
        <!-- 是否有效 -->
        <template slot="isok" slot-scope="scope">
          <i
            class="el-icon-success"
            v-if="scope.row.catDeleted === false"
            style="color: lightgreen;"
          ></i>
          <i class="el-icon-error" v-else style="color red;"></i>
        </template>
        <!--排序  -->
        <template slot="order" slot-scope="scope">
          <el-tag size="mini" v-if="scope.row.catLevel === 0">一级</el-tag>
          <el-tag type="success" size="mini" v-else>二级</el-tag>
        </template>
        <!-- 操作 -->
        <template slot="opt" slot-scope="scope">
          <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row)"></el-button>
          <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeCategoriesById(scope.row)"></el-button>
        </template>
      </tree-table>
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
    <!-- 添加分类对话框 -->
    <el-dialog title="添加分类" :visible.sync="addCateDialogVisible" width="50%" @close="addCateDialogClosed">
      <el-form
        :model="addCateForm"
        :rules="addCateFormRules"
        ref="addCateFormRef"
        label-width="100px">
        <el-form-item label="分类名称" prop="catName">
          <el-input v-model="addCateForm.catName"></el-input>
        </el-form-item>
        <el-form-item label="父级分类">
          <el-cascader
            v-model="selectionedKeys"
            :options="parentCateList"
            expandTrigger='hover'
            :props='cascaderProps'
            @change="parentCateChanged"
            :clearable="true"
            :change-on-select="true"
            size="medium"
          ></el-cascader>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addCateDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addCate">确 定</el-button>
      </span>
    </el-dialog>

     <!-- 修改分类对话框 -->
    <el-dialog title="修改分类" :visible.sync="editDialogVisible" width="50%" @close="editDialogClosed">
      <el-form
        :model="editForm"
        :rules="editFormRules"
        ref="editFormRef"
        label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="editForm.catName"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editCategoriesInfo">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
export default {
  data () {
    return {
      // 商品分类查询条件
      queryInfo: {
        query: '',
        page: 1,
        size: 10
      },
      // 保存分类的数据列表，
      cateList: [],
      total: 0,
      columns: [
        { label: '分类', prop: 'catName' },
        { label: '是否有效', type: 'template', template: 'isok' },
        { label: '排序', type: 'template', template: 'order' },
        { label: '操作', type: 'template', template: 'opt' }
      ],
      addCateDialogVisible: false,
      // 添加分类表单数据对象
      addCateForm: {
        // 将要添加的分类名称
        catName: '',
        // 父分类ID
        catPid: 0,
        // 添加分类的等级
        catLevel: 0
      },
      // 添加分类表单的验证对象
      addCateFormRules: {
        catName: [
          { required: true, message: '请输入分类名', trigger: 'blur' },
          { min: 2, max: 5, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ]
      },
      // 父级分类列表
      parentCateList: [],
      cascaderProps: {
        value: 'catId',
        label: 'catName',
        children: 'children'
      },
      // 选中的父级分类ID数组
      selectionedKeys: [],
      // 修改分类信息对话框
      editDialogVisible: false,
      // 查询到的用户信息对象
      editForm: {
        catId: '',
        catName: '',
        catLevel: ''
      },
      // 编辑分类规则
      editFormRules: {
        catName: [
          { required: true, message: '请输入分类名', trigger: 'blur' },
          { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.getCateList()
  },
  methods: {
    // 获取商品分类数据
    async getCateList () {
      const { data: res } = await this.$http.get(
        'categories/' + this.queryInfo.page + '/' + this.queryInfo.size
      )
      if (res.meta.status !== 200) {
        return this.$message.error('获取商品分类失败')
      }
      console.log(res)
      this.cateList = res.data.records
      this.total = res.data.total
    },
    // 监听pageSize改变事件
    handleSizeChange (newSize) {
      this.queryInfo.size = newSize
      this.getCateList()
    },
    // 监听页面值改变事件
    handleCurrentChange (newPage) {
      this.queryInfo.page = newPage
      this.getCateList()
    },
    // 点击按钮展示添加商品分类的对话框
    showAddCateDialog () {
      this.getParentCateList()
      this.addCateDialogVisible = true
    },
    // 获取商品父分类名称
    async getParentCateList () {
      const { data: res } = await this.$http.get('categories/parent')
      console.log(res)
      if (res.meta.status !== 200) {
        return this.$message.error('获取父级分类列表失败')
      }
      console.log(res)
      this.parentCateList = res.data
    },
    parentCateChanged () {
      console.log(this.selectionedKeys)
      if (this.selectionedKeys.length > 0) {
        this.addCateForm.catPid = this.selectionedKeys[
          this.selectionedKeys.length - 1
        ]
        this.addCateForm.catLevel = this.selectionedKeys.length
      } else {
        this.addCateForm.catPid = 0

        this.addCateForm.catLevel = 0
      }
    },
    //  添加商品分类
    catName: '',
    // 父分类ID
    catPid: 0,
    // 添加分类的等级
    catLevel: 0,
    addCate () {
      this.$refs.addCateFormRef.validate(async valid => {
        if (!valid) return
        const { data: res } = await this.$http.post('addcategories/' + this.addCateForm.catName + '/' + this.addCateForm.catPid + '/' + this.addCateForm.catLevel)
        if (res.meta.status !== 201) {
          return this.$message.error('添加分类列表失败')
        }
        this.addCateDialogVisible = false
        this.addCateDialogClosed()
        this.getCateList()
        this.$message.success('添加分类成功!')
      })
    },
    // 监听对话框关闭事件，重置表单数据
    addCateDialogClosed () {
      this.$refs.addCateFormRef.resetFields()
      this.selectionedKeys = []
      this.addCateForm.catLevel = 0
      this.addCateForm.catPid = 0
    },
    // 展示修改分类的对话框
    async showEditDialog (res1) {
      const { data: res } = await this.$http.post('categories/' + res1.catId + '/' + res1.catLevel)
      if (res.meta.status !== 200) {
        return this.$message.error('获取分类信息失败')
      }
      console.log(res.data)
      this.editForm = res.data
      this.editDialogVisible = true
    },
    // 当关闭修改分类对话框后重置表单数据
    editDialogClosed () {
      this.$refs.editFormRef.resetFields()
    },
    // 修改分类信息并提交，并在提交前实现预验证
    editCategoriesInfo () {
      this.$refs.editFormRef.validate(async valid => {
        if (!valid) return
        const { data: res } = await this.$http.post('categories/' + this.editForm.catId + '/' + this.editForm.catName + '/' + this.editForm.catLevel)
        if (res.meta.status !== 201) {
          return this.$message.error('修改分类失败')
        }
        this.$message.success('修改分类成功')
        this.editDialogVisible = false
        this.getCateList()
      })
    },
    // 模糊查询匹配分类列表
    async getCateListLike () {
      const { data: res } = await this.$http.get(
        'categories/' +
          this.queryInfo.page +
          '/' +
          this.queryInfo.size +
          '/' +
          this.queryInfo.query
      )
      if (res.meta.status !== 200) {
        return this.$message.error('获取分类列表失败')
      }
      this.cateList = res.data.records
      this.total = res.data.total
    },
    // 根据id值来删除商品分类名
    async removeCategoriesById (res1) {
      console.log(res1)
      const confirmResult = await this.$confirm('此操作将永久删除该分类名, 是否继续?', '提示', {
        confirmButtonText: '确定',
        type: 'warning'
      }).catch(err => err)
      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消删除')
      }
      const { data: res } = await this.$http.delete('categories/' + res1.catId + '/' + res1.catLevel)
      if (res.meta.status !== 200) {
        return this.$message.error('删除商品分类名失败')
      }
      this.$message.success('删除分类名成功')
      this.getCateList()
    }
  }
}
</script>

<style lang="less" scoped>
.treeTable {
  margin-top: 15px;
}
.el-cascader {
  width: 100%;
}
</style>
