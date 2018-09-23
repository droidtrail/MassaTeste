package br.ce.wcaquino.estrategia5;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import br.ce.wcaquino.entidades.Conta;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.service.ContaService;
import br.ce.wcaquino.service.UsuarioService;
import dbunit.ImportExport;

public class ContasServiceTeste_5DbUnit {

	ContaService service = new ContaService();
	UsuarioService userService = new UsuarioService();

	@BeforeClass
	public static void inserirConta() throws Exception {

		ImportExport.importarBanco("estr5.xml");
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
		
		Conta contaTeste = service.findByName("Conta CT005 alteracao");
		contaTeste.setNome("Conta alterada");
		service.printAll();
		Conta contaAlterada = service.salvar(contaTeste);
		Assert.assertEquals("Conta alterada", contaAlterada.getNome());
		service.printAll();
	}

	@Test
	public void testeConsultar() throws Exception {
		
		Conta contaBuscada = service.findById(1L);
		Assert.assertEquals("Conta para testes", contaBuscada.getNome());

	}

	@Test
	public void testeExcluir() throws Exception {
		
		Conta contaTeste = service.findByName("Conta para deletar");

		service.printAll();
		service.delete(contaTeste);
		Conta contaBuscada = service.findById(contaTeste.getId());
		Assert.assertNull(contaBuscada);
		service.printAll();

	}

}
