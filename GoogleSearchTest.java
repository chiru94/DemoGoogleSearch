package demoGoogleSearch;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GoogleSearchTest {
	
/*
Write selenium automation which has the following behavior.
Given a keyword, an URL, two numbers (N, M). It does a Google search using the keyword and tests.
1. if the URL has at least M listing in the first N pages.
2. If there is a Google Play store entry with a rating > 3
*/
	WebDriver driver;
	String searchKeyword = "netflix";
	String searchURL = "https://twitter.com";
	String searchURL2 = "play.google.com";
	
	String searchResultLink = "";
	int n=3; //no. of pages
	int m=2; //no. of links listed
	int i=0;
	float f=0.0f;
	boolean t =false;
	
	WebDriverWait wait;
    List<WebElement> l;
	WebElement link;
	
@BeforeMethod
public void OpenGooglePage() {
		
	  driver.get("https://google.com");
	  driver.manage().window().maximize();
	  driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
	  
	  wait = new WebDriverWait(driver, 45);
	  
	  driver.findElement(By.name("q")).clear();
	  driver.findElement(By.name("q")).sendKeys(searchKeyword);
	  driver.findElement(By.name("btnK")).click();

	  wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id=\"xjs\"]/table")));
	  
}

  @Test(priority=1)
  public void GoogleSearchMethod1() {
	  
	 System.out.println("In GoogleSearchMethod1()"); 
	 Iterator<WebElement> iL;
	  
	 for(int j=0;j<n;j++) 
	 {	 
		 l = driver.findElements(By.xpath("//*[@id=\"rso\"]/div/div/div/div[1]//a"));
		 
		 iL = l.iterator();
		 while(iL.hasNext())
		  {
			  link = iL.next();
			  searchResultLink = link.getAttribute("href");
			  System.out.println("searchResultLink="+searchResultLink);
			  if(searchResultLink.contains(searchURL))
			  {
				  i++;
			  }
		  }
		driver.findElement(By.xpath("//*[@id=\"xjs\"]/table/tbody/tr/td["+(j+3)+"]/a")).click();
	 } 
	 
	 System.out.println("No. of twitter link = "+i);

	 l.clear(); 
	
	 Assert.assertTrue(i>2 ,"No. of links listed are not as per the expectations!");
  }
  
  @Test(priority=2)
  public void GoogleSearchMethod2() {
	  
	  System.out.println("In GoogleSearchMethod2()");
	  Iterator<WebElement> iL;
	  
	  String firstPartOfXpath = "//a[contains(@href,\""+searchURL2+ "\")][contains(@href,\""+searchKeyword+"\")][not(contains(@href,\"webcache\"))]";
      
	  t=false;
	  OutForLoop:
	  for(int j=0;j<n && !t;j++) 
	  {	 
		 l = driver.findElements(By.xpath(firstPartOfXpath));
		 //wait.until(ExpectedConditions.visibilityOfAllElements(l));
		 System.out.println("size of l="+l.size());
		 iL = l.iterator();
		 while(iL.hasNext())
		  {
			 
			  link = iL.next();
			  searchResultLink = link.getAttribute("href");
			  System.out.println("searchResultLink="+searchResultLink);
			  if(link!=null)
			  {
				  System.out.println("xpath = "+firstPartOfXpath+"/parent::div/parent::div/div[2]/div/span[1]");
				  try {
						  String ratingValue = link.findElement(By.xpath(firstPartOfXpath+"/parent::div/parent::div/div[2]/div/span[1]")).getText();
						  System.out.println("ratingValue="+ratingValue);
						  String[] rValue = ratingValue.split(" ");
						  f = Float.parseFloat(rValue[1]);
						  t=true;
						  break OutForLoop;
				  }
				  catch(NoSuchElementException e)
				  {
					  driver.findElement(By.xpath("//*[@id=\"xjs\"]/table/tbody/tr/td["+(j+3)+"]/a")).click();
				  }
			  }
		  }
	  }
	
	 System.out.println("Rating Value = "+f);
	
	 l.clear(); 
		 
	 Assert.assertTrue(f>3 ,"Rating should be greater than 3! but it is -"+f);
  }
  
  @BeforeClass
  public void beforeClass() {
 
	  System.setProperty("webdriver.chrome.driver", "D:\\Projects\\Automation\\Eclipse\\Demos\\com.demo.Drona\\drivers\\chromedriver\\chromedriver.exe");
	  
	  driver = new ChromeDriver();
	  
  }

  @AfterClass
  public void afterClass() {
	  driver.quit();
  }

}
