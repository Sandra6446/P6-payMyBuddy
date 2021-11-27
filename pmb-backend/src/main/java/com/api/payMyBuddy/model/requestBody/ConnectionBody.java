package com.api.payMyBuddy.model.requestBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionBody {

    @NotNull
    @NotEmpty
    String userEmail;

    @NotNull
    @NotEmpty
    String connectionEmail;

    @SneakyThrows
    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
}
