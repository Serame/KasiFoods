function init() {
    //Parameters are APIName,APIVersion,CallBack function,API Root
    var apiName = 'menuApi';
          var apiVersion = 'v1';
          //var apiRoot = 'https://' + window.location.host + '/_ah/api';
          var apiRoot = 'https://xenon-lantern-213109.appspot.com/_ah/api';
        /*  if (window.location.hostname == 'localhost'
              || window.location.hostname == '127.0.0.1'
              || ((window.location.port != "") && (window.location.port > 1023))) {
                // We're probably running against the DevAppServer
                apiRoot = 'http://' + window.location.host + '/_ah/api';
          }*/
    gapi.client.load(apiName, apiVersion, null, apiRoot);
    console.log('apiRoot: '+apiRoot);
    /*
    document.getElementById('getOrder').onclick = function (){
        console.log('called the test Method');
        getPlacedOrders();*/


$(document).ready(function(){

    $('#cardOrder').hide();

    $("#getOrder").click(function(){

         var status = 'N';
                gapi.client.menuApi.getPlacedOrders({'status': status}).execute(
                          function(response) {
                            response.items = response.items||[];

                            var results = "";
                            var allItems = response.items;
                            var bunnies = [];
                            var orderIDs = [];
                            var orderItem; //This is a single order item that could be part of one big order e.g order number 3 having 4 kotas i.e this will one kota

                            for(x = 0; x< allItems.length;x++)
                            {
                                orderItem = allItems[x];
                                console.log(orderItem);
                                if(orderItem.parentIND == 'Y')
                                {
                                    bunnies.push(orderItem);
                                    orderIDs.push(orderItem.orderID)

                                }

                            }

                            var uniqueOrderIds = GetUnique(orderIDs);

                            createNewOrderCard(uniqueOrderIds,bunnies);



              }
                        );
    });
});

}



function createNewRow(order,tableID){



    var table = document.getElementById(tableID);

    var row = table.insertRow (-1);

    var itemTotal = (order.price + order.addedItemsTotal);



    var kotaNameCell = row.insertCell(0);
    var kotaDescrCell = row.insertCell(1);
    var addetItemsCell = row.insertCell(2);
    var removedItemsCell = row.insertCell(3);
    var itemPriceCell = row.insertCell(4)
    var totalPriceCell = row.insertCell(5);
  //  var customerNameCell = row.insertCell(5);



    kotaNameCell.innerHTML = order.itemName;
    kotaDescrCell.innerHTML = order.itemDescr;
    addetItemsCell.innerHTML = order.addedItems;
    removedItemsCell.innerHTML = 'Nothing for now';
    itemPriceCell.innerHTML = 'R'+order.price;
    totalPriceCell.innerHTML = 'R'+itemTotal;
   // customerNameCell.innerHTML = order.custName;


}

function createNewOrderCard(orderIDs,bunnies)
{
    var orderID;
    var bunny = bunnies[0];
    var customerName = bunny.custName;
    var d = new Date();
    var orderDate = d.getDate()+'-'+(d.getMonth()+1)+'-'+d.getFullYear()+' '+d.getHours()+':'+d.getMinutes()+':'+d.getSeconds();
    for(x = 0; x< orderIDs.length; x++)
    {
        orderID = orderIDs[x];
        var newCard = $('<div id="cardOrder'+orderID+'"'+' class="w3-panel w3-card w3-small" >'+
                            '<h3 id="orderheader'+orderID+'"'+'>Order number: '+orderID+'<center></center></h3>'+
                            '<h3 id="customerName'+orderID+'"'+'>Customer Name: '+customerName+'<center></center></h3>'+
                            '<h3 id="orderDate'+orderID+'"'+'>Order date: '+orderDate+'<center></center></h3>'+
                            '<table id ="ordersTable'+orderID+'"'+' class="table table-bordered table-dark">'+
                            ' <thead><b><tr>'+
                            '<th class="col-sm-2">Kota Name</th><th class="col-sm-3">Kota Descr</th><th class="col-sm-2">Added Items</th><th class="col-sm-2">Removed Items</th><th class="col-sm-1">Item Price</th><th class="col-sm-1">Total Price</th>'+
                            '</tr></b></thead>'+
                            '<tbody></tbody></table>'+
                            ' <div class="row">'+
                             '<form action="javascript:(0);">'+
                                 '<input id="completeOrder'+orderID+'"'+' class="col-sm-6" type="submit" value="Complete Order">'+
                                 '<input id="rejectOrder'+orderID+'"'+ 'class="col-sm-6" type="submit" value="Reject Order">'+
                             '</form>'+
                         '</div>'+
                            '</div>');
            $('#mainP').append(newCard);
    }


    var bunny;
    for(i = 0; i < bunnies.length; i++)
    {
        bunny = bunnies[i]
        var t = 'ordersTable'+bunny.orderID;
        var table = document.getElementById(t);

        createNewRow(bunny,t);

    }

    $(":submit").click(function(){
        /*console.log($(this).prop("id"));
        console.log($(this).attr("id"));*/
        console.log('Submit has been clicked');

        var orderID = $(this).prop("id").substring(13,$(this).prop("id").length);

        if($(this).prop("id").includes('completeOrder'))
        {

            $.confirm({
                title: 'Confirm!',
                content: 'Are you sure want to complete this order?',
                buttons: {
                    confirm: function () {
                        var cardName = 'cardOrder'+orderID;
                        completeOrder(orderID,cardName);
                        $.alert('Order Completed!');
                    },
                    cancel: function () {
                        $.alert('Canceled!');
                    }
                }
            });

        }
        else if($(this).prop("id").includes('rejectOrder'))
        {
            console.log('Pressed removed object');

        }


        /*var str = "completeOrder230180304";
              var res = str.substring(13, str.length);*/

    });

}

function updateShopToken(refreshedToken){
    console.log(refreshedToken);
    gapi.client.menuApi.updateShopFCMToken({'refreshedToken':refreshedToken}).execute(
                    function(response){
                        console.log('token Refreshed hopefully lol');
                        console.log(response);
                    }
                        );
}

function completeOrder(orderID,cardName){
    console.log('Closing Order!!');
    $('#'+cardName).remove();
    gapi.client.menuApi.closeOrder({'orderid': orderID}).execute(
                      function(response) {
                        console.log('Order is being closed');
                        response.items = response.items||[];
                        var results = "";
                        var allItems = response.items;
                       console.log(allItems);

          }
                    );


}


function getPlacedOrders() {
var status = 'N';
var tempOrderID = 0;
console.log(status);
gapi.client.menuApi.getPlacedOrders({'status': status}).execute(
          function(response) {
            console.log('Miracles are happening papa');
            response.items = response.items||[];
            var results = "";
            var orders = response.items;
            console.log(orders.length);
            for(var i = 0; i <orders.length; i++)
            {



            }
          }
        );
}

function GetUnique(inputArray)
{
    var outputArray = [];

    for (var i = 0; i < inputArray.length; i++)
    {
        if ((jQuery.inArray(inputArray[i], outputArray)) == -1)
        {
            outputArray.push(inputArray[i]);
        }
    }

    return outputArray;
}
