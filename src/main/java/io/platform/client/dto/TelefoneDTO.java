package io.platform.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelefoneDTO {

    private long id;
    @NotEmpty
    private String telefone;
    @NotEmpty
    private Integer tipo;
}
