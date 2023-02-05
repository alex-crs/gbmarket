angular.module('app').controller('regController', function ($scope, $http, $localStorage, $location) {
    const regPathController = 'http://localhost:5555/registration';

    $scope.tryToReg = function () {
        $http.post('http://localhost:5555/authorization/registration', $scope.registrationUserDto).then(function (response) {
            if (response.status===201){
                alert("Пользователь успешно создан");
                $location.path('/auth');
            } else {
                alert("Возникла проблема при создании пользователя");
            }
        });
    };

});

