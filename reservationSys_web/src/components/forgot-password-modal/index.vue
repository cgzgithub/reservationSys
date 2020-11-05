<template>
  <el-dialog :title="title" :visible.sync="visible" width="30%" center>
    <el-form :model="form" status-icon :rules="rules" ref="form" label-width="100px">
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="form.phone" :readonly="isLogged"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="newPwd" required>
        <el-input type="password" v-model="form.newPwd" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="重复新密码" prop="repeatPwd" required>
        <el-input type="password" v-model="form.repeatPwd" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submit">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { mapState } from 'vuex';
import auth from '@/service/auth';
import user from '@/api/user';

export default {
  props: {
    title: {
      type: String,
      default: '忘记密码'
    }
  },

  data() {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.form.repeatPwd !== '') {
          this.$refs.form.validateField('repeatPwd');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.form.newPwd) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      visible: false,
      form: {
        phone: '',
        newPwd: '',
        repeatPwd: ''
      },
      rules: {
        newPwd: [{ validator: validatePass, trigger: 'blur' }],
        repeatPwd: [{ validator: validatePass2, trigger: 'blur' }],
        phone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { pattern: /^[1-9]\d{10}$/, message: '请输入正确的手机号' }
        ]
      }
    };
  },

  computed: {
    ...mapState({
      isLogged: state => state.isLogged,
      userInfo: state => state.userInfo
    })
  },

  methods: {
    show() {
      this.visible = true;
      this.getUserInfo()
    },
    close() {
      this.visible = false;
    },
    submit() {
      this.$refs.form.validate(valid => {
        if (valid) {
          user.changePwd(this.form).then(() => {
            this.close();
            this.$alert('密码修改成功，去登录', '温馨提示', {
              confirmButtonText: '去登录',
              showClose: false,
              callback: action => {
                this.gotoLogin();
              }
            });
          });
        }
      });
    },
    gotoLogin() {
      auth.logout().then(() => {
        auth.expireToLogin('');
      });
    },
    getUserInfo(){
      if (auth.loggedIn) {
      this.$store.dispatch('getUserInfo').then(data => {
        this.form.phone = data.account;
      });
    }
    }
  },

  created() {
    // this.getUserInfo()
  }
};
</script>

<style>
</style>
