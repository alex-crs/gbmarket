angular.module('app').controller('cartController', function ($scope, $http, $localStorage) {
    const corePathController = $localStorage.mainHttpPath + '/core';
    const cartPathController = $localStorage.mainHttpPath + '/carts';
    const startPagePathController = $localStorage.mainHttpPath;

    $scope.loadProductsFromCart = function () {
        $http.get(cartPathController + '/api/v1/cart').then(function (response) {
            $scope.ProductsList = response.data;
        });
    };

    $scope.loadProductsFromCart();

    $scope.deleteProductFromCart = function (productID) {
        $http.get(cartPathController + '/api/v1/cart/delete/' + productID).then(function (response) {
            $scope.loadProductsFromCart();
        });
    };

    $scope.deleteProductStringFromCart = function (productID) {
        $http.get(cartPathController + '/api/v1/cart/delete/productAmounts/' + productID).then(function (response) {
            $scope.loadProductsFromCart();
        });
    };

    $scope.clearCart = function () {
        $http.get(cartPathController + '/api/v1/cart/delete/clear').then(function (response) {
            $scope.loadProductsFromCart();
        });
    };

    $scope.addToCart = function (productId) {
        $http.get(cartPathController + '/api/v1/cart/add/' + productId).then(function (response) {
            $scope.loadProductsFromCart();
        });
    };

    function changeEnterView() {
        if ($localStorage.currentUser) {
            $scope.enterView = "Выход";
        } else {
            $scope.enterView = "Вход";
        }
    };

    changeEnterView();

    $scope.createOrder = function () {
        if ($localStorage.currentUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.currentUser.token;
            $scope.orderInfo.username = $localStorage.currentUser.username;
        }
        $http.post(corePathController + '/api/v1/order/create', $scope.orderInfo).then(function (response) {
            if (response.status === 201) {
                alert("Заказ успешно создан");
                window.location.href = startPagePathController + '/market/index.html';
            }
        }, function (response) {
            if (response.status === 401) {
                alert("ОШИБКА! Перед созданием заказа необходимо авторизоваться");
            }
            if (response.status === 423) {
                alert("ОШИБКА! В корзине отсутствуют товары")
            }
        });
    };

});