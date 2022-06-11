<template>
  <Transition name="modal">
    <div v-if="show" class="modal-mask">
      <div class="modal-wrapper">
        <div class="modal-container">
          <div class="modal-header">
            <slot name="header" class="modal-title">
              <h5 v-if="!isEditModeEnabled">Szczegoły wydarzenia</h5>
              <h5 v-else>Edycja wydarzenia</h5>

            </slot>
            <button type="button" class="close"
                    @click="$emit('close'); isEditModeEnabled=false"
                    aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <form class="requires-validation" @submit="submit" novalidate>
              <div class="form-row">
                <div class="form-check m-auto">
                  <input class="form-check-input" name="color"
                         @click="color = 'primary'" type="radio"
                         id="primaryRadio" :disabled="isReadOnly()"
                         :checked="color === 'primary'">
                  <label class="form-check-label" for="primaryRadio">
                    <div class="box primary"/>
                  </label>
                </div>
                <div class="form-check m-auto">
                  <input class="form-check-input" name="color"
                         @click="color = 'secondary'" type="radio"
                         id="secondaryRadio" :disabled="isReadOnly()"
                         :checked="color === 'secondary'">
                  <label class="form-check-label" for="secondaryRadio">
                    <div class="box secondary"/>
                  </label>
                </div>
                <div class="form-check m-auto">
                  <input class="form-check-input" name="color"
                         @click="color = 'info'" type="radio"
                         id="infoRadio" :disabled="isReadOnly()"
                         :checked="color === 'info'">
                  <label class="form-check-label" for="infoRadio">
                    <div class="box info"/>
                  </label>
                </div>
                <div class="form-check m-auto">
                  <input class="form-check-input" name="color"
                         @click="color = 'danger'" type="radio"
                         id="dangerRadio" :disabled="isReadOnly()"
                         :checked="color === 'danger'">
                  <label class="form-check-label" for="dangerRadio">
                    <div class="box danger"/>
                  </label>
                </div>
              </div>
              <div class="form-row">
                <div class="form-group col">
                  <label for="titleInput">Tytuł</label>
                  <input class="form-control" v-model="title" id="titleInput"
                         type="text" :disabled="isReadOnly()" required/>
                </div>
                <div class="form-group col">
                  <label for="creatorInput">Organizator</label>
                  <input class="form-control" v-model="creator.displayName" id="creatorInput"
                         type="text" :disabled="true" required/>
                </div>
              </div>
              <div class="form-row">
                <div class="form-group col">
                  <label for="durationInput">Czas trwania</label>
                  <input class="form-control" v-model="duration" id="durationInput"
                         type="number" :disabled="isReadOnly()" required/>
                </div>
                <div class="form-group col">
                  <v-date-picker v-model="startDateTime" mode="dateTime" is24hr>
                    <template v-slot="{ inputValue, inputEvents }">
                      <label for="dateInput">Data</label>
                      <input class="form-control" :value="inputValue"
                             id="dateInput" v-on="inputEvents" required
                             :disabled="isReadOnly()"/>
                    </template>
                  </v-date-picker>
                </div>
              </div>
              <div class="form-row">
                <div class="form-group col">
                  <label for="sizeSelect">Uczestnicy</label>
                  <Multiselect v-model="participants" :options="users" :multiple="true"
                               :close-on-select="false" :max-height="200"
                               :clear-on-select="false" :preserve-search="true"
                               placeholder=""
                               label="displayName" track-by="userId" :searchable="true"
                               :taggable="false" :open-direction="'top'" :disabled="isReadOnly()"/>
                </div>
              </div>
              <div class="form-row">
                <button type="button" class="btn btn-primary btn-sm" @click="openEventRoom()">Otwórz okno spotkania</button>
                <button type="button" class="btn btn-info btn-sm ml-auto"
                        v-if="!isEditModeEnabled && creator.userId === loggedUserIdLocal"
                        @click="isEditModeEnabled = true">Edytuj</button>
                <button type="button" class="btn btn-danger btn-sm"
                        v-if="isEditModeEnabled"
                        @click="deleteEvent()">Usuń</button>
                <button type="submit" class="btn btn-primary btn-sm ml-auto mr-2"
                        v-if="isEditModeEnabled">Zapisz</button>
                <button type="button" class="btn btn-warning btn-sm"
                        v-if="isEditModeEnabled"
                        @click="isEditModeEnabled = false; restartForm()">Anuluj</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </Transition>
</template>

