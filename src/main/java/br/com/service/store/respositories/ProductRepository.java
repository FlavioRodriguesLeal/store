package br.com.service.store.respositories;

import br.com.service.store.models.Product;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnMissingBean(name = { "requestContextFilter" }, value = { FilterRegistrationBean.class })
public interface ProductRepository extends MongoRepository<Product, String> {
}
