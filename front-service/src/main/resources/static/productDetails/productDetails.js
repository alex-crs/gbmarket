angular.module('app', ['ngStorage']).controller('indexController', function ($scope, $http, $localStorage) {

    $scope.loadProductFullInfo = function () {
        if ($localStorage.currentUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.currentUser.token;
        }
        let id = $localStorage.selectedProduct;
        $http.get('http://localhost:5555/core/api/v1/products/info/' + id).then(function (response) {
            $scope.ProductFullInfo = response.data;
        });
    };

    $scope.loadProductFullInfo();

    function changeEnterView (){
        if ($localStorage.currentUser){
            $scope.enterView = "Выход";
        } else {
            $scope.enterView = "Вход";
        }
    };

    changeEnterView();

    $scope.addToCart = function (productId) {
        $http.get('http://localhost:5555/carts/api/v1/cart/add/' + productId).then(function (response) {
            alert("Товар успешно добавлен в корзину");
        }, function () {
            alert("Возникла проблема при добавлении в корзину");
        });
    };

});