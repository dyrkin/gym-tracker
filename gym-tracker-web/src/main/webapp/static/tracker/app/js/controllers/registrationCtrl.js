angular.module('app.controllers')
    .controller('RegistrationController', ['$rootScope', '$scope', '$q', '$route', '$window', 'User',
        function ($rootScope, $scope, $q, $route, $window, User) {
            $scope.user = {
                name: '',
                email: '',
                password: ''
            };
        }
    ]);
