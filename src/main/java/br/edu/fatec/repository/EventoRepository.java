package br.edu.fatec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.fatec.model.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long>{
	
	List<Evento> findAll();
	
	List<Evento> findByNome(String nome);
	
	@Query("SELECT e FROM Evento e JOIN e.endereco ed WHERE ed.nome like %?1%")
	List<Evento> fidByEndereco(String nome);

}
