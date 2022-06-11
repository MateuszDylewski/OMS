<template>
  <div>
    <button id="bt" type="button" @click="addVideo()">click</button>
    <div id="videosGrid">
    </div>
  </div>
</template>

<script>
import {} from './script';
import {joinRoomInit} from './script';

export default {
  name: 'EventRoom',
  data() {
    return {
    }
  },
  mounted() {
    joinRoomInit();
  },
  methods: {
    async addVideo() {
      console.log("CLICK");
      const videosGrid = document.getElementById('videosGrid');
      const newVideo = document.createElement('video');

      newVideo.classList.add('video');
      newVideo.srcObject = await navigator.mediaDevices.getUserMedia({ audio: false, video: true });
      newVideo.addEventListener('loadedmetadata', function () {
        newVideo.play();
      });
      videosGrid.append(newVideo);
    }
  }
}

</script>

<style scoped>
div {
  border: 2px red solid;
}

#videosGrid {
  min-height: calc(100vh - 40px);
  display: grid;
  grid-template-columns: repeat(auto-fill, 320px);
  grid-auto-rows: 250px;
  justify-content: center;
}

</style>

<style>
.video {
  height: 100%;
  width: 100%;
  padding: 10px;
}
</style>