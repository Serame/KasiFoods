function init() {
    //Parameters are APIName,APIVersion,CallBack function,API Root
    var apiName = 'menuApi';
          var apiVersion = 'v1';
          var apiRoot = 'https://' + window.location.host + '/_ah/api';
         //var apiRoot = 'https://10.0.3.2:8080/_ah/api';
          if (window.location.hostname == 'localhost'
              || window.location.hostname == '127.0.0.1'
              || ((window.location.port != "") && (window.location.port > 1023))) {
                // We're probably running against the DevAppServer
                apiRoot = 'http://' + window.location.host + '/_ah/api';
          }
          else if(window.location.hostname == '10.0.3.2')
          {
            apiRoot = 'http://10.0.3.2:8080/_ah/api'
          }


    gapi.client.load(apiName, apiVersion, null, apiRoot);
    console.log('apiRoot: '+apiRoot);
    /*
    document.getElementById('getOrder').onclick = function (){
        console.log('called the test Method');
        getPlacedOrders();*/


$(document).ready(function(){


    $('#lblsignup').text(apiRoot);

    $("#register").click(function(){

        var name = $('#first_name').val();
        var surname = $('#surname').val();
        var cell = $('#cell').val();
        var email = $('#email').val();
        var password = $('#password').val();
        var dateofbirth = $('#datofbirth').val();
        var gender = 'Male';

        var user = {name:name,
                    email:email,
                    password:password,
                    cell:cell,
                    surname:surname,
                    gender:gender,
                    dateofbirth:dateofbirth};

        alert('button has been clicked');




        gapi.client.menuApi.registerNewCustomer(user).execute(
                                  function(response) {
                                    console.log(response.items)
                                    alert('user added');
                                     }
                                );

    });


});

}