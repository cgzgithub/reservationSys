<template>
  <el-table
    :data="data"
    v-bind="currentTableConfig"
    @selection-change="handleSelectionChange"
    @select="handleSelect"
    @select-all="handleSelectAll"
    @row-click="handleRowClick"
    @expand-change="handleExpandChange"
    @filter-change="handleFilterChange"
    :highlight-current-row="highlight"
    :row-class-name="tableRowClassName"
    :cell-class-name="cellClassName"
    ref="table"
    row-key="id"
  >
    <template v-for="(colConfig, key) in currentColConfigs">
      <slot v-if="colConfig.slot" :name="colConfig.slot"></slot>
      <component
        v-else-if="colConfig.component"
        :is="colConfig.component"
        :col-config="colConfig"
        :key="key"
      ></component>
      <el-table-column v-bind="colConfig" :key="key" v-else></el-table-column>
    </template>
  </el-table>
</template>

<script>
import formatDate from '@/filters/format-date';
export default {
  name: 'flex-table',
  props: {
    data: {
      type: Array,
      required: true
    },
    tableConfig: {
      type: Object,
      default() {
        return {
          size: 'small',
          showOverflowTooltip: true
        };
      }
    },
    colConfigs: {
      type: Array,
      required: true
    },
    value: {
      type: Array
    },
    highlight:{
      type:Boolean,
      default:false
    },
    tableRowClassName:{
      type:Function
    }
  },

  data() {
    return {
      selections: [],
      cellClassName:'default-width'
    };
  },

  watch: {
    value: {
      handler(val) {
        if (val !== this.selections) {
          setTimeout(() => {
            this.toggleRowSelection();
          }, 100);
        }

        this.selections = val;
      },
      immediate: true
    },

    data: {
      handler() {
        setTimeout(() => {
          this.toggleRowSelection();
          this.handleExpandRow();
        }, 100);
      },
      immediate: true
    },

    selections(val) {
      this.$emit('input', val);
    }
  },

  computed: {
    currentColConfigs() {
      const { colConfigs } = this;
      const defualtColConfig = {
        showOverflowTooltip: true,
        align: 'left',
        formatter(row, column, cellValue) {
          return typeof cellValue === 'undefined' ? '--' : cellValue;
        }
      };

      return colConfigs.map(item => {
        return Object.assign({}, defualtColConfig, item);
      });
    },

    currentTableConfig() {
      const { tableConfig } = this;

      return {
        size: 'small',
        showOverflowTooltip: true,
        ...tableConfig
      };
    },

    valueRowKeys() {
      const {
        selections,
        tableConfig: { rowKey }
      } = this;

      if (selections) {
        return selections.map(item => item[rowKey]);
      }

      return [];
    }
  },

  methods: {
    // tableRowClassName({row,rowIndex}){
    //   console.log(row)
    //   this.$emit
    // },
    handleSelectionChange(val) {
      this.$emit('selection-change', val);
    },

    handleSelect(selection, row) {
      const {
        data,
        valueRowKeys,
        tableConfig: { rowKey }
      } = this;

      if (selection.length < valueRowKeys.length) {
        const index = valueRowKeys.indexOf(row[rowKey]);
        if (index > -1) {
          this.selections.splice(index, 1);
        }
      } else {
        this.selections.push(row);
      }
    },
    handleSelectAll(selection) {
      const {
        data,
        valueRowKeys,
        tableConfig: { rowKey }
      } = this;
      if (selection.length) {
        selection.forEach(row => {
          if (valueRowKeys.indexOf(row[rowKey]) === -1) {
            this.selections.push(row);
          }
        });
      } else {
        data.forEach(item => {
          const index = valueRowKeys.indexOf(item[rowKey]);
          if (index !== -1) {
            this.selections.splice(index, 1);
          }
        });
      }
    },

    toggleRowSelection() {
      const {
        valueRowKeys,
        data,
        value,
        tableConfig: { rowKey }
      } = this;

      if (typeof value !== 'undefined') {
        this.$refs.table && this.$refs.table.clearSelection();
        if (valueRowKeys.length) {
          data.forEach((row, index) => {
            if (valueRowKeys.indexOf(row[rowKey]) > -1) {
              this.$refs.table && this.$refs.table.toggleRowSelection(row);
            }
          });
        }
      }
    },
    handleRowClick(row, column, event) {
      this.$emit('row-click', row, column, event);
    },
    handleFilterChange(filters){
      this.$emit('filter-change',filters)
    },
    handleExpandRow() {
      if (this.data.length) {
        this.$refs.table.toggleRowExpansion(this.data, true);
      }
    },
    handleExpandChange(row, expandedRows){

    }
  }
};
</script>

<style>
.el-table .warning-row {
    background: oldlace;
}
.el-table .red-row {
    background: rgb(238, 194, 176);
}
.cell{
  white-space:nowrap !important;
  min-width:200px;
}
/* .el-table__body-wrapper{
  overflow-x: hidden !important;
} */
</style>
