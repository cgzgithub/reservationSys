<template>
  <div class="left-bar">
    <div class="site-title">
      <!-- <router-link to="/" class="site-logo">
        <img src="~@/assets/img/home/logo.png" alt>
      </router-link> -->
    </div>
    <div class="app-menu">
      <el-menu
        class="app-top-menu"
        :default-active="$route.fullPath"
        router
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409EFF"
      >
        <template v-for="menuItem in leftmenu">
          <el-submenu v-if="menuItem.subMenu" :index="menuItem.fullPath" :key="menuItem.text">
            <template slot="title">
              <span style="font-weight:bold">{{menuItem.text}}</span>
            </template>
            <el-menu-item
              v-for="subMenuItem in menuItem.subMenu"
              :index="subMenuItem.fullPath"
              :key="subMenuItem.fullPath"
            >
              <span slot="title">{{subMenuItem.text}}</span>
            </el-menu-item>
          </el-submenu>
          <el-menu-item :index="menuItem.fullPath" :key="menuItem.text" v-else>
            <span slot="title" style="font-weight:bold">{{menuItem.text}}</span>
          </el-menu-item>
        </template>
      </el-menu>
    </div>
  </div>
</template>

<script>
import api from '@/api/menu'
// import {leftmenu} from '@/config/left-menu';

export default {
  name: 'left-bar',
  data() {
    return {
      leftmenu:[]
    };
  },
  mounted() {
    this.init()
  },
  methods: {
    init() {
      api.getUserMenu().then(res => {
        this.leftmenu = res.data
      })
    }
  },
  created() {}
};
</script>

<style lang="scss" scoped>
.el-menu{
  border-right: none;
}
// .left-bar {
//   width: 100px;
//   min-height: 100%;
//   position: relative;
//   background: #004163;
//   ::v-deep {
//     .el-menu {
//       border-right: 0;
//     }

//     .el-menu-item {
//       margin: 9px 0;
//       height: 38px;
//       line-height: 38px;
//     }

//     .el-menu-item.is-active {
//       position: relative;
//       &::after {
//         position: absolute;
//         content: '';
//         left: -1px;
//         right: -7px;
//         top: 50%;
//         margin-top: -19px;
//         height: 38px;
//         background: linear-gradient(
//           225deg,
//           rgba(56, 175, 255, 1) 0%,
//           rgba(0, 120, 200, 1) 100%
//         );
//         box-shadow: 0px 5px 15px 0px rgba(0, 49, 74, 0.6);
//         border-radius: 5px;
//       }

//       span {
//         position: relative;
//         z-index: 1;
//       }
//     }
//   }
// }
// .site-title {
//   padding-top: 33px;
// }

// .site-logo {
//   display: block;
//   text-align: center;
// }

// .app-menu {
//   padding-bottom: 100px;
// }

// .app-top-menu {
//   padding: 45px 0;
//   border-bottom: 1px solid rgba(227, 230, 244, 0.2);
// }

// .app-bottom-menu {
//   padding: 20px 0;
// }

// .app-setting {
//   width: 60px;
//   height: 60px;
//   border: 10px solid #0a587e;
//   background: rgba(48, 180, 235, 1);
//   border-radius: 50%;

//   display: flex;
//   align-items: center;
//   justify-content: center;

//   font-size: 14px;
//   color: #e3e6f4;

//   cursor: pointer;

//   position: absolute;

//   left: 50%;
//   margin-left: -30px;

//   bottom: 17px;
// }
</style>

