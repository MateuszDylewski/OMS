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
            <form class="requires-validation" @submit="submit" novalidate>
              <div class="form-row">
                <div class="form-check m-auto">
                  <input class="form-check-input" name="color" @click="color = 'primary'" type="radio" id="primaryRadio" checked>
                  <label class="form-check-label" for="primaryRadio">
                    <div class="box primary"/>
                  </label>
                </div>
                <div class="form-check m-auto">
                  <input class="form-check-input" name="color" @click="color = 'secondary'" type="radio" id="secondaryRadio">
                  <label class="form-check-label" for="secondaryRadio">
                    <div class="box secondary"/>
                  </label>
                </div>
                <div class="form-check m-auto">
                  <input class="form-check-input" name="color" @click="color = 'info'" type="radio" id="infoRadio">
                  <label class="form-check-label" for="infoRadio">
                    <div class="box info"/>
                  </label>
                </div>
                <div class="form-check m-auto">
                  <input class="form-check-input" name="color" @click="color = 'danger'" type="radio" id="dangerRadio">
                  <label class="form-check-label" for="dangerRadio">
                    <div class="box danger"/>
                  </label>
                </div>
              </div>
              <div class="form-row">
                <div class="form-group col">
                  <label for="titleInput">Tytuł</label>
                  <input class="form-control" v-model="title" id="titleInput" type="text" required/>
                </div>
              </div>
              <div class="form-row">
                <div class="form-group col">
                  <label for="durationInput">Czas trwania</label>
                  <input class="form-control" v-model="duration" id="durationInput" type="number" required/>
                </div>
                <div class="form-group col">
                  <v-date-picker v-model="startDateTime" mode="dateTime" is24hr>
                    <template v-slot="{ inputValue, inputEvents }">
                      <label for="dateInput">Data</label>
                      <input class="form-control" :value="inputValue"
                          id="dateInput" v-on="inputEvents" required/>
                    </template>
                  </v-date-picker>
                </div>
              </div>
              <div class="form-row">
                <div class="form-group col">
                  <label>Uczestnicy</label>
                  <Multiselect v-model="participants" :options="users" :multiple="true" :close-on-select="false" :max-height="200"
                               :clear-on-select="false" :preserve-search="true" placeholder="" :limit="0" :limit-text="limitText"
                               label="displayName" track-by="userId" :searchable="true" :taggable="false" :open-direction="'top'"/>
                </div>
              </div>
              <div class="form-check">
                <input class="form-check-input" v-model="createRoom" type="checkbox" value="" id="flexCheckDefault">
                <label class="form-check-label" for="flexCheckDefault">
                  Zaznacz, aby utworzyć wideokonferencje do spotkania.
                </label>
              </div>
              <div class="form-row">
                <button type="submit" class="btn btn-primary btn-sm ml-auto">Zapisz</button>
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
import { v4 as uuidv4 } from 'uuid';
import { getCookie } from "../../services/authentication/userAuthenticationService";

export default {
  components: { Multiselect },
  props: {
    show: Boolean
  },
  data() {
    return {
      users: [],
      participants: [],
      title: '',
      startDateTime: new Date(),
      duration: 0,
      color: 'primary',
      createRoom: false
    }
  },
  mounted() {
    fetch(`/api/users/without/${sessionStorage.getItem('userId')}`, {
      headers: {
        'Authorization': 'Bearer ' + getCookie('accessToken')
      }
    })
      .then(response => response.json())
      .then(data => {
        this.users = data.map(user => ({
          userId: user.userId,
          displayName: user.firstName + " " + user.lastName + " - " + user.occupation
        }))
      });
  },
  methods: {
    limitText (count) {
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
        this.saveEvent();
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
        method: "POST",
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + getCookie('accessToken')
        },
        body: JSON.stringify({
          title: this.title,
          startDateTime: this.startDateTime,
          duration: this.duration,
          color: this.color,
          creatorId: sessionStorage.getItem('userId'),
          participantsId: ids,
          roomId: (this.createRoom ? uuidv4() : null)
        })
      }
      await fetch("/api/event/", requestOptions);
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