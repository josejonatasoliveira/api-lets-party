package br.edu.fatec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.fatec.model.Cupom;
@Repository
public interface CupomRepository extends JpaRepository<Cupom, Long>{

	Cupom findCupomByCodigo(String codigo);
	
	List<Cupom> findByEventoNome(String nome);
	
	@Query("SELECT c FROM Cupom c JOIN c.evento e WHERE e.nome LIKE %?1% ")
	List<Cupom> findCupomByEvento(String nome);
}
