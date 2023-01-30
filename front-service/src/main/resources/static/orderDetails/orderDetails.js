angular.module('app').controller('orderDetailsController', function ($scope, $http, $localStorage, $location) {
    const corePathController = $localStorage.mainHttpPath + '/core';

    $scope.loadOrders = function () {
        $http.post(corePathController + '/api/v1/order/check/' + $localStorage.currentOrder).then(function (response) {
            $scope.OrderList = response.data;
        });
    };
    $scope.loadOrders();

    $scope.goToProductInfo = function (productId) {
        $localStorage.selectedProduct = productId;
        $location.path('/productDetails')
    };

});