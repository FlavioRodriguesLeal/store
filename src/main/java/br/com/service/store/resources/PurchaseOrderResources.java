package br.com.service.store.resources;

import br.com.service.store.dtos.ProductDTO;
import br.com.service.store.dtos.PurchaseOrderDTO;
import br.com.service.store.envelop.JsonEnvelop;
import br.com.service.store.exceptions.FaultException;
import br.com.service.store.services.PurchaseOrderService;
import br.com.service.store.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({ "/store/services/purchase" })
@Tag(name = "PurchaseOrderResources", description = "Endpoint for purchase order")
public class PurchaseOrderResources {

    public @Autowired PurchaseOrderService purchaseOrderService;
    public @Autowired UserService userService;


    @GetMapping("/{id}")
    @Operation(summary = "Get purchase order data by id.", tags = { "PurchaseOrderResources" })
    public ResponseEntity<JsonEnvelop<PurchaseOrderDTO>> getPurchaseOrderById(
            @Parameter(description = "Purchase Order ID", required=true)
            @PathVariable("id") String id){

        if(id == null) throw new FaultException(HttpStatus.UNPROCESSABLE_ENTITY, "Purchase ID was not informed.");

        PurchaseOrderDTO purchaseOrderDTO = purchaseOrderService.getPurchaseOrderById(id);

        return new JsonEnvelop.Builder<PurchaseOrderDTO>()
                .statusAndStatusCode(HttpStatus.OK)
                .data(purchaseOrderDTO)
                .build()
                .response();
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get purchase order data by User ID.", tags = { "PurchaseOrderResources" })
    public ResponseEntity<JsonEnvelop<List<PurchaseOrderDTO>>> getPurchaseOrderByUserId(
            @Parameter(description = "User ID", required=true)
            @PathVariable("userId") String userId){

        if(userId == null) throw new FaultException(HttpStatus.UNPROCESSABLE_ENTITY, "User ID was not informed.");

        if(userService.getUserById(userId) == null) throw new FaultException(HttpStatus.UNPROCESSABLE_ENTITY, "User not found.");

        List<PurchaseOrderDTO> purchaseOrderDTOs = purchaseOrderService.getPurchaseOrderByUserId(userId);

        return new JsonEnvelop.Builder<List<PurchaseOrderDTO>>()
                .statusAndStatusCode(HttpStatus.OK)
                .data(purchaseOrderDTOs)
                .build()
                .response();
    }


    @PostMapping
    @Operation(summary = "Save the purchase.", tags = { "PurchaseOrderResources" })
    public ResponseEntity<JsonEnvelop<PurchaseOrderDTO>> savePurchase(
            @Parameter(description = "Data to make the purchase.", required=true)
            @RequestBody PurchaseOrderDTO purchaseOrder){

        PurchaseOrderDTO purchaseOrderDTO = purchaseOrderService.savePurchaseOrder(purchaseOrder);

        return new JsonEnvelop.Builder<PurchaseOrderDTO>()
                .statusAndStatusCode(HttpStatus.OK)
                .data(purchaseOrderDTO)
                .build()
                .response();
    }
}
