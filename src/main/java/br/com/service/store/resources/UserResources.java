package br.com.service.store.resources;

import br.com.service.store.dtos.UserDTO;
import br.com.service.store.envelop.JsonEnvelop;
import br.com.service.store.exceptions.FaultException;
import br.com.service.store.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({ "/store/services/user" })
@Tag(name = "UserResources", description = "Endpoint for user")
public class UserResources {

    public @Autowired UserService userService;

    @GetMapping("/email/{email}")
    @Operation(summary = "Get user data by email.", tags = { "UserResources" })
    public ResponseEntity<JsonEnvelop<UserDTO>> getUserByEmail(
            @Parameter(description = "User email", required=true)
            @PathVariable("email") String email){

        if(email == null) throw new FaultException(HttpStatus.UNPROCESSABLE_ENTITY, "User email was not informed.");

        UserDTO userDTO = userService.getUserByEmail(email);

       return new JsonEnvelop.Builder<UserDTO>()
               .statusAndStatusCode(HttpStatus.OK)
               .data(userDTO)
               .build()
               .response();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user data by user id.", tags = { "UserResources" })
    public ResponseEntity<JsonEnvelop<UserDTO>> getUserById(
            @Parameter(description = "User ID", required=true)
            @PathVariable("id") String id){

        if(id == null) throw new FaultException(HttpStatus.UNPROCESSABLE_ENTITY, "User ID was not informed.");

        UserDTO userDTO = userService.getUserById(id);

        return new JsonEnvelop.Builder<UserDTO>()
                .statusAndStatusCode(HttpStatus.OK)
                .data(userDTO)
                .build()
                .response();
    }

    @PostMapping
    @Operation(summary = "Saves users.", tags = { "UserResources" })
    public ResponseEntity<JsonEnvelop<UserDTO>> saveUser(
            @Parameter(description = "User data to be recorded.", required=true)
            @RequestBody UserDTO userDTO){

        UserDTO user = userService.saveUser(userDTO);

        return new JsonEnvelop.Builder<UserDTO>()
                .statusAndStatusCode(HttpStatus.OK)
                .data(user)
                .build()
                .response();
    }

    @DeleteMapping
    @Operation(summary = "Delete the user.", tags = { "UserResources" })
    public ResponseEntity<JsonEnvelop<UserDTO>> deletUser(
            @Parameter(description = "User ID", required=true)
            @RequestParam String id){

        if(id == null) throw new FaultException(HttpStatus.UNPROCESSABLE_ENTITY, "User ID was not informed.");

        UserDTO userDTO = userService.deletUser(id);

        return new JsonEnvelop.Builder<UserDTO>()
                .statusAndStatusCode(HttpStatus.OK)
                .data(userDTO)
                .build()
                .response();
    }
}
