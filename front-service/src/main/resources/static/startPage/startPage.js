//в текущем методе мы создаем некое приложение app и привязываем его к indexController.
//index.html мы работаем с indexController
angular.module('app').controller('startPageController', function ($rootScope, $scope, $http, $localStorage) {
    const autPathController = $localStorage.mainHttpPath + '/authorization';

    $scope.loadUserInfo = function () {
        if ($localStorage.currentUser) {
            $scope.name = $localStorage.currentUser.username;
        } else {
            $scope.name = "Незнакомец";
        }
    };

    $scope.loadUserInfo();

    $scope.isAuthUser = function () {
        return $localStorage.currentUser;
    }
});

