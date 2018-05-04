package com.pscu.seleniumcucumber.framework;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import cucumber.api.Scenario;
import java.net.URL;

/**
 * @ScriptName : StepBase
 * @Description : This class contains generic functionalities like
 *              setup/teardown test environment
 * @Author : Swathin Ratheendren
 * @Creation Date : September 2016 @Modified Date:
 */
public class StepBase {
	// Local Variables
	protected static WebDriver driver;
	protected static Process process;
	protected static Process webkitprocess;
	protected static WebDriverWait webDriverWait;
	protected static Scenario crScenario;
	static DesiredCapabilities capabilities = null; 
	public static String testPlatform;
	public static String testBrowser;

	static String service_url;
	
	//BrowserStack credentials
	// static String BS_USERNAME = "swathin1";
	// static String BS_AUTOMATE_KEY = "sgw2vEWegpwtM6txvfSr";
	
	static String BS_USERNAME = "tahapatel2";
	static String BS_AUTOMATE_KEY = "roqjsEDEWtdYtXXmuzr5";
	 
	static String BrowserStackURL = null;

	/**
	 * @Method: setUp
	 * @Description: This method is used to initialize test execution
	 *               environment i.e. driver initialization, setting
	 *               capabilities for selected device
	 * @author Swathin Ratheendren
	 * @throws Exception 
	 * @Creation Date: September 2016 @Modified Date:
	 */


	public static void setScenario(Scenario cScenario) throws Exception {
		crScenario = cScenario;
	}

	public static void setUp(String Platform, String Browser) throws Exception {
		try {
			testPlatform = Platform;
			testBrowser = Browser;

			if (Platform.equalsIgnoreCase("desktop")||Platform.equalsIgnoreCase("mac")) {
				if(Browser.toLowerCase().equals("chrome"))
				{
					System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/java/com/PSCU/Resources/chromedriver.exe");
					capabilities= DesiredCapabilities.chrome();
					capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
					driver = new ChromeDriver(new ChromeOptions());
					driver.manage().window().maximize();
				}else if(Browser.toLowerCase().equals("firefox")){
					System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir") + "/src/test/java/com/PSCU/Resources/geckodriver.exe");
					System.out.println("Executing test on Firefox browser");
					driver = new FirefoxDriver();
					driver.manage().window().maximize();
				}else if(Browser.toLowerCase().equals("ie")){
					System.setProperty("webdriver.ie.driver",System.getProperty("user.dir") + "/src/test/java/com/PSCU/Resources/IEDriverServer.exe");
					System.out.println("Executing test on Internet Explorer browser");
					driver = new InternetExplorerDriver();
					driver.manage().window().maximize();
				}else if(Browser.toLowerCase().equals("edge")){
					System.setProperty("webdriver.edge.driver",System.getProperty("user.dir") + "/src/test/java/com/PSCU/Resources/MicrosoftWebDriver.exe");
					System.out.println("Executing test on Internet Explorer browser");
					driver = new InternetExplorerDriver();
					driver.manage().window().maximize();
				}else if(Browser.toLowerCase().equals("safari")){
					capabilities = new DesiredCapabilities();
					capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
					System.out.println("Executing test on Safari browser");
					driver = new SafariDriver(capabilities);
					driver.manage().window().maximize();
				}else if(Browser.toLowerCase().equals("browserstack")){
					capabilities = new DesiredCapabilities();
					//Browser stack
					capabilities.setCapability("browserstack.debug", "true");
					capabilities.setCapability("browserName", "android");
					capabilities.setCapability("platform", "ANDROID");
					capabilities.setCapability("device", "Samsung Galaxy S5");
					System.out.println("Executing test on Browser Stack cloud!");
					//BrowserStack credentials
					String USERNAME = "swathin1";
					String AUTOMATE_KEY = "a7bd305d-db4c-45f3-8cd6-e8f2591223c8";
					String BrowserStackURL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";
					driver = new RemoteWebDriver(new URL(BrowserStackURL), capabilities);
					Thread.sleep(7000);
				}else {
					System.out.println("Provide valid browser choice in config file!");
				}

				driver.manage().timeouts().implicitlyWait(Integer.parseInt(System.getProperty("test.implicitlyWait")), TimeUnit.SECONDS);
				//driver.manage().timeouts().pageLoadTimeout(Integer.parseInt(System.getProperty("test.pageLoadTimeout")), TimeUnit.SECONDS);
			}	
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}


	/**
	 * @Method: getDriver
	 * @Description: This method returns appium driver instance.
	 * @return :Appium Driver instance
	 * @author Swathin Ratheendren
	 * @Creation Date: September 2016 @Modified Date:
	 */
	public static WebDriver getDriver() {
		return driver;
	}

	/**
	 * @Method: tearDown
	 * @Description: this method is used to close the appium driver instance.
	 * @author Swathin Ratheendren
	 * @throws IOException
	 * @Creation Date: September 2016 @Modified Date:
	 */
	public static void tearScenario(Scenario scenario) {
		try {
			if (scenario.isFailed()) {
				StepBase.embedScreenshot();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	public static void tearDown() {
		try{
			if(driver!=null)
			{
				if(testPlatform.equals("desktop")||System.getProperty("test.AppType").equals("webapp"))
				{
					driver.manage().deleteAllCookies();
				}
				driver.quit();
				driver=null;
				Thread.sleep(10000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method: embedScreenshot Description: This method attach screenshot to the
	 * cucumber report.
	 * 
	 * @author Swathin Ratheendren
	 * @Creation Date: September 2016 Modified Date:
	 */
	public static void embedScreenshot() {
		try{
			if(System.getProperty("test.DisableScreenShotCapture").equalsIgnoreCase("false"))
			{
			Thread.sleep(1000);
			final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			crScenario.embed(screenshot, "image/png"); // Stick it to HTML report
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void embedProvidedScreenshot(byte[] screenshot) {
		try{
			if(System.getProperty("test.DisableScreenShotCapture").equalsIgnoreCase("false"))
			{
			Thread.sleep(1000);
			crScenario.embed(screenshot, "image/png"); // Stick it to HTML report
			}else{
				throw new Exception("Test Property - test.ScreenShotCapture is disabled!");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
