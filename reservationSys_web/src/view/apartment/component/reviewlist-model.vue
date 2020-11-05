<template>
    <div class="container">
        <el-tabs v-model="activeName" @tab-click="handleTabClick">
            <el-tab-pane label="审核列表" name="review">

                <el-row>
                    <el-form :inline="true" ref="searchForm" :model="searchForm">
                        <el-form-item>
                            <el-select style="width:150px" size="small" v-model="searchForm.houseType" clearable placeholder="租房类型">
                                <el-option
                                v-for="item in houseTypeList"
                                :key="item.id"
                                :label="item.title"
                                :value="item.id"
                                ></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item>
                            <el-input style="width:200px" size="small" v-model="searchForm.companyName" clearable placeholder="单位名称" />
                        </el-form-item>
                        <el-form-item>
                            <el-input style="width:200px" size="small" v-model="searchForm.name" clearable placeholder="个人姓名" />
                        </el-form-item>
                        <el-form-item>
                            <el-button size="small" type="success" @click="search">查询</el-button>
                        </el-form-item>
                    </el-form>
                </el-row>
                <Row>
                    <flexTable
                    :colConfigs="colConfigs"
                    :data="applyList"
                    v-loading="loading"
                    >
                        <el-table-column slot="action" label="操作" show-overflow-tooltip>
                            <template slot-scope="scope">
                                <el-button
                                type="success"
                                size="small"
                                @click="showModal(scope)"
                                >审核</el-button>
                            </template>
                        </el-table-column>
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
                </el-tab-pane>
            <el-tab-pane label="审核历史列表" name="reviewHis">
                <el-row>
                        <el-form :inline="true" ref="searchHiForm" :model="searchHiForm">
                                <el-form-item>
                                <el-select style="width:150px" size="small" v-model="searchHiForm.applyStatus" clearable placeholder="当前申请状态">
                                        <el-option
                                        v-for="item in statusHiOptions"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value"
                                        ></el-option>
                                    </el-select>
                                </el-form-item>
                                <el-form-item>
                                <el-select style="width:200px" size="small" v-model="searchHiForm.reviewStatus" clearable placeholder="审核结果">
                                        <el-option
                                        v-for="item in reviewStatus"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value"
                                        ></el-option>
                                    </el-select>
                                </el-form-item>
                                <el-form-item>
                                    <el-select style="width:150px" size="small" v-model="searchHiForm.houseType" clearable placeholder="租房类型">
                                        <el-option
                                        v-for="item in houseTypeList"
                                        :key="item.id"
                                        :label="item.title"
                                        :value="item.id"
                                        ></el-option>
                                    </el-select>
                                </el-form-item>
                                <el-form-item>
                                    <el-input style="width:200px" size="small" v-model="searchHiForm.companyName" clearable placeholder="单位名称" />
                                </el-form-item>
                                <el-form-item>
                                    <el-input style="width:200px" size="small" v-model="searchHiForm.name" clearable placeholder="个人姓名" />
                                </el-form-item>
                                <el-form-item>
                                    <el-button size="small" type="success" @click="searchHi">查询</el-button>
                                </el-form-item>
                            </el-form>
                        </el-row>
                        <Row>
                            <flexTable
                            :colConfigs="colHiConfigs"
                            :data="reviewList"
                            v-loading="loading"
                            >
                                <el-table-column slot="companyName" label="单位名称" prop="companyName" width="180" show-overflow-tooltip>
                                    <template slot-scope="scope">
                                        <a @click="showHiModal(scope)">{{scope.row.companyName}}</a>
                                    </template>
                                </el-table-column>
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
                            :total="totalHi"
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
            </el-tab-pane>
        </el-tabs>
      
        <myPopup :form="form" :canEdit="canEdit" :modelTitle="modelTitle" :isReview="isReview" :modelShow="modalVisible" @closeModal="closeModal" @handleApprove="handleApprove" @notApprove="notApprove"></myPopup>
    </div>
