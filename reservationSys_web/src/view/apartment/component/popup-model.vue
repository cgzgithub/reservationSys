<template>
    <div class="container">
        <Modal
        :title="modelTitle"
        v-model="modelShow"
        :mask-closable="false"
        :closable="false"
        :width="900"
        :styles="{top: '30px'}"
        >
            <Form 
            ref="form"                 
            :model="form"
            :label-width="120"
            :inline="true"
            :show-message="canEdit"
            :rules="formValidate"
            >
                <FormItem style="width:300px" label="单位名称：" prop="companyOriginId">
                    <Select v-model="form.companyOriginId"  v-if="canEdit" placeholder="单位名称" filterable  clearable>
                        <Option
                        v-for="item in companyList"
                        :key="item.id"
                        :label="item.companyName"
                        :value="item.id"
                        ></Option>
                    </Select>
                    <!-- <el-autocomplete
                    v-if="canEdit"
                    class="inline-input"
                    v-model="form.companyName"
                    :fetch-suggestions="querySearchCN"
                    size="small"
                    @select="handleSelectCN"
                    ></el-autocomplete> -->
                    <!-- <Input v-if="canEdit" v-model="form.companyOriginId" /> -->
                    <span v-else>
                        {{form.companyName}}
                    </span>
                </FormItem>
                <FormItem style="width:300px" label="个人姓名：" prop="name">
                    <Input v-if="canEdit" v-model="form.name" />
                    <span v-else>
                        {{form.name}}
                    </span>
                </FormItem>
                <FormItem style="width:300px" label="国籍：" prop="nationality">
                    <Input v-if="canEdit" v-model="form.nationality" />
                    <span v-else>
                        {{form.nationality}}
                    </span>
                </FormItem>
                <FormItem style="width:400px" label="证件号：" prop="idNumber">
                    <Input v-if="canEdit" v-model="form.idNumber" />
                    <span v-else>
                        {{form.idNumber}}
                    </span>
                </FormItem>
                <FormItem style="width:300px" label="手机号：" prop="phone">
                    <Input v-if="canEdit" v-model="form.phone " />
                    <span v-else>
                        {{form.phone}}
                    </span>
                </FormItem>
                <FormItem  style="width:300px" label="租房类型：" prop="houseType">
                    <Select v-if="canEdit" style="width:150px"  v-model="form.houseType" clearable placeholder="租房类型">
                        <Option
                        v-for="item in houseTypeList"
                        :key="item.id"
                        :label="item.title"
                        :value="item.id"
                        ></Option>
                    </Select>
                    <!-- <Input v-if="canEdit" v-model="form.houseType" /> -->
                    <span v-else>
                        {{form.houseTypeName}}
                    </span>
                </FormItem>
                <FormItem style="width:850px" label="资料上传：" prop="files">
                    <div class="file-main">
                        <div class="file-item">
                            <div class="item-left">
                                1.企业委托书
                            </div>
                            <div class="item-upload" v-if="canEdit">
                                <myUpload  v-model="form.trustInstrumentUrl"></myUpload>
                            </div>
                            <div class="item-upload"  v-else>
                                <div v-for="(item,index) in form.trustInstrumentUrl" :key="index" >
                                <a class="over-flow" :title="item.fileName" target="_blank" download="file" :href="item.downloadUrl">{{item.fileName}}</a>
                                </div>
                            </div>
                        </div>
                        <div class="file-item">
                            <div class="item-left">
                                2.企业营业执照或事业企业法人登记证 
                            </div>
                            <div v-if="canEdit" class="item-upload" >
                                <myUpload  v-model="form.businessLicenseUrl"></myUpload>
                            </div>
                            <!-- <myUpload v-if="canEdit" v-model="form.businessLicenseUrl">
                            </myUpload> -->
                            <div class="item-upload"  v-else>
                                <div v-for="(item,index) in form.businessLicenseUrl" :key="index" >
                                <a class="over-flow" :title="item.fileName" target="_blank" download="file" :href="item.downloadUrl">{{item.fileName}}</a>
                                </div>
                            </div>
                        </div>
                        <div class="file-item">
                            <div class="item-left">
                                3.企业承诺书 
                            </div>
                            <div v-if="canEdit" class="item-upload" >
                                <myUpload  v-model="form.pledgeUrl"></myUpload>
                            </div>
                            <!-- <myUpload v-if="canEdit" v-model="form.pledgeUrl">
                            </myUpload> -->
                            <div class="item-upload"  v-else>
                                <div v-for="(item,index) in form.pledgeUrl" :key="index" >
                                <a class="over-flow" :title="item.fileName" target="_blank" download="file" :href="item.downloadUrl">{{item.fileName}}</a>
                                </div>
                            </div>
                        </div>
                        <div class="file-item">
                            <div class="item-left">
                                4.经办人身份证、入住人身份证、法人身份证
                            </div>
                            <div v-if="canEdit" class="item-upload" >
                                <myUpload  v-model="form.agentIdCardUrl"></myUpload>
                            </div>
                            <!-- <myUpload v-if="canEdit" v-model="form.agentIdCardUrl">
                            </myUpload> -->
                            <div class="item-upload"  v-else>
                                <div v-for="(item,index) in form.agentIdCardUrl" :key="index" >
                                <a class="over-flow" :title="item.fileName" target="_blank" download="file" :href="item.downloadUrl">{{item.fileName}}</a>
                                </div>
                            </div>
                        </div>
                        <div class="file-item">
                            <div class="item-left">
                                5.《上海湾区科创中心人才公寓申请表》一式四份
                            </div>
                            <div v-if="canEdit" class="item-upload" >
                                <myUpload  v-model="form.applicationUrl"></myUpload>
                            </div>
                            <!-- <myUpload v-if="canEdit" v-model="form.applicationUrl">
                            </myUpload> -->
                            <div class="item-upload"  v-else>
                                <div v-for="(item,index) in form.applicationUrl" :key="index" >
                                <a class="over-flow" :title="item.fileName" target="_blank" download="file" :href="item.downloadUrl">{{item.fileName}}</a>
                                </div>
                            </div>
                        </div>
                        <div class="file-item">
                            <div class="item-left">
                                6.申请人身份证、户口薄或户籍证明  
                            </div>
                            <div v-if="canEdit" class="item-upload" >
                                <myUpload  v-model="form.applicantIdCardUrl"></myUpload>
                            </div>
                            <!-- <myUpload v-if="canEdit" v-model="form.applicantIdCardUrl">
                            </myUpload> -->
                            <div class="item-upload"  v-else>
                                <div v-for="(item,index) in form.applicantIdCardUrl" :key="index" >
                                <a class="over-flow" :title="item.fileName" target="_blank" download="file" :href="item.downloadUrl">{{item.fileName}}</a>
                                </div>
                            </div>
                        </div>
                        <div class="file-item">
                            <div class="item-left">
                                7.劳动（聘用）合同（受理之日起至少有一年的有效期） 
                            </div>
                            <div v-if="canEdit" class="item-upload" >
                                <myUpload  v-model="form.laborContractUrl"></myUpload>
                            </div>
                            <!-- <myUpload v-if="canEdit" v-model="form.laborContractUrl">
                            </myUpload> -->
                            <div class="item-upload"  v-else>
                                <div v-for="(item,index) in form.laborContractUrl" :key="index" >
                                <a class="over-flow" :title="item.fileName" target="_blank" download="file" :href="item.downloadUrl">{{item.fileName}}</a>
                                </div>
                            </div>
                        </div>
                        <div class="file-item">
                            <div class="item-left">
                                8.学历、学位、专业技术职称或职业技能等级证书 
                            </div>
                            <div v-if="canEdit" class="item-upload" >
                                <myUpload  v-model="form.certificateUrl"></myUpload>
                            </div>
                            <!-- <myUpload v-if="canEdit" v-model="form.certificateUrl">
                            </myUpload> -->
                            <div class="item-upload"  v-else>
                                <div v-for="(item,index) in form.certificateUrl" :key="index" >
                                <a class="over-flow" :title="item.fileName" target="_blank" download="file" :href="item.downloadUrl">{{item.fileName}}</a>
                                </div>
                            </div>
                        </div>
                        <div class="file-item">
                            <div class="item-left">
                                9.其他材料（如社保记录等） 
                            </div>
                            <div v-if="canEdit"  class="item-upload" >
                                <myUpload  v-model="form.otherMaterialsUrl"></myUpload>
                            </div>
                            <!-- <myUpload v-if="canEdit" v-model="form.otherMaterialsUrl">
                            </myUpload> -->
                            <div class="item-upload"  v-else>
                                <div v-for="(item,index) in form.otherMaterialsUrl" :key="index" >
                                <a class="over-flow" :title="item.fileName" target="_blank" download="file" :href="item.downloadUrl">{{item.fileName}}</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </FormItem>
                <FormItem style="width:850px" label="备注：" prop="description">
                    <Input type="textarea" v-model="form.description" />
                </FormItem>
            </Form>
            <div v-if="!canEdit">
                <historyNode :flowList="form.apartmentReviewList"></historyNode>
            </div>
            <div slot="footer">
                <Button
                type="default"
                @click="closeModal"
                >关闭</Button>
                <Button
                v-if="canEdit"
                type="primary"
                @click="handleSubmit"
                >确定</Button>
                <Button
                v-if="isReview"
                type="primary"
                @click="handleApprove"
                >通过</Button>
                <Button
                v-if="isReview"
                type="primary"
                @click="handleNotApprove"
                >不通过</Button>
            </div>
        </Modal>
        <!-- 取消预约弹窗 -->
        <Modal
        v-model="showApproveModal"
        :mask-closable="false"
        :closable="true"
        :width="500"
        :styles="{top: '30px'}"
        >
            <Form ref="cancelForm" :model="notApproveForm" :rules="notApproveFormValidate">
                <FormItem label="审核意见" prop="notApproveReason">
                    <Input type="textarea"  v-model="notApproveForm.notApproveReason" />
                </FormItem>
            </Form>
            <div slot="footer">
                <Button
                type="text"
                @click="closeApproveModal"
                >取消</Button>
                <Button
                type="primary"
                @click="handleSubmitNotApprove"
                >确定</Button>
            </div>
        </Modal>
    </div>
