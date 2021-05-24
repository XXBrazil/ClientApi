package io.platform.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Telefone {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String telefone;
	private Integer tipo;

}
