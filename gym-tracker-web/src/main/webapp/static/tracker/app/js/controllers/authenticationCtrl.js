angular.module('gymTrackerApp')
    .controller('AuthenticationController', ['$rootScope', '$scope', '$q', '$route', '$window', '$http', '$location', 'APP', 'PopupService', 'authSrv',
        function ($rootScope, $scope, $q, $route, $window, $http, $location, APP, PopupService, authSrv) {

            $scope.userForLogin = {};
            $scope.userForRegistration = {};

            $scope.loginUser = function (isValid) {
                if (isValid) {
                    authSrv.authenticate($scope.userForLogin.email, $scope.userForLogin.password);
                }
            };

            $scope.registerUser = function (isValid) {
                if (isValid) {
                    authSrv.registration($scope.userForRegistration.email, $scope.userForRegistration.name, $scope.userForRegistration.password);
                }
            };
        }
    ]);
