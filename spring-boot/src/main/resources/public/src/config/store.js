const setting = {
    namespaced: true,
    state: {
        isMobile: false,
        theme: 'dark',
        layout: 'side',
        systemName: 'Vue Antd Admin',
        logoUrl: 'img/vue-antd-logo.png',
        copyright: '2018 ICZER 工作室出品',
        footerLinks: [
            {link: 'https://pro.ant.design', name: 'Pro首页'},
            {link: 'https://github.com/iczer/vue-antd-admin', icon: 'github'},
            {link: 'https://ant.design', name: 'Ant Design'}
        ],
        multipage: true
    },
    mutations: {
        setDevice (state, isMobile) {
            state.isMobile = isMobile
        },
        setTheme (state, theme) {
            state.theme = theme
        },
        setLayout (state, layout) {
            state.layout = layout
        },
        setMultipage (state, multipage) {
            state.multipage = multipage
        }
    }
};

window.globalModules.store = new Vuex.Store({
    modules: {
        setting
    }
});
