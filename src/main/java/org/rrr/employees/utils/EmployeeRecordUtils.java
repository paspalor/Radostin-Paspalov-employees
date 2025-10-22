package org.rrr.employees.utils;

import org.rrr.employees.dto.EmployeeRecordDto;

import java.time.LocalDate;

public class EmployeeRecordUtils {
    private static final String csvSeparator = ",";

    // if spring will be injected, not created on place and not static of course
    private final static DateValidator dateValidator = new DateValidator();

    public static EmployeeRecordDto parseLine(String fileLine) {
        String[] values = fileLine.split(csvSeparator);
        EmployeeRecordDto employeeRecordDto = new EmployeeRecordDto();
        employeeRecordDto.setEmpID(Long.parseLong(values[0].trim()));
        employeeRecordDto.setProjectID(Long.parseLong(values[1].trim()));
        employeeRecordDto.setDateFrom(LocalDate.parse(values[2].trim()));
        if (values.length < 4 || values[3] == null || values[3].trim().isEmpty() || values[3].trim().equalsIgnoreCase("null")) {
            employeeRecordDto.setDateTo(LocalDate.now());
        } else {
            employeeRecordDto.setDateTo(dateValidator.pareseDate(values[3].trim()));
        }
        return employeeRecordDto;
    }
}
