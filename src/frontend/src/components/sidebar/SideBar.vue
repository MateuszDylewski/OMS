<template>
  <div class="sidebar">
    <div class="logo">
      OMS
    </div>
    <div class="links">
      <SidebarLink icon="home" to="/">Początek</SidebarLink>
      <SidebarLink icon="calendar" to="/calendar">Kalendarz</SidebarLink>
      <SidebarLink icon="users" to="/users" v-if="user.userRole === 'ADMIN' || user.userRole === 'HRUSER'">Użytkownicy</SidebarLink>
      <SidebarLink icon="desktop" to="/space">Miejsca</SidebarLink>
      <SidebarLink icon="book" to="/myReservations">Moje rezerwacje</SidebarLink>
    </div>
    <button class="btn btn-secondary logoutButton" @click="logout">
      Wyloguj
    </button>
  </div>
</template>

<script>
  import SidebarLink from "@/components/sidebar/SidebarLink";
  import { deleteCookie } from "../../services/authentication/userAuthenticationService";
  import { mapGetters } from "vuex";

  export default {
    props: {},
    components: {SidebarLink},
    computed: {
      ...mapGetters(['user'])
    },
    methods: {
      logout: function (){
        deleteCookie('refreshToken');
        deleteCookie('accessToken');
        sessionStorage.clear();
        this.$store.dispatch('user', null);
        this.$router.push('/login');
      }
    }
  }
</script>

<style scoped>
.sidebar {
  color: white;
  background-color: var(--bg-color);

  float: left;
  position: fixed;
  z-index: 3;
  top: 0;
  left: 0;
  bottom: 0;
  width: 200px;

  transition: 0.2s;

  display: flex;
  flex-direction: column;
}

.logo {
  padding: 10px;
  font-size: 180%;
  font-weight: bold;
  text-align: center;
}

.logoutButton {
  position: absolute;
  top: calc(100vh - 50px);
  left: 50px;
  width: 100px;
}

.oms {
  display: inline;
}

.links {
  padding-top: 10px;
}
</style>