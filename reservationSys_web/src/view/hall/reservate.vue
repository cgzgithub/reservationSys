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
                    <div class="room-header">展厅列表</div>
                    <div class="room-item" :title="item.name" :class="{'room-select':item.id == searchRoom}" v-for="(item,index) in roomList" :key="index" @click="roomChange(item)">
                        {{item.name}}
                    </div>
            </div>
            <div class="table">
                <div class="header">
                    <div class="header-blank first-column">展厅预定</div>
                    <div class="header-main">
                        <div class="header-first">
                            <div class="header-morning">
                                <div>上午</div>
                                <div>
                                    <!-- 09:00-10:30 -->
                                </div>
                            </div>
                            <div class="header-afternoon">
                                <div>下午</div>
                                <!-- <div>13:30-16:00</div> -->
                            </div>
                        </div>
                        <div class="time-cell">
                            <div class="body-item" v-for="(item,index) in timeLine" :key="index">
                                {{item.beginTime}}
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
                                <div class="body-item" @click="selectCell(item,item1)" :class="{'gray-cell':item1.status == -1,'booked-cell':item1.status == 1,'select-cell':item1.currentSelect}" v-for="(item1,index1) in item.childBookingTimeList" :key="index1">
                                </div>
                            </div>
                        </div>
                </div>
            </div>
        </div>

        
        <div>

        </div>
        <myModel ref="model" :roomType="roomType" :viewType="modelType" :form="modelForm" :modelShow="modelShow" :modelTitle="modelTitle" @handleClose="handleClose" @handleAdd="handleAdd"></myModel>
    </div>
</template>
<script>
import myModel from '@/components/model'
import api from '@/api/hall';
export default {
    name:'reservate',
    data(){
        return{
            roomType:'hall',
            modelType:'add',
            roomList:[],
            searchRoom:'',
            modelForm:{
                visitDate:'2020-08-08',
                beginTime:'09:00',
                endTime:'09:30',
                visitCompanyList:[{name:''}],
                showroomId:'',
                showroomName:''
            },
            modelShow:false,
            modelTitle:'新增预定',
            searchDate:new Date(),
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
            timeLine:[]
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
            // 获取会议室列表
            api.getRoomList().then(res => {
                this.roomList = res.data.list;
                this.searchRoom = res.data.list[0].id;
                this.modelForm.showroomName = res.data.list[0].name;
                this.modelForm.showroomId = res.data.list[0].id;
                this.getData()
            });
        },
        roomChange(item){
            this.searchRoom = item.id;
            this.modelForm.showroomName = item.name;
            this.modelForm.showroomId = item.id;
            this.getData()
            this.showBtn = false
        },
        // 选中单元格
        selectCell(rowItem,cellItem) {
            if(cellItem.status == 0){
                if(cellItem.currentSelect){
                    this.showBtn = false;
                    cellItem.currentSelect = !cellItem.currentSelect;
                    this.selectItem = {}
                }else{
                    let that = this;
                    for(let value of this.timeList) {
                        if(value.childBookingTimeList) {
                            for(let childValue of value.childBookingTimeList){
                                that.$set(childValue,'currentSelect',false)
                            }
                        }
                    }
                    this.$set(cellItem,'currentSelect',true);
                    this.$set(this.selectItem,'date',rowItem.bookingDate);
                    this.$set(this.selectItem,'timeObj',cellItem);
                    this.modelForm.visitDate = rowItem.bookingDate
                    this.modelForm.beginTime = cellItem.beginTime;
                    this.modelForm.endTime = cellItem.endTime;
                }
                if(Object.keys(this.selectItem).length > 0){
                    this.showBtn = true;
                }
            
            }

        },
        // 获取数据
        getData(){
            let params = {
                visitDate: this.searchDate,
                num:7,
                showroomId:this.searchRoom
            }
            api.getReservationTime(params).then(res => {
                this.timeList = res.data;
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
            this.$refs.model.init()
        },
        // 关闭弹窗
        handleClose(){
            this.modelShow = false
        },
        // 新增预约
        handleAdd(v){
            let params = v;
            api.addReservation(params).then(res =>{
                if(res.code == 200){
                    this.showBtn = false;
                    this.modelShow = false;
                    this.$Message.success('预定成功')
                    this.getData()
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
        width: 10%;
        .room-header{
            margin-bottom: 20px;
            font-weight: bold;
        }
        .room-item{
            cursor: pointer;
            padding:5px 10px;
            max-width: 98px;
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
            width: 15%;
            height: 80px;
            line-height: 80px;
            border-right: 1px solid rgb(161, 161, 161);
        }
        .header-main{
            width: 85%;
            .header-first{
                width: 100%;
                height: 40px;
                display: flex;
                border-bottom: 1px solid rgb(161, 161, 161);
                div{
                    flex: 1;
                }
                .header-morning{
                    border-right: 1px solid rgb(161, 161, 161);;
                }
            }
        }
        .time-cell{
            display: flex;
            width: 100%;
            div {
                flex: 1;
                height: 40px;
                line-height: 40px;
                border-right: 1px solid rgb(161, 161, 161);
                overflow: hidden;
            }
            div:last-child{
                border:none;
            }

        }
        .header-title{
            text-align: center;
            border-bottom: 1px solid rgb(161, 161, 161);
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
                width: 15%;
                height: 40px;
                line-height: 40px;
                text-align: center;
                border-right: 1px solid rgb(161, 161, 161);
                overflow: hidden;
            }
            .body-main{
                width: 85%;
                display: flex;
                .body-item{
                    cursor: pointer;
                    flex: 1;
                    height: 40px;
                    border-right: 1px solid rgb(161, 161, 161);
                }
                div:last-child{
                    border:none
                }
            }
        }
        .gray-cell{
            background-color: #F2F2F2;
        }
        .booked-cell{
            background-color: #FFCC66;
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