<template>
  <div class="sidebarMobile">
    <div class="logo">
      OMS
    </div>
    <div class="dropdown">
      <fa class="menuIcon" icon="align-justify" data-toggle="dropdown" size="lg"/>

      <div class="links dropdown-menu">
        <SidebarLink class="dropdown-item" icon="home" to="/">Początek</SidebarLink>
        <SidebarLink class="dropdown-item" icon="users" to="/users" v-if="user.userRole === 'ADMIN' || user.userRole === 'HRUSER'">Użytkownicy</SidebarLink>
        <SidebarLink class="dropdown-item" icon="calendar" to="/calendar">Kalendarz</SidebarLink>
        <SidebarLink class="dropdown-item" icon="desktop" to="/space">Miejsca</SidebarLink>
        <SidebarLink class="dropdown-item" icon="book" to="/myReservations">Moje rezerwacje</SidebarLink>
      </div>
    </div>
    <button class="btn btn-secondary logoutButton" @click="logout">
      Wyloguj
    </button>
  </div>
</template>

<script>
import SidebarLink from "@/components/sidebar/SidebarLink";
import { deleteCookie } from "../../services/authentication/userAuthenticationService";
import { mapGetters } from 'vuex';

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

.sidebarMobile {
  color: white;
  background-color: var(--bg-color);
}

.logo {
  padding: 10px;
  font-size: 180%;
  font-weight: bold;
  text-align: center;
}

.links {
  padding-top: 10px;
  background-color: var(--bg-color);
}

.logoutButton {
  position: absolute;
  left: calc(100vw - 120px);
  top: 10px;
  width: 100px;
}

.dropdown {
  position: absolute;
  top: 10px;
  left: 0;
  padding: 5px 10px 5px 10px;
  font-size: 150%;
}

.dropdown-item:hover {
  color: white;
}
</style>