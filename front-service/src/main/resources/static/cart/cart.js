angular.module('app').controller('cartController', function ($scope, $http, $localStorage, $location) {
    const corePathController = $localStorage.mainHttpPath + '/core';
    const cartPathController = $localStorage.mainHttpPath + '/carts';
    const startPagePathController = $localStorage.mainHttpPath;

    $scope.loadProductsFromCart = function () {
        $http.get(cartPathController + '/api/v1/cart/' + $localStorage.guestUuid).then(function (response) {
            $scope.ProductsList = response.data;
        });
    };

    $scope.loadProductsFromCart();

    $scope.deleteProductFromCart = function (productID) {
        $http.get(cartPathController + '/api/v1/cart/' + $localStorage.guestUuid + '/delete/' + productID).then(function (response) {
            $scope.loadProductsFromCart();
        });
    };

    $scope.deleteProductStringFromCart = function (productID) {
        $http.get(cartPathController + '/api/v1/cart/' + $localStorage.guestUuid + '/delete/productAmounts/' + productID).then(function (response) {
            $scope.loadProductsFromCart();
        });
    };

    $scope.clearCart = function () {
        $http.get(cartPathController + '/api/v1/cart/' + $localStorage.guestUuid + '/clear').then(function (response) {
            $scope.loadProductsFromCart();
        });
    };

    $scope.addToCart = function (productId) {
        $http.get(cartPathController + '/api/v1/cart/' + $localStorage.guestUuid + '/add/' + productId).then(function (response) {
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
        }
        if (checkFields().valueOf() > 0) {
            $http.post(corePathController + '/api/v1/order/create', $scope.orderInfo).then(function (response) {
                if (response.status === 201) {
                    alert("Заказ успешно создан");
                    $location.path('/orders');
                }
            }, function (response) {
                if (response.status === 401) {
                    alert("ОШИБКА! Перед созданием заказа необходимо авторизоваться");
                }
                if (response.status === 423) {
                    alert("ОШИБКА! В корзине отсутствуют товары")
                }
            });
        } else {
            alert("Заполнены не все поля заказа")
        }

    };

    function checkFields() {
        let count = 0;
        if ($scope.orderInfo !== undefined) {
            count++;
        }
        return count;
    }
});