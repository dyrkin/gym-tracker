angular.module('app.controllers')
    .controller('LoginController', ['$rootScope', '$scope', '$q', '$route', '$window', 'User', '$http', '$location', 'APP',
        function ($rootScope, $scope, $q, $route, $window, User, $http, $location, APP) {


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
                        }
                    }).error(function (data, status, headers, config) {
                        console.log("failure => " + data)
                    });
                };
            }
        }
    ]);
