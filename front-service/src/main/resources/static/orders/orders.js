angular.module('app', ['ngStorage']).controller('indexController', function ($scope, $http, $localStorage) {
    function changeEnterView (){
        if ($localStorage.currentUser){
            $scope.enterView = "Выход";
        } else {
            $scope.enterView = "Вход";
        }
    };

    changeEnterView();

    $scope.loadOrders = function () {
        if ($localStorage.currentUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.currentUser.token;

            $http.get('http://localhost:5555/core/api/v1/order/checkAll/' + $localStorage.currentUser.username).then(function (response) {
                $scope.OrderList = response.data;
            });
        }
    };

    $scope.loadOrders();

    $scope.goTo = function (orderId) {
        $localStorage.currentOrder = orderId;
        window.location.href = 'http://localhost:5555/market/orderDetails/orderDetails.html';
    };

});