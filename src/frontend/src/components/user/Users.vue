<template>
  <div class="myTable">
    <div class="d-flex headerSection">
      <div class="users">
        <h4 class="header">Lista użytkowników</h4>
      </div>
      <div class="usersUnder">
        <h4 class="header">Podwładni</h4>
      </div>
      <div class="details">
        <h4 class="header">Szczegóły użytkownika</h4>
      </div>
    </div>
    <div class="d-flex">
      <div class="users">
        <div class="user d-flex" v-for="user in users" :key="user.userId"
             @click="selectUser(user); showSubordinates(user); this.activeUser = user;"
             :class="highlightUsers(user)">
          <span class="ml-2">{{user.firstName}}</span>
          <span class="ml-1">{{user.lastName}}</span>
          <span class="ml-auto mr-5">{{"p" + user.userId}}</span>
        </div>
      </div>
      <div class="usersUnder">
        <div class="user d-flex" v-for="subordinate in subordinates" :key="subordinate.userId"
             @click="selectUser(subordinate); this.activeSubordinate = subordinate;"
             :class="highlightSubordinates(subordinate)">
          <span class="ml-2">{{subordinate.firstName}}</span>
          <span class="ml-1">{{subordinate.lastName}}</span>
          <span class="ml-auto mr-5">{{"p" + subordinate.userId}}</span>
        </div>
      </div>
      <form class="details requires-validation" novalidate>
        <div class="form-group information">
          <label for="email">Email</label>
          <input type="email" class="form-control"
                  :disabled="!editModeOn && !addModeOn" id="email"
                  placeholder="Email" v-model="this.userToEdit.email"
                  required>
        </div>
        <div class="form-group information" v-if="addModeOn">
          <label for="password">Hasło</label>
          <input type="password" class="form-control"
                  :disabled="!editModeOn && !addModeOn" id="password"
                  placeholder="Hasło" v-model="this.userToEdit.password"
                  required>
        </div>
        <div class="form-group information">
          <label for="name">Imię</label>
          <input type="text" class="form-control"
                  :disabled="!editModeOn && !addModeOn" id="name"
                  placeholder="Imię" v-model="this.userToEdit.firstName"
                  required>
        </div>
        <div class="form-group information">
          <label for="surname">Nazwisko</label>
          <input type="text" class="form-control"
                  :disabled="!editModeOn && !addModeOn" id="surname"
                  placeholder="Nazwisko" v-model="this.userToEdit.lastName"
                  required>
        </div>
        <div class="form-group information">
          <label for="dob">Data urodzenia</label>
          <v-date-picker v-model="this.userToEdit.dateOfBirth"
                          :max-date="new Date()">
            <template v-slot="{ inputValue, inputEvents }">
              <input
                  class="form-control"
                  v-on="inputEvents"
                  :value="inputValue"
                  :disabled="!editModeOn && !addModeOn" id="dob"
                  placeholder="DD-MM-YYYY"
                  required
              />
            </template>
          </v-date-picker>

        </div>
        <div class="form-group information">
          <label for="occupation">Stanowisko</label>
          <input type="text" class="form-control"
                  :disabled="!editModeOn && !addModeOn" id="occupation"
                  placeholder="Pracownik" v-model="this.userToEdit.occupation"
                  required>
        </div>
        <div class="form-group information">
          <label for="role">Rola</label>
          <select class="form-control information"
                  :disabled="!editModeOn && !addModeOn" id="role"
                  v-model="this.userToEdit.userRole"
                  required>
            <option value="USER">User</option>
            <option value="HRUSER">HRUser</option>
            <option value="ADMIN">Admin</option>
          </select>
        </div>
        <div class="form-group information">
          <label for="manager">Menadżer</label>
          <input type="number" class="form-control"
                  :disabled="!editModeOn && !addModeOn" id="manager"
                  placeholder="Brak" v-model="this.userToEdit.managerId">
        </div>
        <div class="form-group d-flex">
          <div class="information " @click="addModeOn = !addModeOn; userToEdit = {}; " v-if="!editModeOn && !addModeOn">
            <button type="button" class="btn btn-info btn-sm">Dodaj użytkownika</button>
          </div>
          <div class="information ml-auto" @click="editModeOn = !editModeOn" v-if="!editModeOn && !addModeOn">
            <button type="button" class="btn btn-info btn-sm" :disabled="!ifUserSelected">Edytuj</button>
          </div>
          <div class="information" v-if="editModeOn && !addModeOn">
            <button type="button" class="btn btn-danger btn-sm" @click="deleteUser()">Usuń</button>
          </div>
          <div class="information ml-auto" @click="
          editModeOn = (editModeOn ? !editModeOn:editModeOn); addModeOn = (addModeOn ? !addModeOn : addModeOn); revertChanges();" v-if="editModeOn || addModeOn
          ">
            <button type="button" class="btn btn-warning btn-sm">Anuluj</button>
          </div>
          <div class="information" v-if="editModeOn || addModeOn">
            <button type="submit" class="btn btn-primary btn-sm" @click="submit">Zapisz</button>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import {getCookie} from '../../services/authentication/userAuthenticationService';

