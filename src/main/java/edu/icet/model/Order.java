package edu.icet.model;

import edu.icet.entity.CustomerEntity;
import edu.icet.entity.OrderDetailEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {
    private String id;
    private Customer customer;
    private LocalDate date;
    private Double netTotal;
}
