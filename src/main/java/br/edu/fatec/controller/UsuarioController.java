package br.edu.fatec.controller;

import java.util.Collection;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import br.edu.fatec.model.Usuario;
import br.edu.fatec.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;


	/* Adicionaer novo usuario */
	@CrossOrigin
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public void salvar(@RequestBody Usuario usuario, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		usuarioService.salvarUsuario(usuario);
		response.addHeader("Location", request.getServerName() + ":" + request.getServerPort()
				+ request.getServletPath() + "/usuario/getById?id=" + usuario.getId());
	}

	/* Buscar um usuario */
	@RequestMapping(value = "/getByNome/{nome}", method = RequestMethod.GET)
	public ResponseEntity<Collection<Usuario>> pesquisarUsuarios(@PathVariable("nome") String nome) {
		return new ResponseEntity<Collection<Usuario>>(usuarioService.buscar(nome), HttpStatus.OK);
	}

	/* Buscar usuarios pelo nomee sobrenome */
	@RequestMapping(value = "/get/{nome}/{sobrenome}")
	public ResponseEntity<Collection<Usuario>> usuarioByNomeAndSobrenome(@PathVariable("nome") String nome,
			@PathVariable("sobrenome") String sobrenome) {
		return new ResponseEntity<Collection<Usuario>>(usuarioService.buscaNomeSobrenome(nome, sobrenome),
				HttpStatus.OK);
	}

	/* Busca os usuarios de um determinado evento */
	@RequestMapping(value = "/getByEvento/{nomeEvento}")
	public ResponseEntity<Collection<Usuario>> pesquisarUsuariosEvento(@PathVariable("nomeEvento") String nomeEvento) {
		return new ResponseEntity<Collection<Usuario>>(usuarioService.buscarUsuarioEvento(nomeEvento), HttpStatus.OK);
	}

	/* Pegar usuario pelo id */
	@RequestMapping(value = "/getById")
	public ResponseEntity<Usuario> getUsuario(@RequestParam(value = "id", defaultValue = "1") Long id) {
		Optional<Usuario> usuario = usuarioService.buscar(id);
		if (usuario == null) {
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Usuario>(HttpStatus.OK);
	}

	@RequestMapping(value = "/getAll",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Usuario>> getAll() {
		return new ResponseEntity<Collection<Usuario>>(usuarioService.todos(), HttpStatus.OK);
	}

}
