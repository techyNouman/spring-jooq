package com.example.spring_jooq;

import lombok.*;
import com.example.spring_jooq.generated.tables.pojos.Department;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Integer id;
    private String name;
    private Department department;

//    public EmployeeDTO() {}
//
//    public EmployeeDTO(Integer id, String name, Department department) {
//        this.id = id;
//        this.name = name;
//        this.department = department;
//    }
//
//    public Integer getId() { return id; }
//    public void setId(Integer id) { this.id = id; }
//
//    public String getName() { return name; }
//    public void setName(String name) { this.name = name; }
//
//    public Department getDepartment() { return department; }
//    public void setDepartment(Department department) { this.department = department; }
}
