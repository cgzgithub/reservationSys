<template>
    <div class="flow-main">
        <div class="header">
            <div class="title">项目阶段名称</div>
            <div class="file">上传文件</div>
            <div class="name">负责人</div>
            <div class="time">最后修改时间</div>
        </div>
        <div class="flow-item" v-for="(item,index) in flowList" :key="index">
            <div class="item-large" :class="{'finish':item.isFinished}">
                <div class="left-large">
                    <div class="radio-large">
                    </div>
                    <div v-if="!item.isLast"  class="line-large">
                    </div>
                </div>
                <div class="right-large">
                    <div class="title">{{item.dictName}}</div>
                    <div class="item-upload" v-if="item.relationVOList && item.relationVOList.length > 0">
                        <div  v-for="(item3,index) in item.relationVOList" :key="index">
                        <a class="over-flow" :title="item3.fileName" target="_blank" download="file" :href="item3.downloadUrl">{{item3.fileName}}</a>
                        </div>
                    </div>
                    <div class="item-upload" v-else>
                        
                    </div>
                    <div class="name">{{item.person}}</div>
                    <div class="time">{{item.finishTime}}</div>
                </div>
            </div>
            <div class="item-small" v-if="item.childList" >
                <div class="item-small-box" :class="{'finish':item1.isFinished}" v-for="(item1,index1) in item.childList" :key="index1">
                    <div class="left-small">
                        <div class="radio-small">
                        </div>
                        <div v-if="!item1.isLast" class="line-small">
                        </div>
                    </div>
                    <div class="right-small">
                        <div>{{item1.dictName}}</div>
                        <div class="item-upload" v-if="item1.relationVOList && item1.relationVOList.length > 0">
                            <div v-for="(item2,index) in item1.relationVOList" :key="index">
                            <a class="over-flow" :title="item2.fileName" target="_blank" download="file" :href="item2.downloadUrl">{{item2.fileName}}</a>
                            </div>
                        </div>
                        <div class="item-upload" v-else>
                            
                        </div>
                        <div>{{item1.person}}</div>
                        <div>{{item1.finishTime}}</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    name:'flownode',
    data(){
        return{
        }
    },
    props:{
        flowList:{
            type:Array
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
    padding-right: 10px;
    .header{
        display: flex;
        font-size: 16px;
        font-weight: bold;
        padding-left: 105px;
        margin-bottom: 20px;
        div{
            flex: 1;
        }
        .file{
            // margin-left: 20px;
            // margin-right: 25px;
            width: 100px;
        }
        .name{
            // margin-left: 25px;
        }
    }
    .flow-item{
        .finish{
            .right-large{
                div{
                    color: rgb(19, 190, 70) !important;
                }
            }
            .right-small{
                div{
                    color: rgb(19, 190, 70) !important;
                }
            }
            
            
            .radio-large{
                    border-color: rgb(19, 190, 70) !important;
            }
            .line-large{
                    background-color: rgb(19, 190, 70) !important;
            }
            .radio-small{
                    border-color: rgb(19, 190, 70) !important;
            }
            .line-small{
                    background-color: rgb(19, 190, 70) !important;
            }
        }
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
                    width: 20px;
                    height: 20px;
                    border:2px solid rgb(155, 152, 152);
                    border-radius:50%;
                }
                .line-large{
                    width: 2px;
                    min-height: 35px;
                    flex:1;
                    background-color: rgb(155, 152, 152)
                }
            }
            .right-large{
                font-size: 18px;
                display: flex;
                flex:1;
                div{
                    flex:1;
                    color: rgb(117, 119, 119);
                }
                // .name{
                //     max-width: 100px;
                // }
                .item-upload{
                    // margin-left: 25px;
                    // margin-right: 25px;
                    width: 100px;
                    .over-flow{
                        display: inline-block;
                        white-space: nowrap;
                        width: 100%;
                        font-size: 14px;
                        overflow: hidden;
                        text-overflow: ellipsis;
                    }
                }
            }
        }
        .item-small{

        }
        .item-small-box{
            display: flex;
            .left-small{
                width:10%;
                margin-right:20px;
                display: flex;
                flex-direction: column;
                // justify-content: center;
                align-items: center;
                .radio-small{
                    width: 12px;
                    height: 12px;
                    border:3px solid rgb(155, 152, 152);
                    border-radius:50%;
                }
                .line-small{
                    width: 2px;
                    min-height: 35px;
                    flex:1;
                    background-color: rgb(155, 152, 152);
                }
            }
            .right-small{
                font-size: 14px;
                display: flex;
                flex:1;
                padding-left: 20px;
                div{
                    flex:1;
                    color: rgb(117, 119, 119);
                }
                // .name{
                //     max-width: 100px;
                // }
                .item-upload{
                    // margin-left: 15px;
                    // margin-right: 15px;
                    width: 100px;
                    .over-flow{
                        display: inline-block;
                        // line-height: 25px;
                        width: 100%;
                        font-size: 14px;
                        overflow: hidden;
                        text-overflow: ellipsis;
                    }
                }
                
            }
        }
    }
}
</style>