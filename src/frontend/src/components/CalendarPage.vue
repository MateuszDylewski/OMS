<template>
  <div class="myTable">
    <div class="headerSection d-flex">
      <h4 class="header">Kalendarz</h4>
      <div class="ml-auto mt-auto mb-auto mr-2">
        <button class="btn btn-sm btn-primary" @click="showNewEventModal = true">Dodaj wydarzenie</button>
        <Teleport to="body">
          <NewEventModal :show="showNewEventModal" @close="showNewEventModal = false">
            <template #header>
              <h5>Nowe wydarzenie</h5>
            </template>
          </NewEventModal>
        </Teleport>
      </div>
    </div>
    <CalendarCompactView :events="events" class="mobile-calendar"/>
    <Calendar :events="events" class="desktop-calendar"/>
  </div>
</template>

<script>

import Calendar from './calendar/Calendar.vue';
import CalendarCompactView from './calendar/CalendarCompactView.vue';
import NewEventModal from "@/components/calendar/NewEventModal";
import { getCookie } from '../services/authentication/userAuthenticationService';

export default {
  components: {
    NewEventModal,
    CalendarCompactView,
    Calendar
  },
  data() {
    return {
      showNewEventModal: false,
      events: []
    }
  },
  mounted() {
    const requestOptions = {
      method: 'GET',
      headers: {
        'Authorization': 'Bearer ' + getCookie('accessToken')
      }
    }
    fetch("/api/event/" + sessionStorage.getItem('userId'), requestOptions)
        .then(response => response.json())
        .then(data => {
          this.events = data;
        });
  }
}
</script >

<style lang='scss' scoped>
/*div {
  border: red 1px solid;
}*/
.myTable {
  border: #EAEAEA 2px solid;
  border-radius: 10px;
  padding-bottom: 10px;
}

.headerSection {
  border-bottom: #EAEAEA 2px solid;
  background: #EAEAEA;
  color: #4A5056;
}

.header{
  padding: 8px 8px 0 8px;
}

@media screen and (min-width: 1096px){
    .mobile-calendar{
        display: none;

    }
}
@media screen and (max-width: 1095px) {
    .desktop-calendar {
        display: none;
    }
}


</style>
