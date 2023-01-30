angular.module('app').controller('productDetailsController', function ($scope, $http, $localStorage) {
    const corePathController = $localStorage.mainHttpPath + '/core';
    const cartPathController = $localStorage.mainHttpPath + '/carts';

    $scope.loadProductFullInfo = function () {
        $http.get(corePathController + '/api/v1/products/info/' + $localStorage.selectedProduct).then(function (response) {
            $scope.ProductFullInfo = response.data;
        });
    };

    $scope.loadProductFullInfo();

    $scope.addToCart = function (productId) {
        $http.get(cartPathController + '/api/v1/cart/' + $localStorage.guestUuid + '/add/' + productId).then(function (response) {
            alert("Товар успешно добавлен в корзину");
        }, function () {
            alert("Возникла проблема при добавлении в корзину");
        });
    };

});