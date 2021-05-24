package io.platform.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Login {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String user;
	private String password;

}
