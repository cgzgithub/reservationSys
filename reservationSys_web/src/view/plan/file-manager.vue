<template>
    <div class="container">
      <el-row>
            <el-form :inline="true" ref="searchForm" :model="searchForm">
                <!-- <el-form-item>
                    <el-select style="width:150px" size="small" v-model="searchForm.projectType" placeholder="项目类型" clearable>
                        <el-option v-for="(item,index) in projectTypeList" :key="index" :label="item.title" :value="item.id"></el-option>
                    </el-select>
                </el-form-item> -->
                <el-form-item>
                    <el-select size="small" style="width:150px" v-model="searchForm.projectId" clearable placeholder="选择项目">
                        <el-option v-for="(item,index) in projectList" :key="index" :label="item.name" :value="item.id"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                   <el-cascader
                        style="width:150px"
                        clearable
                        placeholder="选择阶段"
                        size="small"
                        :props="{ value: 'id', label: 'nodeName',children:'childList',checkStrictly:true}"
                        :options="stageList"
                        @change="stageOptionChange"
                    >
                    </el-cascader>
                </el-form-item>
                <el-form-item>
                    <el-input style="width:150px" size="small" v-model="searchForm.createUser" clearable placeholder="上传人"/>
                </el-form-item>
                  <el-form-item>
                    <el-input style="width:150px" size="small" v-model="searchForm.updateUser" clearable placeholder="最后修改人"/>
                </el-form-item>
                <el-form-item>
                    <el-button size="small" type="success" @click="search">查询</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button size="small" type="primary" @click="showModal">新增</el-button>
                </el-form-item>
            </el-form>
        </el-row>
        <Row>
            <flexTable
            :colConfigs="colConfigs"
            :data="fileList"
            v-loading="loading"
            >
            <el-table-column slot="files" label="资料名称" show-overflow-tooltip>
                <template slot-scope="scope">
                    <div v-for="(item,index) in scope.row.url" :key="index">
                        <a target="_blank" download="file" :href="item.downloadUrl">{{item.fileName}}</a>
                    </div>
                </template>
            </el-table-column>
                <el-table-column slot="action" label="操作" fixed="right" width="250px" show-overflow-tooltip min-width="200px">
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
        <el-dialog
        @close="closeModal"
        :title="modalTitle"
        :visible="modalVisible"
        width="700"
        >
        <div>
            <el-form ref="form"  :model="form" label-width="120px" :rules="formValidate">
                <el-form-item  style="min-width:200px;" label="项目名称：" prop="projectId">
                    <el-select size="small" v-if="popType == 'add' && listType == 'withoutQuery'" v-model="form.projectId">
                        <el-option v-for="(item,index) in projectList" :key="index" :label="item.name" :value="item.id"></el-option>
                    </el-select>
                    <span v-else>{{form.projectName}}</span>
                </el-form-item>
                <el-form-item style="min-width:300px;" label="项目阶段：" prop="stageId">
                    <el-cascader
                        clearable
                        placeholder="请选择阶段"
                        size="small"
                        v-model="form.stageId"
                        :props="{ value: 'id', label: 'nodeName',children:'childList',checkStrictly:true}"
                        :options="stageOptionList"
                        @change="stageChange"
                    >
                    </el-cascader>
                </el-form-item>
                <el-form-item :error="errorMsg" style="min-width:200px;" label="资料上传：" prop="url">
                    <myUpload v-model="form.url" :storeType="'project'"></myUpload>
                </el-form-item>
            </el-form>
        </div>
            <div slot="footer">
                <el-button
                size="small"
                type="default"
                @click="closeModal"
                >关闭</el-button>
                <el-button
                size="small"
                v-if="popEdit" 
                type="primary"
                @click="handleSubmit"
                >确定</el-button>
            </div>

        </el-dialog>
    </div>
