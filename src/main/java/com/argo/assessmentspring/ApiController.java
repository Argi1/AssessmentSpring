package com.argo.assessmentspring;

import com.argo.assessmentspring.models.Customer;
import com.argo.assessmentspring.models.OrderLine;
import com.argo.assessmentspring.models.Product;
import com.argo.assessmentspring.services.CustomerService;
import com.argo.assessmentspring.services.OrderLineService;
import com.argo.assessmentspring.services.OrderService;
import com.argo.assessmentspring.models.Order;
import com.argo.assessmentspring.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
public class ApiController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderLineService orderLineService;

    @GetMapping("/api/orders")
    public List<Order> getOrders(@RequestParam(required = false) String orderBy, @RequestParam(required = false) String date) {
        return orderService.getOrders(orderBy, date);
    }

    @GetMapping("/api/orders/products/{id}/JPQL")
    public List<Order> getOrderByProductIdJPQL(@PathVariable Long id) {
        return orderService.getOrderByProductIdJPQL(id);
    }

    @GetMapping("/api/orderlines/products/{id}/JPQL")
    public List<OrderLine> getOrderLinesByProductIdJPQL(@PathVariable Long id) {
        return orderLineService.getOrderLinesByProductIdJPQL(id);
    }

    @GetMapping("/api/orders/products/{id}")
    public List<Order> getOrderByProductId(@PathVariable Long id) {
        return orderService.getOrderByProductId(id);
    }

    @GetMapping("/api/orderlines/products/{id}")
    public List<OrderLine> getOrderLinesByProductId(@PathVariable Long id) {
        return orderLineService.getOrderLinesByProductId(id);
    }

    @GetMapping("/api/orders/customers/{id}")
    public List<Order> getOrdersByCustomerId(@PathVariable Long id) {
        return orderService.getOrdersByCustomerId(id);
    }

    @GetMapping("/api/orders/customers/{id}/JPQL")
    public List<Order> getOrdersByCustomerIdJPQL(@PathVariable Long id) {
        return orderService.getOrdersByCustomerIdJPQL(id);
    }

    @PostMapping("/api/customers/{id}/addOrder")
    public Order addOrder(@RequestBody Order order, @PathVariable Long id) {
        return orderService.saveOrder(order, id);
    }

    @PostMapping("/api/customers")
    public Customer addCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @PostMapping("/api/products")
    public Product addProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @PutMapping("/api/orderlines/{id}")
    public OrderLine updateOrderLineProductQuantity(@RequestBody OrderLine orderLine, @PathVariable Long id) {
        orderLine.setId(id);
        return orderLineService.updateOrderLine(orderLine);
    }
}
