<template>
    <div class="container">
      <el-row>
            <el-form :inline="true" ref="searchForm" :model="searchForm">
                <el-form-item>
                   <el-select style="width:150px" size="small" v-model="searchForm.status" clearable placeholder="申请状态">
                        <el-option
                        v-for="item in statusOptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
                 <el-form-item>
                   <el-select style="width:150px" size="small" v-model="searchForm.contractStatus" clearable placeholder="合同状态">
                        <el-option
                        v-for="item in contractStatus"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                   <el-select style="width:150px" size="small" v-model="searchForm.dueDate" clearable placeholder="到期时间">
                        <el-option
                        v-for="item in dueDates"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-select style="width:150px" size="small" v-model="searchForm.houseType" clearable placeholder="租房类型">
                        <el-option
                        v-for="item in houseTypes"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
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
                    <el-button size="small" type="success" @click="showModal">新增</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button size="small" type="primary" @click="exportFile">导出</el-button>
                </el-form-item>
            </el-form>
        </el-row>
        <Row>
            <flexTable
            :colConfigs="colConfigs"
            :data="applyList"
            :tableRowClassName="tableRowClassName"
            v-loading="loading"
            >
                <el-table-column slot="companyName" label="单位名称" prop="companyName" width="220" show-overflow-tooltip>
                    <template slot-scope="scope">
                        <a @click="showModal(scope,'view')">{{scope.row.companyName}}</a>
                    </template>
                </el-table-column>
                <el-table-column slot="action" fixed="right"  width="200" label="操作" show-overflow-tooltip>
                    <template slot-scope="scope">
                        <el-button
                        v-if="scope.row.status == 5"
                        type="success"
                        size="small"
                        @click="showConModal(scope)"
                        >合同管理</el-button>
                        <el-button
                        v-if="scope.row.status == -1 || scope.row.status == 0"
                        type="success"
                        size="small"
                        @click="showModal(scope,'edit')"
                        >编辑</el-button>
                       <el-button
                        v-if="scope.row.status == -1"
                        type="success"
                        size="small"
                        @click="clone(scope)"
                        >克隆</el-button>
                        <el-button
                        v-if="scope.row.status == 0"
                        type="success"
                        size="small"
                        @click="reviewApply(scope)"
                        >提审</el-button>
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
        <Modal
        :title="modelConTitle"
        v-model="modalConVisible"
        :mask-closable="false"
        :closable="true"
        :width="700"
        :styles="{top: '30px'}"
        >
            <Form
                ref="form"
                :model="conForm"
                :label-width="120"
                :rules="formValidate"
            >
                <FormItem style="width:300px"  label="合同开始时间：" prop="contractBeginDate">
                    <DatePicker v-model="conForm.contractBeginDate" />
                </FormItem>
                <FormItem style="width:300px"  label="合同结束时间：" prop="contractEndDate">
                    <DatePicker v-model="conForm.contractEndDate" />
                </FormItem>
                <FormItem  label="公寓地址：" prop="contractEndDate">
                    <!-- <div > -->
                        <span style="margin-right:20px;">{{conForm.apartmentAddress}}</span>
                        <Button v-if="conForm.contractStatus == 0 || !conForm.contractStatus" type="primary" @click="showAparatlist">修改</Button>
                    <!-- </div> -->
                    
                </FormItem>
                <FormItem v-if="conForm.contractStatus" style="width:300px"  label="合同状态：" prop="contractStatus">
                    <span>{{conForm.contractStatusCN}}</span>
                    <!-- <Select v-model="conForm.contractStatus" >
                       <Option 
                        label="合同执行中"
                        :value="0"
                        ></Option>
                        <Option 
                        label="提前解约"
                        :value="-1"
                        ></Option>
                        <Option 
                        label="合同到期"
                        :value="-2"
                        ></Option>
                    </Select> -->
                </FormItem>
            </Form>
            <div slot="footer">
                <Button
                type="default"
                @click="closeConModal"
                >关闭</Button>
                <Button
                v-if="conForm.contractStatus == 0 || !conForm.contractStatus"
                type="primary"
                @click="handleConSubmit('save')"
                >保存</Button>
                <Button
                v-if="conForm.contractStatus == 0"
                type="primary"
                @click="handleConSubmit('dueCancel')"
                >到期解约</Button>
                <Button
                v-if="conForm.contractStatus == 0"
                type="primary"
                @click="handleConSubmit('preCancel')"
                >提前解约</Button>
            </div>
        </Modal>
        <!-- 选择公寓地址列表弹窗 -->
        <Modal
        :title="modelAppTitle"
        v-model="modalAppVisible"
        :mask-closable="false"
        :closable="true"
        :width="700"
        :styles="{top: '30px'}"
        >
        <Row>
            <Form :inline="true" ref="searchFormApp" :model="searchFormApp">
                <!-- <FormItem>
                   <Select style="width:150px" size="small" v-model="searchFormApp.status" clearable placeholder="请选择状态">
                        <Option
                        v-for="item in statusAppOptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                        ></Option>
                   </Select>
                </FormItem> -->
                <FormItem>
                   <Select style="width:200px" v-model="searchFormApp.houseType" clearable placeholder="户型">
                        <Option
                        v-for="item in houseTypeList"
                        :key="item.id"
                        :label="item.title"
                        :value="item.id"
                        ></Option>
                    </Select>
                </FormItem>
                <FormItem>
                    <Input style="width:200px"  v-model="searchFormApp.residentialAreaName" clearable placeholder="小区名称" />
                </FormItem>
                    <Button  type="success" @click="getApplist">查询</Button>
            </Form>
        </Row>
            <Row>
                <flexTable
                :colConfigs="colAppConfigs"
                :data="apartments"
                v-loading="loading"
                >
                    <el-table-column slot="action" label="操作" show-overflow-tooltip min-width="200px">
                        <template slot-scope="scope">
                            <el-button
                            type="success"
                            size="small"
                            @click="chooseApp(scope)"
                            >选择</el-button>
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
                :current="searchFormApp.pageNumber"
                :total="apartmentTotal"
                :page-size="searchFormApp.pageSize"
                @on-change="changePageApp"
                @on-page-size-change="changePageSizeApp"
                :page-size-opts="[10,20,50]"
                size="small"
                show-elevator
                show-sizer
            ></Page>
            </Row>
            <div slot="footer">
                <Button
                type="default"
                @click="closeAppModal"
                >关闭</Button>
            </div>
        </Modal>
        <myPopup ref="model" :form="form" :canEdit="canEdit" :modelTitle="modelTitle" :modelShow="modalVisible" @closeModal="closeModal" @handleSubmit="handleSubmit"></myPopup>
    </div>
