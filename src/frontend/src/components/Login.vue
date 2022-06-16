<template>
  <div class="box">
    <div class="loginCard">
      <div class="d-flex m-5">
        <h1 class="m-auto">OMS</h1>
      </div>
      <form class="m-4" @submit="submit">
        <div class="form-group">
          <input type="text" class="form-control" v-model="credentials.email" placeholder="E-mail" autofocus>
        </div>
        <div class="form-group">
          <input type="password" class="form-control" v-model="credentials.password" placeholder="Hasło">
        </div>
        <div class="error" v-if="serverError">
          Nieprawidłowy e-mail lub hasło.
        </div>
        <button class="loginButton">ZALOGUJ</button>
      </form>
    </div>
  </div>
</template>

<style scoped>
.box {
  position: fixed;
  left: -220px;
  top: -20px;
  width: 100%;
  height: 100%;
  display: flex;
}

.loginCard {
  height: 400px;
  width: 350px;
  margin: auto;
  border: 1px solid #D3D3D3;
  border-radius: 2%;
  box-shadow: 0 0 15px #D3D3D3;
}

.error{
  position: absolute;
  color: #B00020;
  width: 280px;

  font-size: 80%;
}

.loginButton{
  margin-top: 70px;
  width: 100%;
  height: 50px;
  border: 0;
  color: white;
  background: #455073;
}
.loginButton:hover {
  background-color: #576591;
}
.loginButton:active {
  background-color: #455073;
}
</style>

<script>
import { getCookie } from '../services/authentication/userAuthenticationService';


export default {
  data() {
    return {
      credentials: {
        email: '',
        password: ''
      },
      serverError: false,
      users: {}
    }
  },
  mounted() {
    if(sessionStorage.getItem('userId') !== null) {
      this.$router.push('/');
    }
  },
  methods: {
    submit: async function(event) {
      event.preventDefault();
      let formBody = []
      for(let property in this.credentials) {
        let key = encodeURIComponent(property);
        let value = encodeURIComponent(this.credentials[property]);
        formBody.push(key + "=" + value);
      }
      formBody = formBody.join("&");

      const requestOptions = {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
        },
        body: formBody
      }

      await fetch(`/login`, requestOptions)
      .then(response => {
        if(response.status === 200) {
          fetch(`/api/users/byEmail`, {
            headers: {
              'Authorization': 'Bearer ' + getCookie('accessToken')
            }
          })
          .then(response => response.json())
          .then(data => {
            this.serverError = false;
            sessionStorage.setItem('userId', data.userId);
            this.$store.dispatch('user', data);
            this.$router.push('/');
          });
        } else {
          this.credentials = {
            email: '',
            password: ''
          }
          this.serverError = true;
        }
      })
    }
  }
};
</script>