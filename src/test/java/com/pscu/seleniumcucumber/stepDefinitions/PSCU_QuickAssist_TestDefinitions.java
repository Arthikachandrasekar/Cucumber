package com.pscu.seleniumcucumber.stepDefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.pscu.seleniumcucumber.framework.HashMapContainer;
import com.pscu.seleniumcucumber.framework.StepBase;
import com.pscu.seleniumcucumber.framework.Utilities;
import com.pscu.seleniumcucumber.framework.WrapperFunctions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.runtime.CucumberException;
public class PSCU_QuickAssist_TestDefinitions{
	
	static WrapperFunctions wrapFunc = new WrapperFunctions();
	static StepBase sb = new StepBase();
	static WebDriver driver;
	static Utilities util = new Utilities();

	@When("^I Click '(.*)' with reference to text: '(.*)'$")
	public static void I_click_with_reference_to_text(String element, String text) {
		try {
			driver=StepBase.getDriver();
			String locator = HashMapContainer.getPO(element);
			locator= locator.replace(" replaceFirstName ", text);
			HashMapContainer.addPO(element, locator);
			//wrapFunc.waitForElementPresence(GetPageObject.OR_GetElement(element));
			//driver.findElement(GetPageObject.OR_GetElement(element)).click();
			//wrapFunc.waitForElementPresence(By.xpath(locator));
			System.out.println("driver: "+driver);
			driver.findElement(By.xpath(locator)).click();
			//StepBase.embedScreenshot(); Reporter.addScreenCaptureFromPath(takeScreenshot);
			wrapFunc.waitForPageToLoad();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CucumberException(e.getMessage(), e);
		}
	}	
}