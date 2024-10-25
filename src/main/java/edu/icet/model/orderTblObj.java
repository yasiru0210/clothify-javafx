package edu.icet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@Getter
@Setter
public class orderTblObj {
    private String id;
    private LocalDate date;
    private Double netTotal;
    private String custID;
}
