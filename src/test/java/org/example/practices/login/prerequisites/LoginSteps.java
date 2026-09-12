package org.example.practices.login.prerequisites;

import org.example.practices.login.page.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LoginSteps {
    private static final String LOGIN_URL = "https://practice.expandtesting.com/login";

    private final WebDriver driver;
    private final LoginPage loginPage;

    public LoginSteps(WebDriver driver) {
        this.driver = driver;
        this.loginPage = new LoginPage(driver);
    }

    public void navigateToLoginPage() {
        driver.get(LOGIN_URL);

    }

    public void verifyLoginPageIsDisplayed() {
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed");
    }

    public void loginAs(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
    }

    public void verifySuccessfulLogin() {
        Assert.assertTrue(loginPage.isSecurePageDisplayed(), "User should be redirected to /secure");
        Assert.assertTrue(
                loginPage.getFlashMessage().contains("You logged into a secure area!"),
                "Successful-login message should be displayed"
        );
        Assert.assertTrue(loginPage.isLogoutButtonDisplayed(), "Logout button should be displayed");
    }

    public void verifyLoginError(String expectedMessage) {
        Assert.assertTrue(
                loginPage.getFlashMessage().contains(expectedMessage),
                "Expected login error was not displayed: " + expectedMessage
        );
    }

    public void verifyUserRemainsOnLoginPage() {
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"), "User should remain on the login page");
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should still be displayed");
    }
}
