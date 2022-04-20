package com.argo.assessmentspring;

import com.argo.assessmentspring.models.Customer;
import com.argo.assessmentspring.models.Order;
import com.argo.assessmentspring.models.OrderLine;
import com.argo.assessmentspring.models.Product;
import com.argo.assessmentspring.repositories.CustomerRepository;
import com.argo.assessmentspring.repositories.OrderRepository;
import com.argo.assessmentspring.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class RepositoryUnitTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;

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

    @BeforeEach
    void initData() {
        entityManager.persistAndFlush(argo);
        entityManager.persistAndFlush(john);

        entityManager.persistAndFlush(tvProduct);
        entityManager.persistAndFlush(samsungProduct);
        entityManager.persistAndFlush(psProduct);

        entityManager.persistAndFlush(argoOrder);
        entityManager.persistAndFlush(johnOrder);
    }

    @Test
    public void getAllOrders_success() throws Exception {
        List<Order> ordersAsc = new ArrayList<>(List.of(argoOrder, johnOrder));
        List<Order> ordersDesc = new ArrayList<>(List.of(johnOrder, argoOrder));

        assertEquals(orderRepository.findByOrderBySubmissionDateAsc(), ordersAsc);
        assertEquals(orderRepository.findByOrderBySubmissionDateDesc(), ordersDesc);
        assertEquals(orderRepository.findBySubmissionDate(LocalDate.parse("2022-04-15")), List.of(johnOrder));
    }

    @Test
    public void getOrdersByProduct_success() throws Exception {
        assertEquals(orderRepository.findOrderByProductId(tvProduct.getId()), List.of(argoOrder));
        assertEquals(orderRepository.findByOrderLines_Product_Id(psProduct.getId()), List.of(johnOrder));
    }

    @Test
    public void getOrdersByCustomer_success() throws Exception {
        assertEquals(orderRepository.findByCustomer_Id(argo.getId()), List.of(argoOrder));
        assertEquals(orderRepository.findOrderByCustomerId(john.getId()), List.of(johnOrder));
    }

    @Test
    public void createCustomer_success() throws Exception {
        Customer doe = entityManager.persistAndFlush(new Customer("Doe James", "jamesDoe@gmail.com", "51324112", "072"));

        assertEquals(java.util.Optional.of(doe), customerRepository.findById(doe.getId()));
    }

    @Test
    public void createProduct_success() throws Exception {
        Product laptop = entityManager.persistAndFlush(new Product("HP Laptop", "2414", new BigDecimal(700)));

        assertEquals(laptop, productRepository.getById(laptop.getId()));
    }

    @Test
    public void createOrder_success() throws Exception {
        Order order = entityManager.persistAndFlush(new Order(LocalDate.of(2022, 4, 19), john, new HashSet<>(List.of(orderLine1, orderLine2, orderLine3))));

        assertEquals(order, orderRepository.getById(order.getId()));
    }
}
