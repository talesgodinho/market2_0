package market2_0.model.persistence;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column(nullable = false)
	String nome;
	
	@Column(nullable = false, unique = true)
	String cpf;
	
	@Column(nullable = false)
	LocalDate dataNascimentoDate;
	
	public Cliente() {
	}

	public Cliente(String nome, String cpf, LocalDate dataNascimentoDate) {
		this.nome = nome.toLowerCase();
		this.cpf = cpf;
		this.dataNascimentoDate = dataNascimentoDate;
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
		this.nome = nome.toLowerCase();
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDataNascimentoDate() {
		return dataNascimentoDate;
	}

	public void setDataNascimentoDate(LocalDate dataNascimentoDate) {
		this.dataNascimentoDate = dataNascimentoDate;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", dataNascimentoDate=" + dataNascimentoDate
				+ "]";
	}
}
