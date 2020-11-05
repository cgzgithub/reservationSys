<template>
  <div class="app">
    <router-view/>
    <Spin v-show="loading"></Spin>
  </div>
</template>

<script>
import { mapState } from 'vuex';
import Spin from '@/components/spin';

export default {
  name: 'App',
  components: {
    Spin
  },
  computed: {
    ...mapState({
      loading: state => state.loading
    })
  },
  created() {
    // console.log('url_params>>>>>>>>>>',this.$route.query)
  }
};
</script>

<style lang="scss">

html,
body {
  height: 100%;
  overflow: hidden;
}


.app {
  font-size: 14px;
  height: 100%;
  width: 100%;
  min-width: 0;
}
#rowForm .ivu-form .ivu-form-item-label{
    color: #e3e6f4;
    font-weight: 700; 
}

.el-autocomplete-suggestion{
  z-index: 11111 !important;
}
</style>
