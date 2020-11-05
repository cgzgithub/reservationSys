<template>
    <div class="container">
      <el-row>
            <el-form :inline="true" ref="searchForm" :model="searchForm">
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
                   <el-select style="width:200px" size="small" v-model="searchForm.houseType" clearable placeholder="户型">
                        <el-option
                        v-for="item in houseTypeList"
                        :key="item.id"
                        :label="item.title"
                        :value="item.id"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-input style="width:200px" size="small" v-model="searchForm.residentialAreaName" clearable placeholder="小区名称" />
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
            :data="apartments"
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
        :closable="false"
        :width="700"
        :styles="{top: '30px'}"
        >
            <Form
                ref="form"
                :model="form"
                :label-width="120"
                :rules="formValidate"
            >
                <FormItem  label="编号：" prop="number">
                    <Input v-model="form.number" />
                </FormItem>
                <FormItem label="小区名称：" prop="residentialAreaName">
                    <Input v-model="form.residentialAreaName" />
                </FormItem>
                <FormItem style="display:inline-block;width:240px" label="房间地址：" prop="addressRidgepole">
                    <Input style="width:80px;margin-right:10px" v-model="form.addressRidgepole" /><span>栋</span>
                </FormItem>
                <FormItem style="display:inline-block;width:120px" :label-width="10" prop="addressNumber">
                    <Input style="width:80px;margin-right:10px" v-model="form.addressNumber" />号
                </FormItem>
                <FormItem style="display:inline-block;width:220px" :label-width="10" prop="addressRoom">
                    <Input  style="width:80px;margin-right:10px" v-model="form.addressRoom" />房间号
                </FormItem>
                <FormItem style="display:inline-block;width:440px" label="面积：" prop="area">
                    <Input type="number" style="width:180px;margin-right:10px" v-model="form.area" />m2
                </FormItem>
                <FormItem label="户型：" prop="houseType">
                    <Select v-model="form.houseType" >
                       <Option 
                        v-for="item in houseTypeList"
                        :key="item.title"
                        :label="item.title"
                        :value="item.id"
                        ></Option>
                    </Select>
                </FormItem>
                <FormItem label="状态：" prop="status">
                    <Select v-model="form.status" >
                       <Option 
                        v-for="item in statusOptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                        ></Option>
                    </Select>
                </FormItem>
                <FormItem label="照片：" prop="putPicUrls">
                    <myUpload :listType="'picture-card'" v-model="form.putPicUrls"></myUpload>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button
                type="default"
                @click="closeVisitModal"
                >关闭</Button>
                <Button
                type="primary"
                @click="handleSubmit"
                >保存</Button>
            </div>
        </Modal>
    </div>
</template>
<script>
import myUpload from '@/components/upload-list'
import flexTable from '@/components/flex-table';
import Pagination from '@/components/pagination';
import api from '@/api/apartment';
import dict from '@/api/dict';
export default {
    name:'apartment-list', 
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
                    sortable:true,
                    width:220
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
                },
            ],
            loading:false,
            apartments:[],//公寓列表
            houseTypeList:[],//户型列表
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
            formValidate:{
                number:[{required:true,message:'不能为空', trigger: "blur"}],
                addressRidgepole:[{required:true,message:'不能为空', trigger: "blur"}],
                addressNumber:[{required:true,message:'不能为空', trigger: "blur"}],
                addressRoom:[{required:true,message:'不能为空', trigger: "blur"}],
                residentialAreaName:[{required:true, message:'不能为空', trigger: "blur"}],
                area:[{required:true,message:'不能为空'},{pattern:/^\d+(\.\d{0,2})?$/,message:'请输入最多保留两位小数的正数'}],
                houseType:[{required:true,message:'不能为空'}],
                status:[{required:true,message:'不能为空'}],
            }
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
    methods:{
        // 数据初始化
        init(){
            // 获取公寓列表数据
            this.search()
            dict.getDictInfoByType({type:'apartmentType'}).then(res => {
                this.houseTypeList = res.data
            })
        },
        
        closeVisitModal(){
            this.modalVisible = false;
            this.$refs.form.resetFields()
            this.form.putPicUrls = []
        },
        // 显示弹窗,
        showModal(scope) {
            if(scope.row){
                this.submitType = 'edit';
                this.modalTitle = '编辑公寓';
                this.form = JSON.parse(JSON.stringify(scope.row));
                // let urlArr = []
                // if(this.form.putPicUrls){
                //     this.form.putPicUrls.forEach(function(item){
                //         let obj = {
                //             id:item
                //         }
                //         urlArr.push(obj)
                //     })
                // }
                // this.form.putPicUrls = urlArr
                this.form.putPicUrls = JSON.parse(JSON.stringify(this.form.trelation))
                
            }else{
                this.form = {}
                this.submitType = 'add';
                this.modalTitle = '新增公寓';
            }
            this.modalVisible = true
        },
        // 新增
        handleSubmit(){
            this.$refs.form.validate(valid => {
                if(valid){
                    if(this.submitType == 'add'){
                        api.addApartment(this.form).then(res => {
                            if(res.code == 200){
                                this.$Message.success('添加成功')
                                this.modalVisible = false;
                                this.$refs.form.resetFields()
                                this.form.putPicUrls = []
                                this.search()
                            }
                        })
                    }else{
                        api.editApartment(this.form).then(res => {
                            if(res.code == 200){
                                this.$Message.success('修改成功')
                                this.modalVisible = false;
                                this.$refs.form.resetFields()
                                this.form.putPicUrls = []
                                this.search()
                            }
                        })
                    }
                }
            })
        },
        // 查询
        search(){
            // 获取公寓列表数据
            api.getApartmentByCondition(this.searchForm).then(res => {
                this.apartments = res.data.list
                this.total = res.data.total
                for (let item of this.apartments) {
                    if(item.status == 0) {
                        item.statusCN = '待出租'
                    } else if(item.status == 1) {
                        item.statusCN = '已出租'
                    }
                    item.fullAddress = item.addressRidgepole+'栋'+item.addressNumber+'号'+item.addressRoom;
                }
            })
        },
        // 新增
        add( ){

        },
        // 删除
        remove(v) {
            this.$Modal.confirm({
                title: "确认删除",
                content: "您确认要删除该房间吗 " + " ?",
                loading: true,
                onOk: () => {
                api.delApartment({id:v.id}).then(res => {
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