</template>
<script>
import formatDate from '@/filters/format-date';
import myUpload from '@/components/upload-list';
import flexTable from '@/components/flex-table';
import Pagination from '@/components/pagination';
import project from '@/api/project';
export default {
    name:'file-manager', 
    data(){
        return{
            projectList:[],
            stageList:[],
            stageOptionList:[],
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
            popEdit:true,
            popType:'add',
            projectId:'',
            projectType:'',
            projectName:'',
            projectNum:'',
            modalTitle:'新增',
            modalVisible:false,
            form:{
                projectType:'',
                projectId:''
            },
            // 搜索参数
            searchForm:{
                projectId:'',
                projectType:'',
                stageId:"",
                createUser:'',
                updateUser:'',
                stageNodeId:"",
                pageNumber:1,
                pageSize:10,
            },
            total:0,
            fileList:[],
            colConfigs:[ // 表格列
                {
                    label:'项目名称',
                    prop:'projectName'
                },
                {
                    label:'项目阶段',
                    prop:'stageName'
                },
                {
                    slot:'files'
                },
                {
                    label:'上传人',
                    prop:'createUser'
                },
                {
                    label:'上传时间',
                    prop:'createTime'
                },
                {
                    label:'最后修改人',
                    prop:'updateUser'
                },
                {
                    label:'修改时间',
                    prop:'updateTime'
                },
                {
                    slot:'action'
                },
            ],
            loading:true,
            formValidate:{
                projectId:[{required:true,message:'不能为空'}],
                stageId:[{ required: true, message: '不能为空',trigger:'blur'}]
            },
            errorMsg:'',
            listType:''
        }
    },
    components:{
        myUpload,
        flexTable,
        Pagination
    },
    mounted(){
        this.init()
    },
    methods:{
        // 数据初始化
        init(){
            if(this.$route.query.projectItem){
                this.listType = 'withQuery';
                let projectItem = JSON.parse(this.$route.query.projectItem);
                this.projectType = projectItem.projectType;
                this.projectName = projectItem.projectName;
                // this.projectNum = projectItem.projectNum;
                project.getAllList({pageNumber:0,pageSize:0,projectType:this.projectType}).then(res => {
                    this.projectList = res.data.list;
                })
                this.form.projectId = projectItem.projectId;
                this.projectId = projectItem.projectId;
                this.searchForm.projectId = projectItem.projectId;
                this.searchForm.projectType = projectItem.projectType;
            }else if(this.$route.query.projectType){
                this.listType = 'withoutQuery';
                let projectType = JSON.parse(this.$route.query.projectType);
                this.projectType = projectType;
                project.getAllList({pageNumber:0,pageSize:0,projectType:this.projectType}).then(res => {
                    this.projectList = res.data.list;
                })
                this.searchForm.projectType = projectType
            }
            
            // 获取项目阶段节点字典
            project.getStageNodeDictList({projectType:this.projectType}).then(res => {
                this.stageList = res.data;
                this.stageOptionList = res.data;
            })
            // 获取项目列表数据
            this.search()
        },
        stageChange(v){
            if(v){
                if(v.length == 1){
                this.form.stageId = v[0]
                this.form.stageNodeId=''
            }else if(v.length == 2){
                this.form.stageId = v[0]
                this.form.stageNodeId = v[1]
            }
            }
            
        },
        stageOptionChange(v){
            if(v.length == 1){
                this.searchForm.stageId = v[0]
                this.searchForm.stageNodeId=''
            }else if(v.length == 2){
                this.searchForm.stageId = v[0]
                this.searchForm.stageNodeId = v[1]
            }
        },
        closeModal(){
            this.modalVisible = false
            this.$refs.form.resetFields()
        },
        // 显示弹窗
        showModal(scope) {
            this.popEdit = true;
            if(scope.row){
                this.popType = 'edit';
                this.submitType = 'edit';
                this.modalTitle = '编辑';
                this.form= JSON.parse(JSON.stringify(scope.row));
                // this.form.projectName = this.projectName
                let arr = [this.form.stageId]
                if(this.form.stageNodeId){
                    arr.push(this.form.stageNodeId)
                }
                this.form.stageId = arr
            }else{
                this.popType = 'add';
                this.form = {};
                if(this.listType == 'withQuery'){
                    this.form.projectType = this.projectType;
                    this.form.projectName = this.projectName;
                    // this.form.projectNum = this.projectNum;
                    this.form.projectId = this.projectId;
                }
                this.submitType = 'add';
                this.modalTitle = '新增';
            }
            this.modalVisible = true
        },
        // 新增
        handleSubmit(){
            if(this.listType == 'withQuery'){
                this.form.projectId = this.projectId;
            }
            this.$refs.form.validate(valid => {
                if(valid){
                    if(!this.form.url){
                        this.errorMsg = "请上传文件";
                        return;
                    } else if(this.form.url.length<1){
                        this.errorMsg = "请上传文件";
                        return;
                    }else{
                        this.errorMsg = "";
                    }
                    if(this.submitType == 'add'){
                        project.addArchiving(this.form).then(res => {
                            if(res.code == 200){
                                this.$Message.success('添加成功')
                                this.modalVisible = false;
                                this.$refs.form.resetFields()
                                this.search()
                            }
                        })
                    }else{
                        if(this.form.stageId instanceof Array){
                            let arr = JSON.parse(JSON.stringify(this.form.stageId))
                            if(arr.length == 1){
                                this.form.stageId = arr[0]
                                this.form.stageNodeId=''
                            }else if(arr.length == 2){
                                
                                this.form.stageId = arr[0]
                                this.form.stageNodeId = arr[1]
                            }
                        }
                        project.editArchiving(this.form).then(res => {
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
        // 查询
        search(){
             // 获取项目列表数据
            project.getArchivingList(this.searchForm).then(res => {
                this.loading = false
                this.fileList = res.data.list
                this.total = res.data.total
            })
        },
        // 删除
        remove(v) {
            this.$Modal.confirm({
                title: "确认删除",
                content: "您确认要删除该数据吗 " + " ?",
                loading: true,
                onOk: () => {
                project.delArchiving({id:v.id}).then(res => {
                    this.$Modal.remove();
                    if (res.code == 200) {
                    this.$Message.success("操作成功");
                    this.search();
                    }
                });
                }
            });
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