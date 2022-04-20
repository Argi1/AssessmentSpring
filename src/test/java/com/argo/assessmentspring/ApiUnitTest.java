package com.argo.assessmentspring;

import com.argo.assessmentspring.models.Customer;
import com.argo.assessmentspring.models.Order;
import com.argo.assessmentspring.models.OrderLine;
import com.argo.assessmentspring.models.Product;
import com.argo.assessmentspring.services.CustomerService;
import com.argo.assessmentspring.services.OrderLineService;
import com.argo.assessmentspring.services.OrderService;
import com.argo.assessmentspring.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApiController.class)
@AutoConfigureMockMvc
public class ApiUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;
    @MockBean
    private CustomerService customerService;
    @MockBean
    private OrderLineService orderLineService;
    @MockBean
    private ProductService productService;

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

        when(orderService.getOrders(null, null)).thenReturn(orders);

        mockMvc.perform(get("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].submissionDate").value(argoOrder.getSubmissionDate().toString()));
    }

    @Test
    public void getOrdersByProduct_success() throws Exception {
        List<Order> orders = new ArrayList<>(List.of(argoOrder));

        when(orderService.getOrderByProductId(any(Long.class))).thenReturn(orders);
        when(orderService.getOrderByProductIdJPQL(any(Long.class))).thenReturn(orders);

        mockMvc.perform(get("/api/orders/products/1/JPQL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        mockMvc.perform(get("/api/orders/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void getOrdersByCustomer_success() throws Exception {
        List<Order> orders = new ArrayList<>(List.of(argoOrder));

        when(orderService.getOrdersByCustomerId(any(Long.class))).thenReturn(orders);
        when(orderService.getOrdersByCustomerIdJPQL(any(Long.class))).thenReturn(orders);

        mockMvc.perform(get("/api/orders/customers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        mockMvc.perform(get("/api/orders/customers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void createCustomer_success() throws Exception {

        when(customerService.saveCustomer(any(Customer.class))).thenReturn(argo);

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{\"fullName\":\"Argo Pent\", \"email\": \"pent@gmail.com\", \"telephone\": \"55556612\", \"registrationCode\":\"54\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.fullName").value(argo.getFullName()))
                .andExpect(jsonPath("$.email").value(argo.getEmail()))
                .andExpect(jsonPath("$.telephone").value(argo.getTelephone()))
                .andExpect(jsonPath("$.registrationCode").value(argo.getRegistrationCode()));
    }

    @Test
    public void createProduct_success() throws Exception {
        when(productService.saveProduct(any(Product.class))).thenReturn(tvProduct);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"TV\", \"skuCode\": \"25\", \"unitPrice\": \"2500\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name").value(tvProduct.getName()))
                .andExpect(jsonPath("$.skuCode").value(tvProduct.getSkuCode()))
                .andExpect(jsonPath("$.unitPrice").value(tvProduct.getUnitPrice()));
    }

    @Test
    public void createOrder_success() throws Exception {
        johnOrder.setSubmissionDate(LocalDate.now());
        when(orderService.saveOrder(any(Order.class), any(Long.class))).thenReturn(johnOrder);

        mockMvc.perform(post("/api/customers/1/addOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{\"orderLines\":[{\"quantity\": 5, \"product\":{\"id\":3}}]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.submissionDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.orderLines", hasSize(1)))
                .andExpect(jsonPath("$.orderLines.[0].quantity").value(orderLine3.getQuantity()))
                .andExpect(jsonPath("$.orderLines.[0].productId").value(orderLine3.getProduct().getId()));
    }

    @Test
    public void updateOrderLine_success() throws Exception {
        OrderLine testOrderLine = new OrderLine(tvProduct, 4);
        when(orderLineService.updateOrderLine(any(OrderLine.class))).thenReturn(testOrderLine);

        mockMvc.perform(put("/api/orderlines/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{\"orderLines\":{ \"quantity\": 4 }}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.quantity").value(testOrderLine.getQuantity()));
    }

}
