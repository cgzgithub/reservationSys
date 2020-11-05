<template>
    <div class="container">
        <Modal
        :title="modelTitle"
        v-model="modelShow"
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
            <div  v-if="roomType == 'hall'">
                <FormItem  label="展厅名称：" prop="showroomName">
                    <span>{{form.showroomName}}</span>
                </FormItem>
            </div>
            <FormItem style="display:inline-block;margin-right:25px" label="参观日期：" prop="visitDate">
                <span>{{form.visitDate  | formatDate}}</span>
            </FormItem>
            <FormItem style="display:inline-block;margin-right:25px" label="开始时间：" prop="beginTime">
                <span>{{form.beginTime}}</span>
            </FormItem>
            <FormItem style="display:inline-block" label="结束时间：" prop="endTime">
                <span>{{form.endTime}}</span>
            </FormItem>
            <div class="meeting" v-if="roomType == 'meeting' || roomType == 'perform'">
                <FormItem  label="会议主题：" prop="name">
                <Input :readonly="viewType == 'visitDetail'" style="width:500px" v-model="form.name" />
                </FormItem>
                <FormItem  label="会议室名称：" prop="meetingRoomName">
                    <span>{{form.meetingRoomName || form.meetingName}}</span>
                </FormItem>
            </div>
            <FormItem label="参会单位名称：" prop="companyName">
                <Input style="width:500px;margin-bottom:10px" v-for="(item,index) in form.visitCompanyList" :key="index" :readonly="viewType == 'visitDetail'" v-model="item.name" @on-click="delCompany(index)" icon="ios-close" />
                <Button type="primary" @click="addCompany">新增</Button>
            </FormItem>
            <!-- <FormItem   label="单位名称（中文）" prop="companyName">
                <el-autocomplete
                :readonly="viewType == 'visitDetail'"
                style="width:500px"
                size="small"
                class="inline-input"
                v-model="form.companyName"
                :fetch-suggestions="querySearchCN"
                placeholder="中英文名不能同时为空"
                @select="handleSelectCN"
                ></el-autocomplete>
            </FormItem> -->
            <!-- <FormItem  label="单位名称（英文）" prop="engCompanyName">
                <el-autocomplete
                :readonly="viewType == 'visitDetail'"
                style="width:500px"
                size="small"
                class="inline-input"
                v-model="form.engCompanyName"
                :fetch-suggestions="querySearchEN"
                placeholder="中英文名不能同时为空"
                @select="handleSelectEN"
                ></el-autocomplete>
            </FormItem> -->
            <FormItem label="来访人数：" prop="bookingNumber">
                <InputNumber :readonly="viewType == 'visitDetail'" :min="0" v-model="form.bookingNumber" />
            </FormItem>
            <!-- <FormItem  label="单位类型：" prop="companyTypeId">
                <Select :readonly="viewType == 'visitDetail'" style="width:500px"  v-model="form.companyTypeId">
                    <Option 
                    v-for="item in companyTypeList"
                    :key="item.id"
                    :label="item.title"
                    :value="item.id"
                    ></Option>
                </Select>
            </FormItem> -->
            <FormItem  label="预约人：" prop="bookingPersion">
                <Input :readonly="viewType == 'visitDetail'" style="width:500px" v-model="form.bookingPersion" />
            </FormItem>
            <FormItem  label="手机号：" prop="bookingPhone">
                <Input :readonly="viewType == 'visitDetail'" style="width:500px" v-model="form.bookingPhone" />
            </FormItem>
            <FormItem v-if="roomType == 'hall'" label="讲解员：" prop="guideId">
                <Select :readonly="viewType == 'visitDetail'" style="width:500px" v-model="form.guideId">
                    <Option 
                    v-for="item in guideList"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                    ></Option>
                </Select>
            </FormItem>
            <div class="cancelForm" v-if="viewType == 'cancelDetail'">
                <FormItem  label="取消通道：" prop="cancelChannel">
                    <span>{{form.cancelChannel == 0 ? '小程序':'电脑端'}}</span>
                </FormItem>
                <FormItem label="取消原因：" prop="cancelReason">
                    <Select style="width:500px" v-model="form.cancelReasonId">
                    <Option 
                        v-for="item in cancelReasonList"
                        :key="item.id"
                        :label="item.title"
                        :value="item.id"
                    ></Option>
                    </Select>
                </FormItem>
                <FormItem  label="取消时间：" prop="cancelTime">
                    <span>{{form.cancelTime}}</span>
                </FormItem>
            </div>
            <div class="visitedForm" v-if="viewType == 'visitDetail'">
                <FormItem label="实到人数：" prop="actualNum">
                    <InputNumber :min="1" v-model="form.actualNum" />
                </FormItem>
                <FormItem  label="评价：" prop="appraise">
                    <Rate v-model="form.appraise" />
                </FormItem>
                <FormItem label="来访照片：" prop="trelationList">
                    <div>(最多传三张)</div>
                    <myUpload v-model="form.trelationList" :listType="'picture-card'" :storeType="'reservation'">
                    </myUpload>
                </FormItem>
            </div>
            <div class="">
                <FormItem v-if="roomType == 'perform'" label="路演厅摆放要求：" prop="requirements" >
                    <RadioGroup v-if="form.trelation && form.trelation.length > 0" v-model="form.putRequirementPicId" @on-change="requireChange">
                        <Radio :checked="form.putRequirementPicId === item.id" :label="item.fileUrl" :value="item.fileUrl" v-for="(item,index) in form.trelation" :key="index" ><img width="60" height="60" :src="item.downloadUrl" /></Radio>
                    </RadioGroup>
                    <img v-if="form.putDownloadUrl" width="60" height="60" :src="form.putDownloadUrl" />
                </FormItem>
                <FormItem v-if="roomType == 'perform'" label="路演厅附件：" prop="enclosureList">
                    <div v-if="form.enclosures">
                        {{form.enclosures}}
                    </div>
                    <div v-if="form.enclosureList" style="padding:30px 15px;border:1px solid #ccc;display:flex;flex-wrap:wrap;">
                        <div v-for="(item,index) in form.enclosureList" :key="index" style="display:flex;align-items:center;margin-bottom:20px">
                            <span>{{item.name}}：</span>
                            <InputNumber style="margin-right:30px;width:100px;" :min="0" :max="item.numMax" v-model="item.num" />
                        </div>
                    </div>
                </FormItem>
            </div>
        </Form>
        <div slot="footer" style="display:flex;justify-content:center">
            <!-- 新增预约 -->
            <Button
            v-if="viewType == 'add'"
            type="primary"
            @click="handleAdd"
            >提交</Button>
            <!-- 预约中详情--会议开始十分钟前--可取消 -->
            <div style="margin-right:7px" v-if="viewType == 'cancelable'">
                <Button
                type="primary"
                :loading="submitLoading"
                @click="showCancel"
                >取消预约</Button>
                <Button
                type="primary"
                @click="showVisit"
                >到访</Button>
                <Button
                type="primary"
                @click="handleUpdate"
                >更新</Button>
            </div>
            <!-- 预约中详情--会议开始十分钟内--不可取消 -->
            <div style="margin-right:7px" v-if="viewType == 'unCancelable'">
                <Button
                type="primary"
                @click="handleNotVisit"
                >未到访</Button>
                <Button
                type="primary"
                @click="showVisit"
                >到访</Button>
            </div>
            <!-- 到访详情 -->
                <Button
                v-if="viewType == 'visitDetail'"
                type="primary"
                @click="handleUpdateVisit"
                >到访更新</Button>
            <!-- 未到访详情 -->
                <Button
                v-if="viewType == 'notVisitDetail'"
                type="primary"
                @click="showVisit"
                >到访</Button>
            <Button
            type="default"
            @click="handleClose"
            >关闭</Button>
        </div>
        </Modal>
        <!-- 取消预约弹窗 -->
        <Modal
        v-model="cancelShow"
        :mask-closable="false"
        :closable="true"
        :width="500"
        :styles="{top: '30px'}"
        >
            <Form ref="cancelForm" :model="cancelForm" :rules="cancelFormValidate">
                <FormItem label="取消原因" prop="cancelReason">
                    <Select v-model="cancelForm.cancelReason">
                    <Option 
                        v-for="item in cancelReasonList"
                        :key="item.id"
                        :label="item.title"
                        :value="item.id"
                    ></Option>
                    </Select>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button
                type="text"
                @click="closeCancelModal"
                >取消</Button>
                <Button
                type="primary"
                @click="handleCancel"
                >确定</Button>
            </div>
        </Modal>
        <!-- 到访弹窗 -->
        <Modal
        v-model="visitShow"
        :mask-closable="false"
        :closable="true"
        :width="500"
        :styles="{top: '30px'}"
        >
            <Form ref="visitForm" :model="visitForm" :rules="visitFormValidate">
                <FormItem label="实到人数" prop="actualNum">
                    <InputNumber :min="0" v-model="visitForm.actualNum" />
                </FormItem>
                <FormItem label="评价" prop="appraise">
                    <Rate v-model="visitForm.appraise" />
                </FormItem>
                <FormItem label="来访照片：" prop="imgUrlList">
                    <div>(最多传三张)</div>
                    <myUpload v-model="visitForm.imgUrlList" :listType="'picture-card'" :storeType="'reservation'">
                    </myUpload>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button
                type="text"
                @click="closeVisitModal"
                >取消</Button>
                <Button
                type="primary"
                @click="handleVisit"
                >确定</Button>
            </div>
        </Modal>
    </div>
