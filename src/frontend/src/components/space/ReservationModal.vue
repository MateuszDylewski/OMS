<template>
  <Transition name="modal">
    <div v-if="show" class="modal-mask">
      <div class="modal-wrapper">
        <div class="modal-container">
          <div class="modal-header">
            <slot name="header" class="modal-title">Modal title</slot>
            <button type="button" class="close" @click="$emit('close')" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <form novalidate>
              <div class="form-row">
                <div class="col details">
                  <h6>Szczegóły</h6>
                  <div>
                    {{"Nazwa: " + space.name}}
                  </div>
                  <div>
                    {{"Typ: " + (space.type === "desk" ? "Biurko" : space.type === "room" ? "Salka konferencyjna" : "Miejsce parkingowe")}}
                  </div>
                  <div>
                    {{"Piętro: " + space.floor}}
                  </div>
                  <div>
                    {{"Ilość miejsc: " + space.allowedAttendees}}
                  </div>
                  <div>
                    {{"Powierzchnia: " + space.size}}
                  </div>
                  <div>
                    {{"Opis: \n" + space.description}}
                  </div>
                  <br/>
                </div>
                <div class="col">
                  <h6>Kalendarz</h6>
                  <div class="bookDatePicker">
                    <v-date-picker
                        v-model="range"
                        :disabled-dates='reservations'
                        :min-date="new Date()"
                        is-required
                        is-range
                    />
                  </div>
                </div>
              </div>
              <div class="form-row">
                <button type="submit" class="btn btn-primary btn-sm ml-auto" @click="saveReservation">Zarazerwuj</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </Transition>
</template>

<script>
import { getCookie } from '../../services/authentication/userAuthenticationService';

export default {
  props: {
    show: Boolean,
    space: {}
  },
  data() {
    return {
      range: {
        start: new Date(),
        end: new Date(),
      },
      reservations: []
    }
  },
  watch: {
    show: async function() {
      this.range.start = new Date();
      this.range.end = new Date();
      await fetch(`/api/reservation/${this.space.spaceId}`, {
        headers: {
          'Authorization': 'Bearer ' + getCookie('accessToken')
        }
      })
        .then(response => response.json())
        .then(data => {
          this.reservations = data.map(reservation => ({
            start: reservation.startDate,
            end: reservation.endDate,
          }));
        });
    }
  },
  methods: {
    isSelectedDateValid: function() {
      const selectedStart = this.convertDate(this.range.end);
      const selectedEnd = this.convertDate(this.range.end);

      for (let i = 0; i < this.reservations.length - 1; i++) {
        if(selectedStart === this.reservations[i].start || selectedEnd === this.reservations[i].end){
          return false;
        }
      }
      return true;
    },
    convertDate: function (date) {
      let yy = date.getFullYear();
      let mm = date.getMonth() + 1;
      let dd = date.getDate();
      if (dd < 10) {
        dd = '0' + dd;
      }
      if (mm < 10) {
        mm = '0' + mm;
      }
      return yy + "-" + mm + "-" + dd;
    },
    saveReservation: async function() {
      const requestOptions = {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          'Authorization': 'Bearer ' + getCookie('accessToken')
        },
        body: JSON.stringify({
          spaceId: this.space.spaceId,
          startDate: this.convertDate(this.range.start),
          endDate: this.convertDate(this.range.end),
          userId: sessionStorage.getItem('userId')
        })
      };
      await fetch("/api/reservation", requestOptions)
          .then(response => response.json());
    }
  }
}
</script>

<style scoped>
/*div {
  border: red 1px solid;
}*/

.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: table;
  transition: opacity 0.3s ease;
}

.modal-wrapper {
  display: table-cell;
  vertical-align: middle;
}

.modal-container {
  max-height: 80%;
  width: 42%;
  margin: 0px auto;
  border-radius: 5px;
  background-color: #fff;
  transition: all 0.2s ease;
  overflow-y: auto;
}

@media screen and (max-width: 1200px) {
  .modal-container {
    width: 55%;
  }
}

@media screen and (max-width: 1000px) {
  .modal-container {
    width: 65%;
  }
}

@media screen and (max-width: 800px) {
  .modal-container {
    width: 75%;
  }
}

.modal-header {

}

.modal-body {

}

.bookDatePicker {
  margin: auto;
  width: 252px;
  padding-bottom: 15px;
}

.details {
  min-width: 200px;
}

.modal-enter-from {
  opacity: 0;
}

.modal-leave-to {
  opacity: 0;
}

.modal-enter-from .modal-container,
.modal-leave-to .modal-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}
</style>