<template>
    <div class="container">
        <el-row>
            <el-form :inline="true" ref="searchForm" :model="searchForm">
                <el-form-item>
                    <el-select style="width:100px" size="small" v-model="searchForm.contractType" placeholder="合同类型" clearable>
                        <el-option v-for="(item,index) in contractTypeList" :key="index" :label="item.title" :value="item.id"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-select style="width:100px" size="small" v-model="searchForm.projectType" placeholder="项目类型" clearable>
                        <el-option v-for="(item,index) in projectTypeList" :key="index" :label="item.title" :value="item.id"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-select style="width:100px" size="small"  placeholder="合同状态" clearable v-model="searchForm.status">
                        <el-option v-for="(item,index) in contractStatusList" :key="index" :label="item.title" :value="item.id"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-input style="width:100px" size="small" v-model="searchForm.contractNum" clearable placeholder="合同编号"/>
                </el-form-item>
                  <!-- <el-form-item>
                    <el-input style="width:100px" size="small" v-model="searchForm.projectNum" clearable placeholder="项目编号"/>
                </el-form-item> -->
                <el-form-item>
                    <el-select size="small" style="width:100px" v-model="searchForm.projectId" clearable placeholder="选择项目">
                        <el-option v-for="(item,index) in projectList" :key="index" :label="item.name" :value="item.id"></el-option>
                    </el-select>
                    <!-- <el-input style="width:100px" size="small" v-model="searchForm.projectName" clearable placeholder="项目名称"/> -->
                </el-form-item>
                <el-form-item>
                    <el-input style="width:100px" size="small" v-model="searchForm.firstParty" clearable placeholder="甲方"/>
                </el-form-item>
                <el-form-item>
                    <el-input style="width:100px" size="small" v-model="searchForm.secondParty" clearable placeholder="乙方"/>
                </el-form-item>
                <el-form-item>
                    <el-button size="small" type="success" @click="search">查询</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button size="small" type="primary" @click="showModal">新增</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button size="small" type="primary" @click="exportFile">导出</el-button>
                </el-form-item>
            </el-form>
        </el-row>
        <Row>
            <flexTable
            :colConfigs="colConfigs"
            :data="contractList"
            v-loading="loading"
            >
                <el-table-column slot="contractNum" label="合同编号" prop="contractNum" show-overflow-tooltip>
                    <template slot-scope="scope">
                        <a @click="viewDetail(scope.row)">{{scope.row.contractNum}}</a>
                    </template>
                </el-table-column>
                <el-table-column slot="projectNum" label="项目编号" prop="projectNum" show-overflow-tooltip>
                    <template slot-scope="scope">
                        <span>{{scope.row.projectNum}}</span>
                    </template>
                </el-table-column>
                <el-table-column width="220" slot="projectName" label="项目名称" prop="projectName" show-overflow-tooltip>
                    <template slot-scope="scope">
                        <span>{{scope.row.projectName}}</span>
                    </template>
                </el-table-column>
                <el-table-column slot="action" label="操作"  width="250px" show-overflow-tooltip min-width="200px">
                    <template slot-scope="scope">
                        <el-button
                        type="success"
                        size="small"
                        @click="showModal(scope)"
                        >编辑</el-button>
                         <el-button
                        type="danger"
                        size="small"
                        @click="remove(scope.row)"
                        >删除</el-button>
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
        :width="700"
        >
        <div>
            <div v-if="popType != 'add'">合同基础信息</div>
            <Form ref="form"  :model="form" :label-width="120" :inline="true" :rules="formValidate">
                <FormItem style="min-width:300px;" label="项目名称：" prop="projectId">
                    <Select v-if="popType == 'add' && listType == 'withoutQuery'" v-model="form.projectId">
                        <Option v-for="(item,index) in projectList" :key="index" :label="item.name" :value="item.id"></Option>
                    </Select>
                    <span v-else>{{form.projectName}}</span>
                </FormItem>
                <FormItem style="min-width:300px;" label="合同编号：" prop="contractNum">
                    <Input v-if="popEdit" v-model="form.contractNum" />
                    <span v-else>{{form.contractNum}}</span>
                </FormItem>
                <FormItem style="min-width:300px;" label="合同名称：" prop="contractName">
                    <Input v-if="popEdit" v-model="form.contractName" />
                    <span v-else>{{form.contractName}}</span>
                </FormItem>
                <FormItem style="min-width:300px;" label="合同类型：" prop="contractType">
                    <Select v-if="popEdit" v-model="form.contractType">
                        <Option v-for="(item,index) in contractTypeList" :key="index" :label="item.title" :value="item.id"></Option>
                    </Select>
                    <span v-else>{{form.contractTypeName}}</span>
                </FormItem>
                <FormItem style="min-width:300px;" label="甲方：" prop="firstParty">
                    <Input v-if="popEdit" v-model="form.firstParty" />
                    <span v-else>{{form.firstParty}}</span>
                </FormItem>
                <FormItem style="min-width:300px;" label="乙方：" prop="secondParty">
                    <Input v-if="popEdit" v-model="form.secondParty" />
                    <span v-else>{{form.secondParty}}</span>
                </FormItem>
                <FormItem style="width:300px;" label="签订时间：" prop="signTime">
                    <DatePicker v-if="popEdit" v-model="form.signTime" />
                    <span v-else>{{form.signTime | formatDate}}</span>
                </FormItem>
                <FormItem style="min-width:300px;"  label="签订人：" prop="signPerson">
                    <Input v-if="popEdit" v-model="form.signPerson" />
                    <span v-else>{{form.signPerson}}</span>
                </FormItem>
                <FormItem style="width:300px;" label="开始时间：" prop="beginTime">
                    <DatePicker v-if="popEdit" v-model="form.beginTime" />
                    <span v-else>{{form.beginTime | formatDate}}</span>
                </FormItem>
                <FormItem style="width:300px;" label="结束时间：" prop="endTime">
                    <DatePicker v-if="popEdit" v-model="form.endTime" />
                    <span v-else>{{form.endTime | formatDate}}</span>
                </FormItem>
                <FormItem style="min-width:300px;" label="合同状态：" prop="status">
                    <Select v-if="popEdit" v-model="form.status">
                        <Option v-for="(item,index) in contractStatusList" :key="index" :label="item.title" :value="item.id"></Option>
                    </Select>
                    <span v-else>{{form.contractStatusName}}</span>
                </FormItem>
                <FormItem style="min-width:300px;" label="合同总金额：" prop="totalMoney">
                    <Input v-if="popEdit" v-model="form.totalMoney" @on-change="moneyChange($event,'totalMoney')" />
                    <span v-else>{{form.totalMoneyStr}}</span>
                </FormItem>
                <FormItem style="min-width:300px;" v-if="popType !== 'add'" label="最终版结算价：" prop="finalMoney">
                    <Input  v-if="popEdit" v-model="form.finalMoney" />
                    <span v-else>{{form.finalMoneyStr}}</span>
                </FormItem>
                <!-- <FormItem v-if="popType !=='add'" style="min-width:300px;" label="未支付金额：" prop="unpaidMoneyStr">
                    <span>{{form.unpaidMoneyStr}}</span>
                </FormItem> -->
                <FormItem style="width:600px;" label="文件上传：" prop="contractMaterials">
                    <myUpload v-if="popEdit" v-model="form.contractMaterials"></myUpload>
                    <div class="item-upload"  v-else>
                        <div v-for="(item,index) in form.contractMaterials" :key="index" >
                        <a class="over-flow" :title="item.fileName" target="_blank" download="file" :href="item.downloadUrl">{{item.fileName}}</a>
                        </div>
                    </div>
                </FormItem>
            </Form>
        </div>
        <div v-if="popType != 'add'">
            <div>合同付款节点</div>
            <div style="padding:15px 30px;">
                <Row v-if="popEdit && form.status !== 19"  type="flex" justify="end"><el-button size="small" type="primary" @click="showNodeModal">新增节点</el-button></Row>
                <flexTable
                :colConfigs="colnodeConfigs"
                :data="contractNodeList"
                v-loading="nodeloading"
                >
                    <el-table-column v-if="popEdit && form.status !== 19" slot="action" label="操作" width="200px" :show-overflow-tooltip="false" min-width="200px">
                        <template slot-scope="scope">
                            <el-button
                            type="success"
                            size="small"
                            @click="showNodeModal(scope)"
                            >编辑</el-button>
                            <el-button
                            type="danger"
                            size="small"
                            @click="removeNode(scope.row)"
                            >删除</el-button>
                        </template>
                    </el-table-column>
                </flexTable>
            </div>
            
            <div v-if="popType != 'add'">累计已支付金额：{{totalMoney}} 元</div>
            <div style="margin-top:10px;padding-left:24px" v-if="popType != 'add'">未支付金额：{{unpadiMoney}} 元</div>
        </div>
            <div slot="footer">
                <Button
                type="default"
                @click="closeModal"
                >关闭</Button>
                <Button
                v-if="popEdit" 
                type="primary"
                @click="handleSubmit"
                >确定</Button>
            </div>

        </Modal>
        <!-- 合同付款节点弹窗 -->
        <Modal
            :title="nodemodalTitle"
            v-model="nodemodalVisible"
            :mask-closable="false"
            :closable="false"
            :width="700"
        >
            <Form ref="nodeForm"  :model="nodeForm" :label-width="120" :inline="true" :rules="nodeFormValidate">
                <FormItem style="min-width:300px;" label="节点名称：" prop="paymentNode">
                    <Input v-model="nodeForm.paymentNode" />
                </FormItem>
                <FormItem style="min-width:200px;" label="时间：" prop="paymentTime">
                    <DatePicker v-model="nodeForm.paymentTime" />
                </FormItem>
                <FormItem style="min-width:500px;" label="金额：" prop="paymentMoney">
                    <Input v-model="nodeForm.paymentMoney" />
                </FormItem>
            </Form>
            <div slot="footer">
                <Button
                type="default"
                @click="closeNodeModal"
                >关闭</Button>
                <Button
                type="primary"
                @click="handleNodeSubmit"
                >确定</Button>
            </div>
        </Modal>
    </div>