</template>
<script>
import myUpload from '@/components/upload-list';
import api from '@/api/hall';
import company from '@/api/company';
import dict from '@/api/dict';
export default {
    name:'model',
    props:{
        form:{
            type:Object
        },
        roomType:{
            type:String
        },
        viewType:{
            type:String
        },
        modelTitle:{
            type:String
        },
        modelShow:{
            type:Boolean,
            default:true
        }
    },
    data(){
        return{
            // modelShow,
            visitCompanyList:[{name:'联合服务'},{name:'百度'}],
            submitLoading:false,
            cancelShow:false,
            cancelForm:{
                cancelReason:''
            },
            visitShow:false,
            visitForm:{
                actualNum:1,
                appraise:0,
                imgUrlList:[]
            },
            formValidate:{
                // 表单验证规则
                name: [{ required: true, message: "不能为空", trigger: "blur" }],
                // companyName: [{ required: true, message: "不能为空", trigger: "blur" }],
                // engCompanyName: [{ required: true, message: "不能为空", trigger: "blur"}],
                // bookingNumber: [{ required: true, message: "不能为空", trigger: "blur"}],
                bookingPersion: [{ required: true, message: "不能为空", trigger: "blur"}],
                bookingPhone: [{ required: true, message: "不能为空", trigger: "blur"},{pattern: /^1[3-9]\d{9}$/,message:'请输入正确的手机号！'}],
                // companyTypeId: [{ required: true, message: "不能为空"}],
                // guideId: [{ required: true, message: "不能为空"}],
                // modelKey: [{ required: true, message: "不能为空", trigger: "blur" }]
            },
            cancelFormValidate:{
                cancelReason:[{ required: true, message: "不能为空"}]
            },
            visitFormValidate:{
                actualNum:[{ required: true, message: "不能为空"}],
                imgUrlList:[{type: 'array', max: 3, message: '最多上传三张'}]
            },
            guideList:[],
            cancelReasonList:[],
            companyTypeList:[],
            companyList:[],
            companyListCN:[],
            companyListEN:[],
            defaultGuide:''
        }
    },
    components:{
        myUpload
    },
    mounted(){
       this.init()
    },
    methods:{
        addCompany(){
            this.form.visitCompanyList.push({name:''})
        },
        delCompany(v){
            if(this.form.visitCompanyList.length > 1){
                this.form.visitCompanyList.splice(v,1)
            }
        },
        init(){
            api.getGuideList().then(res =>{
                this.guideList = res.data;
            });
            api.getDefaultGuide().then(res => {
                this.defaultGuide = res.data;
                this.form.guideId = this.defaultGuide;
            })
            // company.getCompanyList({isEnglish:0}).then(res => {
            //     this.companyListCN = res.data
            // })
            // company.getCompanyList({isEnglish:1}).then(res => {
            //     this.companyListEN = res.data
            // })
            dict.getDictInfoByType({type:'companyType'}).then(res => {
                this.companyTypeList = res.data
            })
            dict.getDictInfoByType({type:'cancelReason'}).then(res => {
                this.cancelReasonList = res.data
            })
        },
        querySearchCN(queryString, cb) {
            var companyListCN = this.companyListCN;
            companyListCN.forEach(function(item){
                item.value = item.companyName
            })
            var results = queryString ? companyListCN.filter(this.createFilter(queryString)) : companyListCN;
            // 调用 callback 返回建议列表的数据
            cb(results);
        },
        querySearchEN(queryString, cb) {
            var companyListEN = this.companyListEN;
            companyListEN.forEach(function(item){
                item.value = item.companyName
            })
            var results = queryString ? companyListEN.filter(this.createFilter(queryString)) : companyListEN;
            // 调用 callback 返回建议列表的数据
            cb(results);
        },
        createFilter(queryString) {
            return (item) => {
                return (item.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
            };
        },
        handleSelectCN(item) {
            
        },
        handleSelectEN(item) {
            
        },
        requireChange(v){
            
            this.form.putRequirementPicId = v;
        },
        // 新增
        handleAdd(){
            this.$set(this.form,'passageway',1)
            let that = this;
            // this.$set(this.form,'beginTime',this.form.startTime)
            // delete this.form.startTime;
            if(that.form.visitCompanyList.length < 1){
                that.$Message.error('请填写单位名称')
                return
            }else if(that.form.visitCompanyList.length == 1){
                if(!that.form.visitCompanyList[0].name || that.form.visitCompanyList[0].name == ''){
                    that.$Message.error('请填写单位名称');
                    return;
                }
            }else{
                that.form.visitCompanyList = that.form.visitCompanyList.filter(function(item){
                    return  item.name && item.name !== ''
                })
            }
            if(this.roomType == 'perform'){
                delete this.form.trelation;

            }
            if(this.roomType == 'meeting' || this.roomType == 'perform'){
                // delete this.form.meetingRoomName
            }
            if(!this.form.bookingNumber){
                this.$set(this.form,'bookingNumber',1)
            }
            this.$refs.form.validate(validate => {
                if(validate){
                    this.$emit('handleAdd',this.form)
                }
            })
            
        },
        // 关闭弹窗
        handleClose(){
            this.$emit('handleClose')
            // this.$refs.form.resetFields()
        },
        // 取消原因弹窗
        showCancel(){
            this.cancelShow = true;
        },
         // 关闭取消原因弹窗
        closeCancelModal(){
            this.cancelShow = false;
        },
        // 取消预约
        handleCancel(){
            this.$refs.cancelForm.validate(validate => {
                if(validate){
                    this.$emit('handleCancel',this.cancelForm.cancelReason)
                    this.cancelShow = false;
                }
            })
            
        },
        // 到访弹窗
        showVisit(){
            this.visitShow = true;
        },
         // 关闭到访弹窗
        closeVisitModal(){
            this.visitShow = false;
        },
        // 到访
        handleVisit(){
            this.$refs.visitForm.validate(valid => {
                if(valid){
                    this.$emit('handleVisit',this.visitForm)
                    this.visitShow = false;
                }
            })
        },
        // 未到访
        handleNotVisit(){
            this.$emit('handleNotVisit')
        },
        // 更新
        handleUpdate(){
            if(!this.form.bookingNumber){
                this.$set(this.form,'bookingNumber',1)
            }
            let that = this;
            if(that.form.visitCompanyList.length < 1){
                that.$Message.error('请填写单位名称')
                return
            } else if(that.form.visitCompanyList.length == 1){
                if(!that.form.visitCompanyList[0].name || that.form.visitCompanyList[0].name == ''){
                    that.$Message.error('请填写单位名称');
                    return;
                }
            }else{
                that.form.visitCompanyList.forEach(function(item,index){
                    if(!item.name  || item.name==""){
                        that.form.visitCompanyList.splice(index,1)
                    }
                })
            }
            let params = {
                bookingNumber:this.form.bookingNumber,
                bookingPersion:this.form.bookingPersion,
                bookingPhone:this.form.bookingPhone,
                visitCompanyList:this.form.visitCompanyList,
                companyTypeId:this.form.companyTypeId,
                guideId:this.form.guideId,
                id:this.form.id,
                showroomId:this.form.showroomId,
                meetingId:this.form.meetingId,
            }
            if(this.roomType == 'hall'){
                delete params.meetingId
            }
            if(this.roomType == 'meeting' || this.roomType == 'perform'){
                delete params.showroomId
                delete params.guideId
            }
            this.$refs.form.validate(validate => {
                if(validate){
                    this.$emit('handleUpdate',params)
                }
            })
        },
        // 到访更新
        handleUpdateVisit(){
            let form = {
                id:this.form.id,
                actualNum:this.form.actualNum,
                appraise:this.form.appraise,
                imgUrlList:this.form.trelationList
            }
            if(this.form.trelationList.length>3){
                this.$Message.error('来访照片最多传三张')
                return
            }
            this.$emit('handleUpdateVisit',form)
        },


    }
}
</script>
<style lang="scss" scoped>

</style>