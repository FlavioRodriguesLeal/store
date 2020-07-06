package br.com.service.store.resources;

import br.com.service.store.dtos.ProductDTO;
import br.com.service.store.envelop.JsonEnvelop;
import br.com.service.store.exceptions.FaultException;
import br.com.service.store.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({ "/store/services/product" })
@Tag(name = "ProductResources", description = "Endpoint for product")
public class ProductResources {

    public @Autowired ProductService productService;

    @GetMapping
    @Operation(summary = "Get all product.", tags = { "ProductResources" })
    public ResponseEntity<JsonEnvelop<List<ProductDTO>>> getAllProduct(){

        List<ProductDTO> productDTOS = productService.getAllProduct();

        return new JsonEnvelop.Builder<List<ProductDTO>>()
                .statusAndStatusCode(HttpStatus.OK)
                .data(productDTOS)
                .build()
                .response();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product data by product id.", tags = { "ProductResources" })
    public ResponseEntity<JsonEnvelop<ProductDTO>> getProductById(
            @Parameter(description = "Product ID", required=true)
            @PathVariable("id") String id){

        if(id == null) throw new FaultException(HttpStatus.UNPROCESSABLE_ENTITY, "Product ID was not informed.");

        ProductDTO productDTO = productService.getProductById(id);

        return new JsonEnvelop.Builder<ProductDTO>()
                .statusAndStatusCode(HttpStatus.OK)
                .data(productDTO)
                .build()
                .response();
    }

    @PostMapping
    @Operation(summary = "Saves product.", tags = { "ProductResources" })
    public ResponseEntity<JsonEnvelop<ProductDTO>> saveProduct(
            @Parameter(description = "Product data to be recorded.", required=true)
            @RequestBody ProductDTO product){

        ProductDTO productDTO = productService.saveProduct(product);

        return new JsonEnvelop.Builder<ProductDTO>()
                .statusAndStatusCode(HttpStatus.OK)
                .data(productDTO)
                .build()
                .response();
    }

    @DeleteMapping
    @Operation(summary = "Delete the product.", tags = { "ProductResources" })
    public ResponseEntity<JsonEnvelop<ProductDTO>> deletProduct(
            @Parameter(description = "Product ID", required=true)
            @RequestParam String id){

        if(id == null) throw new FaultException(HttpStatus.UNPROCESSABLE_ENTITY, "Product ID was not informed.");

        ProductDTO productDTO = productService.deletProduct(id);

        return new JsonEnvelop.Builder<ProductDTO>()
                .statusAndStatusCode(HttpStatus.OK)
                .data(productDTO)
                .build()
                .response();
    }

}
