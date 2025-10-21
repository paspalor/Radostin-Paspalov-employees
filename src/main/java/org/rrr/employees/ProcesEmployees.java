package org.rrr.employees;

import org.rrr.employees.dto.EmployeeRecordDto;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class ProcesEmployees {

    // could be injected if spring
    private EmployeesFileOperations employeesFileOperations;


    public void process(String file) throws Exception {
        employeesFileOperations = new EmployeesFileOperations();
        List<EmployeeRecordDto> employeeRecordDtoList = employeesFileOperations.readFile(file);
        Map<Long, List<EmployeeRecordDto>> applicationEmployees = new HashMap<>();

        // find list projects
        Set<Long> projectList = employeeRecordDtoList.stream().map(EmployeeRecordDto::getProjectID).collect(Collectors.toSet());
        projectList.forEach((projectID) -> {
            try {
                List<EmployeeRecordDto> employeeRecordDtoForProject = employeeRecordDtoList.stream()
                        .filter(o -> o.getDateFrom() != null)
                        .filter(o -> o.getProjectID() == projectID)
                        .sorted(compareByDuration())
                        .limit(2)
                        .collect(Collectors.toList());
                applicationEmployees.put(projectID, employeeRecordDtoForProject);
            } catch (Exception e) {
                System.out.println("Error process data: " + e.getMessage());
            }
        });

        displayApplicationEmployees(applicationEmployees);
    }

    public static Comparator<EmployeeRecordDto> compareByDuration() {
        return Comparator.comparing(obj -> ChronoUnit.DAYS.between(obj.getDateFrom(), obj.getDateTo()), Comparator.reverseOrder());
    }

    private void displayApplicationEmployees(Map<Long, List<EmployeeRecordDto>> applicationEmployees) {
        if (applicationEmployees != null) {
            applicationEmployees.forEach((projectID, employeeRecordDtoList) -> {
                employeeRecordDtoList.stream().map(EmployeeRecordDto::getEmpID).forEach(el -> {
                    System.out.print(el + ", ");
                });
                System.out.println(projectID);
            });
        }
    }

}
