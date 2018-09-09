package br.ce.wcaquino.estrategia3;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.github.javafaker.Faker;

import br.ce.wcaquino.entidades.Conta;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.service.ContaService;
import br.ce.wcaquino.service.UsuarioService;

public class GeradorMassas {

	private static FirefoxDriver driver;
	public static final String CHAVE_CONTA_SB = "CONTA_SB";
	public static final String CHAVE_CONTA = "CONTA";

	
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
	
	public void gerarConta() throws Exception {
		
		Faker faker = new Faker();
		ContaService service = new ContaService();
		UsuarioService userService = new UsuarioService();
		
		Usuario usuarioGlobal = new Usuario(faker.name().fullName(), faker.internet().emailAddress(),
				faker.internet().password());
		Usuario Usuariosalvo = userService.salvar(usuarioGlobal);
		Conta conta = new Conta(faker.superhero().name(), Usuariosalvo);
		service.salvar(conta);
		new MassaDAOImpl().inserirMassa(CHAVE_CONTA, conta.getNome());
	}

	public static void main(String[] args) throws Exception {
		GeradorMassas gerador = new GeradorMassas();

		for (int i = 0; i < 2; i++) {
			gerador.gerarContaSeuBarriga();
		}
//		String massa = new MassaDAOImpl().obterMassa(CHAVE_CONTA_SB);
//		System.out.println(massa);
	}
	

}
