<template>
  <a-layout>
    <!-- sider menu -->
    <a-layout-sider v-if="layout === 'side'"
                    :class="[theme, 'sider', isMobile ? null : 'shadow']"
                    width="256px" :collapsible="true" v-model="collapsed" :trigger="null">
      <div :class="['logo', theme]">
        <router-link to="/dashboard/workplace">
          <img :src="logoUrl"/>
          <h1>{{systemName}}</h1>
        </router-link>
      </div>
      <i-menu :theme="theme" :collapsed="collapsed" :menu-data="menuData" ></i-menu>
    </a-layout-sider>

    <a-layout>
      <!-- header -->
      <a-layout-header :class="[headerTheme, 'global-header']">
        <div :class="['global-header-wide', layout]">
          <router-link v-if="isMobile || layout === 'head'" to="/" :class="['logo', isMobile ? null : 'pc', headerTheme]">
            <img width="32" :src="logoUrl" />
            <h1 v-if="!isMobile">{{systemName}}</h1>
          </router-link>
          <a-divider v-if="isMobile" type="vertical"></a-divider>
          <a-icon v-if="layout === 'side'" class="trigger" :type="collapsed ? 'menu-unfold' : 'menu-fold'" @click="toggleCollapse"></a-icon>
          <div v-if="layout === 'head'" class="global-header-menu">
            <i-menu style="height: 64px; line-height: 64px;" :theme="headerTheme" mode="horizontal" :menu-data="menuData" ></i-menu>
          </div>
          <div :class="['global-header-right', headerTheme]">
            <header-avatar class="header-item"></header-avatar>
          </div>
        </div>
      </a-layout-header>

      <!-- content -->
      <a-layout-content :style="{minHeight: minHeight, margin: '24px 24px 0'}">
        <slot></slot>
      </a-layout-content>

      <!-- footer -->
      <a-layout-footer style="padding: 0">
        <div class="footer">
          <div class="links">
            <a target="_blank" :key="index" :href="item.link ? item.link : 'javascript: void(0)'" v-for="(item, index) in linkList">
              <a-icon v-if="item.icon" :type="item.icon"></a-icon>{{item.name}}
            </a>
          </div>
          <div class="copyright">
            Copyright<a-icon type="copyright"></a-icon>{{copyright}}
          </div>
        </div>
      </a-layout-footer>
    </a-layout>
  </a-layout>
</template>

<script>
const GlobalHeader = httpVueLoader('./GlobalHeader.vue');
const SiderMenu = httpVueLoader('./SiderMenu.vue');
const IMenu = httpVueLoader('./menu/menu.vue');
const HeaderAvatar = httpVueLoader("./HeaderAvatar.vue");


const minHeight = window.innerHeight - 64 - 24 - 122;

module.exports = {
  name: 'GlobalLayout',
  components: { GlobalHeader, SiderMenu, IMenu, HeaderAvatar },
  data () {
    return {
      minHeight: minHeight + 'px',
      collapsed: false,
      showSetting: false
    }
  },
  computed: {
    isMobile () {
      return this.$store.state.setting.isMobile
    },
    theme () {
      return this.$store.state.setting.theme
    },

    headerTheme () {
      return this.layout === 'side' ? 'light' : this.$store.state.setting.theme
    },

    layout () {
      return this.$store.state.setting.layout
    },
    linkList () {
      return this.$store.state.setting.footerLinks
    },
    copyright () {
      return this.$store.state.setting.copyright
    },

    systemName () {
      return this.$store.state.setting.systemName
    },

    logoUrl() {
      return this.$store.state.setting.logoUrl
    },

    menuData() {
      return this.$router.options.routes.find((item) => item.path === '/').children
    }
  },
  methods: {
    toggleCollapse () {
      this.collapsed = !this.collapsed
    },
    onDrawerChange (show) {
      this.collapsed = show
    },
    onMenuSelect () {
      this.toggleCollapse()
    },
    onSettingDrawerChange (val) {
      this.showSetting = val
    }
  },
}
</script>

<style scoped>
  .footer {
    padding: 0 16px;
    margin: 48px 0 24px;
    text-align: center;
  }
  .footer .copyright {
    color: rgba(0, 0, 0, 0.45);
    font-size: 14px;
  }
  .footer .links {
    margin-bottom: 8px;
  }
  .footer .links a:not(:last-child) {
    margin-right: 40px;
  }
  .footer .links a {
    color: rgba(0, 0, 0, 0.45);
    -webkit-transition: all .3s;
    transition: all .3s;
  }

  .global-header .trigger {
    font-size: 20px;
    line-height: 64px;
    padding: 0 24px;
    cursor: pointer;
    transition: color .3s;
  }

  .global-header .trigger:hover {
    color: #1890ff;
  }
  .global-header .header-item {
    padding: 0 12px;
    display: inline-block;
    height: 100%;
    cursor: pointer;
    vertical-align: middle;
  }
  .global-header .header-item i {
    font-size: 16px;
    color: rgba(0, 0, 0, 0.65);
  }
  .global-header {
    padding: 0 12px 0 0;
    -webkit-box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
    box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
    position: relative;
  }
  .global-header.light {
    background: #fff;
  }
  .global-header.dark {
    background: #001529;
  }
  .global-header .global-header-wide.head {
    max-width: 1400px;
    margin: auto;
  }
  .global-header .global-header-wide .logo {
    height: 64px;
    line-height: 58px;
    vertical-align: top;
    display: inline-block;
    padding: 0 12px 0 24px;
    cursor: pointer;
    font-size: 20px;
  }
  .global-header .global-header-wide .logo.pc {
    padding: 0 12px 0 0;
  }
  .global-header .global-header-wide .logo img {
    display: inline-block;
    vertical-align: middle;
  }
  .global-header .global-header-wide .logo h1 {
    display: inline-block;
    font-size: 16px;
  }
  .global-header .global-header-wide .logo.dark h1 {
    color: #fff;
  }
  .global-header .global-header-wide .global-header-menu {
    display: inline-block;
  }
  .global-header .global-header-wide .global-header-right {
    float: right;
  }
  .global-header .global-header-wide .global-header-right.dark {
    color: #fff;
  }
  .global-header .global-header-wide .global-header-right.dark i {
    color: #fff;
  }


  .shadow {
    box-shadow: 2px 0 6px rgba(0, 21, 41, 0.35);
  }
  .sider {
    z-index: 10;
  }
  .sider.light {
    background-color: #fff;
  }
  .sider.dark {
    background-color: #001529;
  }
  .sider .logo {
    height: 64px;
    position: relative;
    line-height: 64px;
    padding-left: 24px;
    -webkit-transition: all .3s;
    transition: all .3s;
    overflow: hidden;
  }
  .sider .logo.light {
    background-color: #fff;
  }
  .sider .logo.light h1 {
    color: #1890ff;
  }
  .sider .logo.dark {
    background-color: #002140;
  }
  .sider .logo.dark h1 {
    color: #fff;
  }
  .sider .logo h1 {
    color: #fff;
    font-size: 20px;
    margin: 0 0 0 12px;
    font-family: "Myriad Pro", "Helvetica Neue", Arial, Helvetica, sans-serif;
    font-weight: 600;
    display: inline-block;
    height: 32px;
    line-height: 32px;
    vertical-align: middle;
  }
  .sider .logo img {
    width: 32px;
    display: inline-block;
    vertical-align: middle;
  }

</style>
