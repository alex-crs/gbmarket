//в текущем методе мы создаем некое приложение app и привязываем его к indexController.
//index.html мы работаем с intexController
angular.module('app', ['ngStorage']).controller('indexController', function ($scope, $http) {

    $scope.loadProducts = function () {
        $http.get('http://localhost:8189/gbmarket/api/v1/products').then(function (response) {
            $scope.ProductsList = response.data;
        });
    };

    $scope.loadProducts();

    $scope.showProductInfo = function (productId) {
        $http.get('http://localhost:8189/gbmarket/api/v1/products/' + productId).then(function (response) {
            alert(response.data.title);
        });
    };

    $scope.deleteProductById = function (productId) {
        $http.delete('http://localhost:8189/gbmarket/api/v1/products/' + productId).then(function (response) {
            $scope.loadProducts();
        });
    };

    $scope.addToCart = function (productId) {
        $http.get('http://localhost:8189/gbmarket/api/v1/cart/add/' + productId).then(function (response) {
            alert("Товар успешно добавлен в корзину");
        });
    };

});