<template>
  <a-dropdown style="display: inline-block; height: 100%; vertical-align: initial" >
    <span style="cursor: pointer">
      <a-avatar v-if="currUser.avatarType === 'icon'" style="background-color: #87d068"  class="avatar" size="small" shape="circle" :icon="currUser.avatar"></a-avatar>
      <a-avatar v-else class="avatar" size="small" shape="circle" :src="currUser.avatar"></a-avatar>
      <span>{{currUser.name}}</span>
    </span>
    <a-menu style="width: 150px" slot="overlay">
    <!--
    <a-menu-item>
      <a-icon type="user"></a-icon>
      <span>个人中心</span>
    </a-menu-item>
    <a-menu-item>
      <a-icon type="setting"></a-icon>
      <span>设置</span>
    </a-menu-item>
    <a-menu-item>
      <router-link to="/login">
        <a-icon type="poweroff"></a-icon>
        <span>退出登录</span>
      </router-link>
    </a-menu-item>
    <a-menu-divider></a-menu-divider>
    -->
      <a-menu-item>
        <span style="margin-right: 5px">主题颜色</span>
        <span @click.stop="">
          <a-switch :default-checked="initThemeIsLight" size="small"
                    checked-children="亮" un-checked-children="暗"
                    @change="onThemeChange"></a-switch>
        </span>
      </a-menu-item>
      <a-menu-item>
        <span style="margin-right: 5px">菜单布局</span>
        <span @click.stop="">
          <a-switch :default-checked="initMenuIsSide" size="small"
                    checked-children="侧" un-checked-children="顶"
                    @change="onMenuChange"></a-switch>
        </span>
      </a-menu-item>
      <a-menu-item>
        <span style="margin-right: 5px">多标签页</span>
        <span @click.stop="">
          <a-switch :default-checked="initMultiTab" size="small"
                    checked-children="开" un-checked-children="关"
                    @change="onTabChange"></a-switch>
        </span>
      </a-menu-item>
    </a-menu>
  </a-dropdown>
</template>

<script>
  module.exports = {
    name: 'HeaderAvatar',
    computed: {
      currUser () {
        return {
          "avatarType": "icon",
          "avatar": "setting",
          "name": "设置"
        };
      },

      initThemeIsLight() {
        return this.$store.state.setting.theme === "light";
      },

      initMenuIsSide() {
        return this.$store.state.setting.layout === "side";

      },

      initMultiTab() {
        return this.$store.state.setting.multipage;
      }
    },

    methods: {
      onThemeChange(isLight) {
        this.$store.commit('setting/setTheme', isLight ? "light" : "dark");
      },
      onMenuChange(isSide) {
        this.$store.commit('setting/setLayout', isSide ? "side" : "head");
      },

      onTabChange(isOpen) {
        this.$store.commit('setting/setMultipage', isOpen);
      }
    }
  }
</script>

<style scoped>
  .avatar {
    margin: 20px 4px 20px 0;
    color: #1890ff;
    background: rgba(255, 255, 255, 0.85);
    vertical-align: middle;
  }
</style>
