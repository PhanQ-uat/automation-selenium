package org.example.practices.checkbox.prerequisuites;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.selenium.base.BasePage;

import java.time.Duration;

import static java.sql.DriverManager.getDriver;

@Getter
@Setter
public class checkBoxStep {
    private BasePage basePage = new BasePage();
    
    // Locators
    By checkBox1 = By.xpath("//td//input[@type='checkbox' and @value='cb1']");
    String checkBox1Value = "//td//input[@type='checkbox' and @value='cb1']";
    
    By checkBox2 = By.xpath("//td//input[@type='checkbox' and @value='cb2']");
    String checkBox2Value = "//td//input[@type='checkbox' and @value='cb2']";
    
    By checkBox3 = By.xpath("//td//input[@type='checkbox' and @value='cb3']");
    String checkBox3Value = "//td//input[@type='checkbox' and @value='cb3']";
    
    By radio1 = By.xpath("//td//input[@type='radio' and @value='rd1']");
    String radio1Value = "//td//input[@type='radio' and @value='rd1']";
    
    By radio2 = By.xpath("//td//input[@type='radio' and @value='rd2']");
    String radio2Value = "//td//input[@type='radio' and @value='rd2']";
    
    By radio3 = By.xpath("//td//input[@type='radio' and @value='rd3']");
    String radio3Value = "//td//input[@type='radio' and @value='rd3']";
    
    By usernameField = By.xpath("//input[@name='username']");
    String usernameValue = "//input[@name='username']";
    
    By passwordField = By.xpath("//input[@name='password']");
    String passwordValue = "//input[@name='password']";
    
    By commentsField = By.xpath("//textarea[@name='comments']");
    String commentsValue = "//textarea[@name='comments']";
    
    By submitButton = By.xpath("//input[@type='submit']");
    String submitButtonValue = "//input[@type='submit']";
    /**
     * Generic click helper. Finds element by the given locator and clicks it.
     * Use this for any clickable element.
     */
    public void click(WebDriver driver, By locator) {
        driver.findElement(locator).click();
    }
    
    /**
     * Scrolls the page so the element identified by locator is in view.
     * Uses JavaScript scrollIntoView because WebElement has no scrollIntoView() method in Java.
     */
    public void scrollToElement(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", el);


    }

    /**
     * Convenience method to click the checkbox cb1 defined in this step class.
     */
    public void clickCheckBox1(WebDriver  driver) {
    basePage.clickToElement(driver, checkBox1Value);
    }
    
    public void clickCheckBox2(WebDriver driver) {
        basePage.clickToElement(driver, checkBox2Value);
    }
    
    public void clickCheckBox3(WebDriver driver) {
        basePage.clickToElement(driver, checkBox3Value);
    }
    
    public void clickRadio1(WebDriver driver) {
        basePage.clickToElement(driver, radio1Value);
    }
    
    public void clickRadio2(WebDriver driver) {
        basePage.clickToElement(driver, radio2Value);
    }
    
    public void clickRadio3(WebDriver driver) {
        basePage.clickToElement(driver, radio3Value);
    }
    
    public void enterUsername(WebDriver driver, String username) {
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(username);
    }
    
    public void enterPassword(WebDriver driver, String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }
    
    public void enterComments(WebDriver driver, String comments) {
        driver.findElement(commentsField).clear();
        driver.findElement(commentsField).sendKeys(comments);
    }
    
    public void clickSubmit(WebDriver driver) {
        basePage.clickToElement(driver, submitButtonValue);
    }
    
    public boolean isCheckBoxChecked(WebDriver driver, By locator) {
        return driver.findElement(locator).isSelected();
    }
    
    public boolean isRadioButtonSelected(WebDriver driver, By locator) {
        return driver.findElement(locator).isSelected();
    }
}
