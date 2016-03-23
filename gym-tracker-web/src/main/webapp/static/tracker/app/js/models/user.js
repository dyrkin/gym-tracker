angular.module('app.models', ['ngResource'])
    .factory('User', ['$resource', 'APP', '$rootScope', '$window', '$route', '$q', '$http', '$location', 'CONTEXT',
        function ($resource, APP, $rootScope, $window, $route, $q, $http, $location, CONTEXT) {

            var currentUserInstance = undefined;
            var User = $resource(CONTEXT, {}, {
                'current': {
                    method: 'GET',
                    url: APP.currentUserPath,
                    headers: {'Content-Type': undefined}
                }
            });

            $rootScope.updateUserInfoOnView = function () {
                return User.current({},
                    function (response) {
                        if (!(response && response.success)) {
                            return;
                        }
                    },
                    function (error) {

                    });

            };
            // Class methods
            User.getCurrentUser = function () {
                return currentUserInstance;
            };

            User.setCurrentUser = function (user) {
                currentUserInstance = user;
            };

            User.prototype.getLoginInfo = function () {
                var self = this;
                return User.current({}).$promise
                    .then(
                        function (response) {
                            if (!(response || response == 401)) {
                                return;
                            }
                            $rootScope.currentUser = {
                                login: response._1,
                                email: response._2,
                                pin: response._3
                            };
                            self.isAuthenticated = true;
                            self.login = response._1;
                            self.email = response._2;
                            self.pin = response._3;

                            User.setCurrentUser(self)
                        }, function (response) {
                            self.hasErrors = true;
                        }
                    )
            };

            User.prototype.clearErrors = function () {
                this.hasErrors = false;
                this.errors = [];
            };

            User.prototype.logout = function () {
                var self = this;
                initUser(self);
                $window.location.href = $window.location.protocol + "//" + $window.location.host + "/" + "logout";
            };

            var getHomePath = function () {
                if (User.getCurrentUser().isAuthenticated) {
                    return "/main";
                } else {
                    return '/login'
                }
            };

            User.prototype.openHomePath = function () {
                $location.path(getHomePath());
            };


            User.prototype.deleteLoginInfo = function () {
                var self = this;
                initUser(self);
            };

            var initUser = function (self) {
                self.isAuthenticated = false;
                self.email = '';
                self.roles = [];
                self.hasErrors = false;
                self.photo = [];
                self.language = '';
            };

            User.prototype.buildUser = function () {
                var self = this;
                initUser(self);
            };

            User.prototype.buildUser(self);

            return User;
        }
    ]);