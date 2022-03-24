package com.crm.autodesk.Orgtest;




import java.io.FileInputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreatOrganization {
	public static void main(String[]args) throws Throwable{
	//property file common data
		FileInputStream fiss= new FileInputStream("./src/main/resources/base/commonData.properties");
		Properties pobj1 = new Properties();
		pobj1.load(fiss);
		String browser= pobj1.getProperty("browser");
		String url= pobj1.getProperty("url");
		String username= pobj1.getProperty("username");
		String password= pobj1.getProperty("password");
	WebDriver driver=null;
	if(browser.equals("chrome"))
	{
		driver = new ChromeDriver();
		System.out.println("lanched browser"+browser);
	}
	else if(browser.equals("firefox"))
	{
		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver();	
	}
	else if(browser.equals("edge"))
	{
		WebDriverManager.edgedriver().setup();
		driver = new EdgeDriver();	
	}
	 driver.manage().window().maximize();
	 driver.get(url);
	 driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
	 driver.findElement(By.name("user_name")).sendKeys(username);
	 driver.findElement(By.name("user_password")).sendKeys(password);
	 driver.findElement(By.id("submitButton")).click();
	 
	 FileInputStream f =new FileInputStream("./src/main/resources/base/TestCase.xlsx");
	  Workbook b= WorkbookFactory.create(f); 
	  Sheet sh = b.getSheet("Sheet1");
	  String orgName = sh.getRow(3).getCell(0).getStringCellValue();
	 driver.findElement(By.xpath("//body[@class='small']")).click();
	 driver.findElement(By.xpath("//td[@class='tabUnSelected']//a[text()='Organizations']")).click();
	 //click on add
		
		  driver.findElement(By.cssSelector("img[title='Create Organization...']")).click();
		  Random r =new Random(); 
		  int ran = r.nextInt(); 
		  orgName=orgName+ran;
		  driver.findElement(By.name("accountname")).sendKeys(orgName);
		  driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		  
		  //verification
		  String actualOrgName = driver.findElement(By.className("dvHeaderText")).getText();
		  if(actualOrgName.contains(orgName)) {
			  System.out.println(orgName+" is verified.");
		  }
		  else {
			  System.out.println(orgName+" is not verified.");
		  }
		  
		  //logout
		  WebElement logout=driver.findElement(By.cssSelector("[src='themes/softed/images/user.PNG']"));
			Actions act1=new Actions(driver);
			act1.moveToElement(logout).perform();
			driver.findElement(By.linkText("Sign Out")).click();
			driver.close();
	}
	

}
