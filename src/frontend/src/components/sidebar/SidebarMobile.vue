<template>
  <div class="sidebarMobile">
    <div class="logo">
      OMS
    </div>
    <div class="dropdown">
      <fa class="menuIcon" icon="align-justify" data-toggle="dropdown" size="lg"/>

      <div class="links dropdown-menu">
        <SidebarLink class="dropdown-item" icon="home" to="/">Początek</SidebarLink>
        <SidebarLink class="dropdown-item" icon="users" to="/users">Użytkownicy</SidebarLink>
        <SidebarLink class="dropdown-item" icon="calendar" to="/calendar">Kalendarz</SidebarLink>
        <SidebarLink class="dropdown-item" icon="desktop" to="/space">Miejsca</SidebarLink>
        <SidebarLink class="dropdown-item" icon="book" to="/myReservations">Moje rezerwacje</SidebarLink>
      </div>
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

.sidebarMobile {
  color: white;
  background-color: var(--bg-color);
}

.menuIcon {

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