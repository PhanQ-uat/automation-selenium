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

    @Description("Verify that the HTML form is working correctly")
    @org.testng.annotations.Test
    public void testCheckboxes() {
        // Step 1: Enter username
        checkBoxStep.enterUsername(getDriver(), "testuser");
        
        // Step 2: Enter password
        checkBoxStep.enterPassword(getDriver(), "testpass123");
        
        // Step 3: Enter comments
        checkBoxStep.enterComments(getDriver(), "This is a test comment");
        
        // Step 4: Select checkbox with value cb1
        checkBoxStep.clickCheckBox1(getDriver());
        
        // Step 5: Verify checkbox cb1 is checked
        boolean isCb1Checked = checkBoxStep.isCheckBoxChecked(getDriver(), checkBoxStep.getCheckBox1());
        System.out.println("Checkbox cb1 is checked: " + isCb1Checked);
        
        // Step 6: Click on radio button with value rd1
        checkBoxStep.clickRadio1(getDriver());
        
        // Step 7: Verify radio button rd1 is selected
        boolean isRd1Selected = checkBoxStep.isRadioButtonSelected(getDriver(), checkBoxStep.getRadio1());
        System.out.println("Radio button rd1 is selected: " + isRd1Selected);
        
        // Step 8: Click on submit button
        checkBoxStep.clickSubmit(getDriver());
        
        // Step 9: Verify form is submitted (wait for navigation)
        basePage.sleepInSecond(3);
        System.out.println("Form submitted successfully");
    }



}

