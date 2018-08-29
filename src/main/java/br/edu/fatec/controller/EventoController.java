package br.edu.fatec.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.edu.fatec.model.Evento;
import br.edu.fatec.repository.EventoRepository;

@RestController
@Controller
public class EventoController {
	
	@Autowired
	private EventoRepository eventoRepository;
	
    @RequestMapping(value = "/evento/{nomeCidade}", method = RequestMethod.GET)
    public ResponseEntity<Collection<Evento>> searchEventoByCidade(@PathVariable("nomeCidade") String nome) {
    	return new ResponseEntity<Collection<Evento>>(eventoRepository.findByCidade(nome), HttpStatus.OK);
    }

}
