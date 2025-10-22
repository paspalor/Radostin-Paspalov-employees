package org.rrr.employees.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateValidator { // implements DateValidatorInterface if spring

    // For different date format
    private static final DateTimeFormatter formatterDMY = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    // ......
    private static final DateTimeFormatter formatterYMD = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public LocalDate pareseDate(String dateIn) {
        return LocalDate.parse(dateIn, formatterYMD);
    }
}
