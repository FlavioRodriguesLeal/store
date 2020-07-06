package br.com.service.store.services;

import br.com.service.store.dtos.PurchaseOrderDTO;
import br.com.service.store.models.PurchaseOrder;
import br.com.service.store.respositories.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseOrderService {

    public @Autowired PurchaseOrderRepository purchaseOrderRepository;

    public PurchaseOrderDTO getPurchaseOrderById(String id) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id).orElse(null);
        return PurchaseOrderDTO.fromModel(purchaseOrder);
    }

    public List<PurchaseOrderDTO> getPurchaseOrderByUserId(String userId) {
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findByUserId(userId).orElse(null);
        return PurchaseOrderDTO.fromModel(purchaseOrders);
    }

    public PurchaseOrderDTO savePurchaseOrder(PurchaseOrderDTO purchaseOrderDTO) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.save(PurchaseOrderDTO.toModel(purchaseOrderDTO));
        return PurchaseOrderDTO.fromModel(purchaseOrder);
    }
}
