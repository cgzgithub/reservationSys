import axios from'axios';

import baseConfig from './config';
import deepMerge from 'deepmerge';
import qs from 'qs';
import errorHandler from './error-handler';
import showLoading from './show-loading';


const instance = axios.create();
showLoading(instance)
errorHandler(instance)
export default instance;
// request 函数
export function request(apis) {
    const result = {};
    Object.keys(apis).forEach(key => {
      let currentApi = apis[key];
  
      if (typeof currentApi === 'string') {
        currentApi = {
          url: currentApi
        };
      }
  
      result[key] = (params = {}, config = {}) => {
        config = deepMerge.all([{}, baseConfig(), currentApi, config]);
  
        if (config.noToken) {
          delete config.data.token;
        }
  
        if (!config.data.token && config.localToken) {
          config.data.token = config.data.localToken;
        }
  
        if (['post', 'put', 'patch'].indexOf(config.method) > -1) {
          config.data = Object.assign(config.data || {}, params);
  
          /// form url 格式
          if (
            config.headers['Content-Type'] === 'application/x-www-form-urlencoded'
          ) {
            config.data = qs.stringify(config.data);
          }
  
          if (config.headers['Content-Type'] === 'multipart/form-data') {
            config.transformRequest = [
              function(data, headers) {
                const formData = new FormData();
  
                Object.keys(data).forEach(key => {
                  formData.append(key, data[key]);
                });
  
                return formData;
              }
            ];
          }
        } else {
          config.params = Object.assign(config.params || {}, params);
        }
  
        return instance(config).then(response => response.data);
      };
    });
  
    return result;
  }




