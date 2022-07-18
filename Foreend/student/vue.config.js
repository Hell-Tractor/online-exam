'use strict'
const path = require('path')

function resolve (dir) {
  return path.join(__dirname, dir)
}

module.exports = {
  publicPath: './',
  outputDir: 'student',
  assetsDir: 'static',
  lintOnSave: true,
  productionSourceMap: false,
  // 开发环境主要修改这里
  devServer: {
    open: true,
    host: 'localhost', // 后端接口的域名
    port: 8001, // 前端启动的端口
    https: false,
    hotOnly: false,
    proxy: {
      '/api': {
        target: 'http://localhost:8080', // 后端接口地址，和后端保持一致
        changeOrigin: true,
        pathRewrite: {
          '^/api': '' // 重写请求
        }
      }
    }
  },
  pages: {
    index: {
      entry: 'src/main.js',
      template: 'public/index.html',
      filename: 'index.html'
    }
  },
  pwa: {
    iconPaths: {
      favicon32: '@/icons/favicon.ico',
      favicon16: '@/icons/favicon.ico',
      appleTouchIcon: '@/icons/favicon.ico',
      maskIcon: '@/icons/favicon.ico',
      msTileImage: '@/icons/favicon.ico'
    }
  },
  chainWebpack (config) {
    // set svg-sprite-loader
    config.module
      .rule('svg')
      .exclude.add(resolve('src/icons'))
      .end()
    config.module
      .rule('icons')
      .test(/\.svg$/)
      .include.add(resolve('src/icons'))
      .end()
      .use('svg-sprite-loader')
      .loader('svg-sprite-loader')
      .options({
        symbolId: 'icon-[name]'
      })
      .end()
  }
}
