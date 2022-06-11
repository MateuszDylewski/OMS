<template>
  <meta name="viewport"
        content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <div class="app">
    <SideBar class="sidebar"/>
    <SidebarMobile class="sidebarMobile"/>
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
import SideBar from "./components/sidebar/SideBar";
import SidebarMobile from "@/components/sidebar/SidebarMobile";

export default {
  name: 'App',
  components: {
    SidebarMobile,
    SideBar
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
    /*border: 2px solid red;*/
    margin-left: 220px;
  }
}
@media screen and (max-width: 1095px) {
  .sidebar{
    display: none;
  }
  /*TO DO wyznaczyć wielokość mainBox dla urządzeń mobilnych*/
}
</style>