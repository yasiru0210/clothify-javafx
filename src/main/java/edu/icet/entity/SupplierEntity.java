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
@Entity(name = "supplier")
@Table(name = "supplier")
public class SupplierEntity {
    @Id
    private String id;
    private String name;
    private String email;
    private String mobile;
    private String company;
}
