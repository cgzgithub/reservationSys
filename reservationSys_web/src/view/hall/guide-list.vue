<template>
    <div class="container">
        <Row type="flex" justify="end">
            <el-button size="small" type="primary" @click="showModal">新增</el-button>
        </Row>
        <Row>
            <flexTable
            :colConfigs="colConfigs"
            :data="tableData"
            v-loading="loading"
            style="width:60%"
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
            <Modal
            :title="modalTitle"
            v-model="modalVisible"
            :closable="false"
            :mask-closable="false"
            :width="500"
            :styles="{top: '30px'}"
            >
            <Form
                ref="form"
                :model="form"
                :label-width="80"
                :rules="formValidate"
            >
                <FormItem
                label="讲解员"
                prop="name"
                >
                <Input v-model="form.name" />
                </FormItem>
                <FormItem
                label="电话"
                prop="phone"
                >
                <Input v-model="form.phone" />
                </FormItem>
            </Form>
            <div slot="footer">
                <Button
                type="default"
                @click="handelCancel"
                >关闭</Button>
                <Button
                type="primary"
                :loading="submitLoading"
                @click="handelSubmit"
                >确定</Button>
            </div>
        </Modal>
    </div>
</template>
<script>
import flexTable from '@/components/flex-table';
import Pagination from '@/components/pagination';
import meeting from '@/api/meeting';
import hall from '@/api/hall';
export default {
    name:'guide-list',
    data(){
        return{
            modalTitle:'新增',
            modalVisible:false,
            form:{
                name:'',
                phone:'',
                id:''
            },
            submitLoading:false,
            submitType:'',
            // 搜索参数
            searchForm:{
                pageNumber:1,
                pageSize:6
            },
            total:20,
            colConfigs:[ // 表格列
                {
                    label:'讲解员',
                    prop:'name',
                    sortable:true
                },
                {
                    label:'电话',
                    prop:'phone',
                    sortable:true
                },
                {
                    slot:'action'
                },
            ],
            tableData:[], //表格数据
            formValidate:{
                name:[{required:true,message:'不能为空',trigger:'blur'}],
                phone:[{pattern: /^1[3-9]\d{9}$/,message:'手机号格式不对！'}]
            },
            loading:false
        }
    },
    components:{
        flexTable,
        Pagination
    },
    mounted(){
        // 数据初始化
        this.init()
    },
    methods:{
        // 数据初始化
        init(){
            // 获取讲解员列表数据
            hall.getGuideList().then(res => {
                this.tableData = res.data;
            })
        },
        // 显示弹窗
        showModal(scope) {
            if(scope.row){
                this.submitType = 'edit';
                this.modalTitle = '编辑';
                this.form.name = scope.row.name;
                this.form.phone = scope.row.phone;
                this.form.id = scope.row.id;
            }else{
                this.submitType = 'add';
                this.modalTitle = '新增';
            }
            this.modalVisible = true
        },
        // 新增
        handelSubmit(){
            this.$refs.form.validate(valid => {
                if(valid){
                    if(this.submitType == 'add'){
                        hall.addGuide(this.form).then(res => {
                            if(res.code == 200){
                                this.$Message.success('添加成功')
                                this.modalVisible = false;
                                this.$refs.form.resetFields()
                                this.init()
                            }
                        })
                    }else{
                        hall.editGuide(this.form).then(res => {
                            if(res.code == 200){
                                this.$Message.success('修改成功')
                                this.modalVisible = false;
                                this.$refs.form.resetFields()
                                this.init()
                            }
                        })
                    }
                }
            })
        },
        // 删除
        remove(v) {
            this.$Modal.confirm({
                title: "确认删除",
                content: "您确认要删除 " + v.name + " ?",
                loading: true,
                onOk: () => {
                hall.deleteGuide({id:v.id}).then(res => {
                    this.$Modal.remove();
                    if (res.code == 200) {
                    this.$Message.success("操作成功");
                    this.init();
                    }
                });
                }
            });
        },
        // 关闭弹窗
        handelCancel(){
            this.modalVisible = false;
            this.$refs.form.resetFields()
        },
        changePage(v){
            this.searchForm.pageNumber = v
            this.init()
        },
        changePageSize(v){
            this.searchForm.pageSize = v;
            this.init()
        }
    }
}
</script>