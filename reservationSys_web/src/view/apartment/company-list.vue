<template>
    <div class="container">
      <el-row>
            <el-form :inline="true" ref="searchForm" :model="searchForm">
                <el-form-item>
                    <el-input style="width:200px" size="small" v-model="searchForm.companyName" clearable placeholder="单位名称" />
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
            :data="companys"
            v-loading="loading"
            >
                <el-table-column slot="action" label="操作" show-overflow-tooltip min-width="200px">
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
                <FormItem  label="企业识别编码：" prop="creditRecognitionId">
                    <Input v-model="form.creditRecognitionId" />
                </FormItem>
                <FormItem label="单位名称：" prop="companyName">
                    <Input v-model="form.companyName" />
                </FormItem>
                <FormItem label="公寓名额：" prop="totalNumber">
                    <InputNumber :min="0" style="width:200px;margin-right:20px" v-model="form.totalNumber" /> 套
                </FormItem>
            </Form>
            <div slot="footer">
                <Button
                type="text"
                @click="closeVisitModal"
                >取消</Button>
                <Button
                type="primary"
                @click="handleSubmit"
                >保存</Button>
            </div>
        </Modal>
    </div>
</template>
<script>
import flexTable from '@/components/flex-table';
import Pagination from '@/components/pagination';
import api from '@/api/apartment';
export default {
    name:'company-list',
    data(){
        return{
            modalTitle:'新增',
            modalVisible:false,
            form:{},
            // 搜索参数
            searchForm:{
                pageNumber:1,
                pageSize:10,
            },
            total:0,
            colConfigs:[ // 表格列
                {
                    label:'企业识别编码',
                    prop:'creditRecognitionId',
                    sortable:true,
                    width:180
                },
                {
                    label:'单位名称',
                    prop:'companyName',
                    sortable:true,
                    width:220
                },
                {
                    label:'公寓名额（套）',
                    prop:'totalNumber',
                    sortable:true,
                    width:140
                },
                {
                    label:'申请中名额（套）',
                    prop:'count1',
                    sortable:true,
                    width:140
                },
                {
                    label:'公寓剩余名额（套）',
                    prop:'remainder',
                    sortable:true,
                    width:140
                },
                {
                    slot:'action'
                },
            ],
            loading:false,
            companys:[],//公寓列表
            formValidate:{
                creditRecognitionId:[{ required: true, message: "不能为空", trigger: "blur"}],
                companyName:[{ required: true, message: "不能为空", trigger: "blur"}],
                totalNumber:[{ required: true, message: "不能为空"}]
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
            // 获取单位列表数据
            api.getCompany(this.searchForm).then(res => {
                this.companys = res.data.list
                this.total = res.data.total
            })
        },
        // 查询
        search(){
            // 获取单位列表数据
            api.getCompany(this.searchForm).then(res => {
                this.companys = res.data.list
                this.total = res.data.total
            })
        },
        closeVisitModal(){
            this.modalVisible = false
        },
          // 显示弹窗
        showModal(scope) {
            if(scope.row){
                this.submitType = 'edit';
                this.modalTitle = '编辑';
                this.form = JSON.parse(JSON.stringify(scope.row))
            }else{
                this.form={}
                this.submitType = 'add';
                this.modalTitle = '新增';
            }
            this.modalVisible = true
        },
        // 新增
        handleSubmit(){
            this.$refs.form.validate(valid => {
                if(valid){
                    if(this.submitType == 'add'){
                        api.addCompany(this.form).then(res => {
                            if(res.code == 200){
                                this.$Message.success('添加成功')
                                this.modalVisible = false;
                                this.search()
                            }
                        })
                    }else{
                        api.editCompany(this.form).then(res => {
                            if(res.code == 200){
                                this.$Message.success('修改成功')
                                this.modalVisible = false;
                                this.search()
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
                content: "您确认要删除"+v.companyName + "吗 ?",
                loading: true,
                onOk: () => {
                api.delCompany({id:v.id}).then(res => {
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