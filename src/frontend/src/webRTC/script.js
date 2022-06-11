import AgoraRTC from "agora-rtc-sdk-ng";

const APP_ID = "38ed5944474547f094897db886259afa";

let uid = sessionStorage.getItem('uid');
if(!uid) {
    uid = String(Math.floor(Math.random() * 10000));
    sessionStorage.setItem('uid', uid);
}

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

    videosGrid.append(player);

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

        videosGrid.append(player);
    }

    if(mediaType === 'video') {
        user.videoTrack.play(`user${user.uid}`);
    }

    if(mediaType === 'audio') {
        //user.audioTrack.play();
    }
}

let handleUserLeft = async function(user) {
    delete remoteUsers[user.uid];
    document.getElementById(`user${user.uid}`).remove();
}

// import AgoraRTC from 'agora-rtc-sdk';

// const APP_ID = "38ed5944474547f094897db886259afa";

// let uid = sessionStorage.getItem('uid');
// if(!uid) {
//     uid = String(Math.floor(Math.random() * 10000));
//     sessionStorage.setItem('uid', uid);
// }

// let token = null;
// let client;

// const queryString = window.location.search;
// const urlParams = new URLSearchParams(queryString);
// let roomId = urlParams.get('room');

// if(!roomId) {
//     roomId = 'main';
// }

// let localTracks = [];
// let remoteUsers = {};

// export let joinRoomInit = async () => {
//     client = AgoraRTC.createClient({mode: 'rtc', codec: 'vp8'});
//     client.init(APP_ID,
//         function() {
//             console.log("client initialized");
//         },
//         function () {
//             console.log("CLIENT INITIALIZATION FAILED!");
//         }
//     );
//     console.log(`----${uid}------`);
//     await client.join(token, roomId, uid);
//     console.log('---------');

//     client.on('stream-added', handleUserPublish);

//     joinStream();
// }

// let joinStream = async () => {
//     //let userMedia = await navigator.mediaDevices.getUserMedia({ audio: false, video: true });
//     var localStream = AgoraRTC.createStream({
//         streamID: uid,
//         audio:true,
//         video:true,
//         screen:false
//     });


//     let player = document.createElement('div');
//     player.setAttribute('id', `user${uid}`);
//     //player.classList.add('video');
//     // player.srcObject = userMedia;
//     // player.addEventListener('loadedmetadata', function () {
//     //     player.play();
//     // });

//     //videosGrid is div comming from EventRoom - found by divs id tag
//     videosGrid.append(player);

//     localStream.init(function(){
//         localStream.play(`user${uid}`);
//         client.publish(localStream, function(err) {
//             console.log("dupa " + err);
//         })
//     });
// }

// let handleUserPublish = async (user, mediaType) => {
//     console.log("---------------USER JOINED-------------");
//     remoteUsers[user.uid] = user;

//     await client.subscribe(user, mediaType);
    
//     let player = document.getElementById(`user${user.uid}`);
//     if(player === null) {
//         player = document.createElement('video');
//         player.setAttribute('id', `user${user.uid}`);

//         //videosGrid is div comming from EventRoom - found by divs id tag
//         videosGrid.append(player);
//     }

//     if(mediaType === 'video') {
//         user.videoTrack.play(`user${user.uid}`);
//     }
//     if(mediaType === 'audio') {
//         user.audioTrack.play();
//     }
// }

// //joinRoomInit();