
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.Test;

public class TestSelenium {
	public static boolean checkMoving(WebDriver driver) {
		
		WebDriverWait w = new WebDriverWait(driver, 10);
		WebElement element = w.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/div[5]/div[1]/div[2]/div[3]/div/section/div[2]/div")));
        String top = element.getCssValue("top");
        try{
        	Thread.sleep(3000);
        	}
        	catch(InterruptedException ie){
        	}
        String CurrentTop = element.getCssValue("top");
        System.out.println(top+" "+CurrentTop);
        return (!top.equals(CurrentTop));
	}
	
	@Test	
	public void StartTest() {
		System.setProperty("webdriver.chrome.driver", "C:/Projects/Selenium/chromedriver.exe");
		ChromeOptions ops = new ChromeOptions();
        ops.addArguments("--disable-notifications");
		WebDriver driver = new ChromeDriver();
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		//google search
		driver.get("https://www.google.com/");
		WebElement p=driver.findElement(By.name("q"));
		p.sendKeys("hidabroot");
		WebDriverWait w = new WebDriverWait(driver, 10);
	    w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//ul")));
	    p.submit();
	    WebElement element =w.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='search']//a")));
	    driver.get(element.getAttribute("href"));
	    if(!driver.getCurrentUrl().contains("hidabroot.org")) return;
	    	   
	    //find any article
	    driver.get(driver.findElements(By.xpath("//article//a")).get(0).getAttribute("href"));
	    //open comment
	    element = w.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"comments_wrp\"]/div[1]/a")));
	    if(element == null) return;
	    
	    //send empty comment
	    executor.executeScript("arguments[0].scrollIntoView(true); window.scrollBy(0, -window.innerHeight / 4);", element);
	    executor.executeScript("arguments[0].click()", element);
	    WebElement btn = w.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div/div[4]/div/div[1]/div[4]/div/div[1]/form/div[1]/button")));
	    btn.click();
	    int errors = driver.findElements(By.className("inp_bubble")).size();
	    System.out.println(errors);
	    if(errors <=0) return;
	    
	    //check moving
	    driver.get("https://www.hidabroot.org/");
        if(checkMoving(driver) == false) return;
        //stop moving
        element = w.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/div[5]/div[1]/div[2]/div[3]/div/section/div[2]")));
        Actions builder = new Actions(driver);
        Action mouseOver = builder.moveToElement(element).build();
        mouseOver.perform();
        if(checkMoving(driver) == true) return;
        
        //set windows size
	    driver.manage().window().setSize(new org.openqa.selenium.Dimension(1920, 1080));
        //weather:
        //I don't have weather here, so I did it with the day's times
	    executor.executeScript("window.scrollTo(0, 0);");
        driver.get(w.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/div[1]/header/div[3]/div/div[3]/a[11]"))).getAttribute("href"));
        try{
        	Thread.sleep(1500);
        	}
        	catch(InterruptedException ie){
        	}
        element = w.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/div[5]/div[1]/div[1]/div[1]/div[3]/div[2]/div[1]/div[2]/div[1]/div/input")));
	    WebElement timeText = driver.findElement(By.xpath("/html/body/div[2]/div[5]/div[1]/div[1]/div[1]/div[3]/div[2]/div[1]/div[2]/div[2]/div[1]/ul/li[1]/span"));
        System.out.println(timeText.getAttribute("innerHTML"));
        executor.executeScript("arguments[0].scrollIntoView(true); window.scrollBy(0, -window.innerHeight / 4);", element);
        element.sendKeys("כרמיאל");
        executor.executeScript("arguments[0].dispatchEvent(new Event('input', {'bubbles': true,'cancelable': true}));",element);
        WebElement ul = w.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/div[5]/div[1]/div[1]/div[1]/div[3]/div[2]/div[1]/div[2]/div[1]/div/ul")));
        WebElement li= ul.findElements(By.tagName("li")).get(0);
        li.click();
        try{
        	Thread.sleep(2000);
        	}
        	catch(InterruptedException ie){
        	}
        timeText = driver.findElement(By.xpath("/html/body/div[2]/div[5]/div[1]/div[1]/div[1]/div[3]/div[2]/div[1]/div[2]/div[2]/div[1]/ul/li[1]/span"));
        System.out.println(timeText.getAttribute("innerHTML"));
        
        driver.quit();   	   
	}

}
