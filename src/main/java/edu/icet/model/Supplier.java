package edu.icet.model;

import edu.icet.entity.ItemEntity;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {
    private String id;
    private String name;
    private String email;
    private String mobile;
    private String company;
}
