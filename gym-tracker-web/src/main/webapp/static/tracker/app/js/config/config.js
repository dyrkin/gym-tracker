angular.module('gymTrackerApp')
    .constant('APP', {
        /*AngularJS Routes path. See route.js for example*/
        loginPath: "/",
        mainPath: "/main",

        authenticatePath: "/json/user/authenticate",
        userRegistrationPath: "/json/user/register",
        logoutPath: "/json/user/logout",

        /*Path to UserController*/
        currentUserPath: "/json/user/current"
    })
    .constant('API', {
        /*Path to JSON Content to API calls*/
        apiProgramPath: "/json/api/program/:id",
        apiValidatePath: "/json/api/validate/:id",
        apiRemovePinPath: "/json/api/remove_pin/:id"

    }).factory('CONTEXT', ['$window',
    function ($window) {
        var url = $window.location.pathname;
        return url.substring(0, url.lastIndexOf('/'));
    }
]);