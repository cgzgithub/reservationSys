<template>
    <div class="container">
        <Row>
            <el-form :ref="searchForm" :model="searchForm" :inline="true">
                <el-form-item>
                    <el-input size="small" placeholder="手机号" v-model="searchForm.accountName" clearable></el-input>
                </el-form-item>
                <el-form-item>
                    <el-input size="small" placeholder="姓名" v-model="searchForm.name" clearable></el-input>
                </el-form-item>
                <el-form-item>
                <el-button size="small" type="primary" @click="search">搜索</el-button>
                <el-button size="small" type="primary" @click="showModal">新增</el-button>
                </el-form-item>
            </el-form>
        </Row>
        <Row>
            <flexTable
            :colConfigs="colConfigs"
            :data="accountList"
            v-loading="loading"
            style="width:95%;"
            >
                <el-table-column slot="action" label="操作" show-overflow-tooltip width="200px">
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
          style="margin-top:20px;width:95%;"
        >
        <Page
            :current="searchForm.pageNum"
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
        :title="modelTitle"
        v-model="modalVisible"
        :mask-closable="false"
        :closable="true"
        :width="700"
        :styles="{top: '30px'}"
        >
            <Form
                ref="form"
                :model="form"
                :label-width="120"
                :rules="formValidate"
            >
                <FormItem label="手机号：" prop="accountName">
                    <Input v-model="form.accountName" />
                </FormItem>
                <FormItem v-if="!isEdit" label="密码：" prop="password">
                    <Input type="password" v-model="form.password" />
                </FormItem>
                <FormItem label="姓名：" prop="name">
                    <Input v-model="form.name" />
                </FormItem>
                <FormItem label="账号角色：" prop="roleIdList">
                    <Select v-model="form.roleIdList" multiple>
                        <Option v-for="(item,index) in roleList" :key="index" :value="item.id" :label="item.roleName"></Option>
                    </Select>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button
                type="text"
                @click="closeVisitModal"
                >取消</Button>
                <Button
                type="primary"
                @click="handleAdd"
                >确定</Button>
            </div>
        </Modal>
    </div>
</template>
<script>
import flexTable from '@/components/flex-table';
import Pagination from '@/components/pagination';
import api from '@/api/account';
import role from '@/api/role';
import dict from '@/api/dict';
export default {
    name:'account-list',
    data(){
        return{
            modelTitle:'新增账号',
            modalVisible:false,
            form:{},
            // 搜索参数
            searchForm:{
                pageNum:1,
                pageSize:10,
                account:'',
                accountName:''
            },
            total:0,
            colConfigs:[ // 表格列
                {
                    label:'手机号',
                    prop:'account',
                    sortable:true
                },
                {
                    label:'姓名',
                    prop:'name',
                    sortable:true
                },
                {
                    label:'角色',
                    prop:'roleCN'
                },
                {
                    slot:'action'
                },
            ],
            accountList:[],
            roleList:[],
            loading:false,
            isEdit :false,
            formValidate:{
                accountName:[{required:true,message:'不能为空',trigger:'blur'}],
                name:[{required:true,message:'不能为空',trigger:'blur'}],
                password:[{required:true,message:'不能为空',trigger:'blur'}]
            }
        }
    },
    components:{
        flexTable,
        Pagination
    },
    mounted(){
        this.init()
    },
    methods:{
        // 数据初始化
        init(){
            this.search();
            this.getRoleList()
        },
        getRoleList(){
            role.getRoleList().then(res => {
                this.roleList = res.data.list
            })
        },
        showModal(scope) {
            this.form = {}
            if(scope.row){
                this.modelTitle = '编辑账号'
                this.isEdit  = true;
                this.form = JSON.parse(JSON.stringify(scope.row));
                let arr = []
                this.form.roleIdList = this.form.roles;
                this.form.roles.forEach(function(item){
                    arr.push(item.id)
                })
                this.form.roleIdList = arr
                this.form.password = '******';
                this.form.accountName = this.form.account;
            }else {
                this.modelTitle = '新增账号'
                this.isEdit  = false;
            }
            
            this.modalVisible = true
        },
        // 查询
        search(){
            // 获取预约列表数据
            api.getAccountList(this.searchForm).then(res => {
                this.accountList = res.data.list
                this.total = res.data.total
                this.accountList.forEach(function(item){
                    let arr = []
                    if(item.roles){
                        for(let i of item.roles){
                            arr.push(i.roleName)
                            item.roleCN = arr.join(', ')
                        }
                    }
                });
            });
        },
        // 新增
        add( ){
            this.modalVisible = true
        },
        // 删除
        remove(v) {
            this.$Modal.confirm({
                title: "确认关闭",
                content: "您确认要删除" + v.name + " ?",
                loading: true,
                onOk: () => {
                api.delAccount({id:v.id}).then(res => {
                    this.$Modal.remove();
                    if (res.code == 200) {
                    this.$Message.success("操作成功");
                    this.search()
                    }
                });
                }
            });
        },
        handleAdd(){
            this.$refs.form.validate(valid => {
                if(valid){
                    if(!this.isEdit ){
                        api.openAccount(this.form).then(res => {
                            if (res.code == 200) {
                                this.$Message.success("创建成功");
                                this.search()
                            }
                            this.modalVisible = false;
                        })
                    }else{
                        if(this.form.password === '******'){
                            this.form.password = ''
                        };
                        api.editAccount(this.form).then(res => {
                            if (res.code == 200) {
                                this.$Message.success("操作成功");
                                this.search()
                            }
                            this.modalVisible = false;
                        })
                    }
                }
            })
            

        },
        closeVisitModal(){
            this.modalVisible = false
        },
        changePage(v){
            this.searchForm.pageNum = v;
            this.search()
        },
        changePageSize(v){
            this.searchForm.pageSize = v;
            this.search()
        }
    }
}
</script>