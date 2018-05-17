package br.edu.fatec.model;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.edu.fatec.service.CupomService;
import br.edu.fatec.service.UsuarioService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
//    	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//    	
//    	CupomService cupomService = (CupomService) context.getBean("cupomService");
//    	UsuarioService usuarioService = (UsuarioService) context.getBean("usuarioService");
    	
//    	Usuario usuario = new Usuario();
//    	usuario.setNome("Jose");
//    	usuario.setSobrenome("Jonatas");
//    	usuario.setCpf("42380489890");
//    	usuario.setDataNasc(null);
//    	usuario.setEmail("email@email.com");
//    	
//    	usuarioService.salvarUsuario(usuario);
    	
    	Usuario usuario1 = new Usuario();
    	usuario1.setNome("Paloma");
    	usuario1.setSobrenome("Santana");
    	usuario1.setCpf("42380490805");
    	usuario1.setDataNasc(null);
    	usuario1.setEmail("paloma@email.com");
    	
    	Usuario usuario = new Usuario();
    	usuario.setNome("Joao");
    	usuario.setSobrenome("Silva");
    	usuario.setCpf("25945295086");
    	usuario.setDataNasc(null);
    	usuario.setEmail("joao@email.com");
    	
    	UsuarioService usuarioService=null;	
    	usuarioService.buscar("Jose");
    }
}
