angular.module('app').controller('authController', function ($scope, $http, $localStorage, $location) {
    const authPathController = 'http://localhost:5555/authorization';
    const cartPathController = $localStorage.mainHttpPath + '/carts';

    $scope.tryToAuth = function () {
        $http.post(authPathController + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.currentUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;
                    mergeCarts();
                    showUserName();
                    $location.path('/');
                }
            }, function errorCallback() {
                alert("Неверное имя или пароль")
            });
    };

    function showUserName() {
        if ($localStorage.currentUser) {
            $scope.userName = $localStorage.currentUser.username;
        }
    }

    function mergeCarts(){
        $http.get(cartPathController + '/api/v1/cart/' + $localStorage.guestUuid + '/mergeCarts').then(function (response) {
            alert($localStorage.currentUser.username + ", вы успешно авторизовались.")
        });
    }

    showUserName();

    $scope.tryToLogout = function () {
        $scope.clearUser();
        $scope.user = null;
        $location.path('/');
    };

    $scope.clearUser = function () {
        delete $localStorage.currentUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $scope.isUserLoggedIn = function () {
        if ($localStorage.currentUser) {
            return true;
        } else {
            return false;
        }
    };


});

