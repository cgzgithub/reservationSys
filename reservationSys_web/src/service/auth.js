import user from '@/api/user';
import Router from '@/routes';
// import { UserError } from '@/libs/error';
import store from '@/store';

export default {
  // 获取用户上次登录的手机号
  getCachePhone() {
    return localStorage.getItem("phone") || '';
  },

  getToken() {
    return localStorage.getItem("token") || '';
  },

  removeUser() {
    localStorage.removeItem("token");
    store.commit('SET_LOGIN_STATUS', false);
  },

  expireToLogin(redirectUrl) {
    this.removeUser();
    const { name, fullPath } = Router.currentRoute;
    redirectUrl = redirectUrl || fullPath;

    if (name !== 'login') {
      Router.push({ path: '/login', query: { redirectUrl } });
    }
  },

  /**
   * 判断是否登录
   *
   * @returns
   */
  loggedIn() {
    return new Promise((resolve, reject) => {
      const code = this.getToken();

      if (!code) {
        reject(new Error('用户未登录'));
      } else {
        store.commit('SET_LOGIN_STATUS', true);
        resolve();
      }
    });
  },

  pcLogin(params) {
    return new Promise((resolve,reject) => {
      user.pcLogin(params).then(response => {
        const {data} = response;
        localStorage.setItem("token",data.token);
        store.commit('SET_LOGIN_STATUS', true);
        resolve();
      })
    })
  },

  login(params) {
    return new Promise((resolve, reject) => {
      user.login(params).then(response => {
        const { data } = response;
        localStorage.setItem("token", data.token);
        store.commit('SET_LOGIN_STATUS', true);
        resolve();
      });
    });
  },

  logout() {
    return new Promise((resolve, reject) => {
      this.removeUser();
      resolve();
    });
  }
};
