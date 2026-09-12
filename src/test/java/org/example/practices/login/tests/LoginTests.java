package org.example.practices.login.tests;

import org.example.practices.login.prerequisites.LoginSteps;
import org.selenium.base.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {
    private LoginSteps loginSteps;

    @BeforeMethod(alwaysRun = true)
    public void setUpLoginSteps() {
        loginSteps = new LoginSteps(getDriver());
        loginSteps.navigateToLoginPage();
        loginSteps.verifyLoginPageIsDisplayed();
    }

    @Test(description = "Successful login redirects the user to the secure page")
    public void TC01_VerifyLoginSuccessfully() {
        loginSteps.loginAs("practice", "SuperSecretPassword!");
        loginSteps.verifySuccessfulLogin();
    }

    @Test(description = "Invalid username displays an error and keeps the user on the login page")
    public void invalidUsername() {
        loginSteps.loginAs("wrongUser", "SuperSecretPassword!");
        loginSteps.verifyLoginError("Invalid username.");
        loginSteps.verifyUserRemainsOnLoginPage();
    }

    @Test(description = "Invalid password displays an error and keeps the user on the login page")
    public void invalidPassword() {
        loginSteps.loginAs("practice", "WrongPassword");
        loginSteps.verifyLoginError("Invalid password.");
        loginSteps.verifyUserRemainsOnLoginPage();
    }
}
