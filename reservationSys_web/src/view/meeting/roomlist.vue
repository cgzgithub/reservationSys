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
                <el-table-column slot="action" fixed="right"  width="180" label="操作" show-overflow-tooltip>
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
                <FormItem label="会议室名称：" prop="name">
                    <Input v-model="form.name" />
                </FormItem>
                <FormItem label="会议室类型：" prop="type">
                    <Select v-model="form.type" @on-change="meetingTypeChange">
                       <Option 
                        v-for="item in meetingTypeList"
                        :key="item.id"
                        :label="item.title"
                        :value="item.id"
                        ></Option>
                    </Select>
                </FormItem>
                <FormItem label="会议室位置：" prop="position">
                    <Input v-model="form.position" />
                </FormItem>
                <FormItem style="display:inline-block;margin-right:25px" label="会议室面积：" prop="area">
                    <Input type="number" style="width:100px;margin-right:10px" v-model="form.area" />  ㎡
                </FormItem>
                <FormItem style="display:inline-block;margin-right:25px" label="最大座位数：" prop="seatNum">
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
                <FormItem label="附件："  v-if="isPerform && isEdit">
                    <Button type="success" @click="showAttachModal">添加</Button>
                    <flexTable
                    :colConfigs="enclosureColConfigs"
                    :data="form.enclosureList"
                    v-loading="loading"
                    >
                        <el-table-column slot="action" fixed="right"  width="200" label="操作" show-overflow-tooltip>
                            <template slot-scope="scope">
                                <el-button
                                type="success"
                                size="small"
                                @click="showAttachModal(scope)"
                                >编辑</el-button>
                                <el-button
                                type="danger"
                                size="small"
                                @click="removeAttch(scope.row)"
                                >删除</el-button>
                            </template>
                        </el-table-column>
                    </flexTable>
                    <!-- <div style="padding:30px 15px;border:1px solid #ccc;">
                        <div>
                            电脑：<InputNumber style="margin-right:30px;width:100px" :min="0"  v-model="form.computerNum" />
                            台卡：<InputNumber style="margin-right:30px;width:100px" :min="0"  v-model="form.tecaNum" />
                            投影：<InputNumber style="margin-right:30px;width:100px" :min="0"  v-model="form.projectionNum" />
                        </div>
                        <div style="margin-top:25px">
                        手持话筒：<InputNumber style="margin-right:20px;width:100px" :min="0"  v-model="form.handMicrophoneNum" />
                        鹅颈话筒：<InputNumber style="margin-right:30px;width:100px" :min="0"  v-model="form.gooseneckMicrophoneNum" />
                        </div>
                    </div> -->
                </FormItem>
                <FormItem v-if="isPerform && isEdit"  style="display:inline-block;margin-right:25px" label="摆放要求：" prop="putPicUrls">
                    <myUpload v-model="form.putPicUrls" :listType="'picture-card'" :storeType="'reservation'"></myUpload>
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
        <Modal
        :title="modelAttachTitle"
        v-model="modalAttachVisible"
        :mask-closable="false"
        :closable="true"
        :width="700"
        :styles="{top: '30px'}"
        >
        <Form
        ref="attchForm"
        :model="attchForm"
        :label-width="120"
        :rules="attachFormValidate"
        >
        <FormItem label="附件名称：" prop="name">
            <Input v-model="attchForm.name" />
        </FormItem>
        <FormItem label="数量：" prop="num">
            <InputNumber v-model="attchForm.num" />
        </FormItem>
        </Form>
        <div slot="footer">
                <Button
                type="text"
                @click="closeAttachModal"
                >取消</Button>
                <Button
                type="primary"
                @click="handleAttach"
                >确定</Button>
            </div>
        </Modal>
    </div>
