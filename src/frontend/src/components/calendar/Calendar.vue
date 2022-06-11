<template>
  <div class="ml-auto mr-auto">
    <Teleport to="body">
      <EditEventModal :show="showEditEventModal" :eventId="editEventId" @close="showEditEventModal = false">
        <template #header/>
      </EditEventModal>
    </Teleport>
    <v-calendar
        class="calendar w-100"
        :masks="masks"
        :attributes="attributes"
        disable-page-swipe>
      <template v-slot:day-content="{ day, attributes }">
        <div class="d-flex flex-column h-100" >
          <div class="day"
               v-bind:class="{active: day.day === todayDay && day.month === todayMonth && day.year === todayYear}">
            {{ day.day }}
          </div>
          <div class="events overflow-auto">
            <div
                v-for="event in attributes"
                :key="event.key"
                class="text-sm text-wrap text-break leading-tight rounded-sm w-100 mt-0 p-1 mb-1"
                :class="event.customData.class"
                @click="editEventId = parseInt(event.key); showEditEventModal = true"
            >
              <div v-if="event.customData.startDay === event.customData.endDay">
                {{event.customData.start}}-{{event.customData.end}}
              </div>
              <div v-else>
                <div v-if="event.customData.startDay === day.day">
                  {{event.customData.start}}
                </div>
                <div v-else>
                  {{event.customData.end}}
                </div>
              </div>
              <div>
                {{ event.customData.title }}
              </div>
            </div>
          </div>
        </div>
      </template>
    </v-calendar>
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
      editEventId: -1,
      showEditEventModal: false,
      masks: {
        weekdays: 'WWW',
      },
      todayDay: new Date().getDate(),
      todayMonth: new Date().getMonth() + 1,
      todayYear: new Date().getFullYear(),
    };
  },
  computed: {
    attributes() {
      return [
        ...this.events.map(event => ({
          dates: [event.startDateTime, event.endDateTime],
          key: event.eventId,
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
          }
        }))
      ];
    },
  },
};
</script>

<style lang="scss" scoped>
/*div {
  border: red 1px solid;
}*/

.day.active{
  outline: var(--item-hover) 2px solid;
  padding-left: 5px;
  border-radius: 50%;
  width: 25px;
  display: inline-block;
}

.events {
  line-height: 95%;
}

::v-deep .calendar.vc-container {
  --day-border: 1px solid #b8c1cc;
  --day-width: 30px;
  --day-height: 100px;
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
    color: #4A5056;
    border-bottom: var(--weekday-border);
    border-top: var(--weekday-border);
    padding: 5px 0;
  }
  & .vc-day {
    padding: 3px 5px 3px 5px;
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