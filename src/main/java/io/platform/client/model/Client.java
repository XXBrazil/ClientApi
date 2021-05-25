package io.platform.client.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String nome;

	private String cpf;

	private String cep;

	private String logradouro;

	private String bairro;

	private String cidade;

	private String uf;

	private String complemento;

	@OneToMany(fetch = FetchType.LAZY,
               cascade =  CascadeType.ALL)
	private List<Email> emails;

	@OneToMany(fetch = FetchType.LAZY,
			cascade =  CascadeType.ALL)
	private List<Telefone> telefones;

}