</template>
<script>
import myUpload from '@/components/upload-list';
import flexTable from '@/components/flex-table';
import Pagination from '@/components/pagination';
import api from '@/api/meeting';
import dict from '@/api/dict';
export default {
    name:'roomlist',
    data(){
        return{
            modelAttachTitle:'新增附件',
            modalAttachVisible:false,
            modelTitle:'新增会议室',
            modalVisible:false,
            attchForm:{
                meetingId:'',
                name:'',
                num:1
            },
            form:{
                enclosureList:''
            },
            enclosureList:[],
            // 搜索参数
            searchForm:{
                pageNumber:1,
                pageSize:10,
            },
            amTimeList:["08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30","12:00"],
            pmTimeList:["13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30","18:00","18:30","19:00"],
            total:0,
            enclosureColConfigs:[
                {
                    label:'附件名称',
                    prop:'name'
                },
                {
                    label:'数量',
                    prop:'num'
                },
                {
                    slot:'action'
                }
            ],
            colConfigs:[ // 表格列
                {
                    label:'会议室名称',
                    prop:'name',
                    sortable:true,
                    width:220
                },
                {
                    label:'会议室类型',
                    prop:'meetingTypeName',
                    sortable:true,
                    width:150
                },
                {
                    label:'位置',
                    prop:'position',
                    sortable:true
                },
                {
                    label:'面积(㎡)',
                    prop:'area',
                    sortable:true
                },
                {
                    label:'最大座位数',
                    prop:'seatNum',
                    sortable:true
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
            rooms:[], // 会议室列表
            meetingTypeList:[],
            isPerform:true,
            isEdit :false,
            attchEdit:false,
            formValidate:{
                name:[{required:true,message:'不能为空',trigger:'blur'},{ max: 30, message: '长度不能超过30 个字符', trigger: 'blur' }],
                position:[{required:true,message:'不能为空',trigger:'blur'},{ max: 30, message: '长度不能超过30 个字符', trigger: 'blur' }],
                area:[{required:true,message:'不能为空'},{pattern:/^\d+(\.\d{0,2})?$/,message:'请输入最多保留两位小数的正数'}],
                type:[{required:true,message:'不能为空'}],
                amBeginTime:[{required:true,message:'不能为空',trigger:'change'}],
                pmBeginTime:[{required:true,message:'不能为空',trigger:'change'}],
                amEndTime:[{required:true,message:'不能为空',trigger:'change'}],
                pmEndTime:[{required:true,message:'不能为空',trigger:'change'}]
            },
            attachFormValidate:{},
            curMeeting:null
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
            this.search()
            dict.getDictInfoByType({type:'meetingType'}).then(res => {
                this.meetingTypeList = res.data
            })
        },
        closeVisitModal(){
            this.modalVisible = false
            this.$refs.form.resetFields()
        },
        closeAttachModal(){
            this.modalAttachVisible = false
        },
        showAttachModal(scope){
            this.attchForm.name = ''
            this.attchForm.num = 1
            if(scope.row){
                this.modelAttachTitle = '编辑附件'
                this.attchEdit  = true;
                this.attchForm = JSON.parse(JSON.stringify(scope.row));
            }else {
                this.attchEdit  = false;
            }
            this.modalAttachVisible = true;
        },
        showModal(scope) {
            this.form = {}
            if(scope.row){
                this.curMeeting = scope.row.id
                this.modelTitle = '编辑会议室'
                this.isEdit  = true;
                if(scope.row.type == 2){
                    this.isPerform = true
                }else{
                    this.isPerform = false
                }
                this.form = JSON.parse(JSON.stringify(scope.row));
                this.form.putPicUrls = this.form.trelation
            }else {
                this.isEdit  = false;
            }
            
            this.modalVisible = true
        },
        getAttchList(id){
            api.getEnclosuresByMeetingId({meetingId:id}).then(res => {
                this.form.enclosureList = res.data;
            })
        },
        // 查询
        search(){
            // 获取预约列表数据
            api.getAllRoomList(this.searchForm).then(res => {
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
                api.deleteRoom({id:v.id}).then(res => {
                    this.$Modal.remove();
                    if (res.code == 200) {
                    this.$Message.success("操作成功");
                    this.search()
                    }
                });
                }
            });
        },
        removeAttch(v){
            this.$Modal.confirm({
                title: "确认关闭",
                content: "您确认要删除 " + v.name + " ?",
                loading: true,
                onOk: () => {
                api.delEnclosure({id:v.id}).then(res => {
                    this.$Modal.remove();
                    if (res.code == 200) {
                    this.$Message.success("操作成功");
                    this.search();
                    this.getAttchList(this.curMeeting)
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
            if(this.isEdit){
                let arr =[]
                this.form.enclosureList.forEach(function(item){
                    arr.push(item.id)
                })
                this.form.enclosureIds = arr;
                delete this.form.enclosureList
            }
            this.$refs.form.validate(valid => {
                if(valid){
                    if(!this.isEdit ){
                        api.addRoom(this.form).then(res => {
                            if (res.code == 200) {
                                this.$Message.success("操作成功");
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
                }}
            )
        },
        
        handleAttach(){
            this.attchForm.meetingId = this.curMeeting;
            // 新增会议室
            if(!this.isEdit){
                this.form.enclosureList.push(this.attchForm)
            }
            if(!this.attchEdit ){
                api.addEnclosure(this.attchForm).then(res => {
                    if (res.code == 200) {
                        this.$Message.success("操作成功");
                        this.search();
                        this.getAttchList(this.curMeeting)
                    }
                    this.modalAttachVisible = false;
                })
            }else{
                api.editEnclosure(this.attchForm).then(res => {
                    if (res.code == 200) {
                        this.$Message.success("操作成功");
                        this.search();
                        this.getAttchList(this.curMeeting)
                    }
                    this.modalAttachVisible = false;
                })
            }
        },
        meetingTypeChange(v){
            if(v == 2){
                this.isPerform = true;
            }else{
                this.isPerform = false;
            }
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