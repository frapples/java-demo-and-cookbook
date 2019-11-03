/****  httpVueLoader 配置 ****/
// Thanks for https://github.com/FranckFreiburger/http-vue-loader/issues/38
const useBabel = false;

if (useBabel) {
    const esBabalTransformer = script => {
        console.log("loading");
        console.log(script);
        const s = Babel.transform(script, { presets: ['es2017', 'stage-3'] }).code;
        console.log("loading end");
        console.log(s);
        return s;
    };
    httpVueLoader.langProcessor.javascript = esBabalTransformer;
    httpVueLoader.langProcessor.js = esBabalTransformer;
}

/**** vue vueRouter ***/
Vue.use(VueRouter);


/****  bus 配置 ****/
Vue.use(function (Vue) {
    Vue.prototype.$bus = new Vue();
});

/****  axios 配置 ****/
axios.interceptors.request.use(function (config) {
    return config;
}, function (error) {
    return Promise.reject(error);
});

axios.interceptors.response.use(function (response) {
    // console.log(response);
    const data = response.data;
    if (!data.successed) {
        antd.notification.error({
            message: 'API调用错误',
            description: JSON.stringify(error)
        });
        return Promise.reject(data);
    } else {
        return response;
    }
}, function (error) {
    antd.notification.error({
        message: '网络错误',
        description: JSON.stringify(error)
    });
    // Do something with response error
    return Promise.reject(error);
});

window.globalModules = {};