</template>
<script>
import flexTable from '@/components/flex-table';
import Pagination from '@/components/pagination';
import myUpload from '@/components/upload-list';
import myPopup from './component/popup-model';
import {createUUID} from '@/utils/index';
import api from '@/api/apartment';
import dict from '@/api/dict';
export default {
    name:'apply-list',
    data(){
        return{
            // tableRowClassName:'',
            apartments:[],
            houseTypeList:[],//户型列表
            statusAppOptions:[ //状态
                {
                    label:'待出租',
                    value:0
                },
                {
                    label:'已出租',
                    value:1
                }
            ],
            apartmentTotal:0,
            submitType:'',
            canEdit:true,
            modelTitle:'新增',
            modelConTitle:'合同管理',
            modelAppTitle:'选择公寓',
            modalAppVisible:false,
            modalVisible:false,
            modalConVisible:false,
            conForm:{
                apartmentAddress:'',
                apartmentId:''
            },
            form:{
                // agentIdCardUrl:[{
                //     id:"shbay/other/d11b165ff7094283a97d9db3f1e59a36_1111.png",
                //     name: "1111.png"
                // }]
            },
            // 搜索参数
            searchForm:{
                status:"",
                contractStatus:"",
                dueDate:"",
                houseType:"",
                companyName:"",
                name:"",
                pageNumber:1,
                pageSize:10,
            },
            searchFormApp:{
                pageNumber:1,
                pageSize:5,
            },
            total:0,
            colConfigs:[ // 表格列
                {
                    slot:'companyName'
                },
                {
                    label:'个人姓名',
                    prop:'name',
                    sortable:true,
                    width:150
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
                    label:'申请状态',
                    prop:'statusCN',
                    sortable:true
                },
                {
                    label:'合同开始时间',
                    prop:'contractBeginDate',
                    sortable:true,
                    width:110
                },
                {
                    label:'合同结束时间',
                    prop:'contractEndDate',
                    sortable:true,
                    width:110
                },
                {
                    label:'合同期限',
                    prop:'term',
                    sortable:true
                },
                {
                    label:'合同状态',
                    prop:'contractStatusCN',
                    sortable:true
                },
                {
                    slot:'action'
                },
            ],
            colAppConfigs:[
                {
                    label:'编号',
                    prop:'number',
                    sortable:true
                },
                // {
                //     label:'照片',
                //     prop:'',
                //     sortable:true
                // },
                {
                    label:'小区名称',
                    prop:'residentialAreaName',
                    sortable:true
                },
                {
                    label:'房间地址',
                    prop:'fullAddress',
                    sortable:true
                },
                {
                    label:'户型',
                    prop:'houseTypeName',
                    sortable:true
                },
                {
                    label:'面积(m2)',
                    prop:'area',
                    sortable:true
                },
                {
                    label:'状态',
                    prop:'statusCN',
                    sortable:true
                },
                {
                    slot:'action'
                }
            ],
            loading:false,
            applyList:[],//申请列表
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
            contractStatus:[ //合同状态
                {
                    label:'合同执行中',
                    value:0
                },
                {
                    label:'提前解约',
                    value:-1
                },
                {
                    label:'合同到期',
                    value:-2
                }
            ], 
            houseTypes:[ //状态
                {
                    label:'待出租',
                    value:0
                },
                {
                    label:'已出租',
                    value:1
                }
            ], 
            dueDates:[ //合同到期时间
                {
                    label:'1个月内',
                    value:0
                },
                {
                    label:'3个月内',
                    value:1
                },  
                {
                    label:'大于3个月',
                    value:2
                }
            ], 
            formValidate:{}
           


        }
    },
    components:{
        flexTable,
        Pagination,
        myUpload,
        myPopup
    },
    mounted(){
        this.init()
    },
    methods:{
        // 数据初始化
        init(){
            // 公寓户型
            dict.getDictInfoByType({type:'apartmentType'}).then(res => {
                this.houseTypeList = res.data
            })
            // 获取公寓列表数据
            this.search()
        },
        showConModal(scope){
            this.modalConVisible = true
            this.conForm.id = scope.row.id;
            this.conForm.contractBeginDate = scope.row.contractBeginDate;
            this.conForm.contractEndDate = scope.row.contractEndDate;
            this.conForm.apartmentId = scope.row.apartmentId
            this.conForm.apartmentAddress = scope.row.apartmentAddress;
            this.conForm.contractStatusCN = scope.row.contractStatusCN;
            this.conForm.contractStatus = scope.row.contractStatus;
        },
        getApplist(){
            api.getApartmentByCondition(this.searchFormApp).then(res => {
                this.apartments = res.data.list
                this.apartmentTotal = res.data.total
                for (let item of this.apartments) {
                    if(item.status == 0) {
                        item.statusCN = '待出租'
                    } else if(item.status == 1) {
                        item.statusCN = '已出租'
                    }
                    item.fullAddress = item.addressRidgepole+'栋'+item.addressNumber+'号'+item.addressRoom;
                }
                let arr = this.apartments.filter(function(v){
                    return v.status == 0
                })
                this.apartments = Array.from(arr)
            })
        },
        closeAppModal(){
            this.modalAppVisible = false
        },
        showAparatlist(){
            this.modalAppVisible = true
            // 获取公寓列表数据
            this.getApplist()
        },
        chooseApp(scope){
            this.conForm.apartmentAddress = scope.row.residentialAreaName+scope.row.fullAddress
            this.conForm.apartmentId = scope.row.id
            this.modalAppVisible = false
        },
        // 显示弹窗
        showModal(scope,type) {
            if(type){
                switch(type){
                    case "edit":
                        this.modelTitle = '编辑'
                        this.form = JSON.parse(JSON.stringify(scope.row));
                        this.canEdit = true;
                        this.submitType = 'edit'
                        api.viewApartmentApply({id:scope.row.id}).then(res => {
                            this.form = res.data;
                            this.form.id = scope.row.id;
                        })
                        break;
                    case "view":
                        this.modelTitle = '查看'
                        this.form = scope.row;
                        this.canEdit = false;
                        api.viewApartmentApply({id:scope.row.id}).then(res => {
                            this.form = res.data;
                        })
                        break;
                    case "review":
                        this.form = scope.row;
                        this.canEdit = false;
                        break;
                }
            }else{
                this.form = {}
                this.canEdit = true;
                this.submitType = 'add'
            }
            // if(scope.row){
            //     this.submitType = 'edit';
            //     this.modalTitle = '编辑';
            //     console.log(scope)
            //     this.form.creditRecognitionId = scope.row.creditRecognitionId;
            //     this.form.companyName = scope.row.companyName;
            //     this.form.totalNumber = scope.row.totalNumber;
            //     this.form.id = scope.row.id;
            // }else{
            //     this.submitType = 'add';
            //     this.modalTitle = '新增';
            // }
            this.modalVisible = true
        },
        closeModal(){
            this.modalVisible = false;
        },
        closeConModal(){
            this.modalConVisible = false;
        },
        handleConSubmit(type){
            switch(type){
                case "save":
                    delete this.conForm.contractStatusCN
                    // delete this.conForm.contractStatus
                    break;
                case "dueCancel":
                    delete this.conForm.contractBeginDate
                    delete this.conForm.contractEndDate
                    delete this.conForm.apartmentAddress
                    delete this.conForm.contractStatusCN
                    delete this.conForm.apartmentId
                    this.conForm.contractStatus = -2
                    break;
                case "preCancel":
                    delete this.conForm.contractBeginDate
                    delete this.conForm.contractEndDate
                    delete this.conForm.apartmentAddress
                    delete this.conForm.contractStatusCN
                    this.conForm.contractStatus = -1
                    break;
            }
            api.saveContract(this.conForm).then(res =>{
                    if(res.code == 200){
                                this.$Message.success('操作成功')
                                this.modalConVisible = false;
                                this.search()
                            }
            })
        },
        // 新增
        handleSubmit(v){
            if(this.submitType == 'add'){
                api.addApartmentApply(v).then(res => {
                    if(res.code == 200){
                        this.$Message.success('添加成功')
                        // this.modalVisible = false;
                        this.$refs.model.closeModal()
                        this.search()
                    }
                })
            }else if(this.submitType == 'edit'){
                v.id = this.form.id
                api.editApartmentApply(v).then(res => {
                    if(res.code == 200){
                        this.$Message.success('修改成功')
                        // this.modalVisible = false;
                        this.$refs.model.closeModal()
                        this.init()
                    }
                })
            }
        },
        tableRowClassName({row,rowIndex}){
            switch(row.dueDate){
                case 0:
                    return 'red-row'
                case 1:
                    return 'warning-row'
                case 2:
                    return ''
            }
        // this.$emit
        },
        // 查询
        search(){
            const that = this
            // 获取公寓列表数据
            api.queryApplyList(this.searchForm).then(res => {
                this.applyList = res.data.list
                this.total = res.data.total
                let that = this;
                this.applyList.forEach(function(item){
                    // switch(item.dueDate){
                    //     case 0:
                    //         console.log('warning000')
                    //         that.tableRowClassName = 'warning-row'
                    //         break;
                    //     case 1:
                    //         console.log('warning111')
                    //         that.tableRowClassName = 'warning-row'
                    //     case 2:
                    //         console.log('warning222')
                    //         that.tableRowClassName = ''
                    // }
                    switch(item.contractStatus){
                        case -1:
                            item.contractStatusCN = '提前解约'
                            break;
                        case -2:
                        item.contractStatusCN = '合同到期'
                        break;
                        case 0:
                        item.contractStatusCN = '合同执行中'
                        break;
                            
                    }
                    let re = that.statusOptions.find( (v) => {
                        return v.value == item.status
                    })
                    item.statusCN = re.label
                })

            })
        },
        // 提审
        reviewApply(scope){
            api.arraignment({id:scope.row.id}).then(res => {
                if(res.code == 200){
                    this.$Message.success('提交成功')
                    this.search()
                }else{
                    this.$Message.error(res.msg)
                }
            })
        },
        // 克隆
        clone(scope){
            api.cloneApply({id:scope.row.id}).then(res => {
                if(res.code == 200){
                    this.$Message.success('克隆成功')
                    this.search()
                }else{
                    this.$Message.error(res.msg)
                }
            })
        },
        exportFile(){
            let params = JSON.parse(JSON.stringify(this.searchForm))
            params.pageNumber = 0;
            params.pageSize = 0;
            api.exportApplyExcel(params,{responseType: 'blob'}).then(res => {
                let url = window.URL.createObjectURL(new Blob([res]));
                let link = document.createElement('a');
                link.style.display = 'none';
                link.href = url;
                let fileName = '申请数据'+createUUID().substring(0,8);
                link.setAttribute('download',fileName+'.xls')
                document.body.appendChild(link);
                link.click();
                document.body.removeChild(link)
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
        },
        changePageApp(v){
            this.searchFormApp.pageNumber = v;
            this.getApplist()
        },
        changePageSizeApp(v){
            this.searchFormApp.pageSize = v;
            this.getApplist()
        }
    }
}
</script>