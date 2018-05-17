package br.edu.fatec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.fatec.model.Cupom;
import br.edu.fatec.repository.CupomRepository;

@Service("cupomService")
public class CupomService {
	
	@Autowired
	CupomRepository cupomRepository;
	
	public void salvarCupom(Cupom cupom) throws Exception {
		if (!cupomRepository.findCupomByCodigo(cupom.getCodigo()).equals(cupom)) {
			cupomRepository.save(cupom);
		}else {
			throw new Exception("Nao pode existir dois cupons com o mesmo codigo!");
		}
	}
}