export default {
  data() {
    return {
      editModeOn: false,
      ifUserSelected: false,
      users: [],
      subordinates: [],
      activeUser: {},
      activeSubordinate: {},
      userToEdit: {},
      backUpUser: {},
      date: new Date(),
      addModeOn: false
    }
  },
  async mounted() {
    const requestOptions = {
      method: 'GET',
      headers: {
        'Authorization': 'Bearer ' + getCookie('accessToken')
      }
    }
    fetch(`/api/users`, requestOptions)
      .then(response => response.json())
      .then(data => this.users = data);
  },
  methods: {
    selectUser: function (selectedUser) {
      this.userToEdit = {
        userId: selectedUser.userId,
        email: selectedUser.email,
        firstName: selectedUser.firstName,
        lastName: selectedUser.lastName,
        dateOfBirth: selectedUser.dateOfBirth,
        occupation: selectedUser.occupation,
        userRole: selectedUser.userRole,
        managerId: selectedUser.managerId
      }
      this.backUpUser = {
        userId: selectedUser.userId,
        email: selectedUser.email,
        firstName: selectedUser.firstName,
        lastName: selectedUser.lastName,
        dateOfBirth: selectedUser.dateOfBirth,
        occupation: selectedUser.occupation,
        userRole: selectedUser.userRole,
        managerId: selectedUser.managerId
      }
      this.ifUserSelected = true;
      this.editModeOn = false;
      this.addModeOn = false;
    },
    revertChanges: function () {
      this.userToEdit = {
        userId: this.backUpUser.userId,
        email: this.backUpUser.email,
        firstName: this.backUpUser.firstName,
        lastName: this.backUpUser.lastName,
        dateOfBirth: this.backUpUser.dateOfBirth,
        occupation: this.backUpUser.occupation,
        userRole: this.backUpUser.userRole,
        managerId: this.backUpUser.managerId
      }
    },
    showSubordinates: function (selectedUser){
      this.subordinates = [];
      this.users.forEach(user => {
        if(user.managerId === selectedUser.userId){
          this.subordinates.push(user);
        }
      });
    },
    highlightUsers: function (user){
      let computedClass = '';
      if(user.userId === this.activeUser.userId) {
        computedClass += 'selected';
      }
      if(user.userId === this.activeUser.userId && user.userId === this.userToEdit.userId){
        computedClass += ' lastSelected';
      }
      return computedClass;
    },
    highlightSubordinates: function (subordinate){
      let computedClass = '';
      if(subordinate.userId === this.userToEdit.userId) {
        computedClass += 'lastSelected';
      }
      return computedClass;
    },
    checkIfEmailIsUnique: function (email) {
      for (const user in this.users) {
        if(user.email === email) {
          return false;
        }
      }
      return true;
    },
    deleteUser: async function () {
      const requestOptions = {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + getCookie('accessToken')
          }
      };
      await fetch(`/api/users/${this.userToEdit.userId}`, requestOptions)
          .then(response => response.json());
      window.location.reload();
    },
    submit: async function (event) {
      event.preventDefault();
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
      let requestOptions = {};
      let response = {};
      if(isFormCorrect) {
        if (this.editModeOn) {
          requestOptions = {
            method: 'PUT',
            headers: {
              'Content-Type': 'application/json',
              'Authorization': 'Bearer ' + getCookie('accessToken')
              },
            body: JSON.stringify({
              userId: this.userToEdit.userId,
              email: this.userToEdit.email,
              password: null,
              firstName: this.userToEdit.firstName,
              lastName: this.userToEdit.lastName,
              dateOfBirth: this.userToEdit.dateOfBirth,
              occupation: this.userToEdit.occupation,
              userRole: this.userToEdit.userRole,
              managerId: this.userToEdit.managerId
            })
          };
          response = await fetch("/api/users/", requestOptions);
        } else if (this.addModeOn) {
          requestOptions = {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              'Authorization': 'Bearer ' + getCookie('accessToken')
              },
            body: JSON.stringify({
              userId: this.userToEdit.userId,
              email: this.userToEdit.email,
              password: this.userToEdit.password,
              firstName: this.userToEdit.firstName,
              lastName: this.userToEdit.lastName,
              dateOfBirth: this.userToEdit.dateOfBirth,
              occupation: this.userToEdit.occupation,
              userRole: this.userToEdit.userRole,
              managerId: this.userToEdit.managerId
            })
          };
          response = await fetch("/api/users/", requestOptions);
        }

        if(response.status === 400){
          event.preventDefault();
          alert("Menadżer nie może być osobą będącą niżej w hierarchi od edytowanego użytkownika.");
        } else if(response.status === 403) {
          alert("Menadżer nie może być swoim menadżerem.")
        } else if(response.status === 404) {
          alert("Użytkownik z takim ID nie istnieje.")
        } else {
          window.location.reload();
        }
      }
    }
  }
}

</script>

<style scoped>
/*div {
  border: red 1px solid;
}*/

.headerSection {
  border-bottom: #EAEAEA 2px solid;
  background: #EAEAEA;
  color: #4A5056;
}

.selected {
  color: var(--item-active);
  font-size: 110%;
}
.lastSelected {
  color: var(--item-hover);
  font-size: 120%;
}

.header{
  padding: 8px 8px 0 8px;
}

.user {
  padding: 5px;
  border-bottom: #EAEAEA 2px solid;
  height: 40px;
}
.user:hover{
  color: var(--item-hover);
  cursor: pointer;
}

.information {
  padding: 3px 5px 0 5px;
}

.users {
  min-width: 33.3%;
}

.usersUnder {
  min-width: 33.3%;
}

.details {
  min-width: 33.3%;
  border-left: #EAEAEA 2px solid;
}

.myTable {
  border: #EAEAEA 2px solid;
  border-radius: 10px;
}


</style>