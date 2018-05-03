package com.pscu.seleniumcucumber.framework;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @ScriptName    : Utilities
 * @Description   : This class contains   
 * @Author        : Swathin Ratheendren	
 * @Creation Date : September 2016   @Modified Date:                       
 */
public class Utilities 
{
	private StepBase objStepBase = new StepBase();

	/**
	 * Method: takeScreenshot
	 * Description: 
	 * @param timeInMilliseconds
	 * @author Swathin Ratheendren
	 * @Creation Date:  September 2016 Modified Date:
	 */
	public static String takeScreenshot(WebDriver driver) 
	{
		try 
		{
			String SSPath="";
			if(System.getProperty("test.DisableScreenShotCapture").equalsIgnoreCase("false"))
			{
				Thread.sleep(2000);
				File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				SSPath= System.getProperty("user.dir") + "/output/Screenshots/"+getRequiredDate(0, "yyyy_MM_dd_hh", null)+"/screenshot_"+ getRequiredDate(0, "yyyy_MM_dd_hh_mm_ss", null) +".png";
				FileUtils.copyFile(scrFile, new File(SSPath));
			}
			return SSPath;
		}
		catch (Exception e) 
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * Method: waitFor
	 * Description: Waits for the specified amount of [timeInMilliseconds].
	 * @param timeInMilliseconds
	 * @author Swathin Ratheendren
	 * @Creation Date:  September 2016 Modified Date:
	 */
	public void waitFor(final Long timeInMilliseconds)
	{
		try 
		{
			Thread.sleep(timeInMilliseconds);
		}
		catch (Exception e) 
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * Method: getRequiredDate
	 * Description: This method will give require date
	 * @param incrementDateByDays Number by which user want increase date 
	 * @param sExpectedDateFormat - User expected date format
	 * 		eg. 9 april 2014 --- dd/MM/yyyy -> 09/04/2015, dd-MM-yyyy -> 09-04-2015
	 * @param timeZoneId - Time Zone
	 * @author Swathin Ratheendren Creation Date:  September 2016  Modified Date:
	 */
	public static String getRequiredDate (int incrementDays, String expectedDateFormat, String timeZoneId)
	{
		try 
		{
			DateFormat dateFormat;
			Calendar calendar = Calendar.getInstance();
			dateFormat = new SimpleDateFormat(expectedDateFormat);
			if(timeZoneId != null && ! timeZoneId.equals(""))
				dateFormat.setTimeZone(TimeZone.getTimeZone(timeZoneId));
			calendar.add(Calendar.DAY_OF_MONTH, incrementDays);
			Date tomorrow = calendar.getTime();
			String formattedDate = dateFormat.format(tomorrow);
			return formattedDate;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Method: copyFileUsingStream
	 * Description: 
	 * @param timeInMilliseconds
	 * @author Swathin Ratheendren Creation Date:  September 2016 Modified Date:
	 */
	public void copyFileUsingStream(File source, File dest) throws IOException
	{
		InputStream is = null;
		OutputStream os = null;
		try 
		{
			is = new FileInputStream(source);
			os = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();

		} 
		finally 
		{
			is.close();
			os.close();
		}
	}

	/**
	 * Method: waitForPageLoad
	 * Description: timeInSeconds for the specified amount of [timeInSeconds].
	 * @author Swathin Ratheendren Creation Date:  September 2016 Modified Date:
	 */
	public void waitForPageLoad()
	{
		try 
		{
			WebDriverWait wait = new WebDriverWait(objStepBase.getDriver(), 180);
			final JavascriptExecutor javascript = (JavascriptExecutor) 
					(objStepBase.getDriver() instanceof JavascriptExecutor ? objStepBase.getDriver() : null);
			/*
	      wait.until(new ExpectedCondition<Boolean>()
	          {
	        @Override
	        public Boolean apply(WebDriver d)
	        {
	          boolean outcome = Boolean.parseBoolean(javascript
	              .executeScript("return jQuery.active == 0")
	              .toString());
	          return outcome;
	        }
	          });

			 */   
			wait.until(new ExpectedCondition<Boolean>() 
			{
				//@Override
				public Boolean apply(WebDriver d)
				{
					return javascript.executeScript("return document.readyState").equals("complete");
				}
			});

			objStepBase.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			// objStepBase.getDriver().manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
		}
		catch (Exception e) 
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * Method: waitForAjaxCallComplete
	 * Description: timeInSeconds for the specified amount of [timeInSeconds].
	 * @author Swathin Ratheendren Creation Date:  September 2016 Modified Date:
	 */
	public void waitForAjaxCallComplete()
	{
		try 
		{
			WebDriverWait wait = new WebDriverWait(objStepBase.getDriver(), 180);
			final JavascriptExecutor javascript = (JavascriptExecutor) 
					(objStepBase.getDriver() instanceof JavascriptExecutor ? objStepBase.getDriver() : null);

			wait.until(new ExpectedCondition<Boolean>()
			{
				//@Override
				public Boolean apply(WebDriver d)
				{
					boolean outcome = Boolean.parseBoolean(javascript
							.executeScript("return jQuery.active == 0")
							.toString());
					return outcome;
				}
			});
		}
		catch (Exception e) 
		{
			throw new RuntimeException(e);
		}
	}


	public static void resizeImage(File inputImagePath, File outputImagePath, int scaledWidth, int scaledHeight)throws IOException {
		// reads input image
		BufferedImage inputImage = ImageIO.read(inputImagePath);

		// creates output image
		BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());

		// scales the input image to the output image
		Graphics2D g2d = outputImage.createGraphics();
		g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
		g2d.dispose();

		System.out.println("Resize completed : "+ outputImagePath);

		/* 
       // extracts extension of output file
       String formatName = outputImagePath.substring(outputImagePath.lastIndexOf(".") + 1);
		 */

		// writes to output file
		ImageIO.write(outputImage, "png", outputImagePath);
	}

	/**
	 * Method: ImageComparison
	 * Description: timeInSeconds for the specified amount of [timeInSeconds].
	 * @author Swathin Ratheendren Creation Date:  September 2016 Modified Date:
	 */
	public static float compareImage(File ActualImage, File BaseLinedImage) {
		float percentage = 0;
		try {
			// take buffer data from both image files //
			BufferedImage biA = ImageIO.read(ActualImage);
			DataBuffer dbA = biA.getData().getDataBuffer();
			int sizeA = dbA.getSize();
			BufferedImage biB = ImageIO.read(BaseLinedImage);
			DataBuffer dbB = biB.getData().getDataBuffer();

			int sizeB = dbB.getSize();
			int count = 0;
			// compare data-buffer objects //
			if (sizeA == sizeB) {
				for (int i = 0; i < sizeA; i++) {
					if (dbA.getElem(i) == dbB.getElem(i)) {
						count = count + 1;
					}
				}
				percentage = (count * 100) / sizeA;
				percentage = 100 - percentage;
			} else {
				System.out.println("Both the images are not of same size");
				throw new Exception("Both the images are not of same size");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return percentage;
	}

	public static BufferedImage getDifferenceImage(File ActualImage, File BaseLinedImage) throws Exception{
		BufferedImage img1 = ImageIO.read(ActualImage);
		//DataBuffer dbA = img1.getData().getDataBuffer();
		BufferedImage img2 = ImageIO.read(BaseLinedImage);
		//DataBuffer dbB = img2.getData().getDataBuffer();

		// convert images to pixel arrays...
		final int w = img1.getWidth(),
				h = img1.getHeight(), 
				highlight = Color.RED.getRGB();
		final int[] p1 = img1.getRGB(0, 0, w, h, null, 0, w);
		final int[] p2 = img2.getRGB(0, 0, w, h, null, 0, w);
		//compare pixel by pixel of img1 to img2. Highlight img1's pixels which are different.
		for (int i = 0; i < p1.length; i++) {
			if (p1[i] != p2[i]) {
				p1[i] = highlight;
			}
		}
		// save img1's pixels to a new BufferedImage, and return it...
		// (May require TYPE_INT_ARGB)
		final BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		out.setRGB(0, 0, w, h, p1, 0, w);
		return out;
	}

	/*public static void main(String[] args) throws Exception {
		File f1 = new File("C:\\Users\\user\\Desktop\\image1.png");
		File f2 = new File("C:\\Users\\user\\Desktop\\image2.png");
		System.out.println(compareImage(f1,f2));

		BufferedImage bufferedImage = getDifferenceImage(f1,f2);

		File outputfile = new File("C:\\Users\\user\\Desktop\\output_differenceimage.png");
		ImageIO.write(bufferedImage, "jpg", outputfile);

	}*/
}
