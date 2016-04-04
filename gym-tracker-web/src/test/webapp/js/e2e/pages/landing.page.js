var LandingPage = function() {

    browser.get(browser.params.baseURL);


    this.loginModalButton = element(by.buttonText('Login'));
    this.loginEmail = element(by.model('userForLogin.email'));
    this.loginPassword = element(by.model('userForLogin.password'));

    this.registrationModalButton = element(by.buttonText('Registration'));
    this.regEmail = element(by.model('userForRegistration.email'));
    this.regName = element(by.model('userForRegistration.name'));
    this.regPassword = element(by.model('userForRegistration.password'));
    this.regConfirmPassword = element(by.model('userForRegistration.confirmPassword'));
};

module.exports = LandingPage;
