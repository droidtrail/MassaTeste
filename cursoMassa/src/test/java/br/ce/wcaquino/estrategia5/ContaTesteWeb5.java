package br.ce.wcaquino.estrategia5;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ContaTesteWeb5 {

	//O static aproveita a mesma janela
	private static FirefoxDriver driver;

	@BeforeClass
	public static void loginReset() {
		System.setProperty("webdriver.gecko.driver", "e:\\GeckoDriver\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.get("https://seubarriga.wcaquino.me");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.findElement(By.id("email")).sendKeys("cursoselenium@gmail.com");
		driver.findElement(By.id("senha")).sendKeys("123");
		driver.findElement(By.xpath("//button[.='Entrar']")).click();
		driver.findElement(By.linkText("reset")).click();
		//driver.quit(); Não precisa fechar. Trabalharemos na mesma janela.
	}

	//Não precisa do @Before pois usaremos o navegador do @BeforeClass
//	@Before
//	public void login() {
//		driver = new FirefoxDriver();
//		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//		driver.get("https://seubarriga.wcaquino.me");
//		driver.manage().window().maximize();
//		driver.findElement(By.id("email")).sendKeys("cursoselenium@gmail.com");
//		driver.findElement(By.id("senha")).sendKeys("123");
//		driver.findElement(By.xpath("//button[.='Entrar']")).click();
//	}

	@Test
	public void inserir() throws ClassNotFoundException, SQLException {
		driver.findElement(By.linkText("Contas")).click();
		driver.findElement(By.linkText("Adicionar")).click();
		driver.findElement(By.id("nome")).clear();
		driver.findElement(By.id("nome")).sendKeys("Conta Estratégia 5#");
		driver.findElement(By.tagName("button")).click();
		String msgSucesso = driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
		Assert.assertEquals("Conta adicionada com sucesso!", msgSucesso);
	}

	@Test
	public void consultar() throws ClassNotFoundException, SQLException {
		driver.findElement(By.linkText("Contas")).click();
		driver.findElement(By.linkText("Listar")).click();
		driver.findElement(By.xpath("//td[.='Conta para saldo']/..//a[1]")).click();
		String msgAlterar = driver.findElement(By.id("nome")).getAttribute("value");
		Assert.assertEquals("Conta para saldo", msgAlterar);
	}

	@Test
	public void alterar() throws ClassNotFoundException, SQLException {
		driver.findElement(By.linkText("Contas")).click();
		driver.findElement(By.linkText("Listar")).click();
		driver.findElement(By.xpath("//td[.='Conta para alterar']/..//a[1]")).click();
		driver.findElement(By.id("nome")).sendKeys(" Alterado");
		driver.findElement(By.tagName("button")).click();
		String msgSucesso = driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
		Assert.assertEquals("Conta alterada com sucesso!", msgSucesso);
	}

	@Test
	public void deletar() throws ClassNotFoundException, SQLException {
		driver.findElement(By.linkText("Contas")).click();
		driver.findElement(By.linkText("Listar")).click();
		driver.findElement(By.xpath("//td[.='Conta mesmo nome']/..//a[2]")).click();
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

	@AfterClass/*A seção será fechada apenas quando todos os testes terminarem*/
	public static void fechar() {
		driver.quit();
	}

}
