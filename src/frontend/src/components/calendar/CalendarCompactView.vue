<template>
  <div class="ml-auto mr-auto">
    <v-calendar
        class="calendar w-100"
        :attributes="attributes"
        disable-page-swipe>
    </v-calendar>
    <Teleport to="body">
      <EditEventModal :show="showEditEventModal" :eventId="editEventId" @close="showEditEventModal = false">
        <template #header/>
      </EditEventModal>
    </Teleport>
    <div class="table-responsive">
      <table class="table table-hover table-sm table-res" style="padding: 15px">
        <thead>
          <tr>
            <th></th>
            <th>Wydarzenie</th>
            <th>Data</th>
            <th>Początek</th>
            <th>Koniec</th>
            <th>Ilość uczestników</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="event in events" :key="event.eventId"
              class="tableRow text-center"
              @click="showEditEventModal = true; editEventId = event.eventId">
            <td :class="'btn-' + event.color"/>
            <td class="align-middle text-left">{{ event.title }}</td>
            <td class="align-middle">{{ this.convertToDisplayDate(event.startDateTime) }}</td>
            <td class="align-middle">{{ this.convertToDisplayTime(event.startDateTime) }}</td>
            <td class="align-middle">{{ this.convertToDisplayTime(event.endDateTime) }}</td>
            <td class="align-middle">{{ event.countParticipants}}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import 'v-calendar/dist/style.css';
import EditEventModal from "@/components/calendar/EditEventModal";

export default {
  components: {EditEventModal},
  props: {
    events: {type: Array, required: true}
  },
  data() {
    return {
      editEventId: Number,
      showEditEventModal: false,
      todayDay: new Date().getDate(),
      todayMonth: new Date().getMonth() + 1,
      popperPlacement: "top-end",
      mapVCalColors: {
        danger: 'red',
        secondary: 'gray',
        warning: 'yellow',
        info: 'teal',
        primary: 'blue'
      }
    };
  },
  methods: {
    convertToDisplayDate: function (date) {
      return (new Date(date).getDate() < 10 ? "0" : "")
      + new Date(date).getDate() + "."
      + (new Date(date).getMonth() < 10 ? "0" : "")
      + new Date(date).getMonth() + "."
      + new Date(date).getFullYear();
    },
    convertToDisplayTime: function (date) {
      return (new Date(date).getHours() < 10 ? "0" : "")
          + new Date(date).getHours() + ":"
          + (new Date(date).getMinutes() < 10 ? "0" : "")
          + new Date(date).getMinutes();
    }
  },
  computed: {
    attributes() {
      return [
        ...this.events.map(event => ({
          dates: [event.startDateTime, event.endDateTime],
          key: event.id,
          customData: {
            title: event.title,
            class: 'bg-'+event.color+' text-white',
            start: new Date(event.startDateTime).getHours()
                + ':'
                + (new Date(event.startDateTime).getMinutes() < 10 ? '0' : '')
                + new Date(event.startDateTime).getMinutes(),
            startDay: new Date(event.startDateTime).getDate(),
            end: new Date(event.endDateTime).getHours()
                + ':'
                + (new Date(event.endDateTime).getMinutes() < 10 ? '0' : '')
                + new Date(event.endDateTime).getMinutes(),
            endDay: new Date(event.endDateTime).getDate()
          },
          popover: {
            label: new Date(event.startDateTime).getHours()
                + ':'
                + (new Date(event.startDateTime).getMinutes() < 10 ? '0' : '')
                + new Date(event.startDateTime).getMinutes()
                + '-' +
                new Date(event.endDateTime).getHours()
                + ':'
                + (new Date(event.endDateTime).getMinutes() < 10 ? '0' : '')
                + new Date(event.endDateTime).getMinutes()
                + ' ' + event.title,
            visibility: 'click'
          },
          bar: {
            color: this.mapVCalColors[event.color]
          }
        })),
        {
          key: 'today',
          highlight: {
            color: 'yellow',
            fillMode: 'outline'
          },
          dates: new Date()
        }
      ];
    },
  },
};
</script>

<style lang="scss" scoped>
.table th:first-child,
.table td:first-child {
  position: sticky;
  left: 0;
}

.events {
  line-height: 95%;
}

.tableRow{
  cursor: pointer;
}

::v-deep .calendar.vc-container {
  --day-border: 1px solid #b8c1cc;
  --day-width: 30px;
  --day-height: 50px;
  --weekday-bg: #EAEAEA;
  --weekday-border: 1px solid #eaeaea;

  border-radius: 0;

  & .vc-header {
    padding: 10px;
  }
  & .vc-weeks {
    padding: 0;
  }
  & .vc-weekday {
    background-color: var(--weekday-bg);
    border-bottom: var(--weekday-border);
    border-top: var(--weekday-border);
    color: #4A5056;
    padding: 5px 0;
  }
  & .vc-day {
    padding: 3px 5px 3px 5px;
    text-align: left;
    background-color: white;
    height: var(--day-height);
    min-width: var(--day-width);

    &.weekday-1,
    &.weekday-7 {
      background-color: #fff5e6;
    }
    &:not(.on-bottom) {
      border-bottom: var(--day-border);
    }
    &:not(.on-right) {
      border-right: var(--day-border);
    }
  }

}
</style>