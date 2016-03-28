angular.module('app.controllers')
    .controller('RegistrationController', ['$rootScope', '$scope', '$q', '$route', '$window',
        function ($rootScope, $scope, $q, $route, $window) {
            $scope.user = {
                name: '',
                email: '',
                password: ''
            };
        }
    ]);
