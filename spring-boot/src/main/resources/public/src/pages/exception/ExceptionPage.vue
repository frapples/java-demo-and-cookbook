<template>
  <div class="exception">
    <div class="img">
      <img :src="config[type].img" />
    </div>
    <div class="content">
      <h1>{{config[type].title}}</h1>
      <div class="desc">{{config[type].desc}}</div>
      <div class="action">
        <a-button type="primary" >返回首页</a-button>
      </div>
    </div>
  </div>
</template>

<script>
  const config = {
    403: {
      img: 'https://gw.alipayobjects.com/zos/rmsportal/wZcnGqRDyhPOEYFcZDnb.svg',
      title: '403',
      desc: '抱歉，你无权访问该页面'
    },
    404: {
      img: 'https://gw.alipayobjects.com/zos/rmsportal/KpnpchXsobRgLElEozzI.svg',
      title: '404',
      desc: '抱歉，你访问的页面不存在或仍在开发中'
    },
    500: {
      img: 'https://gw.alipayobjects.com/zos/rmsportal/RVRUAYdCGeYNBWoKiIwB.svg',
      title: '500',
      desc: '抱歉，服务器出错了'
    }
  }

  module.exports = {
  name: 'ExceptionPage',
  props: ['type'],
  data () {
    return {
      config: config
    }
  }
}
</script>

<style scoped>

  .exception {
    min-height: 500px;
    height: 80%;
    align-items: center;
    text-align: center;
    margin-top: 150px;
  }
  .exception .img {
    display: inline-block;
    padding-right: 52px;
    zoom: 1;
  }
  .exception .img img {
    height: 360px;
    max-width: 430px;
  }
  .exception .content {
    display: inline-block;
    flex: auto;
  }
  .exception .content h1 {
    color: #434e59;
    font-size: 72px;
    font-weight: 600;
    line-height: 72px;
    margin-bottom: 24px;
  }
  .exception .content .desc {
    color: rgba(0, 0, 0, 0.45);
    font-size: 20px;
    line-height: 28px;
    margin-bottom: 16px;
  }
</style>
