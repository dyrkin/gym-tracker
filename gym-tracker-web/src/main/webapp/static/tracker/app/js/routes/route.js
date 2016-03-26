angular.module('app.route', ['ngRoute'])
    .config(['$routeProvider', 'CONTEXTProvider',
        function ($routeProvider, CONTEXTProvider) {

            $routeProvider
                .when('/login', {
                    templateUrl: CONTEXTProvider.$get() + '/static/tracker/app/views/login.html',
                    controller: 'AuthenticationController'
                })
                .when('/registration', {
                    templateUrl: CONTEXTProvider.$get() + '/static/tracker/app/views/registration.html',
                    controller: 'RegistrationController'
                })
                .when('/main', {
                templateUrl: CONTEXTProvider.$get() + '/static/tracker/app/views/main.html',
                controller: 'MainController'
            })
                .otherwise({
                redirectTo: '/login'
            });
        }
    ]);