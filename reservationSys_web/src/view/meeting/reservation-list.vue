<template>
    <div class="container">
        会议室预约列表
        <el-row>
            <el-form :inline="true" ref="searchForm" :model="searchForm">
                <el-form-item>
                    <!-- <el-date-picker
                    style="width:150px"
                    size="small"
                    v-model="searchForm.visitDate"
                    type="date"
                    placeholder="预约日期">
                    </el-date-picker> -->
                    <el-date-picker
                    style="width:360px"
                    size="small"
                    @change="dateChange"
                    v-model="dateRange"
                    type="daterange"
                    range-separator="至"
                    start-placeholder="预定开始日期"
                    end-placeholder="预定结束日期">
                    </el-date-picker>
                </el-form-item>
                <el-form-item>
                   <el-select style="width:150px" size="small" v-model="searchForm.status" clearable placeholder="请选择状态">
                        <el-option
                        v-for="item in statusOptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                   <el-select style="width:150px" size="small" v-model="searchForm.passageway" clearable placeholder="预约通道">
                        <el-option
                        v-for="item in passageway"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                   <el-select style="width:150px" size="small" v-model="searchForm.meetingId" clearable placeholder="会议室名称">
                        <el-option
                        v-for="item in rooms"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-input style="width:150px" size="small" v-model="searchForm.companyName" clearable placeholder="单位名称" />
                </el-form-item>
                <el-form-item>
                    <el-input style="width:150px" size="small" v-model="searchForm.engCompanyName" clearable placeholder="单位英文名称" />
                </el-form-item>
                <el-form-item>
                    <el-input style="width:150px" size="small" v-model="searchForm.bookingPersion" clearable placeholder="预约人" />
                </el-form-item>
                <el-form-item>
                    <el-button size="small" type="success" @click="search">查询</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button size="small" type="primary" @click="add">新增</el-button>
                </el-form-item>
            </el-form>
        </el-row>
        <Row>
            <flexTable
            :colConfigs="colConfigs"
            :data="tableData"
            v-loading="loading"
            >
                <el-table-column width="220" slot="name" label="会议主题" prop="name" show-overflow-tooltip>
                    <template slot-scope="scope">
                        <a @click="showModal(scope.row)">{{scope.row.name}}</a>
                    </template>
                </el-table-column>
                <el-table-column width="120" slot="visitCompanyList" label="参会单位名称" prop="visitCompanyList" show-overflow-tooltip>
                    <template slot-scope="scope">
                        <div v-for="(item,index) in scope.row.visitCompanyList" :key="index">{{item.name}}</div>
                    </template>
                </el-table-column>
                <!-- <el-table-column slot="action" label="操作" show-overflow-tooltip min-width="200px">
                    <template slot-scope="scope">
                        <el-button
                        type="success"
                        size="small"
                        @click="showModal(scope.row)"
                        >查看</el-button>
                    </template>
                </el-table-column> -->
            </flexTable>
        </Row>
        <Row
          type="flex"
          justify="end"
          class="page"
          style="margin-top:20px"
        >
        <Page
            :current="searchForm.pageNumber"
            :total="total"
            :page-size="searchForm.pageSize"
            @on-change="changePage"
            @on-page-size-change="changePageSize"
            :page-size-opts="[10,20,50]"
            size="small"
            show-total
            show-elevator
            show-sizer
        ></Page>
        </Row>
        <myModel :roomType="roomType" :viewType="modelType" :form="modelForm" :modelShow="modelShow" :modelTitle="modelTitle" @handleClose="handleClose" @handleCancel="handleCancel" @handleVisit="handleVisit" @handleUpdate="handleUpdate" @handleUpdateVisit="handleUpdateVisit" @handleNotVisit="handleNotVisit"></myModel>
    </div>
