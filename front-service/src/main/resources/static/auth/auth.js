angular.module('app').controller('authController', function ($scope, $http, $localStorage, $location) {
    const authPathController = 'http://localhost:5555/authorization';

    $scope.tryToAuth = function () {
        $http.post(authPathController + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.currentUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;
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

