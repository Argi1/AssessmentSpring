package com.argo.assessmentspring.services;

import com.argo.assessmentspring.models.OrderLine;
import com.argo.assessmentspring.repositories.OrderLineRepository;
import com.argo.assessmentspring.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderLineService {
    @Autowired
    private OrderLineRepository repository;
    @Autowired
    private ProductRepository productRepository;

    public OrderLine saveOrderLine(OrderLine orderLine) {
        return repository.save(orderLine);
    }

    public List<OrderLine> saveOrderLines(List<OrderLine> orderLines) {
        return repository.saveAll(orderLines);
    }

    public List<OrderLine> getOrderLines() {
        return repository.findAll();
    }

    public OrderLine getOrderLineById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public String deleteOrderLine(Long id) {
        repository.deleteById(id);
        return "Order Line removed !! " + id;
    }


    public OrderLine updateOrderLine(OrderLine orderLine) {
        OrderLine existingOrderLine = repository.findById(orderLine.getId()).orElse(null);
        if (existingOrderLine == null) {
            return null;
        }
        existingOrderLine.setQuantity(orderLine.getQuantity());
        return repository.save(existingOrderLine);
    }

    public List<OrderLine> getOrderLinesByProductIdJPQL(Long id) {
        return repository.findOrderLineByProduct(id);
    }

    public List<OrderLine> getOrderLinesByProductId(Long id) {
        return repository.findByProduct_Id(id);
    }

}
