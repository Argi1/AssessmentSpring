package com.argo.assessmentspring.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "order_tbl")
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate submissionDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Set<OrderLine> orderLines = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    public Order() {
    }

    public Order(LocalDate submissionDate, Customer customer, Set<OrderLine> orderLines) {
        this.submissionDate = submissionDate;
        this.customer = customer;
        this.orderLines = orderLines;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Long getId() {
        return id;
    }

    public Set<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(Set<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public void addOrderLine(OrderLine line) {
        if (line != null) {
            orderLines.add(line);
        }
    }

    @JsonBackReference
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customer.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Order))
            return false;
        Order other = (Order) o;
        return Objects.equals(id, other.id)
                && Objects.equals(submissionDate, other.submissionDate)
                && Objects.equals(orderLines, other.orderLines)
                && Objects.equals(customer, other.customer);
    }
}
