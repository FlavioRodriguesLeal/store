package br.com.service.store;//package br.com.service.store;

import br.com.service.store.models.Product;
import br.com.service.store.respositories.ProductRepository;
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
public class ProductTest {

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        RestAssured.port = this.port;
    }

    @Autowired
    ProductRepository productRepository;

    @Test
    public void saveProduct(){
        Product product =new Product();
        product.setName("ProdTeste");
        product.setType("teste");
        product.setDescription("Product for Test");
        product = productRepository.save(product);

        given()
                .contentType(ContentType.JSON)
                .body(product)
                .when()
                .post("/store/services/product")
                .then()
                .statusCode(200);

        productRepository.deleteAll();
    }

    @Test
    public void getProductById(){
        Product product =new Product();
        product.setName("ProdTeste");
        product.setType("teste");
        product.setDescription("Product for Test");
        product = productRepository.save(product);

        given()
                .when()
                .get("/store/services/product/{id}", product.getId())
                .then()
                .statusCode(200);

        productRepository.deleteAll();
    }

    @Test
    public void getAllProduct(){
        Product product =new Product();
        product.setName("ProdTeste");
        product.setType("teste");
        product.setDescription("Product for Test");
        product = productRepository.save(product);

        given()
                .when()
                .get("/store/services/product")
                .then()
                .statusCode(200);

        productRepository.deleteAll();
    }
}
