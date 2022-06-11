<template>
  <div>

  </div>
</template>

<style>

</style>

<script>
import {useRoute} from "vue-router";

export default {
  name: 'HelloUser',
  data() {
    return {
      msg: ''
    }
  },
  mounted() {
    const path = useRoute().path;
    fetch("/api/message" + path)
      .then((response) => response.text())
      .then((message) => {
        this.msg = message;
      })
  }
}
</script>