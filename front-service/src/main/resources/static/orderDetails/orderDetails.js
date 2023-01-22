angular.module('app').controller('orderDetailsController', function ($scope, $http, $localStorage) {

    $scope.loadOrders = function () {
        $http.post('http://localhost:5555/core/api/v1/order/check/' + $localStorage.currentOrder).then(function (response) {
            $scope.OrderList = response.data;
            delete $localStorage.currentOrder;
        });
    };
    $scope.loadOrders();

});