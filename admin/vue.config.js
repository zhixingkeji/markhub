module.exports = {
    devServer: {
        // 在服务器内部的所有其他中间件之前执行定制中间件
        // before: require('../src/mock'),
        port: 80,     // 端口号

    },
    publicPath: '/'
}
