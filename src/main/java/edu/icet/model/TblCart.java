package edu.icet.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TblCart {
    private String itemCode;
    private String itemName;
    private Integer qty;
    private Double unitPrice;
    private Double total;
}
