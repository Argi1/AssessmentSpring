package com.argo.assessmentspring;

import com.argo.assessmentspring.models.Customer;
import com.argo.assessmentspring.models.Order;
import com.argo.assessmentspring.models.OrderLine;
import com.argo.assessmentspring.models.Product;
import com.argo.assessmentspring.repositories.CustomerRepository;
import com.argo.assessmentspring.repositories.OrderLineRepository;
import com.argo.assessmentspring.repositories.OrderRepository;
import com.argo.assessmentspring.repositories.ProductRepository;
import com.argo.assessmentspring.services.CustomerService;
import com.argo.assessmentspring.services.OrderLineService;
import com.argo.assessmentspring.services.OrderService;
import com.argo.assessmentspring.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ServiceUnitTest {

    @MockBean
    CustomerRepository customerRepository;
    @MockBean
    OrderRepository orderRepository;
    @MockBean
    OrderLineRepository orderLineRepository;
    @MockBean
    ProductRepository productRepository;

    @Autowired
    CustomerService customerService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderLineService orderLineService;
    @Autowired
    ProductService productService;

    private Product tvProduct = new Product("TV", "25", new BigDecimal(2500));
    private Product samsungProduct = new Product("Samsung S20", "2876", new BigDecimal(999));
    private Product psProduct = new Product("Playstation 4", "78564", new BigDecimal(699));

    private OrderLine orderLine1 = new OrderLine(tvProduct, 2);
    private OrderLine orderLine2 = new OrderLine(samsungProduct, 1);
    private OrderLine orderLine3 = new OrderLine(psProduct, 5);

    private Customer argo = new Customer("Argo Pent", "pent@gmail.com", "55556612", "54");
    private Customer john = new Customer("John Doe", "johndoe@gmail.com", "514245661", "55");

    private Order argoOrder = new Order(LocalDate.of(2022, 4, 10), argo, new HashSet<>(Arrays.asList(orderLine1, orderLine2)));
    private Order johnOrder = new Order(LocalDate.of(2022, 4, 15), john, new HashSet<>(List.of(orderLine3)));

    @Test
    public void getAllOrders_success() throws Exception {
        List<Order> orders = new ArrayList<>(Arrays.asList(argoOrder, johnOrder));

        when(orderRepository.findAll()).thenReturn(orders);
        when(orderRepository.findByOrderBySubmissionDateDesc()).thenReturn(orders);
        when(orderRepository.findByOrderBySubmissionDateAsc()).thenReturn(orders);
        when(orderRepository.findBySubmissionDate(any(LocalDate.class))).thenReturn(List.of(johnOrder));

        List<Order> serviceOrders = orderService.getOrders(null, null);
        List<Order> serviceOrdersOrderedAsc = orderService.getOrders("asc", null);
        List<Order> serviceOrdersOrderedDesc = orderService.getOrders("desc", null);
        List<Order> serviceOrdersWithDate = orderService.getOrders(null, "15-04-2022");

        assertEquals(serviceOrders, orders);
        assertEquals(serviceOrdersOrderedAsc, orders);
        assertEquals(serviceOrdersOrderedDesc, orders);
        assertEquals(serviceOrdersWithDate, List.of(johnOrder));
    }

    @Test
    public void getOrderByProduct_success() throws Exception {
        List<Order> orders = new ArrayList<>(List.of(argoOrder));

        when(orderRepository.findOrderByProductId(any(Long.class))).thenReturn(orders);
        when(orderRepository.findByOrderLines_Product_Id(any(Long.class))).thenReturn(orders);

        List<Order> serviceOrder = orderService.getOrderByProductId(1L);
        List<Order> serviceOrderJPQL = orderService.getOrderByProductIdJPQL(2L);

        assertEquals(serviceOrder, orders);
        assertEquals(serviceOrderJPQL, orders);
    }

    @Test
    public void getOrderByCustomer_success() throws Exception {
        List<Order> orders = new ArrayList<>(List.of(argoOrder));

        when(orderRepository.findOrderByCustomerId(any(Long.class))).thenReturn(orders);
        when(orderRepository.findByCustomer_Id(any(Long.class))).thenReturn(orders);

        List<Order> serviceOrder = orderService.getOrdersByCustomerId(1L);
        List<Order> serviceOrderJPQL = orderService.getOrdersByCustomerIdJPQL(2L);

        assertEquals(serviceOrder, orders);
        assertEquals(serviceOrderJPQL, orders);
    }

    @Test
    public void createCustomer_success() throws Exception {
        when(customerRepository.save(any(Customer.class))).thenReturn(argo);

        Customer createdCustomer = customerService.saveCustomer(argo);

        assertEquals(createdCustomer, argo);
    }

    @Test
    public void createProduct_success() throws Exception {
        when(productRepository.save(any(Product.class))).thenReturn(tvProduct);

        Product createdProduct = productService.saveProduct(tvProduct);

        assertEquals(createdProduct, tvProduct);
    }

    @Test
    public void createOrder_success() throws Exception {
        when(orderRepository.save(any(Order.class))).thenReturn(argoOrder);

        Order createdOrder = orderService.saveOrder(argoOrder, 1L);

        assertEquals(argoOrder, createdOrder);
    }

    @Test
    public void updateOrderLine_success() throws Exception {
        OrderLine updatedOrderLine = new OrderLine(tvProduct, 4);
        updatedOrderLine.setId(1L);

        when(orderLineRepository.save(any(OrderLine.class))).thenReturn(updatedOrderLine);
        when(orderLineRepository.findById(any(Long.class))).thenReturn(java.util.Optional.of(orderLine1));

        OrderLine createdOrderLine = orderLineService.updateOrderLine(updatedOrderLine);

        assertEquals(createdOrderLine, updatedOrderLine);
    }
}
