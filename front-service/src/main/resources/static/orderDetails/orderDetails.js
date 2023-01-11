angular.module('app', ['ngStorage']).controller('indexController', function ($scope, $http, $localStorage) {

    $scope.loadOrders = function () {
        if ($localStorage.currentUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.currentUser.token;
        }
        let id = $localStorage.currentOrder;
        $http.get('http://localhost:8189/gbmarket/api/v1/order/check/' + id).then(function (response) {
            $scope.OrderList = response.data;
        });
    };
    $scope.loadOrders();



});