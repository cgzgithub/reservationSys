<template>
  <el-container class="app-layout">
    <el-container>
      <el-aside style="width:210px" class="app-aside">
        <LeftBar></LeftBar>
      </el-aside>
      <el-container style="overflow: hidden">
      <el-header height="74px" class="app-header">
          <TopBar></TopBar>
      </el-header>
        <el-main class="main">
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
    <rightMenu></rightMenu>
  </el-container>
</template>

<script>
import LeftBar from '@/components/left-bar';
import rightMenu from '@/components/right-menu';
import TopBar from '@/components/top-bar';

export default {
  components: {
    LeftBar,
    TopBar,
    rightMenu
  }
};
</script>

<style lang="scss" scoped>
.app-layout {
  height: 100vh;
  overflow: hidden;
}
.app-aside {
  background-color: #304156;
}

.app-header {
  z-index: 1000;
  padding: 0;
  background-color: #f0f0f0f5;
  box-shadow: 0px 1px 4px 1px #a3a3a569;
  // color: #fff;
}
.el-main{
  padding:30px 20px;
  padding-right: 60px;
}
</style>

