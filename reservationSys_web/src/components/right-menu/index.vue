<template>
  <div class="right-menu" :style="showMenu ? '' : 'right:-265px;box-shadow:none'">
    <div class="title">消息中心</div>
    <img
      class="toggle-btn"
      @click="toggleShow"
      :style="showMenu ? '' : 'transform:rotate(180deg)'"
      src="../../assets/icon-arrow.png"
      alt
      srcset
    />
    <div class="lists">
      <div class="msg-list-wrap" v-for="(item,index) in messageList" :key="index">
        <div class="msg-list">
          <img src="../../assets/icon-msg3.png" />
          <!-- <i class="el-icon-date"></i> -->
          <div class="msg-content">
            <div class="msg-tit">
              {{item.type}}
              <div class="msg-time">{{item.time}}</div>
            </div>
          </div>
        </div>
        <div class="msg-des">{{item.content}}</div>
      </div>
     </div>
  </div>
</template>

<script>
import api from '@/api/message'
import { mapState } from 'vuex';
export default {
  name: "RightMenu",
  data() {
    return {
      messageList:[],
      navs: [],
      showMenu: false,
      socketUrl:'',
      websocket:null
    };
  },
  created(){
    // this.initWebSocket()
  },
  computed: {
    // ...mapState({
    //   isLogged: state => state.isLogged,
    //   userInfo: state => state.userInfo
    // })
  },
  // watch:{
  //   userInfo(v){
  //   }
  // },
  mounted() {
    this.init()
  },
  methods: {
    // getWSurl(url){
    //   const base = '';
    //   const userId = ''
    //   let that = this;
    //   if(url.indexOf('sockjs') != -1){
    //     that.socketUrl = 'http://'+ base + url + ';' + userId;
    //   }else if(window.location.protocol == 'http:'){
    //     that.socketUrl = 'ws://'+ base + url + ';' + userId;
    //   }else{
    //     that.socketUrl = 'wss://'+ base + url + ';' + userId;
    //   }
    // },
    // initWebSocket(){
    //   const that = this;
    //   let heartCheck = {
    //     timeout:1000*30,
    //     timeoutObj:null,
    //     reset:function(){
    //       clearInterval(this.timeoutObj);
    //       return this;
    //     },
    //     start:function(){
    //       this.timeoutObj = setInterval(function(){
    //         that.websocket.send('HeartBeat');
    //         console.log("HeartBeat")
    //       },this.timeout)
    //     }
    //   }
    //   if('WebSocket' in window){
    //     console.log(window.WebSocket)
    //     that.getWSurl("/webSocketServer")
    //     that.websocket = new WebSocket(that.socketUrl)
    //   }else if('MozWebSocket' in window){
    //     that.getWSurl("/webSocketServer")
    //     that.websocket = new MozWebSocket(that.socketUrl)
    //   }else {
    //     that.getWSurl("/sockjs/webSocketServer")
    //     that.websocket = new SockJS(that.socketUrl)
    //   }
    //   that.websocket.onopen = function(){
    //     console.log('websocket连接成功')
    //     heartCheck.reset().start()
    //   }
    //   that.websocket.onmessage = function(e){
    //     console.log('message',e.data)
    //   }
    //   that.websocket.onerror = function(){
    //     console.log('websocket connect error')
    //   }
    // },
    init(){
      api.getMessageList().then(res => {
        this.messageList = res.data
      })
    },
    toggleShow() {
      if(!this.showMenu){
        this.init()
      }
      this.showMenu = !this.showMenu;
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.title{
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 20px;
}
.right-menu {
  transition: all ease-in-out 0.3s;
  position: fixed;
  right: 0px;
  top: 0px;
  height: 100vh;
  box-sizing: border-box;
  padding: 24px;
  padding-left: 38px;
  padding-top: 100px;
  width: 300px;
  z-index: 999;
  background: #fff;
  box-shadow: 0px 4px 8px 0px rgba(0, 0, 0, 0.1);
  overflow-y:scroll;
}
.right-menu::-webkit-scrollbar { width: 0 !important }
.toggle-btn {
  z-index: 99999;
  position: absolute;
  left: -1px;
  top: 50vh;
  margin-top: -18px;
  z-index: 999;
  width: 36px;
  height: 36px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 1);
  box-shadow: 0px 4px 8px 0px rgba(0, 0, 0, 0.1);
}
.msg-list-wrap {
  box-sizing: border-box;
  width: 100%;
  position: relative;
  padding: 8px;
  margin: 0px auto 12px auto;
  background: rgba(255, 255, 255, 1);
  box-shadow: 2px 6px 12px 0px rgba(0, 0, 0, 0.08);
  border-radius: 8px;
  text-align: left;
}
.msg-list {
  display: flex;
  align-items: center;
}
.msg-list img {
  flex: 0 0 44rpx;
  height: 45px;
  width: 44px;
  margin-right: 6px;
}
.msg-list i{
  font-size: 28px;
  margin-right: 8px;
}
.msg-tit {
  text-align: left;
  font-size: 15px;
  font-weight: bold;
  color: #0f1e3e;
}
.msg-time {
  color: rgba(15, 30, 62, 0.25);
  font-size: 12px;
  margin-top: 4px;
  font-weight: normal;
}
.msg-des {
  margin-top: 8px;
  word-break: break-all;
  font-size: 13px;
  line-height: 18px;
  color: rgba(15, 30, 62, 0.5);
}
</style>
