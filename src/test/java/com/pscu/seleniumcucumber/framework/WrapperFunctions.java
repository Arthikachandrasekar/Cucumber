package com.pscu.seleniumcucumber.framework;

import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.pscu.seleniumcucumber.pageObjects.GetPageObject;


public class WrapperFunctions
{
	private static StepBase objStepBase = new StepBase();
	static By by;
	private static WebDriver driver = objStepBase.getDriver();

	/**
	 * @Method: waitForElementToBeClickable
	 * @Description: This is wrapper method wait for element to be click able
	 * @param locator - By identification of element
	 * @param waitInSeconds - wait time  
	 * @author Swathin Ratheendren  Creation Date: September 2016   Modified Date: 
	 */
	public void waitForElementToBeClickable(By locator, int waitInSeconds) 
	{
		try 
		{
			Wait<WebDriver> wait = new WebDriverWait(driver, waitInSeconds).ignoring(StaleElementReferenceException.class);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
		} 
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}

	// Element highlighter code
	public static void highLightElement(WebElement element)throws Exception
	{
		try {
			if(System.getProperty("test.highlightElements").equals("true")){
				JavascriptExecutor js=(JavascriptExecutor)driver; 
				for(int i=0;i<2;i++){
					js.executeScript("arguments[0].setAttribute('style', 'background: green; border: 2px solid green;');", element);
					Thread.sleep(50);
					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
					Thread.sleep(20);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void waitForPageToLoad() 
	{
		try 
		{
			ExpectedCondition<Boolean> expect =  null;
			Wait<WebDriver> wait = new WebDriverWait(driver, Integer.parseInt(System.getProperty("test.pageLoadTimeout"))).ignoring(StaleElementReferenceException.class);
			//Condition to check page load complete
			if(StepBase.testPlatform.equals("desktop")||System.getProperty("test.AppType").equals("webapp")){
				expect = new ExpectedCondition<Boolean>(){ public Boolean apply(WebDriver dr)
				{
					return  ((JavascriptExecutor)dr).executeScript("return document.readyState").equals("complete");
				}
				};
				wait.until(expect);
			}

		} 
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}

	/**
	 * @Method: waitForElementPresence
	 * @Description: This is wrapper method wait for element presence
	 * @param locator - By identification of element
	 * @param waitInSeconds - wait time 
	 * @author Swathin Ratheendren  Creation Date: September 2016   Modified Date: 
	 */
	public boolean waitForElementPresence(By locator, int waitInSeconds) 
	{
		try 
		{
			Wait<WebDriver> wait = new WebDriverWait(driver, waitInSeconds).ignoring(StaleElementReferenceException.class);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			return true;
		} 
		catch(Exception exception)
		{
			exception.printStackTrace();
			return false;
		}
	}
	
	/**
	 * @Method: waitForElementPresence
	 * @Description: This is wrapper method wait for element presence
	 * @param locator - By identification of element
	 * @param waitInSeconds - wait time 
	 * @author Swathin Ratheendren  Creation Date: September 2016   Modified Date: 
	 */
	public boolean waitForElementPresence(By locator) 
	{
		try 
		{
			Wait<WebDriver> wait = new WebDriverWait(driver, Integer.parseInt(System.getProperty("test.pageLoadTimeout"))).ignoring(StaleElementReferenceException.class);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			return true;
		} 
		catch(Exception exception)
		{
			exception.printStackTrace();
			return false;
		}
	}

	/**
	 * @Method: waitForElementVisibility
	 * @Description: This is wrapper method wait for element presence
	 * @param locator - By identification of element
	 * @param waitInSeconds - wait time 
	 * @author Swathin Ratheendren  Creation Date: September 2016   Modified Date: 
	 */
	public boolean waitForElementVisibility(By locator) 
	{
		try 
		{

			int timeOut = Integer.parseInt(System.getProperty("test.pageLoadTimeout"));

			Wait<WebDriver> wait = new WebDriverWait(driver, timeOut).ignoring(ElementNotVisibleException.class);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			return true;
		} 
		catch(Exception exception)
		{
			exception.printStackTrace();
			return false;
		}
	}

	/**
	 * @Method: checkElement_Existance
	 * @Description: This is wrapper method to check the existence of any web element on the page
	 * @param locator - By identification of element
	 * @param waitInSeconds - wait time 
	 * @return - true if element present  
	 * @author Swathin Ratheendren    Creation Date: September 2016   Modified Date: 
	 */
	public boolean checkElementExistence(By locator, int... sTimeInSecond)
	{
		try 
		{
			WebDriverWait wait = null;
			if(sTimeInSecond.length > 0)
			{
				wait = new WebDriverWait(driver, sTimeInSecond[0]);
			}
			else
			{
				wait = new WebDriverWait(driver, 10);
			}
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			WebElement ele= driver.findElement(locator);
			return ele.isDisplayed();
		}
		catch(Exception exception)
		{
			return false;
		}
	}

	/**
	 * @Method: click
	 * @Description: This is wrapper method to click on web element 
	 * @param locator - By identification of element
	 * @return - true if click successful
	 * @author Swathin Ratheendren  Creation Date: September 2016   Modified Date: 
	 */
	public boolean clickByJS(By locator) 
	{
		waitForElementPresence(locator, 10);
		WebElement webElement = driver.findElement(locator);
		try
		{
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", webElement);
			return true;
		} 
		catch (Exception exception)
		{
			exception.printStackTrace();
			return false;
		}
	}

	/**
	 * @Method: doubleClick
	 * @Description: This is wrapper method used for doubleClick on element
	 * @param locator - By identification of element
	 * @return - true if double click successful
	 * @author Swathin Ratheendren  Creation Date: September 2016   Modified Date: 
	 */
	public boolean doubleClick(By locator)
	{
		waitForElementPresence(locator, 10);
		WebElement webElement = driver.findElement(locator);
		try
		{
			Actions actionBuilder = new Actions(driver);
			actionBuilder.doubleClick(webElement).build().perform();
			return true;
		} 
		catch (Exception exception)
		{
			exception.printStackTrace();
			return false;
		}
	}

	/**
	 * @Method: setText
	 * @Description: This is wrapper method to set text for input element
	 * @param locator - By identification of element
	 * @param fieldValue - field value as string 
	 * @return - true if text entered successfully
	 * @author Swathin Ratheendren  Creation Date: September 2016   Modified Date: 
	 */
	public boolean setText(By locator, String fieldValue) 
	{
		waitForElementPresence(locator, 10);
		WebElement webElement = driver.findElement(locator);
		try
		{
			// replace the text
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", webElement);
			webElement.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			webElement.sendKeys(Keys.DELETE);
			webElement.clear();
			webElement.sendKeys(fieldValue);
			//webElement.sendKeys(Keys.TAB);
			return true;
		} 
		catch (Exception exception)
		{
			exception.printStackTrace();
			return false;
		}
	}

	/**
	 * @Method: getText
	 * @Description: This is wrapper method to get text form input elements
	 * @param locator - By identification of element
	 * @param textBy - get text by value attribute (set textBy as value)/ by visible text (set textBy as text)
	 * @return - text as string
	 * @author Swathin Ratheendren  Creation Date: September 2016   Modified Date: 
	 */
	public String getText(By locator, String textBy) 
	{
		waitForElementPresence(locator, 10);
		WebElement webElement = driver.findElement(locator);
		try
		{
			String strText = "";
			if(textBy.equals("value"))
				strText = webElement.getAttribute("value");
			else if(textBy.equalsIgnoreCase("text"))
				strText = webElement.getText();
			return strText; 
		} 
		catch (Exception exception)
		{
			exception.printStackTrace();
			return null;
		}
	}

	/**
	 * @Method: selectCheckBox
	 * @Description: This is wrapper method select/deselect checkbox
	 * @param locator - By identification of element
	 * @param status - select/deselect 
	 * @author Swathin Ratheendren  Creation Date: September 2016   Modified Date: 
	 */
	public boolean selectCheckBox(By locator, boolean status) 
	{
		waitForElementPresence(locator, 10);
		WebElement webElement = driver.findElement(locator);
		try
		{
			if(webElement.getAttribute("type").equals("checkbox"))   
			{
				if((webElement.isSelected() && !status) || (!webElement.isSelected() && status))
					webElement.click();
				return true;
			}
			else
				return false;
		} 
		catch (Exception exception)
		{
			exception.printStackTrace();
			return false;
		}
	}

	/**
	 * @Method: isCheckBoxSelected
	 * @Description: This is wrapper checkbox is selected or not
	 * @param locator - By identification of element
	 * @author Swathin Ratheendren  Creation Date: September 2016   Modified Date: 
	 */
	public boolean isCheckBoxSelected(By locator, boolean status) 
	{
		waitForElementPresence(locator, 10);
		WebElement webElement = driver.findElement(locator);
		boolean state = false;
		try
		{
			if(webElement.getAttribute("type").equals("checkbox"))   
				state = webElement.isSelected();

			return state;
		} 
		catch (Exception exception)
		{
			exception.printStackTrace();
			return false;
		}
	}

	/**
	 * @Method: selectRadio
	 * @Description: This is wrapper method select/deselect radio button
	 * @param locator - By identification of element
	 * @param status - select/deselect 
	 * @author Swathin Ratheendren  Creation Date: September 2016   Modified Date: 
	 */
	public boolean selectRadioButton(By locator, boolean status)
	{
		waitForElementPresence(locator, 10);
		WebElement webElement = driver.findElement(locator);
		try
		{
			if(webElement.getAttribute("type").equals("radio"))   
			{
				if((webElement.isSelected() && !status) || (!webElement.isSelected() && status))
					webElement.click();
				return true;
			}
			else
				return false;
		} 
		catch (Exception exception)
		{
			exception.printStackTrace();
			return false;
		}
	}

	/**
	 * @Method: mouseHover
	 * @Description: This is wrapper method used for Mouse Hovering to the element
	 * @param locator - By identification of element
	 * @author Swathin Ratheendren  Creation Date: September 2016   Modified Date: 
	 */
	public boolean mouseHover(By locator)
	{
		WebElement webElement = driver.findElement(locator);
		try
		{
			Actions actionBuilder = new Actions(driver);
			actionBuilder.moveToElement(webElement).build().perform();
			return true;
		} 
		catch (Exception exception)
		{
			exception.printStackTrace();
			return false;
		}
	}

	/**
	 * @Method: clickByAction
	 * @Description: This is wrapper method used for Mouse Hovering to the element
	 * @param locator - By identification of element
	 * @author Swathin Ratheendren  Creation Date: September 2016   Modified Date: 
	 */
	public boolean clickByAction(By locator)
	{
		WebElement webElement = driver.findElement(locator);
		try
		{
			Actions actionBuilder = new Actions(driver);
			actionBuilder.moveToElement(webElement).click().build().perform();

			return true;
		} 
		catch (Exception exception)
		{
			exception.printStackTrace();
			return false;
		}
	}

	public boolean scroll(String scrollType,String target)
	{
		try{
			JavascriptExecutor executor = (JavascriptExecutor)driver;

			switch(scrollType){
			case "top":
				executor.executeScript("window.scrollTo(0,0)");
			case "bottom":
				executor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				break;
			case "element":
				waitForElementPresence(GetPageObject.OR_GetElement(target),30);
				//System.out.println("Scorlling to Element"+GetPageObject.OR_GetElement(target));
				executor.executeScript("arguments[0].scrollIntoView();", driver.findElement(GetPageObject.OR_GetElement(target)));
				break;
			case "coordinates":
				String[] coordinates = target.split(",");
				String x = coordinates[0];
				String y = coordinates[1];
				executor.executeScript("window.scrollBy("+x+","+y+")");
				break;
			}
			return true;
		}catch(Exception exception){
			exception.printStackTrace();
			return false;
		}
	}


	/**
	 * @Method: switchToWindowUsingTitle
	 * @Description: This is wrapper method used switch to window using the given title
	 * @param locator - Window title
	 * @author Swathin Ratheendren  Creation Date: September 2016   Modified Date: 
	 */
	public boolean switchToWindowUsingTitle(String windowTitle)
	{
		try
		{
			String mainWindowHandle = driver.getWindowHandle();
			Set<String> openWindows = driver.getWindowHandles();

			if (!openWindows.isEmpty()) 
			{
				for (String windows : openWindows) 
				{
					String window = driver.switchTo().window(windows).getTitle();
					if (windowTitle.equals(window)) 
						return true;
					else 
						driver.switchTo().window(mainWindowHandle);
				}
			}
			return false;
		} 
		catch (Exception exception)
		{
			exception.printStackTrace();
			return false;
		}
	}

	/**
	 * @Method: selectCheckBox
	 * @Description: This is wrapper method select drop down element
	 * @param locator - By identification of element
	 * @param option - drop down element (user may specify text/value/index)
	 * @param selectType - select dorp down element by Text/Value/Index
	 * @author Swathin Ratheendren  Creation Date: September 2016   Modified Date: 
	 */
	public boolean selectDropDownOption(By locator, String option, String... selectType) 
	{
		try
		{
			waitForElementPresence(locator, 10);
			WebElement webElement = driver.findElement(locator);
			Select sltDropDown = new Select(webElement);

			if(selectType.length > 0 && !selectType[0].equals(""))
			{
				if(selectType[0].equalsIgnoreCase("Value"))
					sltDropDown.selectByValue(option);
				else if(selectType[0].equalsIgnoreCase("Text"))
					sltDropDown.selectByVisibleText(option);
				else if(selectType[0].equalsIgnoreCase("Index"))
					sltDropDown.selectByIndex(Integer.parseInt(option));

				return true;
			}
			else
			{
				// Web elements from dropdown list 
				List<WebElement> options = sltDropDown.getOptions();
				boolean blnOptionAvailable = false;
				int iIndex = 0;
				for(WebElement weOptions : options)  
				{  
					if (weOptions.getText().trim().equals(option))
					{
						sltDropDown.selectByIndex(iIndex);
						blnOptionAvailable = true;
					}
					else
						iIndex++;
					if(blnOptionAvailable)
						break;
				}
				if(blnOptionAvailable)
					return true;
				else
					return false;
			}
		} 
		catch (Exception exception)
		{
			exception.printStackTrace();
			return false;
		}
	}

	/**
	 * @Method: getSelectedValueFormDropDown
	 * @Description: This is wrapper method select drop down element
	 * @param locator - By identification of element
	 * @author Swathin Ratheendren  Creation Date: September 2016   Modified Date: 
	 */
	public String getSelectedValueFormDropDown(By locator) 
	{
		try
		{
			waitForElementPresence(locator, 10);
			Select selectDorpDown = new Select(driver.findElement(locator));
			String selectedDorpDownValue = selectDorpDown.getFirstSelectedOption().getText();
			return selectedDorpDownValue;
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			return null;
		}

	}
	/**
	 * @Method: selectRadioButtonForSpecificColumn
	 * @Description: This is wrapper method to select radio button from table with respect to particular column content
	 * @param locator - By identification of element (table with all rows)
	 * @param columnContent - String column content
	 * @columnNumberForRadio - integer column number for radio button
	 * @author Swathin Ratheendren  Creation Date: September 2016   Modified Date: 
	 */
	public boolean selectRadioButtonForSpecificColumn(By locator, String columnContent, int columnNumberForRadio) 
	{
		try
		{
			waitForElementPresence(locator, 10);
			List<WebElement> weResultTable = (List<WebElement>) driver.findElements(locator);
			for(WebElement weRow : weResultTable)
			{
				List<WebElement> weColumns = weRow.findElements(By.xpath(".//td"));
				for(WebElement weColumn : weColumns)
				{
					if(weColumn.getText().trim().equals(columnContent))
					{
						WebElement webElement = weRow.findElement(By.xpath(".//td['" + columnNumberForRadio + "']/input[@type='radio']"));
						JavascriptExecutor executor = (JavascriptExecutor)objStepBase.getDriver();
						executor.executeScript("arguments[0].click();", webElement);
						webElement.click();
						Thread.sleep(1000L);
						webElement.click();
						webElement.click();
					}
				}
			}
			return true;
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			return false;
		}
	}

	/**
	 * @Method: selectCheckBoxForSpecificColumn
	 * @Description: This is wrapper method to select chechbox from table with respect to particular column content
	 * @param locator - By identification of element (table with all rows)
	 * @param columnContent - String column content
	 * @columnNumberForRadio - integer column number for radio button
	 * @author Swathin Ratheendren  Creation Date: September 2016   Modified Date: 
	 */
	public boolean selectCheckBoxForSpecificColumn(By locator, String columnContent, int columnNumberForRadio) 
	{
		try
		{
			waitForElementPresence(locator, 10);
			List<WebElement> weResultTable = (List<WebElement>) driver.findElements(locator);
			for(WebElement weRow : weResultTable)
			{
				List<WebElement> weColumns = weRow.findElements(By.xpath(".//td"));
				for(WebElement weColumn : weColumns)
				{
					if(weColumn.getText().trim().equals(columnContent))
						weRow.findElement(By.xpath(".//td['" + columnNumberForRadio + "']/span/input[@type='checkbox']")).click();
				}
			}
			return true;
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			return false;
		}
	}

	/**
	 * @Method: verifyTableContent
	 * @Description: 
	 * @param locator - By identification of element (table with all rows)
	 * @param columnHeader - String column header
	 * @param columnContent - String column content
	 * @author Swathin Ratheendren  Creation Date: September 2016   Modified Date: 
	 */
	public boolean verifyTableContent(By locator, String columnHeader, String columnContent) 
	{
		Hashtable<String , String> dataColumnHeader = new Hashtable<String, String>();
		int intColumnNumber = 1;
		boolean blnverify = false;
		try
		{
			waitForElementPresence(locator, 10);
			WebElement weResultTable = driver.findElement(locator);

			List<WebElement> weColumnsHeaders = weResultTable.findElements(By.xpath(".//thead/tr/th"));
			for(WebElement weColumnHeader : weColumnsHeaders)
			{
				String strHeader = weColumnHeader.getText().trim();
				if(!strHeader.equals(""))
					dataColumnHeader.put(strHeader, String.valueOf(intColumnNumber));
				intColumnNumber ++;
			}

			List<WebElement> weRows = weResultTable.findElements(By.xpath(".//tbody/tr"));
			for(WebElement weRow : weRows)
			{
				WebElement weExceptedClm = weRow.findElement(By.xpath(".//td[" + dataColumnHeader.get(columnHeader) + "]"));
				if(weExceptedClm.getText().trim().contains(columnContent))
					blnverify = true;
			}

			return blnverify;
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			return false;
		}
	}

	/**
	 * @Method: verifyTableContentAndCheckSelected
	 * @Description: 
	 * @param locator - By identification of element (table with all rows)
	 * @param columnHeader - String column header
	 * @param columnContent - String column content
	 * @author Swathin Ratheendren  Creation Date: September 2016   Modified Date: 
	 */
	public boolean verifyTableContentAndCheckSelected(By locator, String columnHeader, String columnContent, int checkboxColumnNumber) 
	{
		Hashtable<String , String> dataColumnHeader = new Hashtable<String, String>();
		int intColumnNumber = 1;
		boolean blnverify = false;
		try
		{
			waitForElementPresence(locator, 10);
			WebElement weResultTable = driver.findElement(locator);

			List<WebElement> weColumnsHeaders = weResultTable.findElements(By.xpath(".//thead/tr/th"));
			for(WebElement weColumnHeader : weColumnsHeaders)
			{
				String strHeader = weColumnHeader.getText().trim();
				if(!strHeader.equals(""))
					dataColumnHeader.put(strHeader, String.valueOf(intColumnNumber));
				intColumnNumber ++;
			}

			List<WebElement> weRows = weResultTable.findElements(By.xpath(".//tbody/tr"));
			for(WebElement weRow : weRows)
			{
				WebElement weExceptedClm = weRow.findElement(By.xpath(".//td[" + dataColumnHeader.get(columnHeader) + "]"));
				if(weExceptedClm.getText().trim().contains(columnContent))
				{
					WebElement weCheckBox = weRow.findElement(By.xpath(".//td[" + checkboxColumnNumber + "]/span/input[@type='checkbox']"));
					boolean blnIsSelected = weCheckBox.isSelected();
					if(blnIsSelected)
					{
						blnverify = true;
					}
				}
			}
			return blnverify;
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			return false;
		}
	}

	public static By setLocator(String locatorType,String locator){


		switch(locatorType.toLowerCase()){

		case "id":
			by = By.id(locator);
			break;

		case "classname":
			by = By.className(locator);
			break;

		case "name":
			by = By.name(locator);
			break;

		case "linktext":
			by = By.linkText(locator);
			break;

		case "partiallinktext":
			by = By.partialLinkText(locator);
			break;

		case "cssselector":
			by = By.cssSelector(locator);
			break;

		case "xpath":
			by = By.xpath(locator);
			break;

		case "tagname":
			by = By.tagName(locator);
			break;

		}		
		return by;
	}


	/**
	 * @Method: view table details
	 * @Description: 
	 * @param locator - By identification of table element
	 * @author Swathin Ratheendren  Creation Date: September 2016   Modified Date: 
	 */
	public boolean viewTableContent(By locator) 
	{
		try
		{
			WebElement weResultTable = driver.findElement(locator);

			int intHeaderNumber = 1;
			List<WebElement> weColumnsHeaders = weResultTable.findElements(By.xpath(".//thead/tr/th"));
			System.out.println("************************* Table Header details *************");
			for(WebElement weColumnHeader : weColumnsHeaders)
			{
				System.out.println("Table Header * " + intHeaderNumber + " *--->" + weColumnHeader.getText());
				intHeaderNumber++;
			}

			List<WebElement> weRows = weResultTable.findElements(By.xpath(".//tbody/tr"));
			int intRowNum = 1 ;
			System.out.println("************************* Table content details *************");
			for(WebElement weRow : weRows)
			{
				System.out.println("**********Row Number  " + intRowNum + " *************");
				int intClmNum = 1 ;  
				List<WebElement> weClmns = weRow.findElements(By.xpath(".//td"));
				for(WebElement weClmn : weClmns)
				{
					System.out.println("Column Number---->" + intClmNum);
					System.out.println("Column Text-------->" + weClmn.getText());
					intClmNum++;
				}
				intRowNum++;
			}
			return true;
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			return false;
		}
	}


	/******************************************Module not complete - Email body & Template Customization  pending *******************************************/ 	
	public static void sendMail(String filename, String To_EmailAdress) throws Exception{


		System.out.println(To_EmailAdress);


		// Sender's email ID needs to be mentioned
		String from = "swathin@royalcyber.com";

		// Assuming you are sending email from local host
		String host = "smtp.office365.com";

		// Get system properties
		Properties props = System.getProperties();

		// Setup mail server
		props.setProperty("mail.smtp.host", host);
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "25");
		props.put("mail.smtp.starttls.enable","true");
		props.put("mail.smtp.debug", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.port", "25");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "true");

		// Get the default Session object.
		//SMTPAuthenticator auth = new SMTPAuthenticator();
		Session session = Session.getInstance(props);
		try{
			MimeMessage message = new MimeMessage(session);

			message.setSubject("Automation: "+filename+"__Execution Summary");
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(To_EmailAdress));

			// Create the message part 
			BodyPart messageBodyPart = new MimeBodyPart();

			// Create a multi part message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();

			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName("ResultFile_1");
			//message.setText("Please Find attached your Script Execution report file with this email");
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			message.setContent(multipart);
			Transport transport = session.getTransport("smtps");
			transport.connect("smtp.office365.com", 587, "swathin@royalcyber.com", "RCyber0488$");
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();  
		}

		catch (Exception e)
		{
			System.out.println(e);
		}		
	}
}

