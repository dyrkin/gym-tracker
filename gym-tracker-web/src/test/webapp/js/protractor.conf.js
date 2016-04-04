exports.config = {
    seleniumAddress: 'http://localhost:4444/wd/hub',
    framework: 'jasmine',
    specs: [
        'e2e/landing.js'
    ],
    
    params: {
        baseURL: 'http://localhost:8080/',
        UserLogin: {
            username: 'test@test.com',
            password: 'test'
        },
        toEqualBecauseMatcher: {
            toEqualBecause: function (util, customEqualityTesters) {
                return {
                    compare: function (actual, expected) {
                        return {
                            pass: util.equals(actual, expected.value, customEqualityTesters),
                            message: expected.message
                        };
                    }
                };
            }
        }
    },
    
    onPrepare: function() {
    	browser.driver.manage().window().setSize(1280, 720);
    }
};
