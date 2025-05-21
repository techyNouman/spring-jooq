package com.example.spring_jooq;

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
        dsl.insertInto(EMPLOYEE)
                .set(EMPLOYEE.NAME, employeeDTO.getName())
                .set(EMPLOYEE.DEPARTMENT_ID, employeeDTO.getDepartment().getId())
                .execute();
    }

}
