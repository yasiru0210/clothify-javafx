package edu.icet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Item {
    private String id;
    private String name;
    private Integer unitPrice;
    private Integer qty;
    private String category;
    private String size;
    private Supplier supplier;
}
