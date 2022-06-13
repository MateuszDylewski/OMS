<template>
  <div>
    <button type="button" @click="submit">CLICK</button>
    <input type="email" v-model="credentials.email"/>
    <input type="password" v-model="credentials.password"/>
  </div>
</template>

<script>
import { property } from "lodash";
import Multiselect from "vue-multiselect";

export default {
  components: { Multiselect },
  data() {
    return {
      credentials: {
        email: '',
        password: ''
      }
    }
  },
  methods: {
    submit: async function() {
      let formBody = []
      for(let property in this.credentials) {
        let key = encodeURIComponent(property);
        let value = encodeURIComponent(this.credentials[property]);
        formBody.push(key + "=" + value);
      }
      formBody = formBody.join("&");

      const requestOptions = {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
        },
        body: formBody
      }

      await fetch('/login', requestOptions);
        // .then(response => response.json())
        // .then(data => {
        //   console.log(data);
        //   //document.cookie = `accessToken=${data.accessToken}`;
        //   //document.cookie = `refreshToken=${data.refreshToken}`;
        // });
    }
  }
};
</script>

<style scoped>

</style>