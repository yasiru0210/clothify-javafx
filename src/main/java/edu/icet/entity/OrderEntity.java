package edu.icet.entity;

import edu.icet.model.OrderDetail;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "orders")
@Table(name = "orders")
public class OrderEntity {
    @Id
    private String id;
    // Many-to-one relationship
    @ManyToOne
    @JoinColumn(name = "customer_id") //  foreign key column
    private CustomerEntity customer;
    private LocalDate date;
    private Double netTotal;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<OrderDetailEntity> orderDetails =new ArrayList<>();
}
