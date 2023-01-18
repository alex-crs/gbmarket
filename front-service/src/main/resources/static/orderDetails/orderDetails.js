angular.module('app', ['ngStorage']).controller('indexController', function ($scope, $http, $localStorage) {

    $scope.loadOrders = function () {
        if ($localStorage.currentUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.currentUser.token;
        }
        let id = $localStorage.currentOrder;
        let CurrentUser ={
            username: $localStorage.currentUser.username
        }
        $http.post('http://localhost:5555/core/api/v1/order/check/' + id, CurrentUser).then(function (response) {
            $scope.OrderList = response.data;
        });
    };
    $scope.loadOrders();

    function changeEnterView (){
        if ($localStorage.currentUser){
            $scope.enterView = "Выход";
        } else {
            $scope.enterView = "Вход";
        }
    };

    changeEnterView();

});