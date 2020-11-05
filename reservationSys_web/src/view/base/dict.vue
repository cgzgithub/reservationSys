<template>
  <div class="search">
    <Card>
      <Row type="flex" justify="space-between" class="code-row-bg">
        <Col v-if="expand" span="5">
          <Row class="operation">
            <Dropdown @on-click="handleDropdown">
              <Button>更多操作
                <Icon type="md-arrow-dropdown"/>
              </Button>
              <DropdownMenu slot="list">
                <DropdownItem name="editDcit">编辑字典</DropdownItem>
                <DropdownItem name="refreshDcit">刷新</DropdownItem>
              </DropdownMenu>
            </Dropdown>
          </Row>
          <Alert show-icon>
            当前选择：
            <span class="select-title">{{editTitle}}</span>
            <a class="select-clear" v-if="editTitle" @click="cancelEdit">取消选择</a>
          </Alert>
          <Input
            v-model="searchKey"
            suffix="ios-search"
            @on-change="search"
            placeholder="输入搜索字典"
            clearable
          />
          <div class="tree-bar" :style="{maxHeight: maxHeight}">
            <Tree ref="tree" :data="treeData" @on-select-change="selectTree"></Tree>
          </div>
          <Spin size="large" fix v-if="treeLoading"></Spin>
        </Col>
        <div class="expand">
          <Icon :type="expandIcon" size="16" class="icon" @click="changeExpand"/>
        </div>
        <Col :span="span">
          <Row>
            <Form ref="searchForm" :model="searchForm" inline :label-width="60" class="search-form">
              <Form-item label="数据名称" prop="title">
                <Input
                  type="text"
                  v-model="searchForm.title"
                  placeholder="请输入"
                  clearable
                  style="width: 200px"
                />
              </Form-item>
              <Form-item style="margin-left:-35px">
                <Button @click="handleSearch" type="primary" icon="ios-search">搜索</Button>
                <Button @click="handleReset" type="default" style="margin-left:10px">重置</Button>
              </Form-item>
            </Form>
          </Row>
          <Row class="operation">
            <Button @click="add" type="primary" icon="md-add">添加数据</Button>
            <Button @click="getDataList" icon="md-refresh" style="margin-left:10px">刷新数据</Button>
          </Row>
          <Row>
            <Table
              :loading="loading"
              border
              :columns="columns"
              :data="data"
              sortable="custom"
              @on-sort-change="changeSort"
              @on-selection-change="showSelect"
              ref="table"
            ></Table>
          </Row>
          <Row type="flex" justify="end" class="page">
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
        </Col>
      </Row>
    </Card>

    <Modal
      :title="dictModalTitle"
      v-model="dictModalVisible"
      :mask-closable="false"
      :width="500"
      :styles="{top: '30px'}"
    >
      <Form ref="dictForm" :model="dictForm" :label-width="75" :rules="dictFormValidate">
        <FormItem label="字典名称" prop="title">
          <Input v-model="dictForm.title"/>
        </FormItem>
        <FormItem label="备注" prop="description">
          <Input v-model="dictForm.description"/>
        </FormItem>
        <FormItem label="排序值" prop="sortOrder">
          <InputNumber :max="1000" :min="0" v-model="dictForm.sortOrder"></InputNumber>
          <span style="margin-left:5px">值越小越靠前，支持小数</span>
        </FormItem>
      </Form>
      <div slot="footer">
        <Button type="text" @click="dictModalVisible=false">取消</Button>
        <Button type="primary" :loading="submitLoading" @click="handelSubmitDict">提交</Button>
      </div>
    </Modal>

    <Modal
      :title="modalTitle"
      v-model="modalVisible"
      :mask-closable="false"
      :width="500"
      :styles="{top: '30px'}"
    >
      <Form ref="form" :model="form" :label-width="70" :rules="formValidate">
        <FormItem label="名称" prop="title">
          <Input v-model="form.title"/>
        </FormItem>
        <FormItem label="数据值" prop="value">
          <Input v-model="form.value"/>
        </FormItem>
        <FormItem label="备注" prop="description">
          <Input v-model="form.description"/>
        </FormItem>
        <FormItem label="排序值" prop="sortOrder">
          <InputNumber :max="1000" :min="0" v-model="form.sortOrder"></InputNumber>
          <span style="margin-left:5px">值越小越靠前，支持小数</span>
        </FormItem>
      </Form>
      <div slot="footer">
        <Button type="text" @click="modalVisible=false">取消</Button>
        <Button type="primary" :loading="submitLoading" @click="handelSubmit">提交</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
