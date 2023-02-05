angular.module('app').controller('authController', function ($scope, $http, $localStorage, $location) {
    const authPathController = 'http://localhost:5555/authorization';
    const cartPathController = $localStorage.mainHttpPath + '/carts';

    $scope.tryToAuth = function () {
        let settings = {
            "url": "http://localhost:5555/auth/realms/master/protocol/openid-connect/token",
            "method": "POST",
            "timeout": 0,
            "headers": {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            "data": {
                "client_id": "test-client",
                "grant_type": "password",
                "username": "",
                "password": ""
            }
        };
        settings.data.username = $scope.user.username;
        settings.data.password = $scope.user.password;
        $.ajax(settings)
            .then(function (response) {
                if (response.access_token) {
                    let jsonPayload = parseJwt(response.access_token);
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.access_token;
                    $localStorage.currentUser = {
                        username: jsonPayload.preferred_username,
                        token: response.access_token,
                        fullName: jsonPayload.name
                    };

                    $scope.user.username = null;
                    $scope.user.password = null;
                    mergeCarts();
                    showUserName();
                    $location.path('/');
                }
            }, function (response) {
                if (response.status === 401) {
                    alert("Неверное имя или пароль")
                }
            });
    };

    function parseJwt(token) {
        let base64Url = token.split('.')[1];
        let base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        let jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function (c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));

        return JSON.parse(jsonPayload);
    }

    function showUserName() {
        if ($localStorage.currentUser) {
            $scope.userName = $localStorage.currentUser.username;
        }
    }

    function mergeCarts() {
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

