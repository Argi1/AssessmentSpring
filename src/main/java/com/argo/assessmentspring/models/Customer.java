package com.argo.assessmentspring.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "customer_tbl")
public
class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String telephone;
    private String registrationCode;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private Set<Order> orders = new HashSet<>();

    public Customer() {
    }

    public Customer(String fullName, String email, String telephone, String registrationCode) {
        this.fullName = fullName;
        this.email = email;
        this.telephone = telephone;
        this.registrationCode = registrationCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    public Long getId() {
        return id;
    }

    @JsonManagedReference
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order){
        if(order != null){
            orders.add(order);
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Customer))
            return false;
        Customer other = (Customer) o;
        return Objects.equals(id, other.id)
                && Objects.equals(fullName, other.fullName)
                && Objects.equals(email, other.email)
                && Objects.equals(telephone, other.telephone)
                && Objects.equals(registrationCode, other.registrationCode)
                && Objects.equals(orders, other.orders);
    }
}
