
//<script src="https://www.gstatic.com/firebasejs/5.0.2/firebase.js"></script>
//<script>
  // Initialize Firebase
  var config = {
    apiKey: "AIzaSyBmiw1ohMADPVlMjsiS2yDTQBjs0rNQIz4",
    authDomain: "kotatime-e7946.firebaseapp.com",
    databaseURL: "https://kotatime-e7946.firebaseio.com",
    projectId: "kotatime-e7946",
    storageBucket: "kotatime-e7946.appspot.com",
    messagingSenderId: "867353815744"
  };
  firebase.initializeApp(config);
//</script>
const messaging = firebase.messaging();
messaging.requestPermission()
.then(function(){
    //console.log('Have permission');
    return messaging.getToken();
})
.then(function(token){
    //console.log(token);
    updateShopToken(token);

})
.catch(function(err){
    console.log('Error Occured.',err);
})

messaging.onMessage(function(payload){


    //var allItems = payload.data.placed_orders||[];
    console.log('Viewing the items');

    var allItems = JSON.parse(payload.data.placed_orders);


    var bunnies = new Array();
    var orderIDs = new Array();
    var orderItem; //This is a single order item that could be part of one big order e.g order number 3 having 4 kotas i.e this will one kota

    orderIDs.push(allItems[0].orderID);

    for(x = 0; x< allItems.length;x++)
    {
        orderItem = allItems[x];


        if(orderItem.parentIND == 'Y')
        {
            bunnies.push(orderItem);

        }
    }

    createNewOrderCard(orderIDs,bunnies);
});


