var LandingPage = require('./pages/landing.page.js');

describe('landing page', function () {
    var EC = protractor.ExpectedConditions;
    var landingPage;
    beforeEach(function () {
        landingPage = new LandingPage();
        jasmine.addMatchers(browser.params.toEqualBecauseMatcher);
    });

    it('should be available on Login Modal', function () {
        browser.ignoreSynchronization = true;

        landingPage.loginModalButton.click();

        expect(landingPage.loginEmail.isPresent()).toEqualBecause({value :true, message: 'Email input should be present'});
        expect(landingPage.loginPassword.isPresent()).toEqualBecause({value :true, message: 'Password input should be present'});
        $('button[id="closeLoginModalBtn"]').click();
    });

    it('should be available on Registration Modal', function () {
        browser.ignoreSynchronization = true;

        landingPage.registrationModalButton.click();

        expect(landingPage.regEmail.isPresent()).toEqualBecause({value :true, message: 'Email input should be present'});
        expect(landingPage.regName.isPresent()).toEqualBecause({value :true, message: 'Name input should be present'});

        expect(landingPage.regPassword.isPresent()).toEqualBecause({value :true, message: 'Password input should be present'});
        expect(landingPage.regConfirmPassword.isPresent()).toEqualBecause({value :true, message: 'Confirmation Password input should be present'});

        $('button[id="closeRegistrationModalBtn"]').click();
    });

    it('Log In Process', function () {
        browser.ignoreSynchronization = true;

        landingPage.loginModalButton.click();
        landingPage.loginEmail.sendKeys('test@test.com');
        landingPage.loginPassword.sendKeys('test');
        //$('button[class="btn btn-primary btn-block"]').click().then(function (){
        //  $('button[id="logout"]').click();
        //});

    });
});