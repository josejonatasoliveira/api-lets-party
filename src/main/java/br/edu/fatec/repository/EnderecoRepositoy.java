package br.edu.fatec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.fatec.model.Endereco;
@Repository
public interface EnderecoRepositoy extends JpaRepository<Endereco, Long>{

	List<Endereco> findAll();
	
	List<Endereco> findByNomeAndCidadeAndEstado(String nome,String cidade,String estado);
	
	@Query("SELECT ed FROM Endereco ed JOIN ed.eventos e WHERE e.nome like %?1%")
	Endereco findByNomeEvento(String nomeEvento);

}
