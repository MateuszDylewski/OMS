<template>
  <div class="sidebar">
    <div class="logo">
      OMS
    </div>
    <div class="links">
      <SidebarLink icon="home" to="/">Początek</SidebarLink>
      <SidebarLink icon="users" to="/users">Użytkownicy</SidebarLink>
      <SidebarLink icon="calendar" to="/calendar">Kalendarz</SidebarLink>
      <SidebarLink icon="desktop" to="/space">Miejsca</SidebarLink>
      <SidebarLink icon="book" to="/myReservations">Moje rezerwacje</SidebarLink>
    </div>
  </div>
</template>

<script>
  import SidebarLink from "@/components/sidebar/SidebarLink";

  export default {
    props: {},
    components: {SidebarLink},
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

.oms {
  display: inline;
}

.links {
  padding-top: 10px;
}
</style>