<template>
    <div class="container">
        <div class="btn-area">
            <el-date-picker
            @change="searchData"
                style="width:150px"
                size="small"
                v-model="searchDate"
                type="date"
                placeholder="预约日期"
            >
            </el-date-picker>
            <!-- <Button type="primary" @click="searchData">查看</Button> -->
            <Button type="primary" @click="prevData">前七天</Button>
            <Button type="primary" style="margin-left:6px" @click="todayData">今天</Button>
            <Button type="primary" style="margin-left:6px" @click="nextData">后七天</Button>
            <Button v-if="showBtn" type="success" @click="showModal">预定</Button>
            <div class="example">
                <div class="ex-main">
                    <div class="bar-item">
                        <div class="dot yellow"></div>
                        <div class="text">已预约</div>
			        </div>
                    <!-- <div class="bar-item">
                        <div class="dot"></div>
                        <div class="text">可预约</div>
			        </div> -->
                    <div class="bar-item">
                        <div class="dot gray"></div>
                        <div class="text">不可约</div>
			        </div>
                </div>
            </div>
        </div>
        <div class="container-main">
            <div class="room">
                <div class="room-header">会议室列表</div>
                <div class="room-item" :title="item.name" :class="{'room-select':item.id == searchRoom}" v-for="(item,index) in roomList" :key="index" @click="roomChange(item)">
                    {{item.name}}
                </div>
            </div>
            <div class="table">
                <div class="header">
                    <div class="header-wrapper">
                        <div class="header-blank first-column">会议室预定</div>
                        <div class="header-main">
                            <div class="header-first">
                                <div class="header-morning">
                                    <div>上午</div>
                                    <div>
                                        <!-- 09:00-11:00 -->
                                    </div>
                                </div>
                                <div class="header-afternoon">
                                    <div>下午</div>
                                    <!-- <div>13:00-16:30</div> -->
                                </div>
                            </div>
                            <div class="time-cell">
                                <div class="body-item" v-for="(item,index) in timeLine" :key="index">
                                    {{item.beginTime}}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="body">
                    <div class="body-wrap" v-for="(item,index0) in timeList" :key="index0">
                        <div class="body-time first-column">
                            {{item.bookingDate + ' '}}{{item.bookingWeek}}
                        </div>
                        <div class="body-main">
                            <div class="body-item"  @click="selectCell(item,item1,index0,index1)" :class="{'gray-cell':item1.status == -1,'booked-cell':item1.status == 1,'select-cell':item1.currentSelect,'body-item-hover':item1.inmove}" v-for="(item1,index1) in item.childBookingTimeList" :key="index1">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div>
        </div>
        <myModel :roomType="roomType" :viewType="modelType" :form="modelForm" :modelShow="modelShow" :modelTitle="modelTitle"  @handleClose="handleClose" @handleAdd="handleAdd"></myModel>
    </div>
