package com.example.spring_jooq.service;

import com.example.spring_jooq.dto.EmployeeDTO;
import com.example.spring_jooq.exception.DuplicateEmployeeException;
import com.example.spring_jooq.exception.EmployeeNotFoundException;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import java.util.List;

import com.example.spring_jooq.generated.tables.pojos.Department;

import static com.example.spring_jooq.generated.tables.Department.DEPARTMENT;
import static com.example.spring_jooq.generated.tables.Employee.EMPLOYEE;

@Service
public class EmployeeService {

    private final DSLContext dsl;

    public EmployeeService(DSLContext dsl) {
        this.dsl = dsl;
    }

    public List<EmployeeDTO> getAllEmployees() {
        return dsl.select()
                .from(EMPLOYEE)
                .join(DEPARTMENT).on(EMPLOYEE.DEPARTMENT_ID.eq(DEPARTMENT.ID))
                .fetch(record -> {
                    // Create a Department POJO
                    Department department = new Department(
                            record.get(DEPARTMENT.ID),
                            record.get(DEPARTMENT.NAME)
                    );

                    // Return an EmployeeDTO instead of Employee
                    return new EmployeeDTO(
                            record.get(EMPLOYEE.ID),
                            record.get(EMPLOYEE.NAME),
                            department
                    );
                });
    }

    public void insertEmployee(EmployeeDTO employeeDTO) {
        boolean exists = dsl.fetchExists(
                dsl.selectOne().from(EMPLOYEE).where(EMPLOYEE.NAME.eq(employeeDTO.getName()))
        );
        if (exists) {
            throw new DuplicateEmployeeException("Employee with name " + employeeDTO.getName() + " already exists");
        }
        dsl.insertInto(EMPLOYEE)
                .set(EMPLOYEE.NAME, employeeDTO.getName())
                .set(EMPLOYEE.DEPARTMENT_ID, employeeDTO.getDepartment().getId())
                .execute();
    }

    public void updateEmployee(EmployeeDTO employeeDTO) {
        int updated = dsl.update(EMPLOYEE)
                .set(EMPLOYEE.NAME, employeeDTO.getName())
                .set(EMPLOYEE.DEPARTMENT_ID, employeeDTO.getDepartment().getId())
                .where(EMPLOYEE.ID.eq(employeeDTO.getId()))
                .execute();
        if (updated == 0){
            throw new EmployeeNotFoundException("Employee with ID " + employeeDTO.getId() + " not found");
        }
    }

    public void deleteEmployeeById(int id){
        int deleted = dsl.deleteFrom(EMPLOYEE)
                .where(EMPLOYEE.ID.eq(id))
                .execute();
        if (deleted == 0){
            throw new EmployeeNotFoundException("Employee with ID " + id + " not found");
        }
    }
}
