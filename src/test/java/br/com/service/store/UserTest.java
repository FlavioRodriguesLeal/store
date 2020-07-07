package br.com.service.store;//package br.com.service.store;

import br.com.service.store.models.User;
import br.com.service.store.respositories.UserRepository;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest {


    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        RestAssured.port = this.port;
    }

    @Autowired
    UserRepository userRepository;

    @Test
    public void saveUser(){
        User user = new User();
        user.setName("Teste");
        user.setEmail("teste@teste.com");

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/store/services/user")
                .then()
                .statusCode(200);

        userRepository.deleteAll();
    }

    @Test
    public void getUserById(){

        User user = new User();
        user.setName("Teste");
        user.setEmail("teste@teste.com");
        userRepository.save(user);

        given()
                .when()
                .get("/store/services/user/{id}", user.getId())
                .then()
                .statusCode(200);

        userRepository.deleteAll();
    }

    @Test
    public void getUserByEmail(){

        User user = new User();
        user.setName("Teste");
        user.setEmail("teste@teste.com");
        user = userRepository.save(user);

        given()
                .when()
                .get("/store/services/user/email/{email}", user.getEmail())
                .then()
                .statusCode(200);

        userRepository.deleteAll();
    }

    @Test
    public void deletUser(){

        User user = new User();
        user.setName("Teste");
        user.setEmail("teste@teste.com");
        user = userRepository.save(user);

        given()
                .param("id", user.getId())
                .when()
                .delete("/store/services/user")
                .then()
                .statusCode(200);
    }
}
