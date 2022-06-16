import AgoraRTC from "agora-rtc-sdk-ng";

const APP_ID = "38ed5944474547f094897db886259afa";

let uid = sessionStorage.getItem('uid');
let token = null;
let client;

const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
let roomId = urlParams.get('room');

if(!roomId) {
    roomId = 'main';
}

let localTracks = [];
let remoteUsers = {};

export let joinRoomInit = async function() {

    client = AgoraRTC.createClient({mode: 'rtc', codec: 'vp8'});
    await client.join(APP_ID, roomId, token, uid);

    client.on('user-published', handleUserPublish);
    client.on('user-left', handleUserLeft);

    joinStream();
}

let joinStream = async function() {
    localTracks = await AgoraRTC.createMicrophoneAndCameraTracks();

    let player = document.createElement('div');
    player.setAttribute('id', `user${uid}`);
    player.classList.add('videoContainer');

    streamsContainer.append(player);

    localTracks[1].play(`user${uid}`);

    await client.publish([localTracks[0], localTracks[1]]);
}

let handleUserPublish = async function(user, mediaType) {
    remoteUsers[user.uid] = user;

    await client.subscribe(user, mediaType);

    let player = document.getElementById(`user${user.uid}`);
    if(player === null) {
        let player = document.createElement('div');
        player.setAttribute('id', `user${user.uid}`);
        player.classList.add('videoContainer');

        streamsContainer.append(player);
    }

    if(mediaType === 'video') {
        user.videoTrack.play(`user${user.uid}`);
    }

    if(mediaType === 'audio') {
        user.audioTrack.play();
    }
}

let handleUserLeft = async function(user) {
    delete remoteUsers[user.uid];
    document.getElementById(`user${user.uid}`).remove();
}