</template>
<script>
import myUpload from '@/components/upload-list';
import flexTable from '@/components/flex-table';
import Pagination from '@/components/pagination';
import api from '@/api/contract';
import dict from '@/api/dict';
import project from '@/api/project';
import {createUUID,formateMoney} from '@/utils/index';
export default {
    name:'contract', 
    data(){
        return{
            contractStatusList:[],
            contractTypeList:[],
            projectTypeList:[
                {
                    title:'工程项目',
                    id:0
                },
                {
                    title:'规划调整',
                    id:1
                },
                {
                    title:'土地前期出让',
                    id:2
                },
                {
                    title:'项目前期报建',
                    id:3
                }
            ],
            totalMoney:'',
            unpadiMoney:'',
            contractMoneySum:'',
            finalMoney:'',
            listType:'',
            nodeloading:true,
            popEdit:true,
            popType:'add',
            projectId:'',
            projectType:'',
            projectName:'',
            projectNum:'',
            modalTitle:'新增合同',
            modalVisible:false,
            nodemodalTitle:'新增付款节点',
            nodemodalVisible:false,
            nodeSubmitType:'',
            form:{
                projectType:''
            },
            nodeForm:{},
            // 搜索参数
            searchForm:{
                projectType:"",
                projectId:"",
                pageNumber:1,
                pageSize:10,
            },
            total:0,
            contractList:[],
            contractNodeList:[],
            colnodeConfigs:[
                {
                    label:'付款节点',
                    prop:'paymentNode'
                },
                {
                    label:'付款时间',
                    prop:'paymentTime'
                },
                {
                    label:'付款金额（元）',
                    prop:'paymentMoneyStr',
                    align:'right',
                    width:200
                },
                {
                    slot:'action'
                }
            ],
            colConfigs:[ // 表格列
                {
                    slot:'contractNum'
                },
                {
                    label:'合同名称',
                    prop:'contractName',
                    width:220
                },
                {
                    slot:'projectNum'
                },
                {
                    slot:'projectName'
                },
                {
                    label:'甲方',
                    prop:'firstParty'
                },
                {
                    label:'乙方',
                    prop:'secondParty'
                },
                {
                    label:'签订时间',
                    prop:'signTime'
                },
                {
                    label:'签订人',
                    prop:'signPerson'
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
                    label:'合同类型',
                    prop:'contractTypeName'
                },
                {
                    label:'合同状态',
                    prop:'contractStatusName'
                },
                {
                    label:'累计已付金额（元）',
                    prop:'totalPaidMoneyStr',
                    align:'right',
                    width:190
                },
                {
                    label:'合同总金额（元）',
                    prop:'totalMoneyStr',
                    align:'right',
                    width:190
                },
                {
                    label:'最终版结算价（元）',
                    prop:'finalMoneyStr',
                    align:'right',
                    width:190
                },
                {
                    slot:'action'
                },
            ],
            loading:true,
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
                projectId:[{required:true,message:'不能为空'}],
                contractNum:[{required:true,message:'不能为空'}],
                contractName:[{required:true,message:'不能为空'}],
                contractType:[{required:true,message:'不能为空'}],
                firstParty:[{required:true,message:'不能为空'}],
                secondParty:[{required:true,message:'不能为空'}],
                signPerson:[{required:true,message:'不能为空'}],
                signTime:[{required:true,message:'不能为空'}],
                beginTime:[{required:true,message:'不能为空'}],
                endTime:[{required:true,message:'不能为空'}],
                status:[{required:true,message:'不能为空'}],
                totalMoney:[{required:true,message:'不能为空'},{pattern:/^\d+(\.\d{0,2})?$/,message:'请输入最多保留两位小数的正数'}],
                finalMoney:[{pattern:/^\d+(\.\d{0,2})?$/,message:'请输入最多保留两位小数的正数'}]
            },
            nodeFormValidate:{
                paymentNode:[{required:true,message:'不能为空',trigger:'blur'}],
                paymentTime:[{required:true,message:'不能为空'}],
                paymentMoney:[{required:true,message:'不能为空'},{pattern:/^\d+(\.\d{0,2})?$/,message:'请输入最多保留两位小数的正数'}],
            },
            projectList:[]

        }
    },
    components:{
        flexTable,
        Pagination,
        myUpload
    },
    mounted(){
        this.init()
    },
    watch:{
        $route(to,from){
            this.init()
        }
    },
    methods:{
        moneyChange(e,name){
            
        },
        // 数据初始化
        init(){
            dict.getDictInfoByType({type:'contractType'}).then(res => {
                this.contractTypeList = res.data
            })
            dict.getDictInfoByType({type:'contractStatus'}).then(res => {
                this.contractStatusList = res.data
            })
            // project.getAllList({pageNumber:0,pageSize:0}).then(res => {
            //     this.projectList = res.data.list;
            // })
            if(this.$route.query.projectItem){
                this.listType = 'withQuery';
                let projectItem = JSON.parse(this.$route.query.projectItem);
                this.projectType = projectItem.projectType;
                this.projectName = projectItem.projectName;
                // this.projectNum = projectItem.projectNum;
                // this.form.projectType = projectItem.projectType;
                this.form.projectName = projectItem.projectName;
                // this.form.projectNum = projectItem.projectNum;
                this.form.projectId = projectItem.projectId;
                this.projectId = projectItem.projectId;
                this.searchForm.projectType = projectItem.projectType;
                
                project.getAllList({pageNumber:0,pageSize:0,projectType:this.projectType}).then(res => {
                    this.projectList = res.data.list;
                    
                })
                this.searchForm.projectId = projectItem.projectId;
                
            }else if(this.$route.query.projectType){
                this.listType = 'withoutQuery';
                let projectType = JSON.parse(this.$route.query.projectType);
                this.projectType = projectType;
                project.getAllList({pageNumber:0,pageSize:0,projectType:this.projectType}).then(res => {
                    this.projectList = res.data.list;
                })
                this.searchForm.projectType = projectType
            }else{
                this.listType = 'withoutQuery';
                project.getAllList({pageNumber:0,pageSize:0}).then(res => {
                    this.projectList = res.data.list;
                })
            }
            // 获取项目列表数据
            this.search()
        },
        closeModal(){
            this.modalVisible = false
            // this.$refs.form.resetFields()
        },
        closeNodeModal(){
            this.nodemodalVisible = false;
            this.$refs.nodeForm.resetFields()
        },
        viewDetail(row){
            this.popEdit = false;
            this.modalVisible = true;
            this.contractId = row.id;
            this.popType = 'view';
            this.modalTitle = '查看合同';
            this.getNodeList(row.id);
            this.form= JSON.parse(JSON.stringify(row));
        },
        // 显示弹窗
        showModal(scope) {
            this.$refs.form.resetFields()
            this.popEdit = true;
            if(scope.row){
                this.contractId = scope.row.id;
                this.popType = 'edit';
                this.submitType = 'edit';
                this.modalTitle = '编辑合同';
                this.getNodeList(scope.row.id);
                // this.form = scope.row
                this.form= JSON.parse(JSON.stringify(scope.row))
                this.contractMoneySum = scope.row.totalMoney
                this.finalMoney = scope.row.finalMoney
            }else{
                this.form={}
                this.popType = 'add';
                if(this.listType == 'withQuery'){
                    this.form = {};
                    this.form.projectName = this.projectName;
                    this.form.projectId = this.projectId;
                }
                this.submitType = 'add';
                this.modalTitle = '新增合同';
            }
            this.modalVisible = true
        },
        showNodeModal(scope){
            if(scope.row){
                this.nodeSubmitType= 'edit';
                this.nodemodalTitle = '编辑付款节点'
                this.nodeForm = JSON.parse(JSON.stringify(scope.row))
            }else{
                this.nodeForm = {
                    contractId: this.contractId
                }
                this.nodeSubmitType = 'add'
                this.nodemodalTitle = '新增付款节点'
            }
            this.nodemodalVisible = true;
        },
        getNodeList(id){
            this.nodeloading = true;
            api.getNodeListById({contractId:id}).then(res => {
                this.contractNodeList = res.data;
                this.totalMoney = this.contractNodeList.reduce(function(acc,cur){
                    return acc + cur.paymentMoney
                },0)
                if(this.finalMoney){
                    this.unpadiMoney = this.finalMoney - this.totalMoney
                }else{
                    this.unpadiMoney = this.contractMoneySum - this.totalMoney
                }
                let unpaidStr = this.unpadiMoney.toFixed(2).toString()
                this.unpadiMoney = formateMoney(unpaidStr)
                let strMoney = this.totalMoney.toFixed(2).toString()
                this.totalMoney = formateMoney(strMoney)
                this.nodeloading = false
            })
        },
        // 新增
        handleSubmit(){
            // this.form.projectId = this.projectId;
            this.$refs.form.validate(valid => {
                if(valid){
                    if(this.submitType == 'add'){
                        api.addContract(this.form).then(res => {
                            if(res.code == 200){
                                this.$Message.success('合同添加成功')
                                this.modalVisible = false;
                                this.search()
                            }
                        })
                    }else{
                        api.editContract(this.form).then(res => {
                            if(res.code == 200){
                                this.$Message.success('合同修改成功')
                                this.modalVisible = false;
                                this.search()
                            }
                        })
                    }
                }
            })
            
        },
        handleNodeSubmit(){
             this.$refs.nodeForm.validate(valid => {
                if(valid){
                    this.nodeForm.contractId = this.contractId;
                    if(this.nodeSubmitType == 'add'){
                        api.addContractNode(this.nodeForm).then(res => {
                            if(res.code == 200){
                                this.$Message.success('合同节点添加成功')
                                this.nodemodalVisible = false;
                                // this.modalVisible = false;
                                this.$refs.nodeForm.resetFields()
                                this.getNodeList(this.contractId)
                                this.search()
                            }
                        })
                    }else if(this.nodeSubmitType == 'edit'){
                        api.editContractNode(this.nodeForm).then(res => {
                            if(res.code == 200){
                                this.$Message.success('合同节点修改成功')
                                this.nodemodalVisible = false;
                                // this.modalVisible = false;
                                this.$refs.nodeForm.resetFields()
                                this.getNodeList(this.contractId)
                                this.search()
                            }
                        })
                    }
                }
            })
           
        },
        // 查询
        search(){
             // 获取项目列表数据
            api.getContractList(this.searchForm).then(res => {
                this.loading = false
                this.contractList = res.data.list
                this.total = res.data.total
            })
        },
        // 新增
        add( ){

        },
        // 删除
        remove(v) {
            this.$Modal.confirm({
                title: "确认删除",
                content: "您确认要删除该合同吗 " + " ?",
                loading: true,
                onOk: () => {
                api.deleteContract({id:v.id}).then(res => {
                    this.$Modal.remove();
                    if (res.code == 200) {
                    this.$Message.success("操作成功");
                    this.init();
                    }
                });
                }
            });
        },
        removeNode(v){
            this.$Modal.confirm({
                title: "确认删除",
                content: "您确认要删除该节点吗 " + " ?",
                loading: true,
                onOk: () => {
                api.deleteContractNode({id:v.id}).then(res => {
                    this.$Modal.remove();
                    if (res.code == 200) {
                    this.$Message.success("操作成功");
                    this.getNodeList(this.contractId);
                    }
                });
                }
            });
        },
        // 关闭弹窗
        handelCancel(){
            this.modalVisible = false
        },
        exportFile(){
            let params = JSON.parse(JSON.stringify(this.searchForm));
            params.pageNumber = 0;
            params.pageSize = 0;
             api.exportContractExcel(params,{responseType:'blob'}).then(res => {
                 let url = window.URL.createObjectURL(new Blob([res]));
                 let link = document.createElement('a');
                 link.style.display = 'none';
                 link.href = url;
                 let fileName = '合同数据'+createUUID().substring(0,8);
                 link.setAttribute('download',fileName+'.xls');
                 document.body.appendChild(link);
                 link.click();
                 document.body.removeChild(link)
             })
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
<style scoped>
.over-flow{
    display: inline-block;
    line-height: 25px;
    width: 100%;
    font-size: 14px;
    overflow: hidden;
    text-overflow: ellipsis;
}
</style>