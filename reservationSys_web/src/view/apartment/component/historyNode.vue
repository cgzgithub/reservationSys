<template>
    <div class="flow-main">
        <div style="margin-bottom:20px;">审核历史</div>
        <div class="flow-item" v-for="(item,index) in dataList" :key="index">
            <div class="item-large">
                <div class="left-large">
                    <div class="radio-large">
                    </div>
                    <div v-show="index != dataList.length-1" class="line-large">
                    </div>
                </div>
                <div class="right-large">
                    <div class="stage">{{item.stageName}}</div>
                    <div class="title">{{item.reviewOpinion}}</div>
                    <div class="name">{{item.reviewer}}</div>
                    <div class="time">{{item.reviewTime}}</div>
                </div>
            </div>
        </div>
        <div v-if="dataList.length==0" style="padding-left:100px">暂无数据</div>
    </div>
</template>
<script>
export default {
    name:'historynode',
    data(){
        return{
            childIndex:null,
            firstIndex:null,
            statusOptions:[ //申请状态
                {
                    label:'审核不通过',
                    value:-1
                },
                {
                    label:'未提审',
                    value:0
                },
                {
                    label:'复核',
                    value:1
                },
                {
                    label:'湾区分管审核',
                    value:2
                },
                {
                    label:'湾区总经理审核',
                    value:3
                },
                {
                    label:'镇分管审核',
                    value:4
                },
                {
                    label:'审核通过',
                    value:5
                }
            ],
        }
    },
    props:{
        flowList:{
            type:Array,
            default:function(){
                return []
            }
        }
    },
    computed:{
        dataList:function(){
            let that = this
            this.flowList.forEach(function(item){
                let re = that.statusOptions.find( (v) => {
                        return v.value == item.stage
                    })
                    item.stageName = re.label
            });
            return this.flowList;
        }
    },
    mounted(){
    },
    methods:{

    }
}
</script>
<style lang="scss" scoped>
.flow-main{
    margin-top:20px;
    padding-left: 40px;
    .flow-item{
        .item-large{
            display: flex;
            .left-large{
                width:10%;
                margin-right:20px;
                display: flex;
                flex-direction: column;
                // justify-content: center;
                align-items: center;
                .radio-large{
                    width: 18px;
                    height: 18px;
                    border:4px solid #666;
                    border-radius:50%;
                }
                .line-large{
                    width: 2px;
                    height: 35px;
                    background-color: #666;
                }
            }
            .right-large{
                font-size: 16px;
                display: flex;
                flex:1;
                div{
                    flex:1;
                }
            }
        }
    }
}
</style>