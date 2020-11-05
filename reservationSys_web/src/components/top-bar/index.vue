<template>
<div class="header">
  <div class="title">
    <div style="padding-left:10px">湾区科创园区系统</div>
    <div>
      <breadCrumb></breadCrumb>
    </div>
    
  </div>
  <div class="user">
      <!-- 退出登录 -->
      <el-dropdown>
        <div>
         用户中心({{userInfo.name?userInfo.name:userInfo.account}})
        </div>
        <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
            <el-dropdown-item @click.native="forgotPassword">修改密码</el-dropdown-item>
          </el-dropdown-menu>
      </el-dropdown>
  </div>
  <ForgotPasswordModal ref="forgotPasswordModal"></ForgotPasswordModal>
</div>
</template>

<script>
import breadCrumb from "@/components/breadCrumb";
import { mapState } from 'vuex';
import auth from '@/service/auth';
import ForgotPasswordModal from '@/components/forgot-password-modal';

export default {
  name: 'topbar',
  components: {
    breadCrumb,
    ForgotPasswordModal
  },
  data() {
    return {
      form: {
        search: ''
      },
      userInfo: {}
    };
  },

  computed: {
    ...mapState({
      isLogged: state => state.isLogged
    })
  },

  methods: {
    search() {
      const search = this.form.search;
      let type = 'push';

      if (this.$route.name === 'search') {
        type = 'replace';
      }
      this.$router[type]({
        path: '/search',
        query: {
          keyword: search.trim()
        }
      });
    },
    logout() {
      this.$confirm('是否确定退出系统?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        showClose: false
      })
        .then(() => {
          auth.expireToLogin('/');
        })
        .catch(() => {});
    },

    forgotPassword() {
      this.$refs.forgotPasswordModal.show();
    }
  },

  created() {
    if (auth.loggedIn) {
      this.$store
      .dispatch('getUserInfo')
      .then(data => {
        this.userInfo = data;
      });
    }
    
  }
};
</script>

<style lang="scss" scoped>
.header{
  box-sizing: border-box;
  padding: 0 20px;
  width: 100%;
  height: 100%;
  align-items: center;
  justify-content: space-between;
  display: flex;
  .title{
    box-sizing: border-box;
    font-size: 20px;
    padding-top: 20px;
  }
  .user{
    cursor: pointer;
  }
  .user:hover{
    font-weight: bold;
  }

}
</style>

