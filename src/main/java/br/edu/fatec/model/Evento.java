package br.edu.fatec.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.print.attribute.standard.DateTimeAtCompleted;

@Entity
@Table(name = "EVE_EVENTO")
public class Evento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EVE_ID")
	private Long id;
	
	@Column(name = "EVE_NOME")
	private String nome;
	
	@Column(name = "EVE_QTDCUPONS")
	private int qtdCupons;
	
	@Column(name = "EVE_DATAHOR")
//	@Temporal(TemporalType.TIMESTAMP)
	private DateTimeAtCompleted dataHora;
	
	@OneToOne
	@JoinColumn(name = "USU_ID")
	private Usuario usuario;
	
	@OneToOne
	@JoinColumn(name = "END_ID")
	private Endereco endereco;
	
	@OneToMany(mappedBy = "evento", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Cupom> cupons;

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

	public int getQtdCupons() {
		return qtdCupons;
	}

	public void setQtdCupons(int qtdCupons) {
		this.qtdCupons = qtdCupons;
	}

	public DateTimeAtCompleted getDataHora() {
		return dataHora;
	}

	public void setDataHora(DateTimeAtCompleted dataHora) {
		this.dataHora = dataHora;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Set<Cupom> getCupons() {
		return cupons;
	}

	public void setCupons(Set<Cupom> cupons) {
		this.cupons = cupons;
	}

}