</template>
<script>
import historyNode from './historyNode';
import myUpload from '@/components/upload-list';
import api from '@/api/hall';
import company from '@/api/company';
import dict from '@/api/dict';
import apartment from '@/api/apartment';
export default {
    name:'popup-model',
    props:{
        form:{
            type:Object
        },
        canEdit:{
            type:Boolean,
            default:false
        },
        isReview:{
            type:Boolean,
            default:false
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
            approveType:'',
            showApproveModal:false,
            notApproveReason:'',
            notApproveForm:{},
            houseTypeList:[],
            companyList:[],
            // modelShow,
            noValidate:{},
            formValidate:{
                // 表单验证规则
                companyOriginId: [{ required: true, message: "不能为空"}],
                name: [{ required: true, message: "不能为空"}],
                nationality: [{ required: true, message: "不能为空"}],
                idNumber: [{ required: true, message: "不能为空"},
                {
                    pattern: /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/,
                    message: '证件号码格式有误！',
                    trigger: 'blur'
                }],
                phone: [{ required: true, message: "不能为空"},
                {pattern: /^1[3-9]\d{9}$/,message:'手机号格式不对！'}],
                houseType: [{ required: true, message: "不能为空"}],
                // trustInstrumentUrl: [{ required: true, message: "  "}]
            },
            notApproveFormValidate:{
                notApproveReason:[{ required: true, message: "不能为空" }],
            }
        }
    },
    components:{
        myUpload,
        historyNode
    },
    mounted(){
       this.init()
    },
    methods:{
        init(){
            dict.getDictInfoByType({type:'apartmentType'}).then(res => {
                this.houseTypeList = res.data
            }),
            apartment.getCompany({pageNumber:0,pageSize:0}).then(res => {
                this.companyList = res.data.list
            })
        },
        downLoad(url){
            window.location.href = url.downLoadUrl
        },
        // querySearchCN(queryString, cb) {
        //     var companyListCN = this.companyList;
        //     companyListCN.forEach(function(item){
        //         item.value = item.companyName
        //     })
        //     var results = queryString ? companyListCN.filter(this.createFilter(queryString)) : companyListCN;
        //     // 调用 callback 返回建议列表的数据
        //     cb(results);
        // },
        // createFilter(queryString) {
        //     return (item) => {
        //     return (item.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        //     };
        // },
        // handleSelectCN(item) {
        //     // console.log(item)
        //     this.form.companyOriginId = item.id
        // },
        // 关闭弹窗
        closeModal(){
            this.$emit('closeModal')
            this.$Modal.remove()
            this.$refs.form.resetFields()
        },
        fileCheck(file,name){
            if(!file || file.length == 0){
                this.$Message.error('资料不齐全，请上传'+ name)
                return false
            }else{
                return true
            }
        },
        // 提交
        handleSubmit(){
            
            this.$refs.form.validate(valid => {
                if(valid){
                    // if(!this.fileCheck(this.form.trustInstrumentUrl,'企业委托书')){
                    //     return
                    // }
                    // if(!this.fileCheck(this.form.businessLicenseUrl,'企业营业执照或事业企业法人登记证')){
                    //     return
                    // }
                    // if(!this.fileCheck(this.form.pledgeUrl,'企业承诺书')){
                    //     return
                    // }
                    // if(!this.fileCheck(this.form.agentIdCardUrl,'经办人身份证、入住人身份证、法人身份证')){
                    //     return
                    // }
                    // if(!this.fileCheck(this.form.applicationUrl,'《上海湾区科创中心人才公寓申请表》一式四份')){
                    //     return
                    // }
                    // if(!this.fileCheck(this.form.applicantIdCardUrl,'申请人身份证、户口薄或户籍证明')){
                    //     return
                    // }
                    // if(!this.fileCheck(this.form.laborContractUrl,'劳动（聘用）合同（受理之日起至少有一年的有效期）')){
                    //     return
                    // }
                    // if(!this.fileCheck(this.form.certificateUrl,'学历、学位、专业技术职称或职业技能等级证书')){
                    //     return
                    // }
                    // if(!this.fileCheck(this.form.otherMaterialsUrl,'其他材料（如社保记录等）')){
                    //     return
                    // }
                    this.$emit('handleSubmit',this.form);
                }
            })
            
        },
        // 不通过弹窗
        handleNotApprove(){
            this.approveType = 'notApprove'
            this.showApproveModal = true;
        },
        // 通过
        handleApprove(){
            this.approveType = 'approve'
            this.showApproveModal = true;
        },
        // 关闭不通过原因弹窗
        closeApproveModal(){
            this.showApproveModal = false;
        },
        // 不通过
        handleSubmitNotApprove(){
            this.$refs.cancelForm.validate(valid => {
                if(valid){
                    if(this.approveType == 'approve'){
                        this.$emit('handleApprove',this.notApproveForm.notApproveReason)
                    }else{
                        this.$emit('notApprove',this.notApproveForm.notApproveReason)
                    }
                    this.showApproveModal = false;
                }
            })
            
        },
        // 更新
        handleUpdate(){
            this.$refs.form.validate(valid => {
                if(valid){
                    this.$emit('handleUpdate',this.form)
                }
            })
            
        }
    }
}
</script>
<style lang="scss" scoped>
div{
    box-sizing: border-box;
}
.file-main{
    border:1px solid rgba(212, 210, 210, 0.815);
    border-bottom:none;
}
.file-item{
    box-sizing:border-box;
    display:flex;
    padding: 15px 0px;
    border-bottom:1px solid rgba(212, 210, 210, 0.815);
    .item-left{
        padding-left: 20px;
        width:45%;
        margin-right:20px;
        display: flex;
    }
    .item-upload{
        width:50%;
    }
}
.over-flow{
    display: inline-block;
    line-height: 25px;
    width: 100%;
    font-size: 14px;
    overflow: hidden;
    text-overflow: ellipsis;
}
</style>