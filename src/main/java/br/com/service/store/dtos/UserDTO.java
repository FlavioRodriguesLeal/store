package br.com.service.store.dtos;

import br.com.service.store.models.Product;
import br.com.service.store.models.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
@Tag(name = "User", description = "Contains user data")
public class UserDTO implements Serializable {

    private static final long serialVersionUID = -8010139045668048090L;

    @JsonProperty(required = false)
    @Schema(description = "User ID.", required = false)
    private String id;

    @JsonProperty(required = true)
    @Schema(description = "Username.", required = true)
    private String name;

    @JsonProperty(required = true)
    @Schema(description = "User email.", required = true)
    private String email;

    public static UserDTO fromModel(User model) {
        if(model == null) return null;
        return new ObjectMapper().convertValue(model, UserDTO.class);
    }

    public static User toModel(UserDTO dto) {
        if(dto == null) return null;
        return new ObjectMapper().convertValue(dto, User.class);
    }
}
