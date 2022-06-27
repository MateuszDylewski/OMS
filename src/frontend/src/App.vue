<template>
  <div class="app">
    <SideBar class="sidebar" v-if="user"/>
    <SidebarMobile class="sidebarMobile" v-if="user"/>
    <router-view class="mainBox"/>
  </div>
</template>

<style>
:root {
  --bg-color: #455073;
  --item-hover: #cf8825;
  --item-active: #c0904d;
}
</style>

<script>
import SideBar from './components/sidebar/SideBar';
import SidebarMobile from '@/components/sidebar/SidebarMobile';
import { mapGetters } from 'vuex';
import { getCookie, setCookie, deleteCookie } from './services/authentication/userAuthenticationService';

export default {
  name: 'App',
  components: {
    SidebarMobile,
    SideBar
  },
  computed: {
    ...mapGetters(['user'])
  },
  mounted() {
    const refreshToken = getCookie('refreshToken');
    fetch(`/token/refresh`, {
        headers: {
            'Authorization': 'Bearer ' + refreshToken
        }
    })
      .then(response => {
        if(response.status === 200) {
          response.json().then(data => {
            setCookie('accessToken', data.accessToken, 1);
            fetch(`/api/users/byEmail`, {
              headers: {
                'Authorization': 'Bearer ' + getCookie('accessToken')
              }
            })
            .then(response => response.json())
            .then(data => {
              sessionStorage.setItem('userId', data.userId);
              this.$store.dispatch('user', data);
            });
          });
        } else {
          deleteCookie('refreshToken');
          deleteCookie('accessToken');
          sessionStorage.clear();
          this.$store.dispatch('user', null);
          this.$router.push('/login');
        }
      })
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300;500;700&display=swap');
.app {
  font-family: Roboto;

}

.mainBox {
  margin: 20px;
}

@media screen and (min-width: 1096px){
  .sidebarMobile{
    display: none;

  }
  .mainBox {
    margin-left: 220px;
  }
}
@media screen and (max-width: 1095px) {
  .sidebar{
    display: none;
  }
}
</style>