</template>
<script>
import myModel from '@/components/model'
import meeting from '@/api/meeting';
export default {
    name:'reservate',
    data(){
        return{
            roomType:'meeting',
            modelType:'add',
            modelForm:{
                visitDate:'2020-08-08',
                beginTime:'09:00',
                endTime:'09:30',
                visitCompanyList:[{name:''}],
                meetingId:'',
                meetingRoomName:''
            },
            modelShow:false,
            modelTitle:'新增预定',
            searchDate:new Date(),
            searchRoom:'',
            selectItem:{
                date:'',
                timeObj:''
            },
            showBtn:false,
            ModalTitle:'新增预定',
            form:{
                visitDate:'2020-08-08',
                beginTime:'09:00',
                endTime:'09:30'
            },
            timeList:[
            ],
            timeLine:[],
            roomList:[],
            rowStart:null,
            rowEnd:null,
            startFlag:false,
            cellStart:null,
            cellEnd:null,
            currentGap:[]
        }
    },
    components:{
        myModel
    },
    mounted(){
        // this.getData()
        this.init()
    },
    methods:{
        init(){
            this.searchDate = this.func_date(this.searchDate,1)
            // 获取会议室列表
            meeting.getRoomListByType({type:true}).then(res => {
                this.roomList = res.data;
                this.searchRoom = res.data[0].id;
                this.modelForm.meetingRoomName = res.data[0].name;
                this.modelForm.meetingId = res.data[0].id;
                this.getData()
            });
            // 获取会议室时间信息
            // this.getData()
        },
        roomChange(item){
            this.searchRoom = item.id;
            this.modelForm.meetingRoomName = item.name;
            this.modelForm.meetingId = item.id;
            this.getData()
            this.showBtn = false
        },
        // 划过单元格
        // enterCell(rowItem,cellItem,rowIndex,cellIndex) {
        //     console.log('llll')
        //     this.$set(cellItem,'inmove',true)
        // },
        // leaveCell(rowItem,cellItem,rowIndex,cellIndex) {
        //     console.log('llll')
        //     // this.$set(cellItem,'inmove',false)
        // },
        // 选中单元格
        selectCell(rowItem,cellItem,rowIndex,cellIndex) {
            const that = this;
            // 生成选中区间函数
            function setGap(rowS,rowE,cellS,cellE){
                if(that.currentGap.length>1 ){ // 当前选中的格子存在于已被选中的多个区间，取消选中区间
                    that.currentGap.forEach(function(item){
                        that.$set(that.timeList[rowS].childBookingTimeList[item.index],'currentSelect',false)
                    })
                    that.$set(cellItem,'currentSelect',true)
                    that.currentGap = new Array(cellItem);
                }else {
                    if(rowS == rowE){ // 当前选中在同一行
                        let max = Math.max(cellS,cellE);
                        let min = Math.min(cellS,cellE);
                        let selectArr = that.timeList[rowS].childBookingTimeList.slice(min,max+1);
                        let selectAble = selectArr.every(function(item){
                            return item.status == 0
                        })
                        if(selectAble){ // 如果当前区间内不存在不可用的格子，选中该区间
                            for(let i=min;i<=max;i++){
                                that.$set(that.timeList[rowS].childBookingTimeList[i],'currentSelect',true)
                                that.$set(that.timeList[rowS].childBookingTimeList[i],'index',i)
                            }
                            that.currentGap = selectArr;
                        }else{ // 如果当前区间内存在不可用的格子，选中当前格子
                            that.currentGap = new Array(cellItem);
                        }
                    }else if(rowS != rowE){ // 跨行，选中当前格子
                        that.currentGap = new Array(cellItem);
                    }
                }
            }
            // 可点击
            if(cellItem.status == 0){
                // 清空选中
                if(that.currentGap.length > 0){
                    for(let value of this.timeList) {
                        if(value.childBookingTimeList) {
                            for(let childValue of value.childBookingTimeList){
                                that.$set(childValue,'currentSelect',false)
                            }
                        }
                    }
                }
                
                // 默认选中当前单元格
                if(!cellItem.currentSelect){
                    that.$set(cellItem,'currentSelect',true)
                }else{
                    that.$set(cellItem,'currentSelect',false)
                }
                
                that.showBtn = true;
                that.currentRow = rowItem;
                if(!this.startFlag){
                    this.startFlag = !this.startFlag
                    this.rowStart = rowIndex;
                    this.cellStart = cellIndex;
                }else{
                    this.startFlag = !this.startFlag
                    this.rowEnd = rowIndex;
                    this.cellEnd = cellIndex;
                }
                setGap(this.rowStart,this.rowEnd,this.cellStart,this.cellEnd);
                that.modelForm.visitDate = that.currentRow.bookingDate
                that.modelForm.beginTime = that.currentGap[0].beginTime
                that.modelForm.endTime = that.currentGap[that.currentGap.length-1].endTime
            }
        },
        // 获取数据
        getData(){
            let params = {
                visitDate: this.searchDate,
                meetingId:this.searchRoom,
                num:7
            }
            meeting.queryMeetingroomTime(params).then(res => {
                this.timeList = res.data
                this.timeLine = res.data[0].childBookingTimeList;
            })
        },
        // 查找
        searchData(){
            this.showBtn = false;
            this.getData()
        },
        //前七天
        prevData(){
            this.showBtn = false;
            this.searchDate = this.func_date(this.searchDate,-7);
            this.getData()
        }, 
        // 今天     
        todayData(){
            this.showBtn = false;
            this.searchDate = this.func_date(new Date(),0);
            this.getData()
        },
        // 后七天
        nextData(){
            this.showBtn = false;
            this.searchDate = this.func_date(this.searchDate,7);
            this.getData()
        },
        // 日期加减函数
        func_date(curdate,days){
            var date1 = new Date(curdate),
            time1=date1.getFullYear()+"-"+(date1.getMonth()+1)+"-"+date1.getDate();//time1表示当前时间
            var date2 = new Date(date1);
            date2.setDate(date1.getDate()+days);
            var time2 = date2.getFullYear()+"-"+(date2.getMonth()+1)+"-"+date2.getDate();
            return JSON.parse(JSON.stringify(date2));
        },
        // 显示预约弹窗详情
        showModal(){
            this.modelShow = true
        },
        // 关闭弹窗
        handleClose(){
            this.modelShow = false
        },
        // 新增预约
        handleAdd(v){
            meeting.addBooking(v).then(res => {
                if(res.code ==200){
                    this.showBtn = false;
                    this.$Message.success('预定成功')
                    this.modelShow = false;
                    this.getData();
                }
            })
        }
    }
}
</script>
<style lang="scss" scoped>
div{
    box-sizing: border-box;
}
.container{
    padding: 20px;
}
.container-main{
    display: flex;
    .room{
        margin-right: 20px;
        min-width: 10%;
        .room-header{
            margin-bottom: 20px;
            font-weight: bold;
        }
        .room-item{
            cursor: pointer;
            padding:5px 10px;
            max-width: 100px;
            text-overflow: ellipsis;
            overflow: hidden;
            white-space: nowrap;
        }
        .room-select{
            background-color: #617FDE;
            color: #fff;
        }
    }
    .table{
        flex: 1;
        box-sizing: border-box;
        display: flex;
        flex-direction: column;
        .header{
            width: 100%;
            box-sizing: border-box;
            display: flex;
            text-align: center;
            border: 2px solid rgb(144, 177, 202);
            .header-wrapper{
                width: 100%;
                display: flex;
                .header-blank{
                    width: 15%;
                    height: 80px;
                    line-height: 80px;
                    border-right: 2px solid rgb(144, 177, 202);
                }
                .header-main{
                    width: 85%;
                    .header-first{
                        width: 100%;
                        height: 40px;
                        display: flex;
                        border-bottom: 2px solid rgb(144, 177, 202);
                        .header-morning{
                            border-right: 2px solid rgb(144, 177, 202);
                            width: 41%;
                        }
                        .header-afternoon{
                            flex: 1;
                        }

                    }
                }
                .time-cell{
                    display: flex;
                    width: 100%;
                    div {
                        box-sizing: border-box;
                        flex: 1;
                        height: 40px;
                        line-height: 40px;
                        border-right: 2px solid rgb(144, 177, 202);
                        overflow: hidden;
                    }
                    div:last-child{
                        border:none;
                    }

                }
                .header-title{
                    height: 40px;
                    text-align: center;
                    border-bottom: 2px solid rgb(144, 177, 202);;
                }
            }
            
        }
        .body{
            width: 100%;
            border: 2px solid rgb(144, 177, 202);
            border-top:none;
            border-bottom: none;
            .body-wrap{
                display: flex;
                width: 100%;
                border-bottom: 2px solid rgb(144, 177, 202);
                .body-time{
                    width: 15%;
                    height: 40px;
                    line-height: 40px;
                    text-align: center;
                    border-right: 2px solid rgb(144, 177, 202);
                }
                .body-main{
                    width: 85%;
                    display: flex;
                    .body-item{
                        cursor: pointer;
                        flex: 1;
                        height: 40px;
                        border-right: 2px solid rgb(144, 177, 202);
                    }
                    .body-item-hover{
                        background-color: rgb(196, 204, 230);
                    }
                    div:last-child{
                        border:none;
                    }
                }
            }
            .gray-cell{
                background-color: #F2F2F2;
                // border-color: #F2F2F2 !important;
            }
            .booked-cell{
                background-color: rgb(189, 141, 45);
                // border: none!important;
                // border-color: rgb(189, 141, 45) !important;
            }
            .select-cell{
                background-color: #617FDE;
                border-color: #617FDE !important;
            }
        }
    }
}

.btn-area{
    margin-bottom: 30px;
    button{
        margin-left: 20px;
    }
    display: flex;
    align-items: center;
    .example{
        display: inline-block;
        margin-left: 60px;
        .ex-main{
            display: flex;
            .bar-item{
				display: flex;
				align-items: center;
				margin-right: 15px;
				.dot{
					display: inline-block;
					margin-right: 10px;
					height: 30px;
					width: 45px;
					// background-color: #fff;
					border-radius: 5%;
				}
				.yellow{
					background-color: rgb(189, 141, 45);
				}
				.gray{
					background-color: #F2F2F2;
				}
				
				.text{
					font-size: 15px;
				}
			}
        }
    }
}
div{
    box-sizing: border-box;
}
</style>