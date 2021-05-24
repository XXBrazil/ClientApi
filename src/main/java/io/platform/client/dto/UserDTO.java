package io.platform.client.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    @NotEmpty
    private String user;

    @NotEmpty
    private String password;

    private Boolean admin;

}
