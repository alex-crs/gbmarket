angular.module('app').controller('storeController', function ($scope, $http, $location, $localStorage, $rootScope) {
    const corePathController = $localStorage.mainHttpPath + '/core';
    const cartPathController = $localStorage.mainHttpPath +'/carts';

    let currentViewDtoCondition = function () {
        if (!$rootScope.viewDto) {
            $rootScope.viewDto = {
                "currentPage": "0",
                "maxItemsOnThePage": "5",
                "sortBy": "id",
                "sortType": "ASC"
            };
        }
    }
    currentViewDtoCondition();

    $scope.loadProducts = function () {
        $http.post(corePathController + '/api/v1/products', $rootScope.viewDto).then(function (response) {
            $scope.ViewDTO = response.data;
        });
    };

    $scope.loadProducts();

    $scope.showProductInfo = function (productId) {
        $http.get(corePathController + '/api/v1/products/' + productId).then(function (response) {
            alert(response.data.title);
        }, function (response) {
            if (response.status === 404) {
                alert("Продукт не найден");
            }
        });
    };

    $scope.deleteProductById = function (productId) {
        $http.get(corePathController + '/api/v1/products/delete/' + productId).then(function (response) {
            $scope.loadProducts();
        });
    };

    $scope.addToCart = function (productId) {
        $http.get(cartPathController + '/api/v1/cart/'+ $localStorage.guestUuid +'/add/' + productId).then(function (response) {
            alert("Товар успешно добавлен в корзину");
        }, function () {
            alert("Возникла проблема при добавлении в корзину");
        });
    };


    $scope.toPage = function (page) {
        $localStorage.viewDto.currentPage = page;
        $http.post(corePathController + '/api/v1/products', $localStorage.viewDto).then(function (response) {
            $scope.ViewDTO = response.data;
        });
    }

    $scope.changeSort = function (type) {
        $localStorage.viewDto.sortType = type;
        $http.post(corePathController + '/api/v1/products', $localStorage.viewDto).then(function (response) {
            $scope.ViewDTO = response.data;
        });
    }

    $scope.changeSortBy = function (type) {
        $localStorage.viewDto.sortBy = type;
        $http.post(corePathController + '/core/api/v1/products', $localStorage.viewDto).then(function (response) {
            $scope.ViewDTO = response.data;
        });
    }

    $scope.goToProductInfo = function (productId) {
        $rootScope.selectedProduct = productId;
        $location.path('/productDetails')
    };
});