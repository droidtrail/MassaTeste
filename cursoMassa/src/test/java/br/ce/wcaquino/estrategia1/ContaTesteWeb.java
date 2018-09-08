package br.ce.wcaquino.estrategia1;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ContaTesteWeb {

	private static FirefoxDriver driver;
	

	@BeforeClass
	public static void login() {

		System.setProperty("webdriver.gecko.driver", "e:\\GeckoDriver\\geckodriver.exe");
		driver = new FirefoxDriver();

		driver.get("https://seubarriga.wcaquino.me");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.findElement(By.id("email")).sendKeys("cursoselenium@gmail.com");
		driver.findElement(By.id("senha")).sendKeys("123");
		driver.findElement(By.xpath("//button[.='Entrar']")).click();
	}

	@Test
	public void test_1_inserir() {

		driver.findElement(By.linkText("Contas")).click();
		driver.findElement(By.linkText("Adicionar")).click();
		WebElement campoNome = driver.findElement(By.id("nome"));
		campoNome.clear();
		campoNome.sendKeys("Conta Estratégia #1");
		driver.findElement(By.tagName("button")).click();

		String msgSucesso = driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
		Assert.assertEquals("Conta adicionada com sucesso!", msgSucesso);	
	}
	
	@Test
	public void test_2_consultar() {

		driver.findElement(By.linkText("Contas")).click();
		driver.findElement(By.linkText("Listar")).click();
		driver.findElement(By.xpath("//td[.='Conta Estratégia #1']/..//a[1]")).click();
		
		String msgAlterar = driver.findElement(By.id("nome")).getAttribute("value");
		Assert.assertEquals("Conta Estratégia #1", msgAlterar);
	}
	
	@Test
	public void test_3_alterar() {

		driver.findElement(By.linkText("Contas")).click();
		driver.findElement(By.linkText("Listar")).click();
		driver.findElement(By.xpath("//td[.='Conta Estratégia #1']/..//a[1]")).click();
		WebElement campoNome = driver.findElement(By.id("nome"));
		campoNome.clear();
		campoNome.sendKeys("Conta Estratégia #1 Alterada");
		driver.findElement(By.tagName("button")).click();
		
		String msgSucesso = driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
		Assert.assertEquals("Conta alterada com sucesso!", msgSucesso);
	}
	
	@Test
	public void test_4_deletar() {

		driver.findElement(By.linkText("Contas")).click();
		driver.findElement(By.linkText("Listar")).click();
		driver.findElement(By.xpath("//td[.='Conta Estratégia #1 Alterada']/..//a[2]")).click();
		
		String msgAlterar = driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
		Assert.assertEquals("Conta removida com sucesso!", msgAlterar);
	}

	@AfterClass
	public static void fechar() {
		driver.quit();
	}

}
