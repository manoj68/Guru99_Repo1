package com.guru99.demo.testcases;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.guru99.demo.utility.ExcelReadingMethods;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AddCustomerTest 
{
	WebDriver driver;
	String appURL = "http://demo.guru99.com/v4/";
	String inputFileName = "./"+"src\\main\\java\\com\\guru99\\demo\\testdata\\TestData_demoGuru99_v2.xlsx";
	String sheetName = "Sheet1";
	
	@BeforeMethod
	public void setup() throws Exception
	{
		WebDriverManager.chromedriver().setup();	// no need so system.set propert and chromedriver donwload
		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(appURL);
		Thread.sleep(3000);
	}
	
	@AfterMethod
	public void tearDown()
	{
		driver.close();
	}

	@DataProvider (name="userdataSheet2")
	public String[][] getDataSheet2() throws IOException
	{
		//int rowcount = ExcelReadingMethods.getRowCount(inputFileName, sheetName2);
		int rowTotal = ExcelReadingMethods.getRowCount(inputFileName, sheetName);
		
		System.out.println("rowcount is " +rowTotal);
		
		int cellcount = ExcelReadingMethods.getCellData(inputFileName, sheetName, 0);
		System.out.println("cellcount is " +cellcount);
		
//		to store data we have created 2 dimensional string type array
		String loginData2[][] = new String[rowTotal][cellcount];
		//for loop to iterate rows
		for(int row=1; row <= rowTotal; row++)
		{
			for(int cell=0; cell<cellcount; cell++)
			{
				loginData2[row-1][cell] = ExcelReadingMethods.getCellData(inputFileName, sheetName, row, cell);
			}
		}
		return loginData2;
	}
	
	@Test (priority=1, dataProvider="userdataSheet2")
	public void createNewCustomer(String username, String password) throws InterruptedException
	{
		System.out.println("login deails: username = "+username+ "and password "+password );
		
		driver.findElement(By.name("uid")).sendKeys(username);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("btnLogin")).click();
		Thread.sleep(4000);
		
		driver.findElement(By.linkText("New Customer")).click();
		Thread.sleep(3000);
		
		driver.findElement(By.name("name")).sendKeys("Kimmy");
		driver.findElement(By.xpath("//input[@value='m']")).click();
		driver.findElement(By.name("dob")).sendKeys("24/10/1991");
		driver.findElement(By.name("addr")).sendKeys("JM road down town");
		driver.findElement(By.name("city")).sendKeys("Pune");
		driver.findElement(By.name("state")).sendKeys("Maharashtra");
		driver.findElement(By.name("pinno")).sendKeys("123456");
		driver.findElement(By.name("telephoneno")).sendKeys("9988998899");
		driver.findElement(By.name("emailid")).sendKeys("abce@gmil.com");
		driver.findElement(By.name("password")).sendKeys("abc$1234");
		driver.findElement(By.name("sub")).click();
		Thread.sleep(5000);
		
		String actualTitle = driver.getTitle();
		System.out.println("Actual page title is " +actualTitle);
		String expectedTitle = "Guru99 Bank Customer Registration Page";
		
		Assert.assertEquals(actualTitle, expectedTitle);
		
		String customerID = driver.findElement(By.xpath("//table[@id='customer']//tr[4]//td[2]")).getText();
		System.out.println("customerID is : " +customerID);
		
	}
	
	
}
