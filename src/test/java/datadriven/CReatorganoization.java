package datadriven;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CReatorganoization {
	public static void main(String[] args) throws Throwable
	{
		//to get test data from excel sheet
		FileInputStream fil = new FileInputStream(".\\src\\main\\resources\\base\\TestCase.xlsx");
		Workbook wb = WorkbookFactory.create(fil);
		Sheet sheet = wb.getSheet("Sheet1");
		 int rowcount=sheet.getLastRowNum();
		 int colcount=sheet.getRow(0).getLastCellNum();
		 System.out.println("total number of rows:"+ rowcount);
		 System.out.println("total number of columns:"+ colcount);
		    for(int i=0; i<=rowcount; i++)
		    {
		    	for(int j=0; j<colcount; j++)
		    	{
		    		Row row=sheet.getRow(i);
		    		Cell cell=row.getCell(j);
		    		String data=cell.getStringCellValue();
		    		System.out.print(data+ "  ");
		    	}
		    	System.out.println();
		    	}
		    System.out.println("==========");
		    String campaignName=sheet.getRow(1).getCell(0).getStringCellValue();
		 
		//to get comman data from property file
		FileInputStream fis = new FileInputStream(".\\src\\main\\resources\\base\\commonData.properties");
		Properties pobj=new Properties();
		pobj.load(fis);
		String browser=pobj.getProperty("browser");
		String url=pobj.getProperty("url");
		String username=pobj.getProperty("user");
		String password=pobj.getProperty("password");
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver=new ChromeDriver();
	    driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
	    driver.get(url);
	    driver.findElement(By.name("user_name")).sendKeys(username);
	    driver.findElement(By.name("user_password")).sendKeys(password);
	    driver.findElement(By.id("submitButton")).click();
	    
	    //performing mouse action on more
	   WebElement mouse= driver.findElement(By.xpath("//a[text()='More']"));
	    Actions act=new Actions(driver);
		act.moveToElement(mouse).perform();
		
		driver.findElement(By.xpath("//a[@name='Campaigns']")).click();
		
		//clicking on + symbol
		driver.findElement(By.xpath("//img[@title='Create Campaign...']")).click();
		
		//providing campaign name using excel sheet
		driver.findElement(By.name("campaignname")).sendKeys(campaignName);
		System.out.println(campaignName);
		 driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		driver.findElement(By.cssSelector("body[class='small']")).getText();
//		//clicking  product + symbol 
//		driver.findElement(By.xpath("//img[@src='themes/softed/images/select.gif']")).click();
//		
//		//selecting product using multiple window handling
//		 String mainid=driver.getWindowHandle();
//	     Set<String> id = driver.getWindowHandles();
//	  for(String allid:id)
//	  {
//		  if(!mainid.equals(allid))
//		  {
//			  driver.switchTo().window(allid);
//			  driver.findElement(By.linkText("Vtiger Single User Pack")).click();
//		  }
//          
//	   }
//	  driver.switchTo().window(mainid);
//	  driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
//	  System.out.println("campaign created succesfully with mandatort field and product name");
//	  
//	  //logout of application
	  WebElement logout=driver.findElement(By.cssSelector("[src='themes/softed/images/user.PNG']"));
			Actions act1=new Actions(driver);
			act.moveToElement(logout).perform();
			driver.findElement(By.linkText("Sign Out")).click();
//			driver.quit();
		
	    
	    
	    
	}


}
