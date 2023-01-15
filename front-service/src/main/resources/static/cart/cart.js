angular.module('app', ['ngStorage']).controller('indexController', function ($scope, $http, $localStorage) {

    $scope.loadProductsFromCart = function () {
        $http.get('http://localhost:5555/carts/api/v1/cart').then(function (response) {
            $scope.ProductsList = response.data;
        });
    };

    $scope.loadProductsFromCart();

    $scope.deleteProductFromCart = function (productID) {
        $http.get('http://localhost:5555/carts/api/v1/cart/delete/' + productID).then(function (response) {
            $scope.loadProductsFromCart();
        });
    };

    $scope.deleteProductStringFromCart = function (productID) {
        $http.get('http://localhost:5555/carts/api/v1/cart/delete/productAmounts/' + productID).then(function (response) {
            $scope.loadProductsFromCart();
        });
    };

    $scope.clearCart = function () {
        $http.get('http://localhost:5555/carts/api/v1/cart/delete/clear').then(function (response) {
            $scope.loadProductsFromCart();
        });
    };

    $scope.addToCart = function (productId) {
        $http.get('http://localhost:5555/carts/api/v1/cart/add/' + productId).then(function (response) {
            $scope.loadProductsFromCart();
        });
    };



    $scope.createOrder = function () {
        if ($localStorage.currentUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.currentUser.token;
        $scope.orderInfo.username = $localStorage.currentUser.username;
        }
            $http.post('http://localhost:5555/core/api/v1/order/create', $scope.orderInfo).then(function (response) {
                if (response.status===201){
                    $scope.loadProductsFromCart();
                    alert("Заказ успешно создан");
                }
            },function (response){
                if (response.status===401){
                    alert("ОШИБКА! Перед созданием заказа необходимо авторизоваться");
                }
                if (response.status===423){
                    alert("ОШИБКА! В корзине отсутствуют товары")
                }
            });
    };

});