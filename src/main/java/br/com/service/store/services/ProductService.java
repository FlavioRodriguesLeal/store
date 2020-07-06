package br.com.service.store.services;

import br.com.service.store.dtos.ProductDTO;
import br.com.service.store.exceptions.FaultException;
import br.com.service.store.models.Product;
import br.com.service.store.respositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    public @Autowired ProductRepository productRepository;

    public List<ProductDTO> getAllProduct() {
        List<Product> products = productRepository.findAll();
        return ProductDTO.fromModel(products);
    }

    public ProductDTO getProductById(String id) {
        Product product = productRepository.findById(id).orElse(null);
        return ProductDTO.fromModel(product);
    }

    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = productRepository.save(ProductDTO.toModel(productDTO));
        return ProductDTO.fromModel(product);
    }

    public ProductDTO deletProduct(String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new FaultException(HttpStatus.UNPROCESSABLE_ENTITY, "Product not found."));
        productRepository.delete(product);
        return ProductDTO.fromModel(product);
    }
}
