package br.ce.wcaquino.estrategia4;

import static br.ce.wcaquino.dao.utils.ConnectionFactory.getConnection;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.dbunit.Assertion;
import org.dbunit.assertion.DiffCollectingFailureHandler;
import org.dbunit.assertion.Difference;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
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
		Conta contaSalva = service.salvar(conta);
	}
	
	@Test
	public void testeInserir_Filter() throws Exception {
		
		ImportExport.importarBanco("est4_inserirConta.xml");
		Usuario usuario = userService.findById(1L);
		/*Conta conta = new Conta("Conta salva", usuario);
		service.salvar(conta);*/

		// Estado Atual do BD
		DatabaseConnection dbConn = new DatabaseConnection(getConnection());
		IDataSet estadoAtualBanco = dbConn.createDataSet();

		// Estado Esperado (XML)
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		FlatXmlDataSet dataSetEsperado = builder.build(new FileInputStream("massas" + File.separator + "est4_inserirContaSaida.xml"));

		// Comparar os 2 estados
		ITable contaAtualFiltrada = DefaultColumnFilter.excludedColumnsTable(estadoAtualBanco.getTable("contas"), new String [] {"id"});
		ITable contaEsperadaFiltradas = DefaultColumnFilter.excludedColumnsTable(dataSetEsperado.getTable("contas"), new String [] {"id"});
		
		ITable usuarioAtualFiltrada = DefaultColumnFilter.excludedColumnsTable(estadoAtualBanco.getTable("usuarios"), new String [] {"created_at"});
		ITable usuarioEsperadaFiltradas = DefaultColumnFilter.excludedColumnsTable(dataSetEsperado.getTable("usuarios"), new String [] {"created_at"});

		 //Assertion.assertEquals(contaEsperadaFiltradas, contaAtualFiltrada);
		 Assertion.assertEquals(usuarioEsperadaFiltradas, usuarioAtualFiltrada);

	}


	@Test
	public void testeInserir_Assertion() throws Exception {
		
		ImportExport.importarBanco("est4_inserirConta.xml");
		Usuario usuario = userService.findById(1L);
		Conta conta = new Conta("Conta salva", usuario);
		Conta contaSalva = service.salvar(conta);

		// Estado Atual do BD
		DatabaseConnection dbConn = new DatabaseConnection(getConnection());
		IDataSet estadoAtualBanco = dbConn.createDataSet();

		// Estado Esperado (XML)
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		FlatXmlDataSet dataSetEsperado = builder.build(new FileInputStream("massas" + File.separator + "est4_inserirContaSaida.xml"));

		// Comparar os 2 estados
		 //Assertion.assertEquals(dataSetEsperado,estadoAtualBanco);
		
		DiffCollectingFailureHandler handler = new DiffCollectingFailureHandler();
		Assertion.assertEquals(dataSetEsperado,estadoAtualBanco, handler);
		List <Difference> erros = handler.getDiffList();
		
		boolean erroReal = false;
		for(Difference erro: erros) {
			System.out.println(erro.toString());
			if(erro.getActualTable().getTableMetaData().getTableName().equals("contas")) {
				if(erro.getColumnName().equals("id")) {
					
					if(erro.getActualValue().toString().equals(contaSalva.getId().toString())){
						
						continue;
						
					} else {	
						
						System.out.println("Id errado mesmo!");	
						erroReal = true;
						
					}
				} else {
					
					erroReal = true;
				}
				
			} else {
				
				erroReal = true;
			}
		}
		Assert.assertFalse(erroReal);
		
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
