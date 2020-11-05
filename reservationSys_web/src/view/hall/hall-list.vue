<template>
    <div class="container">
        <Row type="flex" justify="end">
            <el-button size="small" type="primary" @click="showModal">新增</el-button>
        </Row>
        <Row>
            <flexTable
            :colConfigs="colConfigs"
            :data="rooms"
            v-loading="loading"
            >
                <el-table-column slot="action" label="操作" show-overflow-tooltip width="200px">
                    <template slot-scope="scope">
                        <el-button
                        type="success"
                        size="small"
                        @click="showModal(scope)"
                        >编辑</el-button>
                        <el-button
                        v-if="scope.row.delFlag == 1"
                        type="default"
                        size="small"
                        @click="remove(scope.row)"
                        >关闭</el-button>
                        <el-button
                        v-if="scope.row.delFlag == 0"
                        type="default"
                        size="small"
                        @click="open(scope.row)"
                        >开放</el-button>
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
        :title="modelTitle"
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
                <FormItem label="展厅名称：" prop="name">
                    <Input v-model="form.name" />
                </FormItem>
                <FormItem label="展厅位置：" prop="position">
                    <Input v-model="form.position" />
                </FormItem>
                <FormItem style="display:inline-block;margin-right:25px" label="展厅面积：" prop="area">
                    <Input type="number" style="width:100px;margin-right:10px" v-model="form.area" />  ㎡
                </FormItem>
                <FormItem style="display:inline-block;margin-right:25px" label="接待人数上限：" prop="seatNum">
                    <InputNumber v-model="form.seatNum" />
                </FormItem>
                <FormItem label="开放时间：">
                    <div style="margin-bottom:20px">
                        <FormItem style="display:inline-block;width:200px;margin-right:25px" label="上午：" :label-width="80" prop="amBeginTime">
                            <Select style="width:100px;" v-model="form.amBeginTime">
                                <Option 
                                    v-for="(item,index) in amTimeList"
                                    :key="index"
                                    :label="item"
                                    :value="item"
                                    ></Option>
                             </Select>
                        </FormItem>
                        <FormItem style="display:inline-block;width:250px;" :label-width="80" label="到：" prop="amEndTime">
                            <Select style="width:100px;margin-right:20px" v-model="form.amEndTime">
                                <Option 
                                    v-for="(item,index) in amTimeList"
                                    :key="index"
                                    :label="item"
                                    :value="item"
                                    ></Option>
                             </Select>
                        </FormItem>
                    </div>
                    <div>
                        <FormItem style="display:inline-block;width:200px;margin-right:25px"  label="下午：" :label-width="80" prop="pmBeginTime">
                             <Select style="width:100px;margin-right:20px" v-model="form.pmBeginTime">
                                <Option 
                                    v-for="(item,index) in pmTimeList"
                                    :key="index"
                                    :label="item"
                                    :value="item"
                                    ></Option>
                             </Select>
                        </FormItem>
                        <FormItem style="display:inline-block;width:250px;" :label-width="80" label="到：" prop="pmEndTime">
                           <Select style="width:100px;margin-right:20px" v-model="form.pmEndTime">
                                <Option 
                                    v-for="(item,index) in pmTimeList"
                                    :key="index"
                                    :label="item"
                                    :value="item"
                                    ></Option>
                             </Select>
                        </FormItem>
                    </div>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button
                type="default"
                @click="closeVisitModal"
                >关闭</Button>
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
import api from '@/api/hall';
import dict from '@/api/dict';
export default {
    name:'hall-list',
    data(){
        return{
            modelTitle:'新增展厅',
            modalVisible:false,
            form:{},
            // 搜索参数
            searchForm:{
                flag:true,
                pageNumber:1,
                pageSize:10,
            },
            amTimeList:["08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30","12:00"],
            pmTimeList:["13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00"],
            total:0,
            colConfigs:[ // 表格列
                {
                    label:'展厅名称',
                    prop:'name',
                    sortable:true
                },
                {
                    label:'位置',
                    prop:'position',
                    sortable:true
                },
                {
                    label:'面积（㎡)',
                    prop:'area',
                    sortable:true
                },
                {
                    label:'接待人数上限',
                    prop:'seatNum',
                    sortable:true,
                    width:150
                },
                {
                    label:'开放时间',
                    prop:'openTime',
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
            rooms:[], // 展厅列表
            meetingTypeList:[],
            isPerform:true,
            isEdit :false,
            formValidate:{
                name:[  {required:true,message:'不能为空',trigger:'blur'},
                        { max: 30, message: '长度不能超过30 个字符', trigger: 'blur' }
                     ],
                position:[{required:true,message:'不能为空',trigger:'blur'},{ max: 30, message: '长度不能超过30 个字符', trigger: 'blur' }],
                area:[{required:true,message:'不能为空'},{pattern:/^\d+(\.\d{0,2})?$/,message:'请输入最多保留两位小数的正数'}],
                amBeginTime:[{required:true,message:'不能为空',trigger:'change'}],
                pmBeginTime:[{required:true,message:'不能为空',trigger:'change'}],
                amEndTime:[{required:true,message:'不能为空',trigger:'change'}],
                pmEndTime:[{required:true,message:'不能为空',trigger:'change'}]
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
            this.search()
            dict.getDictInfoByType({type:'meetingType'}).then(res => {
                this.meetingTypeList = res.data
            })
        },
        showModal(scope) {
            this.form = {}
            if(scope.row){
                this.isEdit  = true;
                this.modelTitle = '编辑展厅'
                this.form = JSON.parse(JSON.stringify(scope.row));
            }else {
                this.modelTitle = '新增展厅'
                this.isEdit  = false;
            }
            
            this.modalVisible = true
        },
        // 查询
        search(){
            // 获取预约列表数据
            api.getRoomList(this.searchForm).then(res => {
                this.rooms = res.data.list
                this.total = res.data.total
                this.rooms.forEach(function(item){
                    // item.areaCN = item.area + ' m2'
                    item.openTime = item.amBeginTime + '-' + item.pmEndTime
                    if(item.delFlag == 1){
                        item.statusCN = '开放'
                    }else if(item.delFlag == 0){
                        item.statusCN = '关闭'
                    }
                })
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
                content: "您确认要关闭 " + v.name + " ?",
                loading: true,
                onOk: () => {
                api.delRoom({id:v.id}).then(res => {
                    this.$Modal.remove();
                    if (res.code == 200) {
                    this.$Message.success("操作成功");
                    this.search()
                    }
                });
                }
            });
        },
        //开放
        open(v) {
            api.openRoom({id:v.id}).then(res => {
                // this.$Modal.openRoom();
                if (res.code == 200) {
                this.$Message.success("操作成功");
                this.search()
                }
            });  
        },
        handleAdd(){
            this.$refs.form.validate(valid => {
                if(valid){
                    if(!this.isEdit ){
                        api.addRoom(this.form).then(res => {
                            if (res.code == 200) {
                                this.$Message.success("添加成功");
                                this.search()
                            }
                            this.modalVisible = false;
                            this.$refs.form.resetFields()
                        })
                    }else{
                        api.editRoom(this.form).then(res => {
                            if (res.code == 200) {
                                this.$Message.success("操作成功");
                                this.search()
                            }
                            this.modalVisible = false;
                            this.$refs.form.resetFields()
                        })
                    }
                }
            })
        },
        meetingTypeChange(v){
            // if(v == 4){
            //     this.isPerform = true;
            // }else{
            //     this.isPerform = false;
            // }
        },
        closeVisitModal(){
            this.modalVisible = false
            this.$refs.form.resetFields()
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