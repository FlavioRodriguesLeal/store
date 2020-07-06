package br.com.service.store.respositories;

import br.com.service.store.models.PurchaseOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@ConditionalOnMissingBean(name = { "requestContextFilter" }, value = { FilterRegistrationBean.class })
public interface PurchaseOrderRepository extends MongoRepository<PurchaseOrder , String> {

    Optional<List<PurchaseOrder>> findByUserId(String userId);
}
