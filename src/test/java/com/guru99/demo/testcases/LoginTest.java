package com.guru99.demo.testcases;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.guru99.demo.utility.ExcelReadingMethods;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginTest 
{
	WebDriver driver;
	ChromeDriver options;
	String appURL = "http://demo.guru99.com/v4/";
	String inputFileName = "./"+"src\\main\\java\\com\\guru99\\demo\\testdata\\TestData_demoGuru99.xlsx";
	String sheetName = "Sheet1";
	
	@BeforeClass
	public void setup()
	{
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(appURL);
	}
	
	@AfterClass
	public void tearDown()
	{
		if(driver != null)
		{
			driver.close();
		}
	}
	
//	@Test (priority=1, dataProvider="userdata")
	public void checkLogin(String username, String password) throws InterruptedException
	{
		System.out.println("login deails: username = "+username+ "and password "+password );
		
		driver.findElement(By.name("uid")).sendKeys(username);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("btnLogin")).click();
		Thread.sleep(6000);
		
		//to handle alerts we need to create another method to check alert is present or not
		//Also note that there are 2 alerts which occures at different stage hence we used if else here which checking the condition
		
		System.out.println("Alert flag is: "+alertExist());
		if(alertExist() == true)
		{
			System.out.println("Alert is present");
			driver.switchTo().alert().accept();
			Thread.sleep(4000);
			
			System.out.println("Alert is closed");
			Assert.assertTrue(true);
		}
		else
		{
			System.out.println("In else");
			Thread.sleep(3000);
			//To logout will click on logout button
			driver.findElement(By.xpath("/html/body/div[3]/div/ul/li[15]/a")).click();
			Thread.sleep(3000);
			driver.switchTo().alert().accept();
			Thread.sleep(3000);
			System.out.println("Alert is closed from else side");
			Assert.assertTrue(true);
		}
	}
	
	@Test (priority=2, dataProvider="userdata")
	public void checkAlertText(String username, String password) throws InterruptedException
	{
		System.out.println("login deails: username = "+username+ "and password "+password );
		
		driver.findElement(By.name("uid")).sendKeys(username);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("btnLogin")).click();
		Thread.sleep(6000);
		
		//to handle alerts we need to create another method to check alert is present or not
		
		System.out.println("Alert flag is: "+alertExist());
		if(alertExist() == true)
		{	System.out.println("Alert is present");
			String actualLoginAlerttext = driver.switchTo().alert().getText();  	
			String expectedLoginAlerttext = "User or Password is not valid";
			driver.switchTo().alert().accept();
			Thread.sleep(4000);
			
			System.out.println("Alert is closed");
			Assert.assertEquals(actualLoginAlerttext, expectedLoginAlerttext);}
		else
		{
			System.out.println("In else");
			Thread.sleep(3000);
			//To logout will click on logout button
			driver.findElement(By.xpath("/html/body/div[3]/div/ul/li[15]/a")).click();
			Thread.sleep(3000);
			String actualLogoutAlerttext = driver.switchTo().alert().getText();
			String expectedLogoutAlerttext = "You Have Succesfully Logged Out!!";
			driver.switchTo().alert().accept();
			Thread.sleep(3000);
			System.out.println("Alert is closed from else side");
			Assert.assertEquals(actualLogoutAlerttext, expectedLogoutAlerttext);
		}

	}
	
	public boolean alertExist()	//method to check alert
	{
		boolean alertFlag = false;
		try{
			driver.switchTo().alert();
			alertFlag=true;
		}
		catch(NoAlertPresentException e)
		{
			e.printStackTrace();
		}
		return alertFlag;
	}
	
	@DataProvider (name="userdata")
	public String[][] getData() throws IOException
	{
		int rowcount = ExcelReadingMethods.getRowCount(inputFileName, sheetName);
		System.out.println("rowcount is " +rowcount);
		
		int cellcount = ExcelReadingMethods.getCellData(inputFileName, sheetName, 0);
		System.out.println("cellcount is " +cellcount);
		
//		to store data we have created 2 dimensional string type array
		String loginData[][] = new String[rowcount][cellcount];
		//for loop to iterate rows
		for(int row=1; row <= rowcount; row++)
		{
			for(int cell=0; cell<cellcount; cell++)
			{
				loginData[row-1][cell] = ExcelReadingMethods.getCellData(inputFileName, sheetName, row, cell);
			}
		}
		return loginData;
	}

}

