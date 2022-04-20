package com.argo.assessmentspring.services;

import com.argo.assessmentspring.models.Customer;
import com.argo.assessmentspring.repositories.OrderRepository;
import com.argo.assessmentspring.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;
    @Autowired
    private CustomerService customerService;

    public Order saveOrder(Order order, long id) {
        Customer customer = customerService.getCustomerById(id);

        order.setCustomer(customer);
        order.setSubmissionDate(LocalDate.now());

        return repository.save(order);
    }

    public List<Order> saveOrders(List<Order> orders) {
        return repository.saveAll(orders);
    }

    public List<Order> getOrders(String orderBy, String date) {

        if (orderBy != null && date == null) {
            if (orderBy.equalsIgnoreCase("asc")) {
                return repository.findByOrderBySubmissionDateAsc();
            } else if(orderBy.equalsIgnoreCase("desc")){
                return repository.findByOrderBySubmissionDateDesc();
            }
        }
        else if(date != null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate localDate = LocalDate.parse(date, formatter);

            return repository.findBySubmissionDate(localDate);
        }
        return repository.findAll();
    }

    public Order getOrderById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public String deleteOrder(Long id) {
        repository.deleteById(id);
        return "Order removed !! " + id;
    }

    public Order updateOrder(Order order) {
        Order existingOrder = repository.findById(order.getId()).orElse(null);
        if (existingOrder == null) {
            return null;
        }
        existingOrder.setOrderLines(order.getOrderLines());
        existingOrder.setSubmissionDate(order.getSubmissionDate());
        existingOrder.setCustomer(order.getCustomer());
        return repository.save(existingOrder);
    }

    public List<Order> getOrderByProductIdJPQL(Long id){
        return repository.findOrderByProductId(id);
    }

    public List<Order> getOrderByProductId(Long id){
        return repository.findByOrderLines_Product_Id(id);
    }

    public List<Order> getOrdersByCustomerId(Long id){
        return repository.findByCustomer_Id(id);
    }

    public List<Order> getOrdersByCustomerIdJPQL(Long id){
        return repository.findOrderByCustomerId(id);
    }
}
