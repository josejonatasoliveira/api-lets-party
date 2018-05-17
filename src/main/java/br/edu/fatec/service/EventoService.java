package br.edu.fatec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.fatec.model.Evento;
import br.edu.fatec.repository.EnderecoRepositoy;
import br.edu.fatec.repository.EventoRepository;
import br.edu.fatec.repository.UsuarioRepository;

@Service("eventoService")
public class EventoService {

	@Autowired
	EventoRepository eventoRepository;
	
	@Autowired
	UsuarioRepository repository;
	
	@Autowired
	EnderecoRepositoy enderecoRepositoy;
	
	public void salvarEvento(Evento evento)throws Exception {
		if (repository.findByNomeAndSobrenome(evento.getUsuario().getNome(),
				evento.getUsuario().getSobrenome())
				.contains(evento.getUsuario())
				&& enderecoRepositoy.findById(evento.getEndereco().getId()).equals(evento.getUsuario())) {
			eventoRepository.save(evento);
			
		}else {
			throw new Exception("Evento nao possui endereco e usuario que o criou!");
		}
	}
}
