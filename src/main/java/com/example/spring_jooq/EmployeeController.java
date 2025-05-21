package com.example.spring_jooq;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }
    @PostMapping
    public void insertEmployee(@RequestBody EmployeeDTO employeeDTO) {
        service.insertEmployee(employeeDTO);
    }
    @GetMapping
    public List<EmployeeDTO> getAll() {
        return service.getAllEmployees();
    }
}
