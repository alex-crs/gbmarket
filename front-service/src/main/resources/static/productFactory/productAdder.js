angular.module('app').controller('productAdderController', function ($scope, $http, $localStorage, $location) {
    const corePathController = $localStorage.mainHttpPath + '/core';

    $scope.tryAddProduct = function () {
        $http.post(corePathController + '/api/v1/products/add', $scope.ProductFullInfo).then(function (response) {
            if (response.status===202){
                alert('Продукт успешно добавлен в базу')
                $location.path('/store');
            }
        });
    };



});

