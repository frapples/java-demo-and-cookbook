const RouteView = httpVueLoader("./src/layouts/RouteView.vue");
const MenuView = httpVueLoader("./src/layouts/MenuView.vue");
const ExceptionPage = httpVueLoader("./src/pages/exception/ExceptionPage.vue");
const CardList = httpVueLoader("./src/pages/demo/CardList.vue");

const Page404 = Vue.component('Page404', {
    components: { ExceptionPage },
    template: '<exception-page type="404"></exception-page>'
});

window.globalModules.router = new VueRouter({
    routes: [
        {
            path: '/',
            name: '主页',
            component: MenuView,
            invisible: true,
            children: [
                {
                    path: '/dashboard',
                    name: '工作台',
                    component: RouteView,
                    icon: 'dashboard',
                    children: [
                        {
                            path: '/dashboard/workplace',
                            name: '工作台示例页',
                            component: CardList,
                            icon: 'none'
                        },
                        {
                            path: '/dashboard/page404',
                            name: '开发中示例页',
                            component: Page404,
                            icon: 'none'
                        }
                    ]
                },
            ]
        }
    ]
});
