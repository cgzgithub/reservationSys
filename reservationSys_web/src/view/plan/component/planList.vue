<template>
    <div class="container">
      <el-row>
            <el-form :inline="true" ref="searchForm" :model="searchForm">
                <el-form-item>
                    <el-button size="small" type="primary" @click="showModal('scope','add')">新增</el-button>
                </el-form-item>
                 <el-form-item>
                    <el-button size="small" type="primary" @click="exportFile">导出</el-button>
                </el-form-item>
                <div style="display:inline-block;margin-left:20px;">
                    <el-form-item>
                        <el-button size="small" type="primary" @click="goFilePage(currentCell)">资料归档</el-button>
                    </el-form-item>
                    <el-form-item>
                        <el-button size="small" type="primary" @click="goContractPage(currentCell)">合同管理</el-button>
                    </el-form-item>
                </div>
            </el-form>
        </el-row>
        <Row>
            <flexTable
            :colConfigs="colConfigs"
            :data="projectList"
            v-loading="loading"
            @row-click="handleRowClick"
            :highlight="true"
            @filter-change="filterChange"
            >
                <el-table-column slot="number" label="项目编号" width="150" prop="number" show-overflow-tooltip :render-header="renderHeader1">
                    <template slot-scope="scope">
                        <a @click="showModal(scope.row,'view')">{{scope.row.number}}</a>
                    </template>
                </el-table-column>
                <el-table-column slot="name" label="项目名称" prop="name" width="260" show-overflow-tooltip :render-header="renderHeader2">
                    <template slot-scope="scope">
                        <span>{{scope.row.name}}</span>
                    </template>
                </el-table-column>
                <el-table-column slot="person" label="项目负责人" prop="person" show-overflow-tooltip :render-header="renderHeader3">
                    <template slot-scope="scope">
                        <span>{{scope.row.person || '--'}}</span>
                    </template>
                </el-table-column>
                <el-table-column slot="stageName" label="项目阶段" prop="stageName" :filters="stageOptions" column-key="stageName"  show-overflow-tooltip >
                    <template slot-scope="scope">
                        <span >{{scope.row.stageName || '--'}}</span>
                    </template>
                </el-table-column>
                <el-table-column slot="stageNodeName" label="项目节点" v-if="projectType == 0"  prop="stageNodeName" column-key="stageNodeName" show-overflow-tooltip >
                    <template slot-scope="scope">
                        <span >{{scope.row.stageNodeName || '--'}}</span>
                    </template>
                </el-table-column>
                <el-table-column v-else slot="stageNodeName" label="项目节点" :filters="Options"  prop="stageNodeName" column-key="stageNodeName" show-overflow-tooltip >
                    <template slot-scope="scope">
                        <span >{{scope.row.stageNodeName || '--'}}</span>
                    </template>
                </el-table-column>
                <el-table-column slot="delFlag" label="状态" prop="stageName" :filters="delOptions" column-key="delFlag"  show-overflow-tooltip >
                    <template slot-scope="scope">
                        <span >{{scope.row.delStatus || '--'}}</span>
                    </template>
                </el-table-column>
                <el-table-column slot="action" label="操作" fixed="right" width="200px" show-overflow-tooltip min-width="100px">
                    <template slot-scope="scope">
                        <el-button
                        type="success"
                        size="small"
                        @click="showModal(scope.row,'edit')"
                        >编辑</el-button>
                         <el-button
                        type="default"
                        size="small"
                        @click="remove(scope.row)"
                        >{{scope.row.delFlag == 1?'关闭':'开放'}}</el-button>
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
        :title="modalTitle"
        v-model="modalVisible"
        :mask-closable="false"
        :closable="false"
        :width="900"
        >
        <div>
            <div style="margin-bottom:20px;">项目基础信息：</div>
            <Form ref="form" :inline="true" :model="form" :label-width="180"  :rules="formValidate">
                <FormItem style="width:360px" label="项目编号：" prop="number">
                    <Input v-if="popEdit" v-model="form.number" />
                    <span v-else>{{form.number}}</span>
                </FormItem>
                <FormItem style="width:360px" label="项目名称：" prop="name">
                    <Input v-if="popEdit" v-model="form.name" />
                    <span v-else>{{form.name}}</span>
                </FormItem>
                <FormItem style="width:360px" label="负责人：" prop="person">
                    <Input v-if="popEdit" v-model="form.person" />
                    <span v-else>{{form.person}}</span>
                </FormItem>
                <FormItem style="width:360px" label="联系电话：" prop="phone">
                    <Input v-if="popEdit" v-model="form.phone" />
                    <span v-else>{{form.phone}}</span>
                </FormItem>
                <FormItem style="width:360px" label="项目开始时间：" prop="beginTime">
                    <DatePicker v-if="popEdit" v-model="form.beginTime" />
                    <span v-else>{{form.beginTime | formatDate}}</span>
                </FormItem>
                <FormItem style="width:360px" label="项目结束时间：" prop="endTime">
                    <DatePicker v-if="popEdit" v-model="form.endTime" />
                    <span v-else>{{form.endTime | formatDate}}</span>
                </FormItem>
                <FormItem style="width:360px" label="项目总投资额：" prop="totalInvestment">
                    <Input v-if="popEdit" type="number" v-model="form.totalInvestment" />
                    <span v-else>{{form.totalInvestmentStr}}</span>
                </FormItem>
                <FormItem v-if="popType !== 'add'" :label-width="180" style="width:360px" label="项目累计已签订合同金额：" prop="totalContractMoneyStr">
                    <span>{{form.totalContractMoneyStr}}</span>
                </FormItem>
                <FormItem v-if="popType !== 'add'" :label-width="180" style="width:360px" label="项目累计已结算金额：" prop="totalPaidMoneyStr">
                    <span>{{form.totalPaidMoneyStr}}</span>
                </FormItem>
            </Form>
        </div>
        <div v-if="popType == 'edit'">
            <div>
                当前项目流程阶段：
            </div>
            <div style="padding:20px 40px;display:flex;justify-content:space-between;align-items:center">
                <div>
                    <span style="display:inline-block;margin-right:20px;width:85px;text-align:right;">当前阶段：</span><span>{{form.stageName}}</span>
                </div>
                <!-- <div>
                    <Input v-model="actionPerson" placeholder="操作人" />
                </div> -->
                <div>
                    <Button style="margin-right:7px" @click="saveStage" type="success">保存并流转</Button>
                    <Button v-if="lastNodeUsing.stageType" @click="addNode" type="success" style="margin-right:7px">添加节点</Button>
                    <Button v-if="lastNodeUsing.stageType" style="margin-right:7px" @click="editStageNode(lastNodeUsing)" type="success">编辑节点</Button>
                    <Button v-if="lastNodeUsing.stageType" @click="delStageNode(lastNodeUsing)" type="error">删除节点</Button>
                </div>
            </div>
            <div v-if="projectType != 0" style="padding:10px 40px; display:flex;justify-content:space-between;align-items:center">
                <div>
                    <span style="margin-right:20px">当前阶段节点：</span><span>{{form.stageNodeName}}</span>
                </div>
            </div>
            <div style="padding:10px 40px;">
                <span style="display:inline-block;margin-right:20px;width:85px;text-align:right;">操作人：</span><Input style="width:150px" v-model="actionPerson" placeholder="请输入" />
            </div>
            <div style="padding:10px 40px;display:flex;">
                <span style="display:inline-block;margin-right:20px;width:85px;text-align:right;">上传文件：</span><myUpload style="display:inline-block;width:200px" v-model="relationList" />
            </div>
            <!-- <div v-if="lastNodeUsing.stageType" style="padding:20px 40px; display:flex;justify-content:space-between;align-items:center">
                <div>
                    <span style="margin-right:20px">当前阶段节点：</span>
                    <span style="margin-right:30px">{{lastNodeUsing.dictName}}</span>
                    <span style="margin-right:30px">{{lastNodeUsing.person}}</span>
                    <span>{{lastNodeUsing.finishTime}}</span>
                </div>
                <div>
                    <Button style="margin-right:7px" @click="editStageNode(lastNodeUsing)" type="primary">编辑</Button>
                    <Button @click="delStageNode(lastNodeUsing)" type="error">删除</Button>
                </div>
            </div> -->
            <div v-if="lastNodeUsing.stageType" style="padding:10px 40px; display:flex;">
                <div>
                    <span style="display:inline-block;margin-right:20px;width:85px;text-align:right;">当前阶段节点：</span>
                    
                </div>
                <div>
                    <div style="margin-bottom:10px">
                        <span>节点名称：</span>
                        <span>{{lastNodeUsing.dictName}}</span>
                    </div>
                    <div style="margin-bottom:10px">
                        <span>负责人：</span>
                        <span>{{lastNodeUsing.person}}</span>
                    </div>
                    <div style="margin-bottom:10px">
                        <span>最后修改时间：</span>
                        <span>{{lastNodeUsing.finishTime}}</span>
                    </div>
                </div>
            </div>
        </div>
        <div v-if="!popEdit">
            <div>
                合同信息：
            </div>
            <div style="padding:10px 50px">
                <flexTable
                :colConfigs="contractColConfigs"
                :data="contractList"
                v-loading="contractLoading"
                ></flexTable>
            </div>
        </div>
        <div v-if="!popEdit || popType == 'edit'" style="margin-bottom:20px;margin-top:20px">
            <div>
                项目流程阶段：
            </div>
            <flowNode v-if="flowList.length>0" :flowList="flowList"></flowNode>
            <div style="margin-top:20px;padding-left:60px" v-else>暂无数据</div>
        </div>
            <div slot="footer">
                <Button
                type="default"
                @click="closeVisitModal"
                >关闭</Button>
                <Button
                v-if="popType !== 'view'"
                type="primary"
                @click="handleSubmit"
                >确定</Button>
            </div>

        </Modal>
        <!-- 添加节点弹窗 -->
        <Modal
        :title="addNodeModalTitle"
        v-model="addNodeModalVisible"
        :mask-closable="false"
        :closable="true"
        :width="700"
        >
            <Form :label-width="120" ref="nodeForm" :model="nodeForm" :rules="nodeFormValidate">
                <FormItem label="节点名称" prop="nodeName">
                    <Input v-model="nodeForm.nodeName" />
                </FormItem>
                <FormItem label="负责人" prop="person">
                    <Input v-model="nodeForm.person" />
                </FormItem> 
            </Form>
            <div slot="footer">
                <Button
                type="text"
                @click="closeNodeModal"
                >关闭</Button>
                <Button
                type="primary"
                @click="submitNodeModal"
                >确认</Button>
            </div>
        </Modal>
    </div>
