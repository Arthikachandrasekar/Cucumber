package com.pscu.seleniumcucumber.Runner;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import org.junit.runner.RunWith;

import com.cucumber.listener.Reporter;
import com.pscu.seleniumcucumber.framework.GenerateCustomReport;
import com.pscu.seleniumcucumber.framework.HashMapContainer;
import com.pscu.seleniumcucumber.framework.JsonReader;
import com.pscu.seleniumcucumber.framework.StepBase;
import com.pscu.seleniumcucumber.framework.TestConfig;

import cucumber.api.CucumberOptions;

@CucumberOptions(
		// dryRun = false,
		plugin = {"com.cucumber.listener.ExtentCucumberFormatter:",
				  "pretty",
				  "html:src/test/java/com/pscu/seleniumcucumber/testresult/cucumber-report",
				  "junit:src/test/java/com/pscu/seleniumcucumber/testresult/cucumber-report/cucumber_1.xml",
				  "json:src/test/java/com/pscu/seleniumcucumber/testresult/cucumber-report/cucumber_1.json"},
		strict = true, 
		features = { "src\\test\\java\\com\\pscu\\seleniumcucumber\\features" }, 
		glue = { "com.pscu.seleniumcucumber.Hooks","com.pscu.seleniumcucumber.stepDefinitions" }, 
		tags = { "@QuickAssist_Sanity","~@Ignore" },  
		monochrome = false
		)

//@RunWith(Cucumber.class)
@RunWith(ExtendedCucumberRunner.class)
public class TestRunner_2{
	
	static String Platform = "desktop";
	static String Browser = "firefox";
	
	
	@BeforeSuite
	public static void setUp() {
		try {
			//Load All Test configurations 
			TestConfig.LoadAllConfig();
			
			if(Platform.equals("desktop")){
				StepBase.setUp(Platform,Browser);	
			}else if(Platform.equals("android") ||Platform.equals("ios") ) {
				JsonReader.ReadJson(System.getProperty("user.dir") + "/src/test/java/com/Cucumber/mobileConfig/android/nodeConfigOnePlus.json");
				if(TestConfig.objConfig.get("test.AppType").equals("webapp")){
					// TODO - Correct Appium Updated path correlation 
					//StepBase.appiumStart(HashMapContainer.get("port"), objConfig.getProperty("nodePath"), objConfig.getProperty("appiumJSPath"), null);
				}else{
					// TODO - Correct Appium Updated path correlation 
					//StepBase.appiumStart(HashMapContainer.get("port"), objConfig.getProperty("nodePath"), objConfig.getProperty("appiumJSPath"), objConfig.getProperty("test.appName"));
				}
				//Disable following line if connecting device via cable
				//HashMapContainer.add("udid", "10.22.175.97:5555");
				//HashMapContainer.add("udid", "192.168.0.101:5555");
				//*********************
				//Thread.sleep(10000);
					StepBase.setUp(Platform,Browser);
			}else{
				System.out.println("Enter valid platform choice: desktop / android / ios");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterSuite
	public static void tearDown() {
		try {
			//WrapperFunctions.sendMail("C:\\Users\\user\\workspace\\CucumberFramework\\output\\Run_1475150446542\\report.html", "swathin@royalcyber.com");
			StepBase.tearDown();	
			
			//Extent Report Config
			Reporter.loadXMLConfig(new File("src/test/resources/extent-config.xml"));
	        Reporter.setSystemInfo("User", System.getProperty("user.name"));
	        Reporter.setSystemInfo("OS", Platform);
	        Reporter.setSystemInfo("Browser", Browser);
	        Reporter.setTestRunnerOutput("Extent Report for Test Run on Firefox");
	        
			if(!System.getProperty("test.DisableCucumberReport").equalsIgnoreCase("true"))
			{
		        //Cucumber Report Generation
				GenerateCustomReport.generateCustomeReport(Browser,Platform);
				HashMapContainer.ClearHM();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
