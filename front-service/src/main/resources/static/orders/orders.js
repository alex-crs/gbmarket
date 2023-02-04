angular.module('app').controller('ordersController', function ($scope, $http, $localStorage, $location, $rootScope) {
    const corePathController = $localStorage.mainHttpPath + '/core';

    $scope.loadOrders = function () {
        if ($localStorage.currentUser) {
            $http.get(corePathController + '/api/v1/order/checkAll').then(function (response) {
                $scope.OrderList = response.data;
            });
        }
    };

    $scope.loadOrders();

    $scope.goTo = function (orderId) {
        $localStorage.currentOrder = orderId;
        $location.path('/orderDetails/')
    };

});