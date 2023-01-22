angular.module('app').controller('storeController', function ($scope, $http, $location, $localStorage) {
    let currentViewDtoCondition = function (){
        if (!$localStorage.viewDto){
            $localStorage.viewDto = {
                "currentPage": "0",
                "maxItemsOnThePage": "5",
                "sortBy": "id",
                "sortType": "ASC"
            };
        }
    }
    currentViewDtoCondition();

    $scope.loadProducts = function () {
        $http.post('http://localhost:5555/core/api/v1/products', $localStorage.viewDto).then(function (response) {
            $scope.ViewDTO = response.data;
        });
    };

    $scope.loadProducts();

    $scope.showProductInfo = function (productId) {
        $http.get('http://localhost:5555/core/api/v1/products/' + productId).then(function (response) {
            alert(response.data.title);
        }, function (response) {
            if (response.status === 404) {
                alert("Продукт не найден");
            }
        });
    };

    $scope.deleteProductById = function (productId) {
        $http.get('http://localhost:5555/core/api/v1/products/delete/' + productId).then(function (response) {
            $scope.loadProducts();
        });
    };

    $scope.addToCart = function (productId) {
        $http.get('http://localhost:5555/carts/api/v1/cart/add/' + productId).then(function (response) {
            alert("Товар успешно добавлен в корзину");
        }, function () {
            alert("Возникла проблема при добавлении в корзину");
        });
    };


    $scope.toPage = function (page) {
        $localStorage.viewDto.currentPage = page;
        $http.post('http://localhost:5555/core/api/v1/products', $localStorage.viewDto).then(function (response) {
            $scope.ViewDTO = response.data;
        });
    }

    $scope.changeSort = function (type) {
        $localStorage.viewDto.sortType = type;
        $http.post('http://localhost:5555/core/api/v1/products', $localStorage.viewDto).then(function (response) {
            $scope.ViewDTO = response.data;
        });
    }

    $scope.changeSortBy = function (type) {
        $localStorage.viewDto.sortBy = type;
        $http.post('http://localhost:5555/core/api/v1/products', $localStorage.viewDto).then(function (response) {
            $scope.ViewDTO = response.data;
        });
    }

    $scope.goToProductInfo = function (productId) {
        $localStorage.selectedProduct = productId;
        $location.path('/productDetails')
    };
});