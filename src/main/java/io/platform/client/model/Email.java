package io.platform.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Email {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String email;

}
