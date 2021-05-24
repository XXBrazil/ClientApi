package io.platform.client.dto;

import io.platform.client.model.Email;
import io.platform.client.model.Telefone;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    @NotEmpty
    private String user;

    @NotEmpty
    private String password;

}
