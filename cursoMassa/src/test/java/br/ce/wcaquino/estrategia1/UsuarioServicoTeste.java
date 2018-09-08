package br.ce.wcaquino.estrategia1;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.service.UsuarioService;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsuarioServicoTeste {
	
	private UsuarioService servico = new UsuarioService();
	private static Usuario usuarioGlobal;

	@Test
	public void teste_1_inserir() throws Exception {
		
		//Inicializando o usuário com os parâmetros
		Usuario usuario = new Usuario("User1 estrategia # 1","User1@gmail.com","User1");
		
		//Salvando o usuário
		usuarioGlobal = servico.salvar(usuario);	
		Assert.assertNotNull(usuarioGlobal.getId());
	}
	
	@Test
	public void teste_2_consultar() throws Exception {
		Usuario usuario = servico.findById(usuarioGlobal.getId());
		Assert.assertEquals("User1 estrategia # 1", usuario.getNome());
	}
	
	@Test
	public void teste_3_alterar() throws Exception {
		Usuario usuario = servico.findById(usuarioGlobal.getId());
		usuario.setNome("Usuario alterado");
		Usuario usuarioAlterado = servico.salvar(usuario);
		Assert.assertEquals("Usuario alterado", usuarioAlterado.getNome());
		
	}
	
	@Test
	public void teste_4_excluir() throws Exception {
		servico.delete(usuarioGlobal);
		Usuario usuarioRemovido = servico.findById(usuarioGlobal.getId());
		Assert.assertNull(usuarioRemovido);
	}

}
