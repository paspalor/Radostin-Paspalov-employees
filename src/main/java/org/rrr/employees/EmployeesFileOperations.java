package org.rrr.employees;

import lombok.extern.java.Log;
import org.rrr.Main;
import org.rrr.employees.dto.EmployeeRecordDto;
import org.rrr.employees.utils.EmployeeRecordUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@Log
public class EmployeesFileOperations {

    public List<EmployeeRecordDto> readFile(String file) throws Exception {
        Path filePath = findFilePath(file);
        List<EmployeeRecordDto> employeeRecords = new ArrayList<>();
        int lineNum = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                employeeRecords.add(EmployeeRecordUtils.parseLine(line));
                lineNum++;
            }
        } catch (IOException e) {
            log.warning("Error in line " + lineNum + ", msg: " + e.getMessage());
        }
        return employeeRecords;
    }

    private Path findFilePath(String file) throws Exception {
        URL resource = Main.class.getResource(file);
        if (resource == null) {
            throw new Exception("File not found!");
        }
        return Paths.get(resource.toURI());
    }
}
