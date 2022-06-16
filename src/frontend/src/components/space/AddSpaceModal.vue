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
                <div class="form-group col">
                  <label for="spaceTypeSelect">Typ miejsca</label>
                  <select class="form-control"
                          id="spaceTypeSelect"
                          v-model="selectedType">
                    <option value="desk">Biurko</option>
                    <option value="parking">Miejsce parkingowe</option>
                    <option value="room">Salka konferencyjna</option>
                  </select>
                </div>
                <div class="form-group col">
                  <label for="nameInput">Nazwa</label>
                  <input class="form-control"
                         type="text"
                         id="nameInput"
                         v-model="selectedName"
                         required>
                </div>
              </div>
              <div class="form-row">
                <div class="form-group col">
                  <label for="descriptionTextArea">Opis</label>
                  <textarea class="description form-control"
                            id="descriptionTextArea"
                            placeholder="Krótko opisz miejsce."
                            rows="3"
                            v-model="selectedDescription"></textarea>
                </div>
              </div>
              <div class="form-row">
                <div class="form-group col">
                  <label for="floorInput">Piętro</label>
                  <input class="form-control"
                         type="number"
                         placeholder="0"
                         id="floorInput"
                         v-model="selectedFloor"
                         required>
                </div>
                <div class="form-group col" v-if="selectedType === 'parking'">
                  <label for="sizeSelect">Rozmiar</label>
                  <select class="form-control"
                          id="sizeSelect"
                          v-model="selectedSize"
                          required>
                    <option value="SMALL">Małe</option>
                    <option value="REGULAR">Średnie</option>
                    <option value="LARGE">Duże</option>
                  </select>
                </div>
                <div class="form-group col" v-if="selectedType === 'room'">
                  <label for="allowedAttendeesInput">Ilość miejsc</label>
                  <input class="form-control"
                         type="number"
                         placeholder="0"
                         id="allowedAttendeesInput"
                         v-model="selectedAllowed"
                         required>
                </div>
                <div class="form-group col" v-if="selectedType === 'room'">
                  <label for="areaInput">Powierzchnia</label>
                  <input class="form-control"
                         type="number"
                         placeholder="0"
                         id="areaInput"
                         v-model="selectedArea"
                         required>
                </div>
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
import { getCookie } from '../../services/authentication/userAuthenticationService';

export default {
  props: {
    show: Boolean
  },
  data() {
    return {
      selectedType: 'desk',
      selectedName: '',
      selectedDescription: '',
      selectedSize: '',
      selectedArea: null,
      selectedAllowed: null,
      selectedFloor: null
    }
  },
  methods: {
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
        this.saveSpace();
        close()
      }
    },
    saveSpace: async function () {
      let requestOptions;
      switch (this.selectedType) {
        case "desk":
          requestOptions = {
            method: "POST",
            headers: {
              'Content-Type': 'application/json',
              'Authorization': 'Bearer ' + getCookie('accessToken')
            },
            body: JSON.stringify({
              name: this.selectedName,
              type: this.selectedType,
              floor: this.selectedFloor,
              description: this.selectedDescription
            })
          };
          await fetch("/api/spaces/createDesk", requestOptions)
              .then(response => response.json());
          break;
        case "room":
          requestOptions = {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              'Authorization': 'Bearer ' + getCookie('accessToken')
            },
            body: JSON.stringify({
              name: this.selectedName,
              type: this.selectedType,
              floor: this.selectedFloor,
              description: this.selectedDescription,
              area: this.selectedArea,
              allowedAttendees: this.selectedAllowed
            })
          };
          await fetch("/api/spaces/createRoom", requestOptions)
              .then(response => response.json());
          break;
        case "parking":
          requestOptions = {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              'Authorization': 'Bearer ' + getCookie('accessToken')
            },
            body: JSON.stringify({
              name: this.selectedName,
              type: this.selectedType,
              floor: this.selectedFloor,
              description: this.selectedDescription,
              size: this.selectedSize
            })
          };
          await fetch("/api/spaces/createParking", requestOptions)
              .then(response => response.json());
          break;
      }
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
  width: 50%;
  margin: 0px auto;
  border-radius: 5px;
  background-color: #fff;
  transition: all 0.2s ease;
  overflow-y: auto;
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