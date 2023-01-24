//в текущем методе мы создаем некое приложение app и привязываем его к indexController.
//index.html мы работаем с indexController
angular.module('app').controller('startPageController', function ($rootScope, $scope, $http, $localStorage) {
    const autPathController = $localStorage.mainHttpPath + '/authorization';

    $scope.loadUserInfo = function () {
        $http.get(autPathController + '/info').then(function (response) {
            $scope.UserInfo = response.data;
        }, function () {
            $scope.UserInfo = {
                "name": "Незнакомец"
            }
        });
    };

    $scope.loadUserInfo();

    $scope.isAuthUser = function () {
        return !!$localStorage.currentUser;
    }
});

