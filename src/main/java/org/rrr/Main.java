package org.rrr;

import org.rrr.employees.ProcesEmployees;


public class Main {
    public static void main(String[] args) {

        System.out.println("Hello and welcome!");

        try {
            ProcesEmployees procesEmployees = new ProcesEmployees();
            System.out.println("----------------- 2 employees with longest work on project -----------------------");
            procesEmployees.process("/testData.cvs");
            System.out.println();
            System.out.println("----------------- 2 employees with longest work on project - same time -----------------------");
            System.out.println();
            procesEmployees.processTogetherAndSameTime("/testData.cvs");
        } catch (Exception e) {
            System.out.println("Error processing file! : " + e.getMessage());
        }

        System.out.println("   ---   END   ---");
        System.out.println();
    }
}
