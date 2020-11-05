<template>
    <div class="container">
      <el-row>
            <el-form :inline="true" ref="searchForm" :model="searchForm">
                <el-form-item>
                   <el-select style="width:150px" size="small" v-model="searchForm.applyStatus" clearable placeholder="当前申请状态">
                        <el-option
                        v-for="item in statusOptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                   <el-select style="width:200px" size="small" v-model="searchForm.reviewStatus" clearable placeholder="审核结果">
                        <el-option
                        v-for="item in reviewStatus"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
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
            :data="reviewList"
            v-loading="loading"
            >
                <el-table-column slot="companyName" label="单位名称" prop="companyName" width="180" show-overflow-tooltip>
                    <template slot-scope="scope">
                        <a @click="showModal(scope)">{{scope.row.companyName}}</a>
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
        <myPopup :form="form" :canEdit="canEdit" :modelTitle="modelTitle" :modelShow="modalVisible" @closeModal="closeModal"></myPopup>
    </div>
</template>
<script>
import flexTable from '@/components/flex-table';
import Pagination from '@/components/pagination';
import api from '@/api/apartment';
import myPopup from './popup-model';
import dict from '@/api/dict';
export default {
    name:'reviewhi-list-model',
    data(){
        return{
            canEdit:true,
            modelTitle:'审核历史',
            modalVisible:false,
            form:{},
            // 搜索参数
            searchForm:{
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
            colConfigs:[ // 表格列
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
                    label:'本阶段审核结果',
                    prop:'reviewResultCN',
                    sortable:true
                },
                {
                    label:'本阶段审核意见',
                    prop:'reviewOpinion',
                    sortable:true
                },
                {
                    label:'当前申请状态',
                    prop:'applyStatusCN',
                    sortable:true
                },
                {
                    slot:'action'
                },
            ],
            loading:false,
            reviewList:[],//公寓列表
            houseTypeList:[],//户型列表
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
            api.viewApartmentApply({id:scope.row.applyId}).then(res => {
                this.form = res.data;
            })
            this.canEdit = false
            this.modalVisible = true
        },
        // 新增
        handelSubmit(){
            if(this.submitType == 'add'){
                hall.addApartment(this.form).then(res => {
                    if(res.code == 200){
                        this.$Message.success('添加成功')
                        this.modalVisible = false;
                        this.init()
                    }
                })
            }else{
                hall.editApartment(this.form).then(res => {
                    if(res.code == 200){
                        this.$Message.success('修改成功')
                        this.modalVisible = false;
                        this.init()
                    }
                })
            }
        },
        // 查询
        search(){
            let that = this;
            // 获取公寓列表数据
            api.queryReviewList(this.searchForm).then(res => {
                this.reviewList = res.data.list
                this.reviewList.forEach(function(item){
                    if(item.reviewResult === 0){
                        item.reviewResultCN = '不通过'
                    }else if(item.reviewResult === 1){
                        item.reviewResultCN = '通过'
                    }
                    let re = that.statusOptions.find( (v) => {
                        return v.value == item.applyStatus
                    })
                    item.applyStatusCN = re.label
                })
                this.total = res.data.total
            })
        },
        // 关闭弹窗
        handelCancel(){
            this.modalVisible = false
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