package br.edu.fatec.model;

import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.persistence.*;

import br.edu.fatec.security.Authority;

@Entity
@Table(name = "USU_USUARIO")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USU_ID", unique=true, nullable=false)
	private Long id;
	
	@Column(name = "USU_NOME")
	private String nome;
	
	@Column(name = "USU_SOBRENOME")
	private String sobrenome;
	
	@Column(name = "USU_DATANASC")
	private Date dataNasc;
	
	@NotBlank
	@Column(name = "USU_SENHA")
	private String senha;
	
	@Column(name = "USU_SEXO")
	private String sexo;
	
	@Column(name = "USU_EMAIL")
	private String email;
	
	@Column(name = "USU_CPF")
	private String cpf;
	
	@OneToOne
	@JoinColumn(name = "END_ID")
	private Endereco endereco;
	
	@OneToMany(mappedBy = "usuario")
	private Set<Cupom> cupons;
	
	@OneToMany(mappedBy = "usuario")
	private Set<Evento> eventos;
	
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "usu_usuario_authorities",
    joinColumns = @JoinColumn(name = "usuarios_usu_id"),
    inverseJoinColumns = @JoinColumn(name = "authorities_id"))
    private Set<Authority> authorities;
    
    @Column(name = "ENABLED")
    @NotNull
    private Boolean enabled;
    
    @Column(name = "LASTPASSWORDRESETDATE")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date lastPasswordResetDate;
	


	public Date getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}

	public void setLastPasswordResetDate(Date lastPasswordResetDate) {
		this.lastPasswordResetDate = lastPasswordResetDate;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	public Set<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(Set<Evento> eventos) {
		this.eventos = eventos;
	}

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

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public Date getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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
