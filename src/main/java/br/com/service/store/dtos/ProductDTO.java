package br.com.service.store.dtos;

import br.com.service.store.models.Product;
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
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
@Tag(name = "Product", description = "Product Data")
public class ProductDTO implements Serializable {

    private static final long serialVersionUID = 7027855586312082468L;

    @JsonProperty(required = false)
    @Schema(description = "Product Id.", required = false)
    private String id;

    @JsonProperty(required = true)
    @Schema(description = "Product's name.", required = true)
    private String name;

    @JsonProperty(required = false)
    @Schema(description = "Product Type.", required = false)
    private String type;

    @JsonProperty(required = false)
    @Schema(description = "Product description.", required = false)
    private String description;

    public static ProductDTO fromModel(Product model) {
        if(model == null) return null;
        return new ObjectMapper().convertValue(model, ProductDTO.class);
    }

    public static List<ProductDTO> fromModel(List<Product> models) {
        if(models == null) return null;
        List<ProductDTO> productDTOS = new ArrayList<>();
        models.stream().forEach(product -> productDTOS.add(ProductDTO.fromModel(product)));
        return productDTOS;
    }

    public static Product toModel(ProductDTO dto) {
        if(dto == null) return null;
        return new ObjectMapper().convertValue(dto, Product.class);
    }
}
