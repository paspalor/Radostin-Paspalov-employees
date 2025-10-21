package org.rrr;

import org.rrr.employees.ProcesEmployees;


public class Main {
    public static void main(String[] args) {

        System.out.println("Hello and welcome!");

        try {
            ProcesEmployees procesEmployees = new ProcesEmployees();
            procesEmployees.process("/testData.cvs");
        } catch (Exception e) {
            System.out.println("Error processing file! : " + e.getMessage());
        }

        System.out.println("   ---   END   ---");
        System.out.println();
    }
}
