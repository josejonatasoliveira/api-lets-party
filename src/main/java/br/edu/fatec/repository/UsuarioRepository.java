package br.edu.fatec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.edu.fatec.model.Usuario;
@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

	List<Usuario> findAll();
	
	List<Usuario> findByNome(String nome);

	List<Usuario> findByCpf(String cpf);
	
	List<Usuario> findByEmail(String email);
	
	List<Usuario> findByEnderecoNome(String nome);
	
	List<Usuario> findByNomeAndSobrenome(String nome, String sobrenome);
	
	@Query("select u FROM Usuario u JOIN u.eventos e WHERE e.nome like %?1% ")
	List<Usuario> findUsuarioByEvento(String nome);
	
	Boolean existsByNome(String username);

    Boolean existsByEmail(String email);
	
	

}
