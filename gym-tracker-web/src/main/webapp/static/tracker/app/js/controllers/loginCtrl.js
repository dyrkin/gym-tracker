angular.module('app.controllers')
    .controller('LoginController', ['$rootScope', '$scope', '$q', '$route', '$window', 'User', '$http', '$location', 'APP', 'PopupService',
        function ($rootScope, $scope, $q, $route, $window, User, $http, $location, APP, PopupService) {


            $scope.user = {};
            $scope.loginUser = function (isValid) {
                if (isValid) {
                    return $http.post(APP.authenticatePath, {
                            email: $scope.user.email, password: $scope.user.password
                        }
                    ).success(function (data, status, headers, config) {
                        if (status === 200) {
                            var user = new User();
                            user.getLoginInfo().then(function () {
                                user.openHomePath();
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
                ;
            }
        }
    ]);
