angular.module('app').controller('ordersController', function ($scope, $http, $localStorage, $location) {

    $scope.loadOrders = function () {
        if ($localStorage.currentUser) {
            $http.get('http://localhost:5555/core/api/v1/order/checkAll').then(function (response) {
                $scope.OrderList = response.data;
            });
        }
    };

    $scope.loadOrders();

    $scope.goTo = function (orderId) {
        $localStorage.currentOrder = orderId;
        $location.path('/orderDetails')
    };

});