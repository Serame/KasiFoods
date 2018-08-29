function init(){
        console.log("Waiting for apis to load1");
        window.init();
        console.log("Waiting for apis to load2");
        }

var mainApp = angular.module("mainApp", []);
mainApp.controller('placedOrderController', function($scope) {
            init()
            $scope.insert= function() {
                var status = $scope.stat;

                gapi.client.menuApi.sayHi(status).execute(function(resp) {
                                                                                       $scope.placedOrders = resp.items;
                                                                                       $scope.$apply();
                                                                                       console.log($scope.placedOrders.length);
                                                                                     });


                            $window.init= function() {
                              $scope.$apply($scope.load_toodles_lib);
                            };

                            $scope.load_toodles_lib = function() {

                              var apiName = 'menuApi';
                              var apiVersion = 'v1';
                             // var apiRoot = 'https://' + window.location.host + '/_ah/api';
                              var apiRoot = 'https://xenon-lantern-213109.appspot.com/_ah/api';
                              gapi.client.load(apiName, apiVersion, function() {
                                $scope.is_backend_ready = true;
                                $scope.list();
                              }, ROOT);
                              console.log("Done loading API");
            };




}
});

