<template>
    <div class="load">
        <div class="loader"></div>
        <div class="text">
            授权登录中...
        </div>
    </div>
</template>
<script>
import auth from '@/service/auth'
export default {
    name:'auth',
    data(){
        return{
            form:{
            },
        }
    },
    mounted(){
        this.authUser()
    },
    methods:{
        authUser(){
            // console.log(this.$route.query.phone)
            let phone = this.$route.query.phone;
            let verificationCode = this.$route.query.verificationCode;
            let params = {
              phone,
              verificationCode
            }
            if(!phone || !verificationCode){
              // console.log('没有获取到手机号')    
                // this.$router.replace('/')
            }else{
                auth.pcLogin(params).then(() => {
                  this.$router.replace('/')
                })
            }
        }
    }
}
</script>
<style scoped>
.load{
    padding-top:25%;
}
.text{
    text-align: center;
    margin-top:85px;
}
.load .loader:before,
.load .loader:after,
.load .loader {
  border-radius: 50%;
  width: 20px;
  height: 20px;
  -webkit-animation-fill-mode: both;
  animation-fill-mode: both;
  -webkit-animation: load 1.5s infinite ease-in-out;
  animation: load 1.5s infinite ease-in-out;
}
.load .loader {
  margin: 15px auto;
  font-size: 18px;
  position: relative;
  text-indent: -9999px;
  -webkit-animation-delay: 0.12s;
  animation-delay: 0.12s;
}
.load .loader:before {
  left: -35px;
}
.load .loader:after {
  left: 35px;
  -webkit-animation-delay: 0.25s;
  animation-delay: 0.25s;
}
.load .loader:before,
.loader:after {
  content: '';
  position: absolute;
  top: 0;
}
@-webkit-keyframes load {
  0%,
  80%,
  100% {
    box-shadow: 0 25px 0 -13px #63aef5;
  }
  40% {
    box-shadow: 0 35px 0 0 #63aef5;
  }
}
@keyframes load {
  0%,
  80%,
  100% {
    box-shadow: 0 35px 0 -15px #63aef5;
  }
  40% {
    box-shadow: 0 35px 0 4px #63aef5;
  }
}

</style>