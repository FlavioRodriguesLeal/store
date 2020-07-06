package br.com.service.store.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "purchaseOrder")
public class PurchaseOrder {

    private @Id String id;

    public String userId;

    public String productId;

    public Instant purchaseDate;

}