</template>
<script>
import flowNode from './flowNode';
import flexTable from '@/components/flex-table';
import Pagination from '@/components/pagination';
import api from '@/api/project';
import contract from '@/api/contract';
import {createUUID} from '@/utils/index';
import myUpload from '@/components/upload-list'
export default {
    name:'plan-list',
    props:['projectType'],
    data(){
        return{
            currentCell:'',
            rowItem:'',
            stageModalTitle:'历史阶段',
            stageSubmitType:'',
            addNodeModalTitle:'新增施工节点',
            stageModalVisible:false,
            addNodeModalVisible:false,
            popEdit:false,
            popType:'',
            actionPerson:'',
            relationList:[],
            flowList:[],
            contractList:[],
            lastNodeUsing:'',
            currentStageId:'',
            currentStageNodeId:'',
            currentProjectId:'',
            currentStageNodeId:'',
            modalTitle:'新增',
            modalVisible:false,
            form:{
                projectType:this.projectType
            },
            nodeForm:{},
            // 搜索参数
            searchForm:{
                projectType:this.projectType,
                stageIds:[],
                stageNodeIds:[],
                number:"",
                name:"",
                person:"",
                pageNumber:1,
                pageSize:10,
            },
            total:0,
            projectList:[],
            colConfigs:[ // 表格列
                {
                    slot:'number'
                },
                {
                    slot:'name'
                },
                {
                    slot:'person'
                },
                {
                    label:'联系电话',
                    prop:'phone'
                },
                {
                    label:'项目开始时间',
                    prop:'beginTime',
                    width:130
                },
                {
                    label:'项目结束时间',
                    prop:'endTime',
                    width:130
                },
                {
                    label:'项目周期',
                    prop:'cycle'
                },
                {
                    slot:'stageName'
                },
                {
                    slot:'stageNodeName'
                },
                {
                    slot:'delFlag'
                },
                {
                    slot:'action'
                },
            ],
            contractColConfigs:[
                {
                    label:'合同编号',
                    prop:'contractNum'
                },
                {
                    label:'合同名称',
                    prop:'contractName'
                },
                {
                    label:'开始时间',
                    prop:'beginTime'
                },
                {
                    label:'结束时间',
                    prop:'endTime'
                },
                {
                    label:'合同总金额',
                    prop:'totalMoneyStr'
                },
                {
                    label:'累计支付金额',
                    prop:'totalPaidMoneyStr'
                },
                {
                    label:'合同状态',
                    prop:'contractStatusName'
                }
            ],
            contractLoading:false,
            loading:false,
            statusOptions:[ //施工阶段
                {
                    label:'项目阶段',
                    value:0
                },
                {
                    label:'方案设计',
                    value:1
                },
                {
                    label:'上会',
                    value:2
                }, 
                {
                    label:'立项',
                    value:3
                },
                {
                    label:'招投标',
                    value:4
                },
                {
                    label:'合同签订',
                    value:5
                },
                {
                    label:'施工',
                    value:6
                },
                {
                    label:'竣工验收',
                    value:7
                }
            ], 
            formValidate:{
                number:[{ required: true, message: "不能为空", trigger: "blur"}],
                name:[{ required: true, message: "不能为空", trigger: "blur"}],
                person:[{ required: true, message: "不能为空", trigger: "blur"}], 
                phone:[{ required: true, message: "不能为空", trigger: "blur"},{pattern: /^1[3-9]\d{9}$/,message:'手机号格式不对！'}],
                beginTime:[{ required: true, message: "不能为空"}],
                endTime:[{ required: true, message: "不能为空"}],
                totalInvestment:[{ required: true, message: "不能为空"},{pattern:/^\d+(\.\d{0,2})?$/,message:'请输入最多保留两位小数的正数'}]
            },
            nodeFormValidate:{
                nodeName:[{ required: true, message: "不能为空", trigger: "blur"}],
                person:[{ required: true, message: "不能为空", trigger: "blur"}],
            },
            search1:false,
            search2:false,
            search3:false,
            visibleNumber:true,
            visiblePerson:true,
            visibleName:false,
            stageOptions:[],
            nodeOptions:[],
            delOptions:[{value:1,text:'已开放'},{value:0,text:'已关闭'}]
        }
    },
    components:{
        flexTable,
        Pagination,
        flowNode,
        myUpload
    },
    mounted(){
        this.init()
    },
    methods:{
        renderHeader1(h,{column,$index}){
            return (
                <span style={this.searchForm.number ? {'color': '#409EFF'} : {'color': '#909399'}}>
                {column.label}
                <el-popover placement='bottom' width='200' height='150' trigger='hover' >
                    <span slot='reference'>
                    <i class='el-icon-arrow-down' ></i>
                    </span>
                    <el-input style="margin-bottom:10px" size='small' v-model={this.searchForm.number} placeholder='请输入'></el-input>
                    <el-row type="flex" justify="end">
                    <el-button size='small' type="primary" on-click={this.searchFilter}>搜索</el-button>
                    <el-button size='small' type="default" on-click={this.clearNumber.bind(this,'number')}>清空</el-button>
                    </el-row>
                    
                </el-popover>
                </span>
            )
        },
        renderHeader2(h,{column,$index}){
            return (
                <span style={this.searchForm.name ? {'color': '#409EFF'} : {'color': '#909399'}}>
                {column.label}
                <el-popover placement='bottom' width='200' height='150' trigger='hover'>
                    <span slot='reference'>
                    <i class='el-icon-arrow-down'></i>
                    </span>
                    <el-input style="margin-bottom:10px" size='small' v-model={this.searchForm.name} placeholder='请输入'></el-input>
                    <el-row type="flex" justify="end">
                    <el-button size='small' type="primary" on-click={this.searchFilter}>搜索</el-button>
                    <el-button size='small' type="default" on-click={this.clearNumber.bind(this,'name')}>清空</el-button>
                    </el-row>
                    
                </el-popover>
                </span>
            )
        },
        renderHeader3(h,{column,$index}){
            return (
                <span style={this.searchForm.person ? {'color': '#409EFF'} : {'color': '#909399'}}>
                {column.label}
                <el-popover placement='bottom' width='200' height='150' trigger='hover'>
                    <span slot='reference'>
                    <i class='el-icon-arrow-down'></i>
                    </span>
                    <el-input style="margin-bottom:10px" size='small' v-model={this.searchForm.person} placeholder='请输入'></el-input>
                    <el-row type="flex" justify="end">
                    <el-button size='small' type="primary" on-click={this.searchFilter}>搜索</el-button>
                    <el-button size='small' type="default" on-click={this.clearNumber.bind(this,'person')}>清空</el-button>
                    </el-row>
                </el-popover>
                </span>
            )
        },
        searchFilter(){
            this.visibleNumber = false
            this.visibleName = false
            this.visiblePerson = false
            this.search()
        },
        clearNumber(key){
            this.visibleNumber = false
            this.searchForm[key] = ''
            this.search()
        },
        filterChange(v){
            if(v.stageName){
                this.searchForm.stageIds = v.stageName
                this.search()
            }else if(v.stageNodeName){
                this.searchForm.stageNodeIds = v.stageNodeName
                this.search()
            }else if(v.delFlag){
                this.searchForm.delFlag = v.delFlag
                this.search()
            }
        },
        filterHandler1(value, row, column){
            // console.log(value)
            // this.searchForm.stageId = value
            // this.search()
            return true;
        },
        // 数据初始化
        init(){
            api.getStageNodeDictList({projectType:this.projectType}).then(res => {
                this.statusOptions = res.data
            })
            api.getStageDictList({projectType:this.projectType}).then(res => {
                this.stageOptions = res.data
            })
            api.getNodeDictList({projectType:this.projectType}).then(res => {
                this.nodeOptions = res.data
            })
            // 获取项目列表数据
            this.search()
        },
        closeVisitModal(){
            this.modalVisible = false
            this.$refs.form.resetFields()
        },
        // 合同管理
        goContractPage(row){
            let projectItem = {
                projectId:row.id,
                projectNum:row.number,
                projectName:row.name,
                projectType:this.projectType
            }
            if(row){
                this.$router.push({path:'/plan/contract-manager',query:{projectItem:JSON.stringify(projectItem)}})
            }else{
                this.$router.push({path:'/plan/contract-manager',query:{projectType:JSON.stringify(this.projectType)}})
            }
            
        },
        // 资料归档
        goFilePage(row){
            let projectItem = {
                projectId:row.id,
                projectNum:row.number,
                projectName:row.name,
                projectType:this.projectType
            }
            if(row){
                this.$router.push({path:'/plan/file-manager',query:{projectItem:JSON.stringify(projectItem)}})
            }else{
                this.$router.push({path:'/plan/file-manager',query:{projectType:JSON.stringify(this.projectType)}})
            }
            
        },
        handleRowClick(row){
            this.currentCell = row;
        },
        // 显示弹窗
        showModal(row,type) {
            switch(type){
                case "view":
                    this.rowItem = row
                    this.form= JSON.parse(JSON.stringify(row));
                    this.modalTitle = '查看';
                    this.popEdit = false;
                    this.popType = 'view';
                    this.currentProjectId = row.id;
                    this.currentStageId = row.stageId;
                    if(row.stageNodeId){
                        this.currentStageNodeId = row.stageNodeId
                    }
                    this.getStageNodeList(row.id);
                    this.getContractList(row.id);
                    break;
                case "edit":
                    this.relationList = []
                    this.rowItem = JSON.parse(JSON.stringify(row));
                    this.popEdit = true;
                    this.popType = 'edit';
                    this.submitType = 'edit';
                    this.modalTitle = '编辑';
                    this.currentProjectId = row.id;
                    this.currentStageId = row.stageId;
                    if(row.stageNodeId){
                        this.currentStageNodeId = row.stageNodeId
                    }
                    this.getLastCustomNode(row.id,row.stageId);
                    this.getStageNodeList(row.id);
                    this.getContractList(row.id);
                    this.form= JSON.parse(JSON.stringify(row))
                    break;
                case "add":
                    this.form= {}
                    this.popType = 'add';
                    this.popEdit = true;
                    this.submitType = 'add';
                    this.modalTitle = '新增';
                    break;
            }
            this.modalVisible = true
        },
         // 获取项目阶段流程信息
        getStageNodeList(id){
            api.getStageNodeList({projectId:id}).then(res => {
                this.flowList = res.data;
            })
        },
        // 获取合同列表
        getContractList(id){
            let params = {
                pageNumber:1,
                pageSize:10,
                projectId:id
            }
            contract.getContractList(params).then(res => {
                this.contractList = res.data.list;
            })
        },
        // 查询当前阶段最后一个可自定义节点
        getLastCustomNode(projectId,stageId){
            let params = {
                projectId:projectId,
                stageId:stageId
            }
            api.getLastCustomNode(params).then(res => {
                
                if(Object.keys(res.data).length == 0){
                    this.lastNodeUsing = false;
                }else{
                    this.lastNodeUsing = res.data;
                }
            })
        },
        // 新增项目
        handleSubmit(){
	        this.$refs.form.validate(valid => {
                if(valid){
                    this.form.projectType = this.projectType
                    if(this.submitType == 'add'){
                        api.addProject(this.form).then(res => {
                            if(res.code == 200){
                                this.$Message.success('添加成功')
                                this.modalVisible = false;
                                this.$refs.form.resetFields()
                                this.search()
                            }
                        })
                    }else{
                        api.editProject(this.form).then(res => {
                            if(res.code == 200){
                                this.$Message.success('修改成功')
                                this.modalVisible = false;
                                this.$refs.form.resetFields()
                                this.search()
                            }
                        })
                    }
                }
	        })
        },
        // 查询项目列表
        search(){
             // 获取项目列表数据
            api.getAllList(this.searchForm).then(res => {
                this.projectList = res.data.list
                this.projectList.forEach(function(item){
                    if(item.delFlag == 1) {
                        item.delStatus = '已开放'
                    }else if(item.delFlag == 0){
                        item.delStatus = '已关闭'
                    }
                })
                this.total = res.data.total
            })
        },
        // 新增
        add( ){

        },
        // 删除
        remove(v) {
            if(v.delFlag == 1){
                this.$Modal.confirm({
                    title: "确认关闭",
                    content: "您确认要关闭该项目吗 " + " ?",
                    loading: true,
                    onOk: () => {
                    api.deleteProject({id:v.id}).then(res => {
                        this.$Modal.remove();
                        if (res.code == 200) {
                        this.$Message.success("操作成功");
                        this.search();
                        }
                    });
                    }
                });
            }else if(v.delFlag == 0){
                api.openProject({id:v.id}).then(res => {
                        this.$Modal.remove();
                        if (res.code == 200) {
                        this.$Message.success("操作成功");
                        this.search();
                        }
                    });
            }
            
        },
        // 查看历史阶段弹窗
        viewStage(){
            this.stageModalVisible = true; 
        },
        // 关闭查看历史阶段弹窗
        closeStageModal(){
            this.stageModalVisible = false;
        },
        // 打开新增节点弹窗
        addNode(){
            this.addNodeModalTitle = '新增施工节点'
            this.stageSubmitType = 'add'
            this.addNodeModalVisible = true;
            this.nodeForm = {}
        },
        saveStage(){
            let params = {
                projectId: this.currentProjectId,
                stageId:this.currentStageId,
                person:this.actionPerson,
                stageNodeId:this.currentStageNodeId,
                relationList:this.relationList
            }
            if(this.projectType == 0){
                delete params.stageNodeId
            }
            if(this.actionPerson){
                api.addStageNodeInfo(params).then(res => {
                    if(res.code == 200){
                        this.$Message.success('操作成功')
                        this.search();
                        this.modalVisible = false
                    }
                })
            }else{
                this.$Message.warning('操作人不能为空')
            }

        },
        submitNodeModal(){
            let params = {
                projectId: this.currentProjectId,
                stageId:this.currentStageId
            }
            params = Object.assign(params,this.nodeForm);

            this.$refs.nodeForm.validate(valid => {
                if(valid){
                    if(this.stageSubmitType == 'add'){
                        api.addStageNodeInfo(params).then(res => {
                            if(res.code == 200){
                                this.addNodeModalVisible = false;
                                this.$Message.success('添加成功')
                                this.getLastCustomNode(this.currentProjectId,this.currentStageId)
                                this.getStageNodeList(this.currentProjectId)
                            }
                        })
                    }else if(this.stageSubmitType == 'edit'){
                        params.id = this.currentStageNodeId;
                        api.editStageNodeInfo(params).then(res => {
                            if(res.code == 200){
                                this.addNodeModalVisible = false;
                                this.$Message.success('修改成功')
                                this.getStageNodeList(this.currentProjectId)
                                this.getLastCustomNode(this.currentProjectId,this.currentStageId)
                            }
                        })
                    }
                }
            });
        },
        editStageNode(row){
            this.currentStageNodeId = row.id;
            this.nodeForm = JSON.parse(JSON.stringify(row));
            this.nodeForm.nodeName=row.dictName
            this.stageSubmitType = 'edit'
            this.addNodeModalVisible = true;
            this.addNodeModalTitle = '编辑施工节点'
        },
        delStageNode(row){
            api.delStageNodeInfo({id:row.id}).then(res =>{
                if(res.code == 200){
                    this.$Message.success('删除成功')
                    this.getStageNodeList(this.currentProjectId)
                    this.getLastCustomNode(this.currentProjectId,this.currentStageId)
                }
            })
        },
        // 关闭新增节点弹窗
        closeNodeModal(){
            this.addNodeModalVisible = false;
        },
        // 关闭弹窗
        handelCancel(){
            this.modalVisible = false
        },
        statusChange(v){
            this.searchForm.stageId = v[0]
        },
        exportFile(){
            let params = JSON.parse(JSON.stringify(this.searchForm))
            params.pageNumber = 0;
            params.pageSize = 0;
            api.exportProjectExcel(params,{responseType: 'blob'}).then(res => {
                let url = window.URL.createObjectURL(new Blob([res]));
                let link = document.createElement('a');
                link.style.display = 'none';
                link.href = url;
                let fileName = '项目数据'+createUUID().substring(0,8);
                link.setAttribute('download',fileName+'.xls')
                document.body.appendChild(link);
                link.click();
                document.body.removeChild(link)
            })
        },
        goFile(){
            this.$router.push('')
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
<style lang="scss" scoped>
</style>