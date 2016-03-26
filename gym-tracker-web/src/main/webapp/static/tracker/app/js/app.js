'use strict';

var myApp = angular.module('gymTrackerApp', [
    'app.config',
    'app.controllers',
    'app.models',
    'app.route',
    'ui.bootstrap',
    'ng',
    'toaster'

]).run(['$rootScope', 'User', '$route', '$location', 'CONTEXT', 'APP',
    function ($rootScope, User, $route, $location, CONTEXT, APP) {
        var user = new User();
        var loginUrl = CONTEXT + APP.loginPath;
        var mainUrl = CONTEXT + APP.mainPath;

        $rootScope.$on('$routeChangeStart', function (event, next) {
            if (typeof User.getCurrentUser() == 'undefined') {
                user.getLoginInfo().then(function () {
                    if (typeof User.getCurrentUser() != 'undefined' && User.getCurrentUser().isAuthenticated) {
                        User.getCurrentUser().openHomePath();
                    } else {
                        $location.path(loginUrl);
                    }
                });
            } else if ($location.path() == loginUrl) {
                $location.path(mainUrl)
            }
        });
    }
]).directive('passwordConfirm', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        scope: {
            matchTarget: '=',
        },
        require: 'ngModel',
        link: function link(scope, elem, attrs, ctrl) {
            var validator = function (value) {
                ctrl.$setValidity('match', value === scope.matchTarget);
                return value;
            }

            ctrl.$parsers.unshift(validator);
            ctrl.$formatters.push(validator);

            // This is to force validator when the original password gets changed
            scope.$watch('matchTarget', function(newval, oldval) {
                validator(ctrl.$viewValue);
            });

        }
    };
}]);
;