'use strict';

var myApp = angular.module('gymTrackerApp', [
    'ui.bootstrap',
    'ng',
    'toaster',
    'ngRoute'

]).run(['$rootScope', '$route', '$location', 'CONTEXT', 'APP', 'authSrv',
    function ($rootScope, $route, $location, CONTEXT, APP, authSrv) {


        $rootScope.$on('$routeChangeStart', function (event, next) {
            if (!authSrv.authorized()) {
                $location.path("/login")
            } else {


            }
        });
    }
]);