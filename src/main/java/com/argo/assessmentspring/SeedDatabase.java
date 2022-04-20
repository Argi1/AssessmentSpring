package com.argo.assessmentspring;

import com.argo.assessmentspring.models.Customer;
import com.argo.assessmentspring.models.Order;
import com.argo.assessmentspring.models.OrderLine;
import com.argo.assessmentspring.models.Product;
import com.argo.assessmentspring.repositories.CustomerRepository;
import com.argo.assessmentspring.repositories.OrderLineRepository;
import com.argo.assessmentspring.repositories.OrderRepository;
import com.argo.assessmentspring.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

@Configuration
class SeedDatabase {

    private static final Logger log = LoggerFactory.getLogger(SeedDatabase.class);

    @Bean
    CommandLineRunner initDatabase(OrderRepository orderRepository, CustomerRepository customerRepository, OrderLineRepository orderLineRepository, ProductRepository productRepository) {

        return args -> {

            Product tvProduct = new Product("TV", "25", new BigDecimal(2500));
            Product samsungProduct = new Product("Samsung S20", "2876", new BigDecimal(999));
            Product psProduct = new Product("Playstation 4", "78564", new BigDecimal(699));
            Product lenovoProduct = new Product("Lenovo Laptop", "2", new BigDecimal(720));

            OrderLine orderLine1 = new OrderLine(tvProduct, 2);
            OrderLine orderLine2 = new OrderLine(samsungProduct, 1);
            OrderLine orderLine3 = new OrderLine(lenovoProduct, 6);

            OrderLine orderLine4 = new OrderLine(tvProduct, 21);
            OrderLine orderLine5 = new OrderLine(samsungProduct, 11);
            OrderLine orderLine6 = new OrderLine(psProduct, 61);
            OrderLine orderLine7 = new OrderLine(psProduct, 71);

            OrderLine orderLine8 = new OrderLine(tvProduct, 22);
            OrderLine orderLine9 = new OrderLine(tvProduct, 12);
            OrderLine orderLine10 = new OrderLine(psProduct, 62);
            OrderLine orderLine11 = new OrderLine(psProduct, 82);

            OrderLine orderLine12 = new OrderLine(tvProduct, 23);
            OrderLine orderLine13 = new OrderLine(samsungProduct, 13);
            OrderLine orderLine14 = new OrderLine(psProduct, 63);
            OrderLine orderLine15 = new OrderLine(lenovoProduct, 63);


            Customer argo = new Customer("Argo Pent", "pent@gmail.com", "55556612", "54");
            Customer john = new Customer("John Doe", "johndoe@gmail.com", "514245661", "55");
            Customer jaanus = new Customer("Jaanus Tamm", "JaanusTamm@gmail.com", "57665234", "56");

            Order argoOrder1 = new Order(LocalDate.now(), argo, new HashSet<>(Arrays.asList(orderLine1, orderLine2, orderLine3)));

            Order argoOrder2 = new Order(LocalDate.now(), argo, new HashSet<>(Arrays.asList(orderLine4, orderLine5, orderLine6, orderLine7)));

            Order johnOrder1 = new Order(LocalDate.now(), john, new HashSet<>(Arrays.asList(orderLine8, orderLine9, orderLine10, orderLine11)));

            Order jaanusOrder1 = new Order(LocalDate.now(), jaanus, new HashSet<>(Arrays.asList(orderLine12, orderLine13, orderLine14, orderLine15)));

            log.info("Preloading " + productRepository.save(tvProduct));
            log.info("Preloading " + productRepository.save(samsungProduct));
            log.info("Preloading " + productRepository.save(psProduct));
            log.info("Preloading " + productRepository.save(lenovoProduct));

            log.info("Preloading " + customerRepository.save(argo));
            log.info("Preloading " + customerRepository.save(john));
            log.info("Preloading " + customerRepository.save(jaanus));

            log.info("Preloading " + orderRepository.save(argoOrder1));
            log.info("Preloading " + orderRepository.save(argoOrder2));
            log.info("Preloading " + orderRepository.save(jaanusOrder1));
            log.info("Preloading " + orderRepository.save(johnOrder1));
        };
    }
}