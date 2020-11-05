const fs = require('fs');
const path = require('path');
const bodyParser = require('body-parser');
const resolve = dir => path.join(__dirname, dir);
const isProd = process.env.NODE_ENV === 'production';

module.exports = {
  publicPath: '/',
  assetsDir: 'static',
  transpileDependencies: [],
  lintOnSave: !isProd,

  css: {
    loaderOptions: {
      // 给 sass-loader 传递选项
      sass: {
        // data: "@import '~@/assets/scss/function';"
      }
    }
  },
  // these devServer options should be customized in /config/index.js
  devServer: {
    port: 6002,
    open: true,
    proxy: {
      '/api': {
        target: 'http://10.0.10.190:10056',
        // target: 'https://bay.zhiduoduo.com.cn',
        changeOrigin: true
      }
    },
  before(app) {
      if (process.env.IS_MOCK === 'true') {
        app.use(bodyParser.json());
        app.all(['/api/*'], function(req, res) {
          const pathArr = req.path.split('/');
          const fileName = pathArr[pathArr.length - 1];
          const mockDir = path.resolve(__dirname, './mock');
          const mock = require(path.resolve(mockDir, fileName));

          if (typeof mock === 'object') {
            res.send(mock);
          } else {
            res.send(mock && mock(req.body));
          }
        });
      }
    }
  },

  configureWebpack: config => {
    if (process.env.NODE_ENV === 'production') {
      config.optimization.splitChunks = {
        cacheGroups: {
          polyfills: {
            name: 'chunk-polyfills',
            test: /[\\/]node_modules[\\/](core-js|@babel|vue-lazyload|mars|qs)/,
            priority: -10,
            chunks: 'initial'
          },
          vendors: {
            name: 'chunk-vendors',
            test: /[\\/]node_modules[\\/](vue|vue-router|vuex|vuex-router-sync|axios)\//,
            priority: -20,
            chunks: 'initial'
          }
        }
      };
    } else {
      // 为开发环境修改配置...
    }
  },

  chainWebpack: config => {
    // 移除 prefetch 插件
    config.plugins.delete('prefetch');
    // 添加别名
    config.resolve.alias.set('@', resolve('src'));

    // svg
    // config.module.rules.delete('svg'); //重点:删除默认配置中处理svg,
    // config.module
    //   .rule('svg-sprite-loader')
    //   .test(/\.svg$/)
    //   .include.add(resolve('src/assets/svg')) //处理svg目录
    //   .end()
    //   .use('svg-sprite-loader')
    //   .loader('svg-sprite-loader')
    //   .options({
    //     symbolId: 'icon-svg-[name]'
    //   });
  },
  
  
};
