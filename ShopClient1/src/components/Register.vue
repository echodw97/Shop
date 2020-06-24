<template>
  <div class="register_container">
    <div class="login_text"><h2><center>商城后台管理系统</center></h2></div>
    <div class="register_box">
      <!-- 头像区域 -->
      <div class="avatar_box">
        <img src="../assets/logo.png" alt />
      </div>
      <!-- 登陆表单区 -->
      <el-form ref="registerFormRef" :model="registerForm" :rules="registerFormRules"  label-width="0px" class="register_form">
        <!-- 用户名 -->
        <el-form-item prop="username">
          <el-input v-model="registerForm.username" prefix-icon="iconfont icon-user" placeholder="用户名"></el-input>
        </el-form-item>
        <!-- 密码 -->
        <el-form-item prop="password">
          <el-input v-model="registerForm.password" prefix-icon="iconfont icon-3702mima" type="password" placeholder="密码"></el-input>
        </el-form-item>
         <!-- 电话 -->
        <el-form-item prop="mobile">
          <el-input v-model="registerForm.mobile" prefix-icon="el-icon-phone" placeholder="电话"></el-input>
        </el-form-item>
         <!-- 邮箱 -->
        <el-form-item prop="email">
          <el-input v-model="registerForm.email" prefix-icon="el-icon-message" placeholder="邮箱"></el-input>
        </el-form-item>
        <!-- 按钮 -->
        <el-form-item class="btns">
          <el-button type="primary" @click="register">注册</el-button>
          <el-button type="info" @click="restRegisterForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    // 验证邮箱规则
    var checkEmail = (rule, value, cb) => {
      const regEmail = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
      if (regEmail.test(value)) {
        // 合法的邮箱
        return cb()
      }
      cb(new Error('请输入合法的邮箱'))
    }
    // 验证手机规则
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
      // 表单数据绑定
      registerForm: {
        username: '',
        password: '',
        mobile: '',
        email: ''
      },
      // 表单数据验证
      registerFormRules: {
        //   验证用户名是否合法
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 2, max: 5, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ],
        // 验证密码是否合法
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
        ],
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
  methods: {
    restRegisterForm () {
      this.$refs.registerFormRef.resetFields()
    },
    register () {
      this.$refs.registerFormRef.validate(async valid => {
        if (!valid) return
        const { data: res } = await this.$http.get('register/' + this.registerForm.username + '/' + this.registerForm.password + '/' + this.registerForm.mobile + '/' + this.registerForm.email)
        console.log(res)
        if (res.meta.status !== 200) return this.$message.error('注册失败,用户名已存在')
        this.$message.success('注册成功!')
        this.$router.push('/login')
      })
    }
  }
}
</script>

<style lang="less" scoped>
.register_container {
  background: linear-gradient(to right, #2980b9, #6dd5fa, #ffffff);
  height: 100%;
}
.login_text{
 position: absolute;
 margin-top: 60px;
 margin-left: 767px;
 font-size: 24px;
 color: #faffd1;
}

.register_box {
  width: 450px;
  height: 400px;
  background: linear-gradient(to right, #a1ffce, #faffd1);
  border-radius: 3px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);

  .avatar_box {
    height: 130px;
    width: 130px;
    border: 1px solid #eee;
    border-radius: 50%;
    padding: 10px;
    box-shadow: 0 0 10px #ddd;
    position: absolute;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #fff;
    img {
      width: 100%;
      height: 100%;
      border-radius: 50%;
      background-color: #eee;
    }
  }
}
.register_form {
  position: absolute;
  width: 100%;
  top: 80px;
  padding: 0 20px;
  box-sizing: border-box;
}
.btns {
  position: absolute;
  left: 50%;
  transform: translate(-50%);
}
</style>
