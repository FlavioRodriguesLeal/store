package br.com.service.store;//package br.com.service.store;

import br.com.service.store.models.Product;
import br.com.service.store.models.PurchaseOrder;
import br.com.service.store.models.User;
import br.com.service.store.respositories.ProductRepository;
import br.com.service.store.respositories.PurchaseOrderRepository;
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
public class PurchaseOrderTest {


    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        RestAssured.port = this.port;
    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Test
    public void savePurchaseOrderTest(){
        User user = new User();
        user.setName("Teste");
        user.setEmail("teste@teste.com");
        userRepository.save(user);

        Product product =new Product();
        product.setName("ProdTeste");
        product.setType("teste");
        product.setDescription("Product for Test");
        product = productRepository.save(product);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setUserId(user.getId());
        purchaseOrder.setProductId(product.getId());

        given()
                .contentType(ContentType.JSON)
                .body(purchaseOrder)
                .when()
                .post("/store/services/purchase")
                .then()
                .statusCode(200);

        purchaseOrderRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void getPurchaseOrderById(){
        User user = new User();
        user.setName("Teste");
        user.setEmail("teste@teste.com");
        userRepository.save(user);

        Product product =new Product();
        product.setName("ProdTeste");
        product.setType("teste");
        product.setDescription("Product for Test");
        product = productRepository.save(product);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setUserId(user.getId());
        purchaseOrder.setProductId(product.getId());
        purchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        given()
                .when()
                .get("/store/services/purchase/{id}", purchaseOrder.getId())
                .then()
                .statusCode(200);

        purchaseOrderRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void getPurchaseOrderByUserId(){
        User user = new User();
        user.setName("Teste");
        user.setEmail("teste@teste.com");
        userRepository.save(user);

        Product product =new Product();
        product.setName("ProdTeste");
        product.setType("teste");
        product.setDescription("Product for Test");
        product = productRepository.save(product);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setUserId(user.getId());
        purchaseOrder.setProductId(product.getId());
        purchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        given()
                .when()
                .get("/store/services/purchase/user/{userId}", user.getId())
                .then()
                .statusCode(200);

        purchaseOrderRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();
    }
}
