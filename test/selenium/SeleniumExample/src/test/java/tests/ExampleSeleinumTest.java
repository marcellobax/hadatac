package tests;

//This test project was created from Java project based on the Maven. 
//From the Maven is possible to set up versions of JUnit and Selenium

//Here are all the packages and classes used by JUnit and Selenium
//import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ExampleSeleinumTest {
	
	//The variable below allows us to instantiate an object of the WebDriver class.
	//Thus it is possible to setup the Browser used during test.
	private WebDriver driver;
		
	@Before	
	//This method is usually used to instantiate objects or set up the test environment
	public void setUp() throws Exception {
					
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
				
	}

	@After
	//The code within tearDown() method is executed after executing each of the test scenarios.
	//For instance, here the tearDown() method was coded to quit from the browser.
	public void tearDown() throws Exception {		
		
		driver.quit();
	}

	@Test	
	//This test aims to log into Hadatac and an upload file.	
	public void test() throws InterruptedException {
		
		//The get() method is used to access Hadatac's web address
		driver.get("http://localhost:9000/hadatac/login");
		
		//Thread.sleep() method can be used to pause the execution of current thread, 
		//for specified time in milliseconds. 
		//The test keep waiting time configured in milliseconds and after this time the next command is executed.
		Thread.sleep(5000);
				
		String keyUser = "evaldo.oliveira@gmail.com";		
     	//Entering user to log. 
		//WebElement is a class that it allows instantiate objects to execute some action using a HTML element.
     	WebElement login = driver.findElement(By.name("email"));     
     	login.sendKeys(keyUser);
     	
     	String keyPassword = "gcas1302";
     	//String keyPassword = "\t"+"pwd";
     	//Entering password to log; \t separate and tabs the field
     	WebElement password = driver.findElement(By.name("password"));     
     	password.sendKeys(keyPassword);
    	
     	//The parameter Keys.RETURN is used. 
     	driver.findElement(By.name("password")).sendKeys(Keys.RETURN);
     	Thread.sleep(5000);
     	
     	//Here we used a link in order to access of the "Ingest File" functionality
     	driver.get("http://localhost:9000/hadatac/annotator/autoccsv?dir=%2F&dest=.");
		Thread.sleep(5000);
		
		//It was necessary to identify the HTML tag "input type='file'" in order to upload file. 
		//In order to access the path or directory of file we use xpath() method.
		WebElement fileComputer = driver.findElement(By.xpath(".//input[@type='file']"));   
		fileComputer.sendKeys("C:\\Users\\evald\\workspace\\projetohadatac\\SeleniumExample\\DA-2018-05-LAB.csv");
		Thread.sleep(20000);
		
		//Here the test returns to Hadatac main page
		driver.get("http://localhost:9000/hadatac/");
		Thread.sleep(5000);	
		
		//At the end, the test executes logout by get() method
		driver.get("http://localhost:9000/hadatac/logout");
		Thread.sleep(5000);		
	 	
		//The importance to know this is: we can make sure we are at the right page, 
		//using  page title or some label tag		
     	//assertTrue("Page title differs from expected", driver.findElement(By.className("DisplayH900-bfb998fa--pageTitle-33dc39a3--pageTitleInput-b7d6ce52")).getText().contentEquals("11. TESTING"));
		//Thread.sleep(5000);
	}

}