<script>
import Multiselect from "vue-multiselect";
import {loggedUserId} from "@/services/authentication/userAuthenticationService";

export default {
  components: { Multiselect },
  props: {
    show: Boolean,
    eventId: Number
  },
  data() {
    return {
      loggedUserIdLocal: loggedUserId,
      isEditModeEnabled: false,
      users: [],
      participants: [],
      participantsBackUp: [],
      title: '',
      titleBackUp: '',
      startDateTime: Date,
      startDateTimeBackUp: Date,
      duration: 0,
      durationBackUp: 0,
      color: '',
      colorBackUp: '',
      creator: JSON
    }
  },
  mounted() {
    fetch(`/api/users/without/${loggedUserId}`)
        .then(response => response.json())
        .then(data => {
          this.users = data.map(user => ({
            userId: user.userId,
            displayName: user.firstName + " " + user.lastName + " - " + user.occupation
          }))
        });
  },
  watch: {
    show: async function() {
      if(this.show === true) {
        await fetch(`api/event/getEvent/${this.eventId}`)
            .then(response => response.json())
            .then(data => {
              this.title = data.title;
              this.titleBackUp = data.title;
              this.startDateTime = data.startDateTime;
              this.startDateTimeBackUp = data.startDateTime;
              this.duration = data.duration;
              this.durationBackUp = data.duration;
              this.color = data.color;
              this.colorBackUp = data.color;
              this.creator = {
                userId: data.creator.userId,
                displayName: data.creator.firstName + " "
                    + data.creator.lastName + " - "
                    + data.creator.occupation
              };
              this.participants = data.participants.map(user => ({
                userId: user.userId,
                displayName: user.firstName + " " + user.lastName + " - " + user.occupation
              }));
              this.participantsBackUp = data.participants.map(user => ({
                userId: user.userId,
                displayName: user.firstName + " " + user.lastName + " - " + user.occupation
              }))
            })
      }
    }
  },
  methods: {
    openEventRoom: function() {
      console.log("CLICK");
      let route = this.$router.resolve({path: '/eventRoom'});
      window.open(route.href);
      
    },
    restartForm: function () {
      this.title = this.titleBackUp;
      this.startDateTime = this.startDateTimeBackUp;
      this.duration = this.durationBackUp;
      this.participants = this.participantsBackUp;
      this.color = this.colorBackUp;
    },
    isReadOnly: function() {
      return !(this.creator.userId === loggedUserId && this.isEditModeEnabled);
    },
    limitText: function (count) {
      return `${count} uczestników`
    },
    submit: function (event) {
      let isFormCorrect = true;
      const forms = document.querySelectorAll('.requires-validation');
      Array.from(forms)
          .forEach(function (form) {
            if (!form.checkValidity()) {
              event.preventDefault();
              event.stopPropagation();
              isFormCorrect = false;
            }
            form.classList.add('was-validated');
          });
      if(isFormCorrect){
        this.saveEvent(event);
        close()
      }
    },
    saveEvent: async function () {
      let ids = [];
      this.participants.forEach(obj => {
        Object.entries(obj).forEach(([key, value]) => {
          if(key === 'userId') {
            ids.push(value);
          }
        });
      });

      const requestOptions = {
        method: "PUT",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
          eventId: this.eventId,
          title: this.title,
          startDateTime: this.startDateTime,
          duration: this.duration,
          color: this.color,
          creatorId: 1,
          participantsId: ids
        })
      }
      await fetch(`/api/event/update/${this.eventId}`, requestOptions);
    },
    deleteEvent: async function () {
      const requestOptions = {
        method: "DELETE",
        headers: {"Content-Type": "application/json"},
      }
      await fetch(`/api/event/delete/${this.eventId}`, requestOptions);
      window.location.reload();
    }
  }
}
</script>

<style scoped>
/*div {
  border: red 1px solid;
}*/
.box {
  float: left;
  height: 20px;
  width: 20px;
  clear: both;
}

.primary {
  background-color: #017BFE;
}

.secondary {
  background-color: #6C757D;
}

.danger {
  background-color: #DC3444;
}
.info {
  background-color: #14A2B8;
}

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
  width: 50%;
  margin: 0px auto;
  border-radius: 5px;
  background-color: #fff;
  transition: all 0.2s ease;
  /*overflow-y: auto;*/
  overflow: visible;
}

@media screen and (max-width: 1095px) {
  .modal-container {
    width: 80%;
  }
}

.description {
  min-height: 35px;
  max-height: 150px;
}

.modal-header {

}

.modal-body {

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