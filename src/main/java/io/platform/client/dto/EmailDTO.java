package io.platform.client.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {

    private long id;
    @NotEmpty
    private String email;
}
