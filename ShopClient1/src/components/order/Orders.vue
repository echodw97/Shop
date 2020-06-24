/* eslint-disable no-unused-vars */
<template>
  <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>订单管理</el-breadcrumb-item>
      <el-breadcrumb-item>订单列表</el-breadcrumb-item>
    </el-breadcrumb>

    <!--卡片内容区  -->
    <el-card>
      <!-- 搜索与添加区 -->
      <el-row :gutter="40">
        <el-col :span="8">
          <el-input placeholder="请输入内容" v-model="queryInfo.query">
            <el-button slot="append" icon="el-icon-search" @click="getOrderListLike()"></el-button>
          </el-input>
        </el-col>
         <el-col :span="4">
            <el-button type="primary" @click="showAddOrderDialog">添加订单</el-button>
        </el-col>
      </el-row>
      <!-- 订单列表区域 -->
      <el-table :data="orderList" border stripe>
        <el-table-column label="序号" type="index"></el-table-column>
        <el-table-column label="订单编号" prop="numbering"></el-table-column>
        <el-table-column label="商品" prop="commodity"></el-table-column>
        <el-table-column label="价格" prop="price"></el-table-column>
        <el-table-column label="是否付款" prop="payment">
             <template slot-scope="scope">
                <el-tag v-if="scope.row.payment === '是'">已付款</el-tag>
                <el-tag type="danger" v-else>未付款</el-tag>
            </template>
        </el-table-column>
        <el-table-column label="是否发货" prop="ship"></el-table-column>
       <el-table-column label="创建时间" prop="createTime"></el-table-column>
        <el-table-column label="操作" width="230px">
          <template slot-scope="scope">
            <!-- 修改按钮 -->
            <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row.id)">修改</el-button>
            <!-- 删除按钮 -->
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeOrderById(scope.row.id)">删除</el-button>
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

       <!-- 添加订单对话框 -->
    <el-dialog title="添加订单" :visible.sync="addOrderDialogVisible" width="50%" @close="addOrderDialogClosed">
      <el-form
        :model="addOrderForm"
        ref="addOrderFormRef"
        label-width="100px">
        <el-form-item label="商品名">
          <el-cascader
            v-model="selectionedKeys"
            :options="commodityList"
            expandTrigger='hover'
            :props='cascaderProps'
            :clearable="true"
            :change-on-select="true"
            size="medium"
          ></el-cascader>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addOrderDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addOrder">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 修改用户对话框 -->
    <el-dialog title="修改订单" :visible.sync="editDialogVisible" width="50%" @close="editDialogClosed">
      <el-form
        :model="editForm"
        ref="editFormRef"
        label-width="80px">
        <el-form-item label="订单号">
          <el-input v-model="editForm.numbering" disabled></el-input>
        </el-form-item>
         <el-form-item label="商品名">
          <el-cascader
            v-model="selectionedKeys"
            :options="commodityList"
            expandTrigger='hover'
            :props='cascaderProps'
            :clearable="true"
            :change-on-select="true"
            size="medium"
          ></el-cascader>
        </el-form-item>
         <el-form-item label="是否付款">
          <el-input v-model="editForm.payment" ></el-input>
        </el-form-item>
         <el-form-item label="是否发货">
          <el-input v-model="editForm.ship"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editOrderInfo">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      queryInfo: {
        query: '',
        page: 1,
        size: 10
      },
      // 获取订单列表的参数对象
      orderList: [],
      total: 0,
      //   控制添加订单对话框的显示与隐藏
      addOrderDialogVisible: false,
      // 添加订单表单数据对象
      addOrderForm: {
        // 将要添加的商品名称
        name: ''
      },
      // 商品列表
      commodityList: [],
      cascaderProps: {
        value: 'id',
        label: 'name'
      },
      // 选中的订单ID数组
      selectionedKeys: [],
      // 修改分类信息对话框
      editDialogVisible: false,
      // 查询到的用户信息对象
      editForm: {
        numbering: '',
        payment: '',
        ship: ''
      }
    }
  },
  created () {
    this.getOrderList()
  },
  methods: {
    // 响应所有的用户列表
    async getOrderList () {
      const { data: res } = await this.$http.get(
        'order/' + this.queryInfo.page + '/' + this.queryInfo.size
      )
      if (res.meta.status !== 200) {
        return this.$message.error('获取订单列表失败')
      }
      this.orderList = res.data.records
      this.total = res.data.total
    },
    // 模糊查询匹配订单列表
    async getOrderListLike () {
      const { data: res } = await this.$http.get(
        'order/' +
          this.queryInfo.page +
          '/' +
          this.queryInfo.size +
          '/' +
          this.queryInfo.query
      )
      if (res.meta.status !== 200) {
        return this.$message.error('获取订单列表失败')
      }
      this.orderList = res.data.records
      this.total = res.data.total
    },
    // 监听pageSize改变事件
    handleSizeChange (newSize) {
      this.queryInfo.size = newSize
      this.getOrderList()
    },
    // 监听页面值改变事件
    handleCurrentChange (newPage) {
      this.queryInfo.page = newPage
      this.getOrderList()
    },
    // 点击按钮展示添加订单的对话框
    showAddOrderDialog () {
      this.getCommodityList()
      this.addOrderDialogVisible = true
    },
    // 获取商品名称
    async getCommodityList () {
      const { data: res } = await this.$http.get('order/commodity')
      if (res.meta.status !== 200) {
        return this.$message.error('获取商品列表失败')
      }
      this.commodityList = res.data
    },
    addOrder () {
      this.$refs.addOrderFormRef.validate(async valid => {
        if (!valid) return
        const { data: res } = await this.$http.post('addorder/' + this.selectionedKeys)
        if (res.meta.status !== 201) {
          return this.$message.error('添加订单失败')
        }
        this.addOrderDialogClosed()
        this.getOrderList()
        this.$message.success('添加订单成功!')
        this.addOrderDialogVisible = false
      })
    },
    // 监听对话框关闭事件，重置表单数据
    addOrderDialogClosed () {
      this.$refs.addOrderFormRef.resetFields()
      this.selectionedKeys = []
    },
    // 展示修改用户的对话框
    async showEditDialog (id) {
      this.getCommodityList()

      const { data: res } = await this.$http.post('getorder/' + id)
      if (res.meta.status !== 200) {
        return this.$message.error('获取订单信息失败')
      }
      this.editForm = res.data
      this.editDialogVisible = true
    },
    // 当关闭修改用户对话框后重置表单数据
    editDialogClosed () {
      this.$refs.editFormRef.resetFields()
    },
    // 修改订单信息并提交，并在提交前实现预验证
    editOrderInfo () {
      this.$refs.editFormRef.validate(async valid => {
        if (!valid) return
        const { data: res } = await this.$http.post('updateorder/' + this.selectionedKeys + '/' + this.editForm.id + '/' + this.editForm.payment + '/' + this.editForm.ship)
        if (res.meta.status !== 201) {
          return this.$message.error('修改订单失败')
        }
        this.$message.success('修改订单成功')
        this.editDialogVisible = false
        this.getOrderList()
      })
    },
    // 根据id值来删除用户
    async removeOrderById (id) {
      const confirmResult = await this.$confirm('此操作将永久删除该订单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        type: 'warning'
      }).catch(err => err)
      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消删除')
      }
      const { data: res } = await this.$http.delete('order/' + id)
      if (res.meta.status !== 200) {
        return this.$message.error('删除订单失败')
      }
      this.$message.success('删除订单成功')
      this.getOrderList()
    }
  }
}
</script>

<style lang="less" scoped>
</style>
