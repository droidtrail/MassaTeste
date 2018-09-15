package br.ce.wcaquino.estrategia4;

import java.sql.Connection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.javafaker.Faker;

import br.ce.wcaquino.dao.utils.ConnectionFactory;
import br.ce.wcaquino.entidades.Conta;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.service.ContaService;
import br.ce.wcaquino.service.UsuarioService;

public class ContasServiceTeste4 {

	ContaService service = new ContaService();
	UsuarioService userService = new UsuarioService();

	@Before
	public void inserirConta() throws Exception {

		Connection conn = ConnectionFactory.getConnection();
		conn.createStatement().executeUpdate("DELETE FROM transacoes");
		conn.createStatement().executeUpdate("DELETE FROM contas");
		conn.createStatement().executeUpdate("DELETE FROM usuarios");
		conn.createStatement().executeUpdate("INSERT INTO usuarios (id, nome, email, senha) "
				+ "VALUES(1, 'Usuario de controle','usuario@email.com','passwd')");
	}

	@Test
	public void testeInserir() throws Exception {

		Usuario usuario = userService.findById(1L);
		Conta conta = new Conta("Conta salva",usuario);
		Conta ContaSalva = service.salvar(conta);
		Assert.assertNotNull(ContaSalva.getId());
		userService.printAll();
		service.printAll();
	}

	@Test
	public void testeAlterar() throws Exception {
		ConnectionFactory.getConnection().createStatement().executeUpdate(
				"INSERT INTO contas (id, nome, usuario_id) "
				+ "VALUES(1, 'Conta para testes','1')");
		
		Conta contaTeste = service.findByName("Conta para testes");
		contaTeste.setNome("Conta alterada");
		service.printAll();
		Conta contaAlterada = service.salvar(contaTeste);
		Assert.assertEquals("Conta alterada", contaAlterada.getNome());
		service.printAll();
	}

	@Test
	public void testeConsultar() throws Exception {
		
		ConnectionFactory.getConnection().createStatement().executeUpdate(
				"INSERT INTO contas (id, nome, usuario_id) "
				+ "VALUES(1, 'Conta para testes','1')");

		Conta contaBuscada = service.findById(1L);
		Assert.assertEquals("Conta para testes", contaBuscada.getNome());

	}

	@Test
	public void testeExcluir() throws Exception {
		
		ConnectionFactory.getConnection().createStatement().executeUpdate(
				"INSERT INTO contas (id, nome, usuario_id) "
				+ "VALUES(1, 'Conta para testes','1')");
		
		Conta contaTeste = service.findByName("Conta para testes");

		service.printAll();
		service.delete(contaTeste);
		Conta contaBuscada = service.findById(contaTeste.getId());
		Assert.assertNull(contaBuscada);
		service.printAll();

	}

}
