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
                <div class="room-header">路演厅列表</div>
                <div class="room-item" :title="item.name" :class="{'room-select':item.id == searchRoom}" v-for="(item,index) in roomList" :key="index" @click="roomChange(item)">
                    {{item.name}}
                </div>
            </div>
            <div class="table">
                <div class="header">
                    <div class="header-blank first-column">路演厅预定</div>
                    <div class="header-morning">
                        <div class="header-title">
                            <div>上午</div>
                            <div>
                                {{currentRoom.amBeginTime}} - {{currentRoom.amEndTime}}
                            </div>
                        </div>
                    </div>
                    <div class="header-afternoon">
                        <div class="header-title">
                            <div>下午</div>
                            <div>{{currentRoom.pmBeginTime}} - {{currentRoom.pmEndTime}}</div>
                        </div>
                    </div>
                </div>
                <div class="body">
                    <div class="body-wrap" v-for="(item,index1) in timeList" :key="index1">
                        <div class="body-time first-column">
                            {{item.bookingDate + ' ' }}{{item.bookingWeek}}
                        </div>
                        <div class="body-main">
                            <div class="body-item" @click="selectCell(item,item1,index1)" :class="{'gray-cell':item1.status == -1,'booked-cell':item1.status == 1,'select-cell':item1.currentSelect}" v-for="(item1,index) in item.childBookingTimeList" :key="index">
                                <!-- {{item.time}} -->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <myModel :roomType="roomType" :viewType="modelType" :form="modelForm" :modelShow="modelShow" :modelTitle="modelTitle" @handleClose="handleClose" @handleAdd="handleAdd"></myModel>
    </div>
</template>
<script>
import myModel from '@/components/model'
import meeting from '@/api/meeting';
export default {
    name:'reservate-performhall',
    data(){
        return{
            roomType:'perform',
            modelType:'add',
            modelForm:{
                visitDate:'2020-08-08',
                beginTime:'09:00',
                endTime:'09:30',
                meetingId:'',
                meetingRoomName:'',
                visitCompanyList:[{name:''}]
            },
            modelShow:false,
            modelTitle:'新增预定',
            roomList:[],
            searchDate:new Date(),
            selectItem:[],
            rowS:null,
            rowE:null,
            selectFlag:false,
            showBtn:false,
            ModalTitle:'新增预定',
            form:{
                visitDate:'2020-08-08',
                beginTime:'09:00',
                endTime:'09:30'
            },
            timeList:[
            ],
            currentRoom:''
        }
    },
    components:{
        myModel
    },
    mounted(){
        this.init()
    },
    methods:{
        init(){
            this.searchDate = this.func_date(this.searchDate,7)
            // 获取会议室列表
            meeting.getRoomListByType({type:false}).then(res => {
                this.roomList = res.data;
                this.currentRoom = res.data[0];
                this.searchRoom = res.data[0].id;
                this.modelForm.meetingRoomName = res.data[0].name;
                this.modelForm.meetingId = res.data[0].id;
                this.modelForm.enclosureList = res.data[0].enclosureList;
                this.modelForm.trelation = res.data[0].trelation;
                this.getData()
            });
            // 获取会议室时间信息
            // this.getData()
        },
        roomChange(item){
            this.currentRoom = item;
            this.searchRoom = item.id;
            this.modelForm.meetingId = item.id;
            this.modelForm.meetingRoomName = item.name;
            this.modelForm.enclosureList = item.enclosureList;
            this.modelForm.trelation = item.trelation;
            this.getData()
            this.showBtn = false
        },
        // 选中单元格
        selectCell(rowItem,cellItem,rowIndex) {
            if(cellItem.status == 0){
                if(!this.selectFlag){
                    this.selectFlag  =true;
                    this.rowS = rowIndex
                }else{
                    this.selectFlag = !this.selectFlag;
                    this.rowE = rowIndex;
                }
                if(cellItem.currentSelect){
                    cellItem.currentSelect = !cellItem.currentSelect;
                    this.selectItem.splice(this.selectItem.findIndex(it => it.beginTime == cellItem.beginTime),1);
                }else{
                    let that = this;
                    if(this.rowE != null && this.rowS != this.rowE){ //跨行选择，清空
                        this.selectItem = [];
                        for(let value of this.timeList) {
                            if(value.childBookingTimeList) {
                                for(let childValue of value.childBookingTimeList){
                                    that.$set(childValue,'currentSelect',false)
                                }
                            }
                        }
                    }
                    this.selectItem.push(cellItem)
                    this.$set(cellItem,'currentSelect',true);
                }
                if(this.selectItem.length > 0){
                    this.showBtn = true;
                    this.modelForm.visitDate = rowItem.bookingDate;
                    if(this.selectItem.length>1){
                        this.modelForm.beginTime = this.timeList[this.rowS].childBookingTimeList[0].beginTime;
                        this.modelForm.endTime = this.timeList[this.rowS].childBookingTimeList[1].endTime;
                    }else{
                        this.modelForm.beginTime = this.selectItem[0].beginTime;
                        this.modelForm.endTime = this.selectItem[0].endTime;
                    }
                }else{
                    this.showBtn = false;
                }
            }

        },
        // 获取数据
        getData(){
            let params = {
                visitDate: this.searchDate,
                meetingId:this.searchRoom,
                num:7
            }
            meeting.queryRoadshowTime(params).then(res => {
                this.timeList = res.data
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
            this.modelForm.enclosureList.forEach(function(item){
                item.numMax = item.num
            })
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
            border: 1px solid rgb(161, 161, 161);
            .header-blank{
                width: 20%;
                height: 80px;
                line-height: 80px;
                border-right: 1px solid rgb(161, 161, 161);
            }
            .header-morning{
                width: 40%;
                height: 80px;
                border-right: 1px solid rgb(161, 161, 161);
            }
            .header-afternoon{
                width: 40%;
                height: 80px;
            }
            .header-title{
                text-align: center;
                height: 80px;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
            }
        }
        .body{
            width: 100%;
            border: 1px solid rgb(161, 161, 161);
            border-top:none;
            border-bottom: none;
            .body-wrap{
                display: flex;
                border-bottom: 1px solid rgb(161, 161, 161);
                .body-time{
                    width: 20%;
                    height: 40px;
                    line-height: 40px;
                    text-align: center;
                    border-right: 1px solid rgb(161, 161, 161);
                }
                .body-main{
                    width: 80%;
                    display: flex;
                    .body-item{
                        cursor: pointer;
                        flex: 1;
                        height: 40px;
                        border-right: 1px solid rgb(161, 161, 161);
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
                background-color: #FFCC66;
                // border: none!important;
                // border-color: #FFCC66 !important;
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
					background-color: #FFCC66;
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
</style>