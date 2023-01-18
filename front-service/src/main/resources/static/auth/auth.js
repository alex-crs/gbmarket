angular.module('app', ['ngStorage']).controller('indexController', function ($scope, $http, $localStorage) {

    $scope.tryToAuth = function () {
        $http.post("http://localhost:5555/authorization/auth", $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.currentUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;
                    showUserName();
                }
            }, function errorCallback(response) {
                alert("Неверное имя или пароль")
            });
    };

    function showUserName (){
        if ($localStorage.currentUser){
            $scope.userName = $localStorage.currentUser.username;
        }
    };

    showUserName();

    $scope.tryToLogout = function () {
        $scope.clearUser();
        $scope.user = null;
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

    $scope.run = function() {
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
    };

    $scope.run();

});

