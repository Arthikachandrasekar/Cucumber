package com.pscu.seleniumcucumber.framework;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

/**
 * @ScriptName    : GenerateCustomeReport
 * @Description   : This class generate test execution reports using net.masterthought 
 * @Author        : Swathin Ratheendren
 * @Creation Date : September 2016   @Modified Date:                       
 */
public class GenerateCustomReport
{
	// Local variables
	static private String reportPath;
	static private String jsonFilePath;
	static private String xmlFilePath;
	static private String buildNumber;
	static private String buildProjectName;
	static private boolean skippedFails;
	static private boolean undefinedFails;
	static private boolean runWithJenkins;
	static private boolean parallelTesting;
	static private boolean missingFails;
	static private DateTimeFormatter dateFormat;
	static private Utilities objUtilities = new Utilities(); 
	
	
	public static void generateCustomeReport(String Browser, String Platform)
	{
		try
		{
			dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd_HHmmss");
			String currentTimeStamp = dateFormat.print(new DateTime());
			reportPath = "src/test/java/com/pscu/TestResults/custom-report/cucumber-html-reports_" + currentTimeStamp+"_"+Platform+"_"+Browser;
			jsonFilePath = "src/test/java/com/pscu/TestResults/cucumber-report/";
			xmlFilePath = "src/test/java/com/pscu/TestResults/cucumber-report/cucumber.xml";

			List<String> jsonReportFiles = new ArrayList<String>();
			
			jsonReportFiles.add(jsonFilePath+"cucumber_1.json");
			jsonReportFiles.add(jsonFilePath+"cucumber_2.json");
			
			buildNumber = "1";
			buildProjectName = "Royal Cyber Test Automation";
			skippedFails = true;
			undefinedFails = true;
			missingFails = true;
			runWithJenkins = true;
			parallelTesting = true;
			/*ReportBuilder reportBuilder = new ReportBuilder(jsonReportFiles,
					new File(reportPath), pluginURLPath, buildNumber,
					buildProjectName, skippedFails, undefinedFails, flashCharts,
					runWithJenkins, artificatsEnabled, artifactsConfig, highCharts);*/
			Configuration configuration = new Configuration(new File(reportPath), buildProjectName);
	        // optionally only if you need
	        configuration.setStatusFlags(skippedFails, undefinedFails, missingFails);
	        configuration.setParallelTesting(true);
	       //configuration.setJenkinsBasePath(jenkinsBasePath);
	        configuration.setRunWithJenkins(runWithJenkins);
	        configuration.setBuildNumber(buildNumber);
	        
			ReportBuilder reportBuilder = new ReportBuilder(jsonReportFiles, configuration);
			reportBuilder.generateReports();

			//objUtilities.copyFileUsingStream(new File(jsonFilePath), new File(reportPath + "/cucumber.json"));
			//objUtilities.copyFileUsingStream(new File(xmlFilePath), new File(reportPath + "/cucumber.xml"));
			
			Runtime rTime = Runtime.getRuntime();
			String browser = "C:/Program Files (x86)/Google/Chrome/Application/chrome.exe ";
			Process pc = rTime.exec(browser +System.getProperty("user.dir")+"/"+ reportPath);
			pc.waitFor();
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
	}
	
	/*public static void main(String args[]){
		generateCustomeReport(Browser,Platform);
	}*/
}
