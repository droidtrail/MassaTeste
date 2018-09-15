package br.ce.wcaquino.estrategia3;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.github.javafaker.Faker;


public class ContaTesteWeb3 {

	private static FirefoxDriver driver;
	private Faker faker = new Faker();
	

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
	public void inserir() throws ClassNotFoundException, SQLException {
		
		String conta = faker.gameOfThrones().character() + " " + faker.gameOfThrones().dragon();
		
		driver.findElement(By.linkText("Contas")).click();
		driver.findElement(By.linkText("Adicionar")).click();
		driver.findElement(By.id("nome")).clear();
		driver.findElement(By.id("nome")).sendKeys(conta);
		driver.findElement(By.tagName("button")).click();

		String msgSucesso = driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
		Assert.assertEquals("Conta adicionada com sucesso!", msgSucesso);
		
		new MassaDAOImpl().inserirMassa(GeradorMassas.CHAVE_CONTA_SB, conta);
	}
	
	@Test
	public void consultar() throws ClassNotFoundException, SQLException {
		//String conta = inserirConta();
		
		String conta = new MassaDAOImpl().obterMassa(GeradorMassas.CHAVE_CONTA_SB);
		
		driver.findElement(By.linkText("Contas")).click();
		driver.findElement(By.linkText("Listar")).click();
		driver.findElement(By.xpath("//td[.='"+conta+"']/..//a[1]")).click();
		
		String msgAlterar = driver.findElement(By.id("nome")).getAttribute("value");
		Assert.assertEquals(conta, msgAlterar);
		
		new MassaDAOImpl().inserirMassa(GeradorMassas.CHAVE_CONTA_SB, conta);
	}
	
	@Test
	public void alterar() throws ClassNotFoundException, SQLException {

		//String conta = inserirConta();
		String conta = new MassaDAOImpl().obterMassa(GeradorMassas.CHAVE_CONTA_SB);
		
		driver.findElement(By.linkText("Contas")).click();
		driver.findElement(By.linkText("Listar")).click();
		driver.findElement(By.xpath("//td[.='"+conta+"']/..//a[1]")).click();
		driver.findElement(By.id("nome")).sendKeys(" Alterado");
		driver.findElement(By.tagName("button")).click();
		
		String msgSucesso = driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
		Assert.assertEquals("Conta alterada com sucesso!", msgSucesso);
	}
	
	@Test
	public void deletar() throws ClassNotFoundException, SQLException {
		//String conta = inserirConta();
		String conta = new MassaDAOImpl().obterMassa(GeradorMassas.CHAVE_CONTA_SB);

		driver.findElement(By.linkText("Contas")).click();
		driver.findElement(By.linkText("Listar")).click();
		driver.findElement(By.xpath("//td[.='"+conta+"']/..//a[2]")).click();
		
		String msgAlterar = driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
		Assert.assertEquals("Conta removida com sucesso!", msgAlterar);
	}
	
//	private String inserirConta() {
//		
//		String registro = faker.harryPotter().character();
//		driver.findElement(By.linkText("Contas")).click();
//		driver.findElement(By.linkText("Adicionar")).click();
//		driver.findElement(By.id("nome")).clear();
//		driver.findElement(By.id("nome")).sendKeys(registro);
//		driver.findElement(By.tagName("button")).click();
//		
//		return registro;
//	}

	@AfterClass
	public static void fechar() {
		driver.quit();
	}

}
