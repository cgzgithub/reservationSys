<template>
    <div class="container">
        <div class="main">
            <div class="title">
            欢迎来到湾区科创中心！
            </div>
            <div class="main-wrapper">
                <div class="main-item" :class="'bg-color-'+index" v-for="(item,index) in navList" :key="index" @click="goPage(item)">
                    <div class="icon">
                        <i :class="item.icon"></i>
                    </div>
                    <div class="name">
                        {{item.name}}
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import api from '@/api/menu'
export default {
    name:'home',
    data(){
        return{
            navList:[]
        }
    },
    mounted(){
            this.init()
    },
    methods:{
        init(){
            api.getHomePageMenu().then(res => {
                this.navList = res.data;
            })
        },
        goPage(item){
            this.$router.push({path:item.subMenuFullPath})
        }
    }
}
</script>
<style lang="scss" scoped>
div{
    box-sizing: border-box;
}
.container{
    // text-align: center;
    padding:30px;
    
    .main{
        // text-align: center;
        .title{
            padding-left: 30%;
            font-size: 25px;
            margin-bottom:35px;
            // font-weight: bold;
        }
        .main-wrapper{
            display: flex;
            width: 88%;
            flex-wrap: wrap;
            .main-item{
                width: 30%;
                height: 200px;
                background-color: rgba(80, 125, 223, 0.753);
                text-align: center;
                margin-right: 15px;
                margin-bottom:15px;
                cursor: pointer;
                border-radius:5px;
                transform: scale(1);
                transition-duration: 0.15s;
                display: flex;
                align-items: center;
                justify-content: center;
                flex-direction: column;
                .icon{
                    color: #fff;
                    font-size: 32px;
                    margin-bottom: 10px;
                }
                .name{
                    font-size: 22px;
                    color: #fff;
                }
            }
            .main-item:hover{
                transform: scale(1.03);
                transition-duration: 0.15s;
            }
            .bg-color-0{
                background-color: rgb(91, 182, 224);
            }
            .bg-color-1{
                background-color: rgb(218, 145, 97);
            }
            .bg-color-2{
                background-color: rgb(120, 150, 231);
            }
            .bg-color-3{
                background-color: rgb(58, 113, 214);
            }
            .bg-color-4{
                background-color: rgb(165, 105, 31);
            }
            .bg-color-5{
                background-color: rgb(161, 243, 128);
            }
            .bg-color-6{
                background-color: rgb(91, 182, 224);
            }
            .bg-color-7{
                background-color: rgb(218, 145, 97);
            }
            .bg-color-8{
                background-color: rgb(120, 150, 231);
            }
        }
        
    }
}

</style>