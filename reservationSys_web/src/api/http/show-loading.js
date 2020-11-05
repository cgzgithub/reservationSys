import store from '@/store';

export default instance => {
  // Add a request interceptor
  instance.interceptors.request.use(
    config => {
      if (config.showLoading !== false) {
        store.dispatch('toggleLoadingStatus', true);
      }

      return config;
    },
    error => {
      // Do something with request error
      if (error && error.config && error.config.showLoading !== false) {
        store.dispatch('toggleLoadingStatus', false);
      }
      return Promise.reject(error);
    }
  );

  // Add a response interceptor
  instance.interceptors.response.use(
    response => {
      if (response.config.showLoading !== false) {
        store.dispatch('toggleLoadingStatus', false);
      }

      return response;
    },
    error => {
      // Do something with response error
      if (error && error.config && error.config.showLoading !== false) {
        store.dispatch('toggleLoadingStatus', false);
      }

      return Promise.reject(error);
    }
  );
};
