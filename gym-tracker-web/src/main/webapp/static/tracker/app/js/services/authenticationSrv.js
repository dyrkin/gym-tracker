angular.module('app.services')
    .service('authSrv', ['$rootScope', '$q', '$route', '$window', '$http', '$location', 'APP', 'PopupService',
        function ($rootScope, $q, $route, $window, $http, $location, APP, PopupService) {


            var authorized = false;
            var user;
            var concurrentAuthTry = false;

            function authenticate(email, password) {
                return $http.post(APP.authenticatePath, {
                        email: email, password: password
                    }
                ).success(function (data, status, headers, config) {

                    if (status === 200) {
                        authorized = true;
                        user = data;
                        console.log(user);
                        angular.element("#myLoginModal").modal("hide");
                        $location.path('/main');
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

            function registrationUser(email, name, password) {

                return $http.post(APP.userRegistrationPath, {
                        email: email, name: name, password: password
                    }
                ).success(function (data, status, headers, config) {

                    if (status === 200 || status === 201) {

                        //  $scope.loginUser()
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
                authorized: function () {
                    return authorized;
                },
                getUser: function () {
                    return user;
                },
                //unAuthenticate : unAuthenticate,
                authenticate: authenticate,
                //menuItemAllowed : menuItemAllowed,
                setConcurrentAuthTry: function (value) {
                    concurrentAuthTry = value;
                },
                isConcurrentAuthTry: function () {
                    return concurrentAuthTry;
                }
            }
        }
    ]);
