package org.example.practices.checkbox;

import jdk.jfr.Description;
import org.example.practices.checkbox.prerequisuites.checkBoxStep;
import org.selenium.base.BasePage;
import org.selenium.base.BaseTest;
import org.testng.annotations.BeforeMethod;

public class TC1_VerifyThatTheCheckBoxWorkingCorrectly extends BaseTest {

    private checkBoxStep checkBoxStep;
    public BasePage basePage;
    @BeforeMethod
    public void setUp() {
        checkBoxStep = new checkBoxStep();
        basePage = new BasePage();
        getDriver().get("https://testpages.eviltester.com/pages/forms/html-form/");
    }

    @Description("Verify that the checkboxes are working correctly")
    @org.testng.annotations.Test
    public void testCheckboxes() {
        // Step 1: Scroll to the checkbox

       checkBoxStep.scrollToElement(getDriver(), checkBoxStep.getCheckBox1());
        checkBoxStep.clickCheckBox1(getDriver());
        basePage  .sleepInSecond(5);}
}
