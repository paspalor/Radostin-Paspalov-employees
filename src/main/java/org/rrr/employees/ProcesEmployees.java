package org.rrr.employees;

import org.rrr.employees.dto.CoupleEmployeeTogether;
import org.rrr.employees.dto.EmployeeRecordDto;

import java.time.LocalDate;
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
                employeeRecordDtoList.stream()
                        .map(EmployeeRecordDto::getEmpID)
                        .forEach(el -> {
                            System.out.print(el + ", ");
                        });
                System.out.println(projectID);
            });
        }
    }


    public void processTogetherAndSameTime(String file) throws Exception {
        employeesFileOperations = new EmployeesFileOperations();
        List<EmployeeRecordDto> employeeRecordDtoList = employeesFileOperations.readFile(file);
        Map<Long, List<EmployeeRecordDto>> applicationEmployees = new HashMap<>();

        // find list projects
        Set<Long> projectList = employeeRecordDtoList.stream().map(EmployeeRecordDto::getProjectID).collect(Collectors.toSet());
        projectList.forEach((projectID) -> {
            try {
                List<EmployeeRecordDto> employeesForProject = employeeRecordDtoList.stream()
                        .filter(o -> o.getProjectID() == projectID)
                        .collect(Collectors.toList());

                calcEmployeesForProject(projectID, employeesForProject);
            } catch (Exception e) {
                System.out.println("Error process data: " + e.getMessage());
            }
        });

        displayApplicationEmployees(applicationEmployees);
    }

    private void calcEmployeesForProject(Long projectID, List<EmployeeRecordDto> employeesForProject) {
        int listSize = employeesForProject.size();
        EmployeeRecordDto employe1;
        EmployeeRecordDto employe2;
        List<CoupleEmployeeTogether> coupleEmployeeTogetherList = new ArrayList<>();

        // System.out.println(projectID);
        // Combinations from 1-st to last el without duplicate. Yes I am using i,j :)
        for (int i = 0; i < listSize - 1; i++) {
            employe1 = employeesForProject.get(i);

            for (int j = i + 1; j < listSize; j++) {
                employe2 = employeesForProject.get(j);

                LocalDate dateFrom = employe1.getDateFrom().isAfter(employe2.getDateFrom())
                        ? employe1.getDateFrom()
                        : employe2.getDateFrom();
                LocalDate dateTo = employe1.getDateTo().isBefore(employe2.getDateTo())
                        ? employe1.getDateTo()
                        : employe2.getDateTo();

                long daysTogether = ChronoUnit.DAYS.between(dateFrom, dateTo);
                if (daysTogether > 0) {
                    coupleEmployeeTogetherList.add(new CoupleEmployeeTogether(employe1.getEmpID(), employe2.getEmpID(), daysTogether));
                    coupleEmployeeTogetherList = coupleEmployeeTogetherList.stream()
                            .sorted(compareByDaysTogether())
                            .limit(1)
                            .collect(Collectors.toList());

                } //else {
                //     System.out.println("No work together on same time");
                // }
            }
        }

        coupleEmployeeTogetherList.forEach(el -> {
            System.out.print(el.getEmployee1() + ", " + el.getEmployee2() + ", "); // + ", ### " + el.getDaysTogether()
        });
        System.out.println(projectID);
    }

    public static Comparator<CoupleEmployeeTogether> compareByDaysTogether() {
        return Comparator.comparing(CoupleEmployeeTogether::getDaysTogether, Comparator.reverseOrder());
    }

}
