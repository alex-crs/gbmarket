(function () {
    angular
        .module('app', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'startPage/startPage.html',
                controller: 'startPageController'
            })
            .when('/index', {
                templateUrl: 'index.html',
                controller: 'indexController'
            })
            .when('/store', {
                templateUrl: 'store/store.html',
                controller: 'storeController'
            })
            .when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
            })
            .when('/orders', {
                templateUrl: 'orders/orders.html',
                controller: 'ordersController'
            })
            .when('/orderDetails', {
                templateUrl: 'orderDetails/orderDetails.html',
                controller: 'orderDetailsController'
            })
            .when('/auth', {
                templateUrl: 'auth/auth.html',
                controller: 'authController'
            })
            .when('/productDetails', {
                templateUrl: 'productDetails/productDetails.html',
                controller: 'productDetailsController'
            });
    }

    function run($rootScope, $http, $localStorage) {
        $localStorage.mainHttpPath = 'http://localhost:5555';
        if ($localStorage.currentUser) {
            try {
                let jwt = $localStorage.currentUser.token;
                let payload = JSON.parse(atob(jwt.split('.')[1]));
                let currentTime = parseInt(new Date().getTime() / 1000);
                if (currentTime > payload.exp) {
                    console.log("Token is expired!!!");
                    delete $localStorage.currentUser;
                    $http.defaults.headers.common.Authorization = '';
                }
            } catch (e) {

            }
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.currentUser.token;
        }
            $http.get($localStorage.mainHttpPath + '/carts/api/v1/cart/generateUuid/newUuid').then(function (response) {
                $localStorage.guestUuid = response.data.value;
            });
    }

})();

angular.module('app').controller('indexController', function ($scope, $http, $location, $localStorage) {
    $scope.isAuthUser = function () {
        return !!$localStorage.currentUser;
    }
});

