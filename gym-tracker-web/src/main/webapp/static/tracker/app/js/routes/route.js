angular.module('gymTrackerApp')
    .config(['$routeProvider', 'CONTEXTProvider',
        function ($routeProvider, CONTEXTProvider) {

            $routeProvider
                .when('/', {
                    templateUrl: CONTEXTProvider.$get() + '/static/tracker/app/views/landing.html',
                    controller: 'AuthenticationController'
                })
                .when('/main', {
                    templateUrl: CONTEXTProvider.$get() + '/static/tracker/app/views/main.html',
                    controller: 'MainController'
                })
                .otherwise({
                    redirectTo: '/'
                });
        }
    ]);