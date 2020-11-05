import Vue from 'vue';
import Vuex from 'vuex';
import account from '@/api/account'
Vue.use(Vuex);
const state ={
    loading:0, // 是否加载
    isLogged: false, // 是否登录
    userInfo:''
}
const getters = {

}
const mutations = {
    ['TOGGLE_LOADING_STATUS'](state,isShow) {
        if(isShow) {
            state.loading = state.loading + 1;
        } else {
            if(state.loading >= 1) {
                state.loading = state.loading - 1;
            }
        }
    },
    ['SET_LOGIN_STATUS'](state,isLogged) {
        state.isLogged = isLogged
    },
    ['GET_USER_INFO'](state,data) {
            state.userInfo = data;
    }
}
const actions = {
    toggleLoadingStatus({state,commit},isShow){
        commit('TOGGLE_LOADING_STATUS',isShow)
    },
    getUserInfo({state,commit}){
        return new Promise((resolve,reject) => {
            account.getAccountDetail().then(res => {
                const {data} = res;
                commit('GET_USER_INFO',data);
                resolve(data)
            })
        })
    }
}
export default new Vuex.Store({
    state,
    getters,
    mutations,
    actions
})