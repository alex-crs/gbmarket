angular.module('app', []).controller('indexController', function ($scope, $http) {

    $scope.loadProductsFromCart = function () {
        $http.get('http://localhost:8189/gbmarket/api/v1/cart').then(function (response) {
            $scope.ProductsList = response.data;
        });
    };

    $scope.loadProductsFromCart();

    $scope.deleteProductFromCart = function (productID) {
        $http.delete('http://localhost:8189/gbmarket/api/v1/cart/' + productID).then(function (response) {
            $scope.loadProductsFromCart();
        });
    };

});