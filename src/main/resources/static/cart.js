angular.module('app', []).controller('indexController', function ($scope, $http) {

    $scope.loadProductsFromCart = function () {
        $http.get('http://localhost:8189/gbmarket/api/v1/cart').then(function (response) {
            $scope.ProductsList = response.data;
        });
    };

    $scope.loadProductsFromCart();

    $scope.deleteProductFromCart = function (productID) {
        $http.get('http://localhost:8189/gbmarket/api/v1/cart/delete/' + productID).then(function (response) {
            $scope.loadProductsFromCart();
        });
    };

    $scope.deleteProductStringFromCart = function (productID) {
        $http.get('http://localhost:8189/gbmarket/api/v1/cart/delete/productAmounts/' + productID).then(function (response) {
            $scope.loadProductsFromCart();
        });
    };

    $scope.clearCart = function (){
        $http.get('http://localhost:8189/gbmarket/api/v1/cart/delete/clear').then(function (response) {
            $scope.loadProductsFromCart();
        });
    };

    $scope.addToCart = function (productId) {
        $http.get('http://localhost:8189/gbmarket/api/v1/cart/add/' + productId).then(function (response) {
            $scope.loadProductsFromCart();
        });
    };

});