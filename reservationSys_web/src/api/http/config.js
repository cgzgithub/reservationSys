import auth from '@/service/auth';

export default () => {
  const config = {
    baseURL: '',
    timeout: 1000 * 60,
    method: 'post',
    data: {
      token: auth.getToken(),
    },
    headers: {
      'session_channel':'MANAGER'
    }
  };

  return config;
};
