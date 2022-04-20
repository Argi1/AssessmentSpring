package com.argo.assessmentspring.repositories;

import com.argo.assessmentspring.models.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long>{
    @Query("select oL from OrderLine oL where oL.product.id = ?1")
    List<OrderLine> findOrderLineByProduct(Long id);

    List<OrderLine> findByProduct_Id(Long id);
}
