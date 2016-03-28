'use strict';

var myApp = angular.module('gymTrackerApp', [
    'app.config',
    'app.controllers',
    'app.route',
    'app.services',
    'ui.bootstrap',
    'ng',
    'toaster'

]).run(['$rootScope', '$route', '$location', 'CONTEXT', 'APP', 'authSrv',
    function ($rootScope, $route, $location, CONTEXT, APP, authSrv) {


        $rootScope.$on('$routeChangeStart', function (event, next) {
          if (!authSrv.authorized()) {
              console.log("not logined")
              $location.path("/login")
          } else {
              console.log("logined")
          }
        });
    }
]).directive('passwordConfirm', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        scope: {
            matchTarget: '='
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