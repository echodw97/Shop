<template>
    <div>
        <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
      <el-breadcrumb-item>分类参数</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片内容区 -->
    <el-card>
        <!-- 警告区 -->
         <el-alert show-icon title="注意：只允许为第二级分类设置相关参数" type="warning" :closable="false"></el-alert>

   <!--选择商品分类区域  -->
   <el-row class="cat_opt">
       <el-col>
           <span>选择商品分类:</span>
           <!-- 选择商品分类的级联选择框 -->
        <el-cascader expand-trigger="hover" :options="cateList" :props="cateProps" v-model="selectedCateKeys" @change="handleChange"></el-cascader>
        </el-col>
   </el-row>
   <!-- tabs页签区 -->
    <el-tabs v-model="activeName" @tab-click="handleTabClick">
        <el-tab-pane label="动态参数" name="many">
            <el-button type="primary" size="mini" :disabled="isBtnDisabled" @click="addDynamicDialogVisible = true">添加参数</el-button>
            <!-- 动态参数表格 -->
            <el-table :data="manyTableData" border stripe>
                <el-table-column type="index"></el-table-column>
                <el-table-column label="参数名称" prop="parameter"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" type="primary" icon="el-icon-edit" @click="showDynamicEditDialog(scope.row.id)">编辑</el-button>
                        <el-button size="mini" type="danger" icon="el-icon-delete" @click="removeParameter(scope.row.id)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-tab-pane>
        <el-tab-pane label="静态属性" name="only">
            <el-button type="primary" size="mini" :disabled="isBtnDisabled" @click="addStaticDialogVisible = true">添加属性</el-button>
             <!-- 静态属性表格 -->
            <el-table :data="onlyTableData" border stripe>
                <!-- 展开行 -->
                <el-table-column type="index"></el-table-column>
                <el-table-column label="属性名称" prop="attribute"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" type="primary" icon="el-icon-edit" @click="showStaticEditDialog(scope.row.id)">编辑</el-button>
                        <el-button size="mini" type="danger" icon="el-icon-delete" @click="removeAttribute(scope.row.id)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-tab-pane>
    </el-tabs>
    </el-card>

    <!-- 添加动态参数对话框 -->
    <el-dialog
        title="添加动态参数"
        :visible.sync="addDynamicDialogVisible"
        width="50%"
        @close="addDynamicDialogClosed">
      <el-form :model="addDynamicForm" :rules="addDynamicFormRules" ref="addDynamicFormRef" label-width="100px">
        <el-form-item label="动态参数" prop="parameter">
          <el-input v-model="addDynamicForm.parameter"></el-input>
        </el-form-item>
      </el-form>
    <span slot="footer" class="dialog-footer">
        <el-button @click="addDynamicDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addParameter">确 定</el-button>
    </span>
    </el-dialog>

     <!-- 添加静态属性对话框 -->
    <el-dialog
        title="添加静态属性"
        :visible.sync="addStaticDialogVisible"
        width="50%"
        @close="addStaticDialogClosed">
      <el-form :model="addStaticForm" :rules="addStaticFormRules" ref="addStaticFormRef" label-width="100px">
        <el-form-item label="静态属性" prop="attribute">
          <el-input v-model="addStaticForm.attribute"></el-input>
        </el-form-item>
      </el-form>
    <span slot="footer" class="dialog-footer">
        <el-button @click="addStaticDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addAttribute">确 定</el-button>
    </span>
    </el-dialog>

       <!-- 修改动态参数对话框 -->
    <el-dialog
        title="修改动态参数"
        :visible.sync="editDynamicDialogVisible"
        width="50%"
        @close="editDynamicDialogClosed">
      <el-form :model="editDynamicForm" :rules="editDynamicFormRules" ref="editDynamicFormRef" label-width="100px">
        <el-form-item label="动态参数" prop="parameter">
          <el-input v-model="editDynamicForm.parameter"></el-input>
        </el-form-item>
      </el-form>
    <span slot="footer" class="dialog-footer">
        <el-button @click="editDynamicDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editParameter">确 定</el-button>
    </span>
    </el-dialog>

        <!-- 修改静态属性对话框 -->
    <el-dialog
        title="修改静态属性"
        :visible.sync="editStaticDialogVisible"
        width="50%"
        @close="editStaticDialogClosed">
      <el-form :model="editStaticForm" :rules="editStaticFormRules" ref="editStaticFormRef" label-width="100px">
        <el-form-item label="静态属性" prop="attribute">
          <el-input v-model="editStaticForm.attribute"></el-input>
        </el-form-item>
      </el-form>
    <span slot="footer" class="dialog-footer">
        <el-button @click="editStaticDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editAttribute">确 定</el-button>
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
      //   被激活的页签名称
      activeName: 'many',
      catesId: 1,
      //   动态参数的数据
      manyTableData: [],
      //  静态属性的数据
      onlyTableData: [],
      //   控制添加动态参数对话框的显示与隐藏
      addDynamicDialogVisible: false,
      //   控制添加静态属性对话框的显示与隐藏
      addStaticDialogVisible: false,
      // 添加动态参数的表单数据对象
      addDynamicForm: {
        parameter: ''
      },
      // 添加动态参数表单验证对象
      addDynamicFormRules: {
        parameter: [
          { required: true, message: '请输入动态参数', trigger: 'blur' },
          { min: 2, max: 100, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ]
      },
      // 添加静态属性的表单数据对象
      addStaticForm: {
        attribute: ''
      },
      // 添加静态属性表单验证对象
      addStaticFormRules: {
        attributes: [
          { required: true, message: '请输入静态属性', trigger: 'blur' },
          { min: 2, max: 100, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ]
      },
      // 控制修改动态参数对话框的显示与隐藏
      editDynamicDialogVisible: false,
      // 修改动态参数的表单数据对象
      editDynamicForm: {},
      // 修改动态参数表单数据验证对象
      editDynamicFormRules: {
        parameter: [
          { required: true, message: '请输入修改的动态参数', trigger: 'blur' },
          { min: 2, max: 100, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ]
      },
      // 控制修改静态属性对话框的显示与隐藏
      editStaticDialogVisible: false,
      // 修改静态属性的表单数据对象
      editStaticForm: {},
      // 修改静态属性表单数据验证对象
      editStaticFormRules: {
        attribute: [
          { required: true, message: '请输入修改的静态属性', trigger: 'blur' },
          { min: 2, max: 100, message: '长度在 2 到 10 个字符', trigger: 'blur' }
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
    // 级联选择框选中项变化，会触发此函数
    handleChange () {
      this.getParamsData()
    },
    // tab页签点击事件处理函数
    handleTabClick () {
      this.getParamsData()
    },
    getId () {
      this.catesId = this.selectedCateKeys[1]
    },
    // 获取参数的列表数据
    async getParamsData () {
      // 选中的非二级分类
      if (this.selectedCateKeys.length !== 2) { this.selectedCateKeys = [] }
      this.getId()
      //  选中的是二级分类
      // 根据分类id以及所处的面板获取相应的动态参数或者静态属性
      const { data: res } = await this.$http.get('categories/attributes/' + this.activeName + '/' + this.catesId)

      if (res.meta.status !== 200) {
        return this.$message.error('获取参数列表失败')
      }

      if (this.activeName === 'many') {
        this.manyTableData = res.data
      } else {
        this.onlyTableData = res.data
      }
    },
    // 监听添加动态参数对话框的关闭
    addDynamicDialogClosed () {
      this.$refs.addDynamicFormRef.resetFields()
    },
    // 监听添加静态属性对话框的关闭
    addStaticDialogClosed () {
      this.$refs.addStaticFormRef.resetFields()
    },
    // 点击按钮，添加动态参数
    addParameter () {
      this.$refs.addDynamicFormRef.validate(async valid => {
        if (!valid) return
        const { data: res } = await this.$http.post('categories/parameter/' + this.catesId + '/' + this.addDynamicForm.parameter)
        if (res.meta.status !== 201) {
          return this.$message.error('添加动态参数失败')
        }

        this.addDynamicDialogVisible = false
        this.getParamsData()
        return this.$message.success('添加动态参数成功')
      })
    },
    // 点击按钮，添加静态属性
    addAttribute () {
      this.$refs.addStaticFormRef.validate(async valid => {
        if (!valid) return
        const { data: res } = await this.$http.post('categories/attribute/' + this.catesId + '/' + this.addStaticForm.attribute)
        if (res.meta.status !== 201) {
          return this.$message.error('添加静态属性失败')
        }

        this.addStaticDialogVisible = false
        this.getParamsData()
        return this.$message.success('添加静态属性成功')
      })
    },
    // 点击按钮，展示修改动态参数的对话框
    async showDynamicEditDialog (id) {
      // 根据id查询当前动态参数的信息
      const { data: res } = await this.$http.get('categories/parameter/' + id)
      if (res.meta.status !== 200) {
        return this.$message.error('根据id查询当前动态参数信息失败')
      }
      this.editDynamicForm = res.data
      this.editDynamicDialogVisible = true
    },
    // 监听修改动态参数对话框的关闭
    editDynamicDialogClosed () {
      this.$refs.editDynamicFormRef.resetFields()
    },
    // 点击按钮修改动态参数
    editParameter () {
      this.$refs.editDynamicFormRef.validate(async validate => {
        if (!validate) return
        const { data: res } = await this.$http.get('categories/' + this.editDynamicForm.id + '/parameter/' + this.editDynamicForm.parameter)
        if (res.meta.status !== 200) {
          return this.$message.error('根据id修改当前动态参数信息失败')
        }
        this.$message.success('根据id修改当前动态参数信息成功')
        this.getParamsData()
        this.editDynamicDialogVisible = false
      })
    },
    // 点击按钮，展示修改静态属性的对话框
    async showStaticEditDialog (id) {
      // 根据id查询当前静态属性的信息
      const { data: res } = await this.$http.get('categories/attribute/' + id)
      if (res.meta.status !== 200) {
        return this.$message.error('根据id查询当前静态属性信息失败')
      }
      console.log(res)
      this.editStaticForm = res.data
      this.editStaticDialogVisible = true
    },
    // 监听修改静态属性对话框的关闭
    editStaticDialogClosed () {
      this.$refs.editStaticFormRef.resetFields()
    },
    // 点击按钮修改静态属性
    editAttribute () {
      this.$refs.editStaticFormRef.validate(async validate => {
        if (!validate) return
        const { data: res } = await this.$http.get('categories/' + this.editStaticForm.id + '/attribute/' + this.editStaticForm.attribute)
        if (res.meta.status !== 200) {
          return this.$message.error('根据id修改当前静态属性信息失败')
        }
        this.$message.success('根据id修改当前静态属性信息成功')
        this.getParamsData()
        this.editStaticDialogVisible = false
      })
    },
    // 根据id删除对应的动态参数
    async removeParameter (id) {
      const confirmResult = await this.$confirm('此操作将永久删除该动态参数, 是否继续?', '提示', {
        confirmButtonText: '确定',
        type: 'warning'
      }).catch(err => err)
      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消删除')
      }
      const { data: res } = await this.$http.delete('categories/parameter/' + id)
      if (res.meta.status !== 200) {
        return this.$message.error('根据id删除动态参数失败')
      }
      this.$message.success('根据id删除动态参数成功')
      this.getParamsData()
    },
    // 根据id删除对应的静态属性
    async removeAttribute (id) {
      const confirmResult = await this.$confirm('此操作将永久删除该静态属性, 是否继续?', '提示', {
        confirmButtonText: '确定',
        type: 'warning'
      }).catch(err => err)
      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消删除')
      }
      const { data: res } = await this.$http.delete('categories/attribute/' + id)
      if (res.meta.status !== 200) {
        return this.$message.error('根据id删除静态属性失败')
      }
      this.$message.success('根据id删除静态属性成功')
      this.getParamsData()
    }
  },
  computed: {
    isBtnDisabled () {
      if (this.selectedCateKeys.length !== 2) {
        return true
      }
      return false
    }
  }
}
</script>

<style scoped>
.cat_opt{
    margin: 15px 0;
}
.el-cascader{
    margin-left: 10px;
}
</style>
