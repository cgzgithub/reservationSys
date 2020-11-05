<template>
  <section class="pagination" v-show="page.total">
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="page.pageNum"
      :page-size="page.pageSize"
      layout="total, prev, pager, next, sizes"
      :total="page.total"
    ></el-pagination>
  </section>
</template>
<script>
export default {
  name: 'pagination',

  props: {
    page: {
      type: Object,
      default() {
        return {
          pageSize: 15,
          currentPage: 1,
          recordsTotal: 0
        };
      }
    }
  },

  data() {
    return {};
  },

  methods: {
    handleCurrentChange(...args) {
      this.$emit('pageChange', args);
    },
    handleSizeChange(...args) {
      this.$emit('sizeChange', args);
    }
  }
};
</script>
<style lang="scss" scoped>
.pagination {
  margin: 10px 0;
  display: flex;
  justify-content: flex-end;
}
</style>

