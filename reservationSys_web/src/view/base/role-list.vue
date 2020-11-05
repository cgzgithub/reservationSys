<template>
    <div class="container">
        <div style="display:flex;align-items:center;margin-bottom:20px;">
            <div style="margin-right:30px;font-size:15px;font-weight:bold">
                    角色列表
            </div>
            <el-button size="mini" type="primary" @click="showModal">新增角色</el-button>
        </div>
        <Row>
            <flexTable
            :colConfigs="colConfigs"
            :data="roleList"
            v-loading="loading"
            style="width:75%"
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
          style="margin-top:20px;width:75%"
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
                <FormItem label="角色名称：" prop="roleName">
                    <Input v-model="form.roleName" />
                </FormItem>
                <FormItem label="角色描述：" prop="roleDesc">
                    <Input v-model="form.roleDesc" />
                </FormItem>
                <Row style="padding-left:50px;">
                <div style="display:flex;align-items:center;margin-bottom:20px;">
                    <div style="margin-right:30px;">
                        菜单分配
                    </div>
                    <div v-if="showCheckbox">
                        <Button size="small" type="primary" @click="selectAll" style="margin-right:5px">全选</Button>
                    </div>
                </div>
                <el-tree
                ref="tree"
                :node-key="nodeKey"
                :data="menuList"
                :props="treeProps"
                :show-checkbox="showCheckbox"
                @check-change="handleCheckChange"
                ></el-tree>
            </Row>
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
import api from '@/api/role';
import menu from '@/api/menu';
import dict from '@/api/dict';
export default {
    name:'role-list',
    data(){
        return{
            modelTitle:'新增角色',
            modalVisible:false,
            form:{
                menuIds:[]
            },
            // 搜索参数
            searchForm:{
                pageNum:1,
                pageSize:10,
            },
            total:0,
            colConfigs:[ // 表格列
                {
                    label:'角色名称',
                    prop:'roleName',
                    sortable:true
                },
                {
                    label:'角色描述',
                    prop:'roleDesc',
                    sortable:true
                },
                {
                    label:'最后修改时间',
                    prop:'updateTime',
                    sortable:true,
                    width:200
                },
                {
                    slot:'action'
                },
            ],
            loading:false,
            roleList:[], // 角色列表
            isEdit :false,
            formValidate:{
                roleName:[{required:true,message:'不能为空',trigger:'blur'}]
            },
            menuList:[],
            nodeKey:'id',
            treeProps:{id:'id',label:'text',children:'subMenu'},
            showCheckbox:false,
            currentRoleId:''
        }
    },
    components:{
        flexTable,
        Pagination
    },
    mounted(){
        this.init()
    },
    computed: {
        treeDataKeys() {
        const { menuList, nodeKey } = this;
        const result = [];

        function traverse(children) {
            if (children && children.length) {
            children.forEach(item => {
                result.push(item[nodeKey]);
                if (item.subMenu) {
                traverse(item.subMenu);
                }
            });
            }
        }

        traverse(menuList);

        return result;
        }
    },
    methods:{
        // 数据初始化
        init(){
            this.search();
            this.getAllMenu()
        },
        showModal(scope) {
            this.form = {}
            this.showCheckbox = true;
            if(scope.row){
                this.modelTitle = '编辑角色'
                this.getCurrentMenu(JSON.parse(JSON.stringify(scope.row.menuIds)));
                this.isEdit  = true;
                this.form = JSON.parse(JSON.stringify(scope.row));
            }else {
                this.modelTitle = '新增角色'
                this.getCurrentMenu();
                this.isEdit  = false;
            }
            
            this.modalVisible = true
        },
        // 查询
        search(){
            // 获取预约列表数据
            api.getRoleList(this.searchForm).then(res => {
                this.roleList = res.data.list
                this.total = res.data.total
            });
        },
        // 获取所有菜单
        getAllMenu(){
            menu.getAllMenu().then(res => {
                this.menuList = res.data;
            })
        },
        selectAll(){
            const { treeDataKeys } = this;
            let checkedKeys = this.$refs.tree.getCheckedKeys();

            if (checkedKeys.length === treeDataKeys.length) {
                this.$refs.tree.setCheckedKeys([]);
            } else {
                this.$refs.tree.setCheckedKeys(treeDataKeys);
            }
        },
        // 新增
        add(){
            this.modalVisible = true
        },
        // 删除
        remove(v) {
            this.$Modal.confirm({
                title: "确认删除",
                content: "您确认要删除 " + v.roleName + " ?",
                loading: true,
                onOk: () => {
                api.delRole({id:v.id}).then(res => {
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
            let checkedKeys = this.$refs.tree.getCheckedKeys();
            checkedKeys=checkedKeys.concat(this.$refs.tree.getHalfCheckedKeys())
            this.form.menuIds = checkedKeys
            this.$refs.form.validate(valid => {
                if(valid){
                    if(!this.isEdit ){
                        api.addRole(this.form).then(res => {
                            if (res.code == 200) {
                                this.$Message.success("添加成功");
                                this.search()
                            }
                            this.modalVisible = false;
                        })
                    }else{
                        api.editRole(this.form).then(res => {
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
        getCurrentMenu(arr){
                let filterArr = []
                const firstIds = this.menuList.map(item => {
                    if(item.subMenu && item.subMenu.length > 0){
                        return item.id
                    }
                })
                if(arr){
                    filterArr = arr.filter(item => firstIds.indexOf(item) === -1)
                }
                this.$refs.tree.setCheckedKeys(filterArr || []);
        },
        // 选中角色
        handleRowClick(v){
            this.currentRoleId= v.id;
            this.showCheckbox = true;
            this.getCurrentMenu(v.id);
        },
        // 树形控件改变事件
        handleCheckChange(v){

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