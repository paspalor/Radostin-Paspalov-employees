package org.rrr.employees.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CoupleEmployeeTogether {
    private long employee1;
    private long employee2;
    private long daysTogether;
}
