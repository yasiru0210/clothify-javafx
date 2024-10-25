package edu.icet.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "customer")
@Table(name = "customer")
public class CustomerEntity {
    @Id
    private String id;
    private String name;
    private String email;
    private String address;
}
