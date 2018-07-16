var webpack = require('webpack');//引入webpack插件
module.exports = {
    baseUrl: process.env.baseUrl,//项目发布地址
    outputDir: process.env.outputDir,//编译项目输出地址
    assetsDir: "static",//编译项目静态资源目录
    devServer: {
        port: process.env.port,//本地启动 端口号
        https: false,
        hotOnly: true,
        // 查阅 https://github.com/vuejs/vue-docs-zh-cn/blob/master/vue-cli/cli-service.md#配置代理
        proxy: null, // string | Object
        before: app => {
        }
    },
    configureWebpack: {
        plugins: [
            new webpack.ProvidePlugin({$: "jquery", jQuery: "jquery", "windows.jQuery": "jquery"})//全局jquery配置
        ]
    }
}