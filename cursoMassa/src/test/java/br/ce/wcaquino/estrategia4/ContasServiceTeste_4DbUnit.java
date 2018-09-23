package br.ce.wcaquino.estrategia4;

import static br.ce.wcaquino.dao.utils.ConnectionFactory.getConnection;

import java.io.File;
import java.io.FileInputStream;

import org.dbunit.Assertion;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Conta;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.service.ContaService;
import br.ce.wcaquino.service.UsuarioService;
import dbunit.ImportExport;

public class ContasServiceTeste_4DbUnit {

	ContaService service = new ContaService();
	UsuarioService userService = new UsuarioService();

	@Test
	public void testeInserir() throws Exception {

		ImportExport.importarBanco("est4_inserirConta.xml");
		Usuario usuario = userService.findById(1L);
		Conta conta = new Conta("Conta salva", usuario);
		Conta ContaSalva = service.salvar(conta);
		Assert.assertNotNull(ContaSalva.getId());

	}

	@Test
	public void testeInserir_Assertion() throws Exception {

		ImportExport.importarBanco("est4_inserirConta.xml");

		// Estado Atual do BD
		DatabaseConnection dbConn = new DatabaseConnection(getConnection());
		IDataSet estadoAtualBanco = dbConn.createDataSet();

		// Estado Esperado (XML)
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		FlatXmlDataSet dataSetEsperado = builder.build(new FileInputStream("massas" + File.separator + "est4_inserirContaSaida.xml"));

		// Comparar os 2 estados
		 Assertion.assertEquals(dataSetEsperado,estadoAtualBanco);
	}

	@Test
	public void testeAlterar() throws Exception {

		ImportExport.importarBanco("est4_umaConta.xml");
		Conta contaTeste = service.findByName("Conta para testes");
		contaTeste.setNome("Conta alterada");
		service.printAll();
		Conta contaAlterada = service.salvar(contaTeste);
		Assert.assertEquals("Conta alterada", contaAlterada.getNome());
		service.printAll();
	}

	@Test
	public void testeConsultar() throws Exception {

		ImportExport.importarBanco("est4_umaConta.xml");
		Conta contaBuscada = service.findById(1L);
		Assert.assertEquals("Conta para testes", contaBuscada.getNome());

	}

	@Test
	public void testeExcluir() throws Exception {

		ImportExport.importarBanco("est4_umaConta.xml");
		Conta contaTeste = service.findByName("Conta para testes");
		service.printAll();
		service.delete(contaTeste);
		Conta contaBuscada = service.findById(contaTeste.getId());
		Assert.assertNull(contaBuscada);
		service.printAll();

	}

}
