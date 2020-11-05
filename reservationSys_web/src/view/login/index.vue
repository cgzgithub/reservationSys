<template>
    <div class="container">
        <div class="login">
            <div class="title">
                用户登录
            </div>
            <Form ref="form"  :mode="form" :rules="formValidate">
                <FormItem  prop="username">
                    <Input placeholder="请输入手机号" v-model="form.username" />
                </FormItem>
                <FormItem  prop="password">
                    <Input type="password" placeholder="请输入密码" v-model="form.password" @keyup.enter.native="login" />
                </FormItem>
                <FormItem >
                    <Button type="primary" @click="login" style="margin-right:20px">登录</Button>
                    <!-- <a @click.prevent="forgetPass">忘记密码?</a> -->
                </FormItem>
            </Form>
            
        </div>
        <ForgotPasswordModal ref="ForgotPasswordModal"></ForgotPasswordModal>
    </div>
</template>
<script>
import ForgotPasswordModal from '@/components/forgot-password-modal';
import auth from '@/service/auth'
export default {
    name:'login',
    data(){
        return{
            form:{
                loginChannel:'MANAGER'
            },
            formValidate:{
                // username:[{required: true, message: "用户名不能为空"}],
                // password:[{required:true,message:"密码不能为空"}]
            }
        }
    },
    components:{
        ForgotPasswordModal
    },
    mounted(){
        
    },
    methods:{
        login(){
            this.$refs.form.validate(valid => {
                if(valid){
                    auth.login(this.form).then(() =>{
                        this.$router.replace('/')
                    })
                }else{

                }
            })
        },
        forgetPass(){
            this.$refs.ForgotPasswordModal.show()
        }
    }
}
</script>
<style lang="scss" scoped>
.container{
    display: flex;
    width: 100vw;
    height: 100vh;
    align-items: center;
    justify-content: center;
    background-color: rgb(240, 239, 239);
    .login{
        background-color: #fff;
        width: 500px;
        padding:60px 60px;
        border:1px solid #ccc;
        border-radius: 5px;
        .title{
            font-weight: bold;
            margin-bottom: 20px;
        }
    }
}
</style>