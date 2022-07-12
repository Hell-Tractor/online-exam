<template>
  <div id="app">
    <router-view />
    <back-to-top :visibility-height="100" :back-position="0" transition-name="fade" ref="backTop"/>
  </div>

</template>

<script>
// 返回顶端的部件
import BackToTop from '@/components/BackToTop'

export default {
  name: 'App',
  components: {
    BackToTop
  }
}
</script>
