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
    private BasePage basePage;
    By checkBox1 = By.xpath("//td//input[@type='checkbox' and @value='cb1']");
    String checkBox1Value = "//td//input[@type='checkbox' and @value='cb1']";
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
}
