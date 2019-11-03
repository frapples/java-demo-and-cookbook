<template>
  <transition name="page-toggle">
    <keep-alive v-if="multipage">
      <router-view></router-view>
    </keep-alive>
    <router-view v-else></router-view>
  </transition>
</template>

<script>
  module.exports = {
    name: 'RouteView',
    computed: {
      multipage () {
        return this.$store.state.setting.multipage
      }
    }
  }
</script>

<style scoped>

</style>
