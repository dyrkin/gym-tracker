angular.module('gymTrackerApp').service('PopupService', function(toaster) {

    /* https://github.com/jirikavi/AngularJS-Toaster */

    this.showPopup = function(messageType, title, text, timeout) {
        toaster.pop({
            type: messageType,
            title: title,
            body: text,
            showCloseButton: true,
            timeout: timeout
        });
    };
});