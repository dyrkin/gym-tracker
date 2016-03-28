angular.module('app.controllers')
    .controller('AuthenticationController', ['$rootScope', '$scope', '$q', '$route', '$window', '$http', '$location', 'APP', 'PopupService', 'authSrv',
        function ($rootScope, $scope, $q, $route, $window, $http, $location, APP, PopupService, authSrv) {


            var authorized;
            var user;
            var concurrentAuthTry = false;


            $scope.userForLogin = {};
            $scope.userForRegistration = {};
            $scope.loginUser = function (isValid){
                if (isValid){
                    authSrv.authenticate($scope.userForLogin.email, $scope.userForLogin.password);
                }
            }





            $scope.registrationUser = function () {

                return $http.post(APP.userRegistrationPath, {
                        email: $scope.userForRegistration.email, name: $scope.userForRegistration.name, password: $scope.userForRegistration.password
                    }
                ).success(function (data, status, headers, config) {

                    if (status === 200 || status === 201) {
                        $scope.userForLogin.email = $scope.userForRegistration.email;
                        $scope.userForLogin.password = $scope.userForRegistration.password;
                        $scope.userForRegistration = undefined;
                        $scope.loginUser()
                    }
                }).error(function (data, status, headers, config) {
                    if (status === 401)
                        PopupService.showPopup('error', 'Login Failed', 'Incorrect login or password', 1600);
                    else
                        PopupService.showPopup('error', 'Unknown error', 'Try again', 1600);
                    console.log("failure => " + data)
                });
            }

            return {
                authorized : function(){
                    return authorized;
                },
                getUser: function(){
                    return user;
                },
                //unAuthenticate : unAuthenticate,
                //authenticate : authenticate,
                //menuItemAllowed : menuItemAllowed,
                setConcurrentAuthTry:function(value){
                    concurrentAuthTry = value;
                },
                isConcurrentAuthTry:function(){
                    return concurrentAuthTry;
                }
            }
        }
    ]);
