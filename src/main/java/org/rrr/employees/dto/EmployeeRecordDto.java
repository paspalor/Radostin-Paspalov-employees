package org.rrr.employees.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeRecordDto {
    private long empID;
    private long projectID;
    private LocalDate dateFrom;
    private LocalDate dateTo;
}
