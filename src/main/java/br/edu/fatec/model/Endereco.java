package br.edu.fatec.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "END_ENDERECO")
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "END_ID")
	private Long id;
	
	@Column(name = "END_NOME")
	private String nome;
	
	@Column(name = "END_CEP")
	private String cep;
	
	@Column(name = "END_BAIRRO")
	private String bairro;
	

	@Column(name = "END_cidade")
	private String cidade;
	
	@Column(name = "END_estado")
	private String estado;

	@OneToMany(mappedBy = "endereco")
	private Set<Evento> eventos;
	
	@OneToMany(mappedBy = "endereco")
	private Set<Usuario> usuarios;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getBairro() {
		return bairro;
	}
	
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	public String getCidade() {
		return cidade;
	}
	
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
}
