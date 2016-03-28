'use strict';

var myApp = angular.module('gymTrackerApp', ['ui.bootstrap', 'ng', 'toaster', 'ngRoute'

]).run(['$rootScope', '$route', '$location', 'CONTEXT', 'APP', 'authSrv',
    function ($rootScope, $route, $location, CONTEXT, APP, authSrv) {

        $rootScope.$on('$routeChangeStart', function (event, next) {
            if (!authSrv.authorized() && typeof next != 'undefined' && typeof next.$$route != 'undefined' && next.$$route.originalPath != '/') {

                authSrv.getCurrentUser().then(function () { // success
                        $location.path("/main")
                    },
                    function () { // error
                        $location.path("/")
                    });
            } else if (authSrv.authorized() && typeof next != 'undefined' && typeof next.$$route != 'undefined' && next.$$route.originalPath == '/') {
                $location.path("/main")
            }
        });

        $rootScope.logout = function () {
            authSrv.unAuthenticate();
        };
    }
]);