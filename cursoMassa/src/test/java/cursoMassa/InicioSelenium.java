package cursoMassa;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class InicioSelenium {
	
	public static void main(String[] args) {
		
		System.setProperty("webdriver.gecko.driver", "e:\\GeckoDriver\\geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		
		driver.get("https://seubarriga.wcaquino.me");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.findElement(By.id("email")).sendKeys("cursoselenium@gmail.com");
		driver.findElement(By.id("senha")).sendKeys("123");
		driver.findElement(By.xpath("//button[.='Entrar']")).click();
		
		driver.quit();
		
	}
}