</template>
<script>
import flexTable from '@/components/flex-table';
import Pagination from '@/components/pagination';
import myPopup from './popup-model';
import api from '@/api/apartment';
import dict from '@/api/dict';
export default {
    name:'reviewlist-model',
    data(){
        return{
            activeName:'review',
            canEdit:true,
            isReview:true,
            modelTitle:'审核历史',
            modalVisible:false,
            form:{},
            applyId:'',
            // 搜索参数
            searchForm:{
                status:this.stage,
                houseType:"",
                companyName:"",
                name:"",
                pageNumber:1,
                pageSize:10,
            },
            searchHiForm:{
                stage:this.stage,
                houseType:"",
                name:"",
                companyName:"",
                applyStatus:"",
                reviewStatus:"",
                pageNumber:1,
                pageSize:10,
            },
            total:0,
            totalHi:0,
            colConfigs:[ // 表格列
                // {
                //     slot:'companyName'
                // },
                {
                    label:'单位名称',
                    prop:'companyName',
                    sortable:true
                },
                {
                    label:'个人姓名',
                    prop:'name',
                    sortable:true
                },
                {
                    label:'证件号',
                    prop:'idNumber',
                    sortable:true
                },
                {
                    label:'手机号',
                    prop:'phone',
                    sortable:true
                },
                {
                    label:'国籍',
                    prop:'nationality',
                    sortable:true
                },
                {
                    label:'租房类型',
                    prop:'houseTypeName',
                    sortable:true
                },
                {
                    label:'申请日期',
                    prop:'applyDate',
                    sortable:true
                },
                {
                    slot:'action'
                },
            ],
            colHiConfigs:[ // 表格列
                {
                    slot:'companyName'
                },
                {
                    label:'个人姓名',
                    prop:'name',
                    sortable:true
                },
                {
                    label:'证件号',
                    prop:'idNumber',
                    sortable:true
                },
                {
                    label:'手机号',
                    prop:'phone',
                    sortable:true
                },
                {
                    label:'国籍',
                    prop:'nationality',
                    sortable:true
                },
                {
                    label:'租房类型',
                    prop:'houseType',
                    sortable:true
                },
                {
                    label:'申请日期',
                    prop:'applyDate',
                    sortable:true
                },
                {
                    label:'审核人',
                    prop:'reviewer',
                    sortable:true
                },
                {
                    label:'审核时间',
                    prop:'reviewTime',
                    sortable:true
                },
                {
                    label:'本阶段审核结果',
                    prop:'reviewResultCN',
                    sortable:true,
                    width:130
                },
                {
                    label:'本阶段审核意见',
                    prop:'reviewOpinion',
                    sortable:true,
                    width:130
                },
                {
                    label:'当前申请状态',
                    prop:'applyStatusCN',
                    sortable:true,
                    width:130
                },
                {
                    slot:'action'
                },
            ],
            loading:false,
            applyList:[],//申请列表
            reviewList:[],//申请历史列表
            statusOptions:[ //状态
                {
                    label:'待出租',
                    value:0
                },
                {
                    label:'已出租',
                    value:1
                }
            ],
            houseTypeList:[],
            statusHiOptions:[ //申请状态
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
                    value:2
                },
                {
                    label:'湾区分管审核',
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
            reviewStatus:[ //合同状态
                {
                    label:'不通过',
                    value:0
                },
                {
                    label:'通过',
                    value:1
                }
            ]
        }
    },
    props:{
        stage:{
            type:Number,
            default:1
        }
    },
    components:{
        flexTable,
        Pagination,
        myPopup
    },
    mounted(){
        this.init()
    },
    methods:{
        handleTabClick(tab,event){
            this.activeName = tab.name
            if(tab.name == 'review'){
                this.search()
            }else if(tab.name == 'reviewHis'){
                this.searchHi()
            }
        },  
        // 数据初始化
        init(){
            // 获取公寓列表数据
            this.searchForm.stage = this.stage
            this.search()
            dict.getDictInfoByType({type:'apartmentType'}).then(res => {
                this.houseTypeList = res.data
            })
        },
        closeModal(){
            this.modalVisible = false
        },
        // 显示弹窗
        showModal(scope) {
            this.modelTitle = '审核'
            this.isReview = true
            this.applyId = scope.row.id;
            api.viewApartmentApply({id:scope.row.id}).then(res => {
                this.form = res.data;
            })
            this.canEdit = false
            this.modalVisible = true
        },
        // 显示历史审核详情弹窗
        showHiModal(scope) {
            this.modelTitle = '审核历史'
            this.isReview = false
            api.viewApartmentApply({id:scope.row.applyId}).then(res => {
                this.form = res.data;
            })
            this.canEdit = false
            this.modalVisible = true
        },
        // 查询审核列表
        search(){
            // 获取公寓列表数据
            api.queryApplyList(this.searchForm).then(res => {
                this.applyList = res.data.list
                this.total = res.data.total
            })
        },
        // 查询历史列表
        searchHi(){
            let that = this;
            // 获取公寓列表数据
            api.queryReviewList(this.searchHiForm).then(res => {
                this.reviewList = res.data.list
                this.reviewList.forEach(function(item){
                    if(item.reviewResult === 0){
                        item.reviewResultCN = '不通过'
                    }else if(item.reviewResult === 1){
                        item.reviewResultCN = '通过'
                    }
                    let re = that.statusHiOptions.find( (v) => {
                        return v.value == item.applyStatus
                    })
                    item.applyStatusCN = re.label
                })
                this.totalHi = res.data.total
            })
        },
        // 关闭弹窗
        handelCancel(){
            this.modalVisible = false
        },
        // 不通过
        notApprove(v){
            let params = {
                applyId:this.applyId,
                reviewOpinion:v,
                isPass:0,
                stage:this.stage
            }
            api.passApartmentReview(params).then(res => {
                if(res.code ==200){
                    this.$Message.success('操作成功')
                    this.search()
                }else{
                    this.$Message.error(res.msg)
                }
            })
            this.modalVisible = false;
        },
         // 通过
        handleApprove(v){
            let params = {
                applyId:this.applyId,
                reviewOpinion:v,
                isPass:1,
                stage:this.stage
            }
            api.passApartmentReview(params).then(res => {
                if(res.code ==200){
                    this.$Message.success('操作成功')
                    this.search()
                }else{
                    this.$Message.error(res.msg)
                }
            })
            this.modalVisible = false;
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

