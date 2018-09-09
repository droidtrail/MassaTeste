package br.ce.wcaquino.estrategia3;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.github.javafaker.Faker;

public class GeradorMassas {

	private static FirefoxDriver driver;
	public static final String CHAVE_CONTA_SB = "CONTA_SB";

	
	public void gerarContaSeuBarriga() throws ClassNotFoundException, SQLException {

		System.setProperty("webdriver.gecko.driver", "e:\\GeckoDriver\\geckodriver.exe");
		driver = new FirefoxDriver();

		driver.get("https://seubarriga.wcaquino.me");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.findElement(By.id("email")).sendKeys("cursoselenium@gmail.com");
		driver.findElement(By.id("senha")).sendKeys("123");
		driver.findElement(By.xpath("//button[.='Entrar']")).click();

		Faker faker = new Faker();
		String registro = faker.gameOfThrones().character() + " " + faker.gameOfThrones().dragon();
		driver.findElement(By.linkText("Contas")).click();
		driver.findElement(By.linkText("Adicionar")).click();
		driver.findElement(By.id("nome")).clear();
		driver.findElement(By.id("nome")).sendKeys(registro);
		driver.findElement(By.tagName("button")).click();

		driver.quit();

		new MassaDAOImpl().inserirMassa(CHAVE_CONTA_SB, registro);

	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		GeradorMassas gerador = new GeradorMassas();

		for (int i = 0; i < 5; i++) {
			gerador.gerarContaSeuBarriga();
		}
//		String massa = new MassaDAOImpl().obterMassa(CHAVE_CONTA_SB);
//		System.out.println(massa);
	}
	

}
