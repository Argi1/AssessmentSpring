package com.argo.assessmentspring.repositories;

import com.argo.assessmentspring.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrderBySubmissionDateAsc();

    List<Order> findByOrderBySubmissionDateDesc();

    List<Order> findBySubmissionDate(LocalDate submissionDate);

    List<Order> findByOrderLines_Product_Id(Long id);

    List<Order> findByCustomer_Id(Long id);

    @Query("select o from Order o join o.orderLines oL where oL.product.id = ?1")
    List<Order> findOrderByProductId(Long id);

    @Query("select o from Order o where o.customer.id = ?1")
    List<Order> findOrderByCustomerId(Long id);
}
