module.exports = {
    devServer: {
        port: 3000,
        proxy: 'http://localhost:8080'
        // proxy: {
        //     '/api' : {
        //         target: 'http://localhost:8080',
        //         ws: true,
        //         changeOrigin: true
        //     }
        // }
    }
}
