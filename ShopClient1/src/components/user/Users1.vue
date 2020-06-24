/* eslint-disable no-unused-vars */
<template>
  <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
      <el-breadcrumb-item>用户列表</el-breadcrumb-item>
    </el-breadcrumb>

    <!--卡片内容区  -->
    <el-card>
      <!-- 搜索与添加区 -->
      <el-row :gutter="40">
        <el-col :span="8">
          <el-input placeholder="请输入工作岗位" v-model="queryInfo.query">
            <el-button slot="append" icon="el-icon-search" @click="getUserListLike()"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible = true">匹配求职者</el-button>
        </el-col>
      </el-row>
      <!-- 用户列表区域 -->
      <el-table :data="userList" border stripe>
        <el-table-column label="序号" type="index"></el-table-column>
        <el-table-column label="姓名" prop="username"></el-table-column>
        <el-table-column label="简历ID" prop="email"></el-table-column>
        <el-table-column label="匹配度" prop="mobile"></el-table-column>
        <el-table-column label="操作" width="230px">
          <template slot-scope="scope">
            <!-- 修改按钮 -->
            <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row.id)">查看简历</el-button>
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
    <el-dialog title="添加用户" :visible.sync="addDialogVisible" width="50%" @close="addDialogClosed">
      <el-form
        :model="addForm"
        :rules="addFormRules"
        ref="addFormRef"
        label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="addForm.username"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="addForm.email"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="mobile">
          <el-input v-model="addForm.mobile"></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="roleName">
          <el-input v-model="addForm.roleName"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addUser">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 修改用户对话框 -->
    <el-dialog title="修改用户" :visible.sync="editDialogVisible" width="50%" @close="editDialogClosed">
      <el-form
        :model="editForm"
        :rules="editFormRules"
        ref="editFormRef"
        label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="editForm.username" disabled></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="editForm.email"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="mobile">
          <el-input v-model="editForm.mobile"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editUserInfo">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    // 验证邮箱规则
    // eslint-disable-next-line no-unused-vars
    var checkEmail = (rule, value, cb) => {
      const regEmail = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
      if (regEmail.test(value)) {
        // 合法的邮箱
        return cb()
      }
      cb(new Error('请输入合法的邮箱'))
    }
    // eslint-disable-next-line no-unused-vars
    var checkMobile = (rule, value, cb) => {
      // eslint-disable-next-line no-unused-vars
      const regMobile = /^1[3|4|5|7|8][0-9]{9}$/
      if (regMobile.test(value)) {
        // 合法的手机号
        return cb()
      }
      cb(new Error('请输入合法的手机号码'))
    }
    return {
      queryInfo: {
        query: '',
        page: 1,
        size: 10
      },
      // 获取用户列表的参数对象
      userList: [
        { username: '李晓', email: 23, mobile: 0.13 },
        { username: '张琴', email: 12, mobile: 0.24 },
        { username: '王杨', email: 4, mobile: 0.22 },
        { username: '陈静', email: 21, mobile: 0.35 },
        { username: '李丹丹', email: 11, mobile: 0.53 },
        { username: '杨涵', email: 46, mobile: 0.52 },
        { username: '宋雅婷', email: 37, mobile: 0.67 },
        { username: '翦芬', email: 42, mobile: 0.79 },
        { username: '陈虎', email: 39, mobile: 0.67 },
        { username: '庄佳', email: 25, mobile: 0.77 }
      ],
      total: 20,
      // 控制添加用户对话框的显示与隐藏
      addDialogVisible: false,
      // 添加用户的表单数据
      addForm: {
        username: '',
        email: '',
        mobile: '',
        roleName: ''
      },
      // 添加表单的验证规则
      addFormRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 2, max: 5, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { validator: checkEmail, trigger: 'blur' }
        ],
        mobile: [
          { required: true, message: '请输入手机号码', trigger: 'blur' },
          { validator: checkMobile, trigger: 'blur' }
        ],
        roleName: [
          { required: true, message: '请输入角色', trigger: 'blur' },
          { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ]
      },
      editDialogVisible: false,
      // 查询到的用户信息对象
      editForm: {
        username: '',
        email: '',
        mobile: '',
        roleName: ''
      },
      // 编辑用户规则
      editFormRules: {
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { validator: checkEmail, trigger: 'blur' }
        ],
        mobile: [
          { required: true, message: '请输入手机号码', trigger: 'blur' },
          { validator: checkMobile, trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.getUserList()
  },
  methods: {
    // 响应所有的用户列表
    async getUserList () {
      const { data: res } = await this.$http.get(
        'users/' + this.queryInfo.page + '/' + this.queryInfo.size
      )
      if (res.meta.status !== 200) {
        return this.$message.error('获取用户列表失败')
      }
      this.userList = res.data.records
      this.total = res.data.total
    },
    // 模糊查询匹配用户列表
    async getUserListLike () {
      const { data: res } = await this.$http.get(
        'users/' +
          this.queryInfo.page +
          '/' +
          this.queryInfo.size +
          '/' +
          this.queryInfo.query
      )
      if (res.meta.status !== 200) {
        return this.$message.error('获取用户列表失败')
      }
      this.userList = res.data.records
      this.total = res.data.total
    },
    // 监听pageSize改变事件
    handleSizeChange (newSize) {
      this.queryInfo.size = newSize
      this.getUserList()
    },
    // 监听页面值改变事件
    handleCurrentChange (newPage) {
      this.queryInfo.page = newPage
      this.getUserList()
    },
    // 监听Switch开关的改变
    async userStateChanged (userInfo) {
      const id = userInfo.id
      const mgState = userInfo.mgState
      const { data: res } = await this.$http.post('users/mgState/' + id + '/' + mgState)
      if (res.meta.status !== 200) {
        userInfo.mgState = !userInfo.mgState
        return this.$message.error('获取用户状态失败')
      }
      userInfo.mgState = res.data.mgState
      this.$message.success('更新用户状态成功')
    },
    // 当关闭添加用户对话框后重置表单数据
    addDialogClosed () {
      this.$refs.addFormRef.resetFields()
    },
    // 添加新用户，并在添加前实现预验证
    addUser () {
      this.$refs.addFormRef.validate(async valid => {
        // eslint-disable-next-line no-useless-return
        if (!valid) return
        // eslint-disable-next-line no-unused-vars
        const { data: res } = await this.$http.get('users/' + this.addForm.username + '/' + this.addForm.email + '/' + this.addForm.mobile + '/' + this.addForm.roleName)
        if (res.meta.status !== 201) {
          return this.$message.error('添加用户失败')
        }
        this.$message.success('添加用户成功')
        this.addDialogVisible = false
        this.getUserList()
      })
    },
    // 展示修改用户的对话框
    async showEditDialog (id) {
      // eslint-disable-next-line no-unused-vars
      const { data: res } = await this.$http.post('users/' + id)
      if (res.meta.status !== 200) {
        return this.$message.error('修改用户失败')
      }
      this.editForm = res.data
      this.editDialogVisible = true
    },
    // 当关闭修改用户对话框后重置表单数据
    editDialogClosed () {
      this.$refs.editFormRef.resetFields()
    },
    // 修改用户信息并提交，并在提交前实现预验证
    editUserInfo () {
      this.$refs.editFormRef.validate(async valid => {
        // eslint-disable-next-line no-useless-return
        if (!valid) return
        // eslint-disable-next-line no-unused-vars
        const { data: res } = await this.$http.post('users/' + this.editForm.username + '/' + this.editForm.email + '/' + this.editForm.mobile)
        if (res.meta.status !== 201) {
          return this.$message.error('修改用户失败')
        }
        this.$message.success('修改用户成功')
        this.editDialogVisible = false
        this.getUserList()
      })
    },
    // 根据id值来删除用户
    async removeUserById (id) {
      const confirmResult = await this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        type: 'warning'
      }).catch(err => err)
      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消删除')
      }
      const { data: res } = await this.$http.delete('users/' + id)
      if (res.meta.status !== 200) {
        return this.$message.error('删除用户失败')
      }
      this.$message.success('删除用户成功')
      this.getUserList()
    }
  }
}
</script>

<style lang="less" scoped>
</style>
