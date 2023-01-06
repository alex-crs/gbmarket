angular.module('app', ['ngStorage']).controller('indexController', function ($scope, $http, $localStorage) {

    $scope.loadOrders = function () {
        if ($localStorage.currentUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.currentUser.token;
        }
        $http.get('http://localhost:8189/gbmarket/api/v1/order/checkAll').then(function (response) {
            $scope.OrderList = response.data;
        });
    };
    $scope.loadOrders();

    $scope.goTo = function (orderId) {
        $localStorage.currentOrder = orderId;
        window.location.href = 'http://localhost:8189/gbmarket/orderDetails.html';
    };


});