</template>
<script>
import flexTable from '@/components/flex-table';
import Pagination from '@/components/pagination';
import myModel from '@/components/model'
import api from '@/api/meeting';
export default {
    name:'reservation-list',
    data(){
        return{
            dateRange:[],
            // 搜索参数
            searchForm:{
                pageNumber:1,
                pageSize:10,
                visitDate:'',
                status:'',
                passageway:'',
                companyName:'',
                bookingPersion:'',
                meetingId:'',
                companyName:'',
                engCompanyName:'',
                person:''
            },
            total:0,
            colConfigs:[ // 表格列
                {
                    slot:'name'
                },
                {
                    slot:'visitCompanyList'
                },
                {
                    label:'预约人',
                    prop:'bookingPersion',
                    sortable:true
                },
                {
                    label:'手机号',
                    prop:'bookingPhone',
                    sortable:true
                },
                {
                    label:'会议室名称',
                    prop:'meetingName',
                    sortable:true
                },
                {
                    label:'预定日期',
                    prop:'visitDate',
                    sortable:true
                },
                {
                    label:'会议时间',
                    prop:'meetingTime',
                    sortable:true
                },
                // {
                //     label:'结束时间',
                //     prop:'endTime',
                //     sortable:true
                // },
                // {
                //     label:'参会人数',
                //     prop:'bookingNumber',
                //     sortable:true
                // },
                {
                    label:'预约通道',
                    prop:'passagewayCN',
                    sortable:true
                },
                {
                    label:'状态',
                    prop:'statusCN',
                    sortable:true
                }
            ],
            tableData:[], //表格数据
            loading:false,
            statusOptions:[ //状态
                {
                    label:'预约中',
                    value:0
                },
                {
                    label:'到访',
                    value:1
                },
                {
                    label:'未到访',
                    value:-1
                },
                {
                    label:'取消',
                    value:-2
                }
            ],
            passageway:[ //预约通道
                {
                    label:'小程序',
                    value:0
                },
                {
                    label:'电脑端',
                    value:1
                }
            ],
            rooms:[], // 会议室列表
            roomType:'meeting',
            modelType:'cancelable',
            modelForm:{
                visitDate:'2020-08-08',
                startTime:'09:00',
                endTime:'09:30'
            },
            modelShow:false,
            modelTitle:'新增预定',
        }
    },
    components:{
        flexTable,
        Pagination,
        myModel
    },
    mounted(){
        this.init()
    },
    methods:{
        dateChange(v){
            if(v == null){
                this.searchForm.beginDate=''
                this.searchForm.endDate = ''
            }else{
                let ranges = JSON.parse(JSON.stringify(this.dateRange));
                let dateStart = ranges[0];
                let dateEnd = ranges[1];
                this.searchForm.beginDate = dateStart
                this.searchForm.endDate = dateEnd
                let days = (dateEnd - dateStart)/(1*24*60*60*1000);
            }
            
        },
        // 数据初始化
        init(){
            // 获取预约列表数据
            this.search()
            api.getAllRoomList().then(res => {
                this.rooms = res.data.list
            })
        },
        showModal(row) {
            this.modelTitle = '预约信息'
            let params = {
                beginTime:row.beginTime,
                visitDate:row.visitDate
            }
            if(row.isRoadshow){
                this.roomType = 'perform'
            }else{
                this.roomType = 'meeting'
            }
            if(row.status == 0){
                api.timeDecide(params).then(res => {
                    if(res.data){
                        this.modelType = 'cancelable'
                    }else{
                        this.modelType = 'unCancelable'
                    }
                })
            }else if(row.status == -2) {
                this.modelType = 'cancelDetail'
            }else if(row.status == -1){
                this.modelType = 'notVisitDetail'
            }else if(row.status == 1){
                this.modelType = 'visitDetail'
            }

            this.modelForm = JSON.parse(JSON.stringify(row))
            this.modelShow = true
        },
        // 查询
        search(){
            api.getBookingList(this.searchForm).then(res => {
                this.tableData = res.data.list;
                this.total = res.data.total
                this.tableData.forEach(function(item){
                    switch(item.status){
                        case 0:
                            item.statusCN = '预约中';
                            break;
                        case 1:
                            item.statusCN = '到访';
                            break;
                        case -1:
                            item.statusCN = '未到访';
                            break;
                        case -2:
                            item.statusCN = '已取消';
                            break;
                    }
                    if(item.passageway == 0){
                        item.passagewayCN = '小程序'
                    }else if(item.passageway == 1){
                        item.passagewayCN = '电脑端'
                    }
                    item.meetingTime = item.beginTime + '-' + item.endTime
                })
            });
        },
        // 关闭弹窗
        handleClose(){
            this.modelShow = false
        },
        // 取消预约
        handleCancel(v){
            let params = {
                cancelChannel:1,
                cancelReasonId:v,
                id:this.modelForm.id
            }
            api.cancelBook(params).then(res => {
                if(res.code == 200){
                    this.modelShow = false;
                    this.$Message.success('取消预定成功')
                    this.search()
                }
            })
        },
        // 到访
        handleVisit(v){
            let params = Object.assign({id:this.modelForm.id},v);
            api.visit(params).then(res => {
                if(res.code == 200){
                    this.modelShow = false;
                    this.$Message.success('已到访')
                    this.search()
                }
            })
        },
        // 未到访
        handleNotVisit(){
            api.unVisit({id:this.modelForm.id}).then(res => {
                if(res.code == 200){
                    this.modelShow = false;
                    this.$Message.success('未到访')
                    this.search()
                }
            })
        },
        // 更新
        handleUpdate(v){
            api.editBook(v).then(res => {
                if(res.code == 200){
                    this.modelShow = false;
                    this.$Message.success('更新成功')
                    this.search()
                }
            })
        },
        // 到访更新
        handleUpdateVisit(v){
            api.visit(v).then(res => {
                if(res.code == 200){
                    this.modelShow = false;
                    this.$Message.success('更新成功')
                    this.search()
                }
            })
        },
        // 新增
        add(){
            this.$router.push('/meeting/reservate')
        },
        changePage(v){
            this.searchForm.pageNumber = v;
            this.search()
        },
        changePageSize(v){
            this.searchForm.pageSize = v;
            this.search()
        }
    }
}
</script>