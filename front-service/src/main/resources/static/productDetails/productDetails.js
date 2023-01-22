angular.module('app').controller('productDetailsController', function ($scope, $http, $localStorage) {

    $scope.loadProductFullInfo = function () {
        $http.get('http://localhost:5555/core/api/v1/products/info/' + $localStorage.selectedProduct).then(function (response) {
            delete $localStorage.selectedProduct;
            $scope.ProductFullInfo = response.data;
        });
    };

    $scope.loadProductFullInfo();

    $scope.addToCart = function (productId) {
        $http.get('http://localhost:5555/carts/api/v1/cart/add/' + productId).then(function (response) {
            alert("Товар успешно добавлен в корзину");
        }, function () {
            alert("Возникла проблема при добавлении в корзину");
        });
    };

});