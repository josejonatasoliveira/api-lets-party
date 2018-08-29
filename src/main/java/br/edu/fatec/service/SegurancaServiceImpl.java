package br.edu.fatec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.fatec.model.Usuario;
import br.edu.fatec.repository.UsuarioRepository;


@Service("segurancaService")
public class SegurancaServiceImpl implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepo;

	/**
	 * @param usuarioRepo the usuarioRepo to set
	 */
	public void setUsuarioRepo(UsuarioRepository usuarioRepo) {
		this.usuarioRepo = usuarioRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = (Usuario) usuarioRepo.findByNome(username);
		if(usuario == null) {
			throw new UsernameNotFoundException(username);
		}
		return (UserDetails) usuario;
	}

}
