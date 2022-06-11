<template>
  <div class="eventRoom">
    <div class="topBar d-flex p-2">
        <div class="ml-1 mr-2 pointer" @click="showParticipants = !showParticipants">Uczestnicy</div>
        <div class="ml-auto mr-auto">TEST TYasd faa sdf asdfa</div>
        <div class="ml-2 mr-1 pointer" @click="showChat = !showChat">Czat</div>
    </div>
    <div class="body">
      <div class="participantsList" v-if="showParticipants">
        <div class="ml-2 text-truncate" v-for="user in users" :key="user.name">
          <span class="dot"></span>
          <div class="d-inline ml-2">{{ user.name + " " + user.surname }}</div>
        </div>
      </div>
      <section class="videoBox">

        <div id="streamBox">
          <div>{{ message }}</div>
          <button type="button" @click="sendMessage()">SEND</button>
        </div>

        <div id="streamsContainer">
          <div class="videoContainer" id="1">
            <h1>1</h1>
          </div>
        </div>

      </section>
      <div class="chatBox" v-if="showChat">
        <div class="messages">
          <div :class="isLoggedUserMessage(2)? 'loggedUserMessage' : ''">
            <div class="message" :class="isLoggedUserMessage(2)? 'loggedUserMessageContent' : 'messageContent'">
              <div v-if="isLoggedUserMessage(2)">Ty</div>
              <div v-else>Jan</div>
              <div>Ej, wie ktoś jaki jest asd asd asd  email działu kadr?</div>
            </div>
          </div>
          <div :class="isLoggedUserMessage(1)? 'loggedUserMessage' : ''">
            <div class="message" :class="isLoggedUserMessage(1)? 'loggedUserMessageContent' : 'messageContent'">
              <div v-if="isLoggedUserMessage(1)">Ty</div>
              <div v-else>Adam</div>
              <div>kadry@oms.oms</div>
            </div>
          </div>
        </div>
        <div class="d-flex w-100">
          <input class="messageInput" placeholder="Wyślij wiadomość..."/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { loggedUserId } from '../../services/authentication/userAuthenticationService'


export default {
  name: 'EventRoom',
  data() {
    return {
      showParticipants: true,
      showChat: true,
      message: 'Default',
      users: [
        {
          name: 'Adam',
          surname: 'Nowak',
        },
        {
          name: 'Jan',
          surname: 'Kowalsdfgsdfgdsfgsdfgsdfgsd',
        }
      ]
    }
  },
  methods: {
    isLoggedUserMessage: function(creatorId) {
      console.log(loggedUserId === creatorId);
      return creatorId === loggedUserId;
    }
  }

}

</script>

<style scoped>
/* div {
  border: red 1px solid;
} */

.eventRoom {
  border: #eaeaea 2px solid;
  border-radius: 10px;
}

.topBar {
  width: 100%;
  height: 45px;
  font-size: 120%;
  border-bottom: #eaeaea 2px solid;
}

.body {
  /* usunąć min */
  min-height: calc(100vh - 90px); 
  display: flex;
}

.participantsList {
  min-width: 150px;
  border-right: #eaeaea 2px solid;
}

.videoBox {
  width: 100%;
}

#streamBox {
  background-color: #d5d5d5;
  height: 60vh;
}

#streamsContainer {
  margin-top: 25px;
  margin-bottom: 225px;
  display: flex;
  gap: 2em;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  
}

.videoContainer {
  display: flex;
  justify-content: center;
  align-items: center;
  border: 2px solid #42adb0;
  border-radius: 50%;
  cursor: pointer;
  overflow: hidden;
  height: 200px;
  width: 200px;
}

@media (max-width: 1400px) {
  .videoContainer {
    height: 150px;
    width: 150px;
  }
}

@media (max-width: 768px) {
  .videoContainer {

    height: 100px;
    width: 100px;
  }
}


.chatBox {
  min-width: 300px;
  border-left: #eaeaea 2px solid;
}

.iconActive {
  transform: rotate(180deg);
}

.pointer {
  cursor: pointer;
}

.messages {
  height: calc(100vh - 160px);
  overflow-y: scroll;
  overflow-x: hidden;
  border-bottom: #eaeaea 2px solid;
}

.message {
  word-wrap: break-word;
  width: fit-content;
  max-width: 240px;
  margin: 10px 5px 10px 5px;
  padding: 10px;
  border-radius: 15px;
}

.loggedUserMessage {
  direction: rtl;
}

.messageContent {
  background: #a6fcfe;
}

.loggedUserMessageContent {
  background: #89f0bb;
}

.messageInput {
  padding: 10px;
  margin-top: 10px;
  margin-left: auto;
  margin-right: auto;
  border: 0;
  background: #d5d5d5;
  border-radius: 5px;
  height: 50px;
  width: 280px;
}

.dot {
  height: 8px;
  width: 8px;
  background-color: #04A96D;
  border-radius: 50%;
  display: inline-block;
}
</style>