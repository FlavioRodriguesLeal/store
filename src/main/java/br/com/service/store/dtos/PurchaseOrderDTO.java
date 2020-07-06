package br.com.service.store.dtos;

import br.com.service.store.models.Product;
import br.com.service.store.models.PurchaseOrder;
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
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
@Tag(name = "PurchaseOrder", description = "User purchase data")
public class PurchaseOrderDTO implements Serializable {

    private static final long serialVersionUID = -2321020072883469240L;

    @JsonProperty(required = false)
    @Schema(description = "Purchase order id.", required = false)
    private String id;

    @JsonProperty(required = true)
    @Schema(description = "User ID.", required = true)
    public String userId;

    @JsonProperty(required = true)
    @Schema(description = "Product Id.", required = true)
    public String productId;

    @JsonProperty(required = false)
    @Schema(description = "Purchase date.", required = false)
    public Instant purchaseDate;

    public static PurchaseOrderDTO fromModel(PurchaseOrder model) {
        if(model == null) return null;
        return new ObjectMapper().convertValue(model, PurchaseOrderDTO.class);
    }

    public static List<PurchaseOrderDTO> fromModel(List<PurchaseOrder> models) {
        if(models == null) return null;
        List<PurchaseOrderDTO> purchaseOrderDTOS = new ArrayList<>();
        models.stream().forEach(purchaseOrder -> purchaseOrderDTOS.add(PurchaseOrderDTO.fromModel(purchaseOrder)));
        return purchaseOrderDTOS;
    }

    public static PurchaseOrder toModel(PurchaseOrderDTO dto) {
        if(dto == null) return null;
        return new ObjectMapper().convertValue(dto, PurchaseOrder.class);
    }
}
