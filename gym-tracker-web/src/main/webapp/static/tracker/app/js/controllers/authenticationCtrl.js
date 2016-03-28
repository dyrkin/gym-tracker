angular.module('app.controllers')
    .controller('AuthenticationController', ['$rootScope', '$scope', '$q', '$route', '$window', 'User', '$http', '$location', 'APP', 'PopupService', '$uibModalStack',
        function ($rootScope, $scope, $q, $route, $window, User, $http, $location, APP, PopupService, $uibModalStack) {

            $scope.userForLogin = {};
            $scope.loginUser = function () {
                return $http.post(APP.authenticatePath, {
                        email: $scope.userForLogin.email, password: $scope.userForLogin.password
                    }
                ).success(function (data, status, headers, config) {

                    if (status === 200) {
                        var user = new User();
                        user.getLoginInfo().then(function () {
                            angular.element("#myLoginModal").modal("hide");
                            user.openHomePath();
                            $scope.userForLogin = undefined;
                        });
                    } else {
                        PopupService.showPopup('error', 'Login Failed', 'Incorrect login or password', 1600);
                    }
                }).error(function (data, status, headers, config) {
                    if (status === 401)
                        PopupService.showPopup('error', 'Login Failed', 'Incorrect login or password', 1600);
                    else
                        PopupService.showPopup('error', 'Unknown error', 'Try again', 1600);
                    console.log("failure => " + data)
                });
            }


            $scope.userForRegistration = {};
            $scope.registrationUser = function () {

                console.log($scope.userForRegistration.email)
                console.log($scope.userForRegistration.name)
                console.log($scope.userForRegistration.password)
                console.log($scope.userForRegistration.confirmPassword)






                return $http.post(APP.userRegistrationPath, {
                        email: $scope.userForRegistration.email, name: $scope.userForRegistration.name, password: $scope.userForRegistration.password
                    }
                ).success(function (data, status, headers, config) {

                    if (status === 200 || status === 201) {
                      //  angular.element("#myRegistrationModal").modal("hide");
                      //  angular.element("#myLoginModal").modal("show");
                      //console.log('success')
                      //PopupService.showPopup('success', 'Registration', 'Successful Registration', 2500);
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
        }
    ]);
