angular.module('gymTrackerApp')
    .service('authSrv', ['$rootScope', '$q', '$route', '$window', '$http', '$location', 'APP', 'PopupService',
        function ($rootScope, $q, $route, $window, $http, $location, APP, PopupService) {

            var authorized = false;
            var user;
            var concurrentAuthTry = false;

            function getCurrentUser(email, password) {
                return $http.get(APP.currentUserPath).success(function (data, status, headers, config) {
                    if (status === 200) {
                        authorized = true;
                        user = data;
                        angular.element("#myLoginModal").modal("hide");
                        $location.path('/main');
                    }
                }).error(function (data, status, headers, config) {
                    //   PopupService.showPopup('error', 'Internal server error', 'Try again', 1600);
                });
            }


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
                        PopupService.showPopup('error', 'Internal server error', 'Try again', 1600);
                    console.log("failure => " + data)
                });
            }

            function registrationUser(email, name, password) {

                return $http.post(APP.userRegistrationPath, {
                        email: email, name: name, password: password
                    }
                ).success(function (data, status, headers, config) {
                    if (status === 200 || status === 201) {
                        user = data;

                        console.log(user);
                        angular.element("#myRegistrationModal").modal("hide");
                        $location.path('/main');
                    }
                }).error(function (data, status, headers, config) {
                    if (status === 401)
                        PopupService.showPopup('error', 'Login Failed', 'Incorrect login or password', 1600);
                    else
                        PopupService.showPopup('error', 'Unknown error', 'Try again', 1600);
                    console.log("failure => " + data)
                });
            }

            function unAuthenticate(idReason) {
                return $http.get(APP.logoutPath)
                    .finally(function () {
                        user = undefined;
                        authorized = false;

                        $location.path("/");
                    });
            }

            return {
                authorized: function () {
                    return authorized;
                },
                getUser: function () {
                    return user;
                },
                unAuthenticate: unAuthenticate,
                authenticate: authenticate,
                registration: registrationUser,
                getCurrentUser: getCurrentUser,
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
