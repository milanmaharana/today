package datadriven;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class CreateOrganization {
	private static CharSequence[] OrgName;
	private static int randomName;
	private static int org;
	private static Object act;
 
	public static void main(String[] args) throws Throwable {
		WebDriver driver = null;
		
		FileInputStream fis = new FileInputStream("./src/main/resources/base/TestCase.xlsx");
		Workbook wb = WorkbookFactory.create(fis);
		
		Sheet sheet = wb.getSheet("sheet1");
		int rowcount = sheet.getLastRowNum();
		int columncount = sheet.getRow(0).getLastCellNum();
		System.out.println("total number of rows" + rowcount);
		System.out.println("total number of columns" + columncount);

		// Fetch data from property file
		FileInputStream fis1 = new FileInputStream(".\\src\\main\\resources\\Common\\base.properties");
		Properties pro_obj = new Properties();
		pro_obj.load(fis1);
		System.out.println(pro_obj);
		String browser = pro_obj.getProperty("browser");
		String url = pro_obj.getProperty("url");
		String id = pro_obj.getProperty("id");
		String password = pro_obj.getProperty("password");
		
		// run time polymorphism
		
		if (browser.equalsIgnoreCase("Chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			System.out.println("launched browser is " + browser);
		} else if (browser.equals("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			System.out.println("lanunched browser is " + browser);
		} else {
			System.out.println("specify a valid browser");
		}
		// for handling action
		// Actions act = new Actions(driver);
		
		driver.get(url);
	//	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		// Random random = new Random();
		// int ranNum = random.nextInt(1000);
		// String orgnizationName = orgName+ ranNum;

		// step-1: launch v-tiger and login
		driver.manage().window().maximize();
		driver.get(url);
		driver.findElement(By.name("user_name")).sendKeys(id);
		driver.findElement(By.name("user_password")).sendKeys(password);
		driver.findElement(By.id("submitButton")).click();

		/* step-2: navigate to organization */
		driver.findElement(By.xpath("//a[text()='Organizations']")).click();
		
		/* Step-3: navigate to create organization */
		//driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();

	}
}
