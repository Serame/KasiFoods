importScripts("https://www.gstatic.com/firebasejs/5.0.1/firebase-app.js");
importScripts("https://www.gstatic.com/firebasejs/5.0.1/firebase-messaging.js");


var config = {
    apiKey: "AIzaSyBmiw1ohMADPVlMjsiS2yDTQBjs0rNQIz4",
    authDomain: "kotatime-e7946.firebaseapp.com",
    databaseURL: "https://kotatime-e7946.firebaseio.com",
    projectId: "kotatime-e7946",
    storageBucket: "kotatime-e7946.appspot.com",
    messagingSenderId: "867353815744"
  };
  firebase.initializeApp(config);

const messging = firebase.messaging();

