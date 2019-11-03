<template>
  <div style="margin: -24px -24px 0px">
    <page-header :breadcrumb="breadcrumb" :title="title" :logo="logo" :avatar="avatar">
      <slot name="action"  slot="action"></slot>
      <slot slot="content" name="headerContent"></slot>
      <div slot="content" v-if="!this.$slots.headerContent && desc">
        <p style="font-size: 14px;line-height: 1.5;color: rgba(0,0,0,.65)">{{desc}}</p>
        <div class="link">
          <template  v-for="(link, index) in linkList">
            <a :key="index" :href="link.href"><a-icon :type="link.icon" />{{link.title}}</a>
          </template>
        </div>
      </div>
      <slot slot="extra" name="extra"></slot>
    </page-header>
    <div ref="page" :class="['page-content', layout]" >
      <slot ></slot>
    </div>
  </div>
</template>

<script>
  const PageHeader = httpVueLoader('./PageHeader.vue');

  module.exports = {
  name: 'PageLayout',
  components: {PageHeader},
  props: ['desc', 'logo', 'title', 'avatar', 'linkList', 'extraImage'],
  data () {
    return {
      breadcrumb: []
    }
  },
  computed: {
    layout () {
      return this.$store.state.setting.layout
    }
  },
  mounted () {
    this.getBreadcrumb()
  },
  updated () {
    this.getBreadcrumb()
  },
  methods: {
    getBreadcrumb () {
      this.breadcrumb = this.$route.matched
    }
  }
}
</script>

<style scoped>

  .link {
    margin-top: 16px;
    line-height: 24px;
  }
  .link a {
    font-size: 14px;
    margin-right: 32px;
  }
  .link a i {
    font-size: 22px;
    margin-right: 8px;
  }
  .page-content.side {
    margin: 24px 24px 0px;
  }
  .page-content.head {
    margin: 24px auto 0;
    max-width: 1400px;
  }
</style>
