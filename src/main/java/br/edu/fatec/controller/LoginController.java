package br.edu.fatec.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.edu.fatec.jwt.JwtUtils;
import br.edu.fatec.model.Login;
import br.edu.fatec.model.Usuario;
import br.edu.fatec.service.SegurancaServiceImpl;
import br.edu.fatec.service.UsuarioService;


@RestController
@RequestMapping(value = "/login")
@ComponentScan(basePackages= "br.edu.fatec")
public class LoginController {
    
    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager auth;


    
    @Autowired
    private SegurancaServiceImpl impl;
    
    @Autowired
    private UsuarioService usuarioService;

    public void setAuth(AuthenticationManager auth) {
        this.auth = auth;
    }
    
    @RequestMapping(path = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Usuario login(@RequestBody Login login, HttpServletResponse response) throws JsonProcessingException {
        Authentication credentials = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        Usuario usuario = (Usuario) auth.authenticate(credentials).getPrincipal();
        usuario.setSenha(null);
           response.setHeader("Token", JwtUtils.generateToken(usuario));
           return usuario;
    }
    
    @RequestMapping(path = "/refresh", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> refreshAndGetAuthenticationToken(Authentication authentication, HttpServletResponse response) throws JsonProcessingException {
       String nomeUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
       Usuario user = (Usuario) impl.loadUserByUsername(nomeUsuario);
       String token = JwtUtils.generateToken(user);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("token",token);
		response.setHeader("Token",token);
		return model;
       
    }
    
    @PreAuthorize("ROLE_ADMIN")
	@RequestMapping(value = "/getUser/{nome}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDetails> pesquisarUsuarios(@PathVariable("nome") String nome) {
    	UserDetails user = usuarioService.loadUser(nome);
		if (user == null) {
			return new ResponseEntity<UserDetails>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<UserDetails>(user, HttpStatus.OK);
	}

}
