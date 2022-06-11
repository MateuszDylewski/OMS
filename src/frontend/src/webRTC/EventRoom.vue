<template>
  <div>
    <div></div>
    <div id="streamsContainer" >
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
  }
}

</script>

<style scoped>
/* div {
  border: 2px red solid;
} */

#streamsContainer {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-content: center;
  margin-top: 20px;
  gap: 20px;
}

</style>

<style>
.videoContainer {
  border: 2px solid grey;
  border-radius: 5px;
  display: flex;
  justify-content: center;
  align-content: center;
  overflow: hidden;
  height: 300px;
  width: 300px;
}
</style>