package com.argo.assessmentspring.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "product_tbl")
public
class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String skuCode;
    private BigDecimal unitPrice;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "product")
    private Set<OrderLine> orderLines = new HashSet<>();

    Product() {
    }

    public Product(String name, String skuCode, BigDecimal unitPrice) {
        this.name = name;
        this.skuCode = skuCode;
        this.unitPrice = unitPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getId() {
        return id;
    }

    @JsonManagedReference
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

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Product))
            return false;
        Product other = (Product) o;
        return Objects.equals(id, other.id)
                && Objects.equals(name, other.name)
                && Objects.equals(skuCode, other.skuCode)
                && Objects.equals(unitPrice, other.unitPrice)
                && Objects.equals(orderLines, other.orderLines);
    }
}