import {
  getAllDictList,
  addDict,
  editDict,
  deleteDict,
  searchDict,
  getAllDictDataList,
  addDictData,
  editDictData,
  deleteData
} from "@/api/dict";
import api from '@/api/dict';
export default {
  name: "dict",
  data() {
    return {
      treeLoading: false, // 树加载状态
      maxHeight: "500px",
      loading: false, // 表格加载状态
      editTitle: "", // 编辑节点名称
      searchKey: "", // 搜索树
      expand: true,
      span: 18,
      expandIcon: "ios-arrow-back",
      selectNode: {},
      treeData: [], // 树数据
      selectCount: 0, // 多选计数
      selectList: [], // 多选数据
      searchForm: {
        // 搜索框对应data对象
        title: "",
        status: "",
        pageNumber: 1, // 当前页数
        pageSize: 10, // 页面大小
        sort: "sortOrder", // 默认排序字段
        order: "asc" // 默认排序方式
      },
      modalType: 0, // 添加或编辑标识
      modalVisible: false, // 添加或编辑显示
      dictModalVisible: false,
      dictModalTitle: "",
      modalTitle: "", // 添加或编辑标题
      dictForm: {
        title: "",
        type: "",
        description: "",
        sortOrder: 0
      },
      form: {
        // 添加或编辑表单对象初始化数据
        title: "",
        value: "",
        status: 0,
        description: "",
        sortOrder: 0
      },
      dictFormValidate: {
        // 表单验证规则
        title: [{ required: true, message: "不能为空", trigger: "blur" }],
        type: [{ required: true, message: "不能为空", trigger: "blur" }]
      },
      formValidate: {
        // 表单验证规则
        title: [{ required: true, message: "不能为空", trigger: "blur" }],
        value: [{ required: true, message: "不能为空", trigger: "blur" }]
      },
      columns: [
        // 表头
        {
          type: "index",
          width: 60,
          align: "center"
        },
        {
          title: "名称",
          key: "title",
          minWidth: 150,
          sortable: true
        },
        {
          title: "数据值",
          key: "value",
          width: 150,
          sortable: true
        },
        {
          title: "备注",
          key: "description",
          width: 150,
          sortable: true
        },
        {
          title: "排序值",
          key: "sortOrder",
          width: 100,
          sortable: true,
          sortType: "asc"
        },
        {
          title: "创建时间",
          key: "createTime",
          width: 250,
          sortable: true
        },
        {
          title: "操作",
          key: "action",
          width: 160,
          align: "center",
          fixed: "right",
          render: (h, params) => {
            return h("div", [
              h(
                "Button",
                {
                  props: {
                    type: "primary",
                    size: "small"
                  },
                  style: {
                    marginRight: "5px"
                  },
                  on: {
                    click: () => {
                      this.edit(params.row);
                    }
                  }
                },
                "编辑"
              ),
              h(
                "Button",
                {
                  props: {
                    type: "error",
                    size: "small"
                  },
                  on: {
                    click: () => {
                      this.remove(params.row);
                    }
                  }
                },
                "删除"
              )
            ]);
          }
        }
      ],
      submitLoading: false, // 添加或编辑提交状态
      data: [], //表单数据
      total: 0 // 表单数据总数
    };
  },
  methods: {
    init() {
      // 获取树数据
      this.getAllDict();
      // 获取表单数据
      this.getDataList();
    },
    getAllDict() {
      this.treeLoading = true;
      api.getAllDictTypeList().then(res => {
        this.treeLoading = false;
          this.treeData = res.data;
      });
    },
    search() {
      // 搜索树
      if (this.searchKey) {
        this.treeLoading = true;
        api.getAllDictTypeList({ title: this.searchKey }).then(res => {
          this.treeLoading = false;
            this.treeData = res.data;
        });
      } else {
        // 为空重新加载
        this.getAllDict();
      }
    },
    selectTree(v) {
      if (v.length > 0) {
        this.$refs.dictForm.resetFields();
        // 转换null为""
        for (let attr in v[0]) {
          if (v[0][attr] == null) {
            v[0][attr] = "";
          }
        }
        let str = JSON.stringify(v[0]);
        let data = JSON.parse(str);
        this.selectNode = data;
        this.dictForm = data;
        this.editTitle = data.title + "(" + data.type + ")";
        // 重新加载表
        this.searchForm.pageNumber = 1;
        this.searchForm.pageSize = 10;
        this.getDataList();
      } else {
        this.cancelEdit();
      }
    },
    cancelEdit() {
      let data = this.$refs.tree.getSelectedNodes()[0];
      if (data) {
        data.selected = false;
      }
      // 取消选择后获取全部数据
      this.selectNode = {};
      this.editTitle = "";
      this.getDataList();
    },
    changeSelect(v) {
      this.selectCount = v.length;
      this.selectList = v;
    },
    changeExpand() {
      this.expand = !this.expand;
      if (this.expand) {
        this.expandIcon = "ios-arrow-back";
        this.span = 18;
      } else {
        this.expandIcon = "ios-arrow-forward";
        this.span = 23;
      }
    },
    changePage(v) {
      this.searchForm.pageNumber = v;
      this.getDataList();
      this.clearSelectAll();
    },
    changePageSize(v) {
      this.searchForm.pageSize = v;
      this.getDataList();
    },
    getDataList() {
      this.loading = true;
      if (this.selectNode.id) {
        this.searchForm.dictTypeId = this.selectNode.id;
      } else {
        delete this.searchForm.dictTypeId;
      }
      if (!this.searchForm.status) {
        this.searchForm.status = "";
      }
      api.getDictInfoByCondition(this.searchForm).then(res => {
        this.loading = false;

          this.data = res.data.list;
          this.total = res.data.total;
        
      });
    },
    handleSearch() {
      this.searchForm.pageNumber = 1;
      this.searchForm.pageSize = 10;
      this.getDataList();
    },
    handleReset() {
      this.$refs.searchForm.resetFields();
      this.searchForm.pageNumber = 1;
      this.searchForm.pageSize = 10;
      // 重新加载数据
      this.getDataList();
    },
    changeSort(e) {
      this.searchForm.sort = e.key;
      this.searchForm.order = e.order;
      if (e.order == "normal") {
        this.searchForm.order = "";
      }
      this.getDataList();
    },
    showSelect(e) {
      this.selectList = e;
      this.selectCount = e.length;
    },
    clearSelectAll() {
      this.$refs.table.selectAll(false);
    },
    refreshDict() {
      this.getAllDict();
      this.selectNode = {};
      this.editTitle = "";
      this.getDataList();
    },
    handleDropdown(name) {
      if (name == "editDcit") {
        if (!this.selectNode.id) {
          this.$Message.warning("您还未选择要编辑的字典");
          return;
        }
        this.editDcit();
      } else if (name == "delDcit") {
        this.delDcit();
      } else if (name == "refreshDcit") {
        this.refreshDict();
      }
    },
    addDcit() {
      this.modalType = 0;
      this.dictModalTitle = "添加字典";
      this.$refs.dictForm.resetFields();
      this.dictModalVisible = true;
    },
    editDcit() {
      this.modalType = 1;
      this.dictModalTitle = "编辑字典";
      this.dictModalVisible = true;
    },
    delDcit() {
      if (!this.selectNode.id) {
        this.$Message.warning("您还未选择要删除的字典");
        return;
      }
      this.$Modal.confirm({
        title: "确认删除",
        loading: true,
        content: "您确认要删除 " + this.selectNode.title + " ?",
        onOk: () => {
          // 删除
          api.deleteDictType({id:this.selectNode.id}).then(res => {
            this.$Modal.remove();
            if (res.code  == 200) {
              this.$Message.success("操作成功");
              this.refreshDict();
            }
          });
        }
      });
    },
    add() {
      if (!this.selectNode.id) {
        this.$Message.warning("请先选择一个字典类别");
        return;
      }
      this.modalType = 0;
      this.modalTitle = "添加字典 " + this.editTitle + " 的数据";
      this.$refs.form.resetFields();
      this.modalVisible = true;
    },
    edit(v) {
      this.modalType = 1;
      this.modalTitle = "编辑字典 " + this.editTitle + " 的数据";
      this.$refs.form.resetFields();
      // 转换null为""
      for (let attr in v) {
        if (v[attr] == null) {
          v[attr] = "";
        }
      }
      let str = JSON.stringify(v);
      let data = JSON.parse(str);
      this.form = data;
      this.modalVisible = true;
    },
    handelSubmitDict() {
      this.$refs.dictForm.validate(valid => {
        if (valid) {
          this.submitLoading = true;
          if (this.modalType == 0) {
            // 添加 避免编辑后传入id等数据 记得删除
            delete this.dictForm.id;
            api.addDictType(this.dictForm).then(res => {
              this.submitLoading = false;
              if (res.code  == 200) {
                this.$Message.success("操作成功");
                this.getAllDict();
                this.dictModalVisible = false;
              }
            });
          } else if (this.modalType == 1) {
            // 编辑
            api.editDictType(this.dictForm).then(res => {
              this.submitLoading = false;
              if (res.code  == 200) {
                this.$Message.success("操作成功");
                this.getAllDict();
                this.dictModalVisible = false;
              }
            });
          }
        }
      });
    },
    handelSubmit() {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.submitLoading = true;
          if (this.modalType == 0) {
            // 添加 避免编辑后传入id等数据 记得删除
            delete this.form.id;
            this.form.dictTypeId = this.selectNode.id;
            api.addDictInfo(this.form).then(res => {
              this.submitLoading = false;
              if (res.code  == 200) {
                this.$Message.success("操作成功");
                this.getDataList();
                this.modalVisible = false;
              }
            });
          } else if (this.modalType == 1) {
            // 编辑
            api.editDictInfo(this.form).then(res => {
              this.submitLoading = false;
              if (res.code  == 200) {
                this.$Message.success("操作成功");
                this.getDataList();
                this.modalVisible = false;
              }
            });
          }
        }
      });
    },
    remove(v) {
      if(v.isSys){
        this.$message({
          type:'warning',
          message:'系统数据不可删除'
        })
        return
      }
      this.$Modal.confirm({
        title: "确认删除",
        content: "您确认要删除 " + v.title + " ?",
        onOk: () => {
          // 删除
          this.$store.commit("setLoading", true);
          api.deleteDictInfo({ids:[v.id]}).then(res => {
            this.$store.commit("setLoading", false);
            if (res.code  == 200) {
              this.$Message.success("操作成功");
              this.getDataList();
            }
          });
        }
      });
    },
    delAll() {
      if (this.selectCount <= 0) {
        this.$Message.warning("您还未选择要删除的数据");
        return;
      }
      this.$Modal.confirm({
        title: "确认删除",
        content: "您确认要删除所选的 " + this.selectCount + " 条数据?",
        onOk: () => {
          let ids = [];
          this.selectList.forEach(function(e) {
            ids.push(e.id);
          });
          // 批量删除
           this.$store.commit("setLoading", true);
          api.deleteDictInfo({ids:ids}).then(res => {
            this.$store.commit("setLoading", false);
            if (res.code  == 200) {
              this.$Message.success("操作成功");
              this.clearSelectAll();
              this.getDataList();
            }
          });
        }
      });
    }
  },
  mounted() {
    // 计算高度
    let height = document.documentElement.clientHeight;
    this.maxHeight = Number(height-287) + "px";
    this.init();
  }
};
</script>
<style lang="scss" scoped>
.tree-bar {
    overflow: auto;
    margin-top: 5px;
}

.tree-bar::-webkit-scrollbar {
    width: 6px;
    height: 6px;
}

.tree-bar::-webkit-scrollbar-thumb {
    border-radius: 4px;
    -webkit-box-shadow: inset 0 0 2px #d1d1d1;
    background: #e4e4e4;
}

.ivu-tree .ivu-tree-empty {
    margin-top: 10px;
    font-size: 12px;
}

.expand {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    .icon {
        cursor: pointer;
    }
    :hover {
        color: #1890ff !important;
    }
}

.search {
    .operation {
        margin-bottom: 2vh;
    }
    .select-count {
        font-size: 13px;
        font-weight: 600;
        color: #40a9ff;
    }
    .select-title {
        font-size: 12px;
        font-weight: 600;
        color: #40a9ff;
    }
    .select-clear {
        margin-left: 10px;
    }
    .page {
        margin-top: 2vh;
    }
    .drop-down {
        font-size: 13px;
        margin-left: 5px;
    }
}
</style>