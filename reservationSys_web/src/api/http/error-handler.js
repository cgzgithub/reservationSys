import { Message } from 'iview';
import auth from '@/service/auth';

function errorHandler(data) {
  const { msg } = data;
  Message.error(msg || '请求出错, 请稍后重试！')
  // Message({
  //   type: 'error',
  //   message: msg || '请求出错, 请稍后重试！'
  // });
}

export default instance => {
  // Add a response interceptor
  instance.interceptors.response.use(
    response => {
      const data = response.data;
      const { status, msg, code } = data;

      // 接口不返回data或者status为0
      if (status === 0) {
        // 登录code失效
        if(code === 401 ){
            auth.expireToLogin();
            return Promise.reject(new Error(msg));
        } else {
            errorHandler(data);
          return Promise.reject(new Error(msg));
        }
      } else {
        return Promise.resolve(response);
      }
    },
    error => {
      // Do something with response error
      const data = {
        error,
        msg: ''
      };
      const { response, config } = error;

      if (response) {
        const res = response.data;

        data.msg = res && res.msg;
        data.code = res && res.code;
      } else {
        data.msg = error.message && '当前网络不可用，请稍后重试！';
      }

      if (config && config.showDefaultError !== false) {
        errorHandler(data);
      }

      return Promise.reject(error);
    }
  );
};
