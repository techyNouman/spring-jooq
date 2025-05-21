package com.example.spring_jooq.controller;

import com.example.spring_jooq.dto.EmployeeDTO;
import com.example.spring_jooq.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> insertEmployee(@RequestBody EmployeeDTO employeeDTO) {
        service.insertEmployee(employeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Employee inserted successfully");
    }

    @GetMapping
    public List<EmployeeDTO> getAll() {
        return service.getAllEmployees();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable int id, @RequestBody EmployeeDTO employeeDTO) {
        employeeDTO.setId(id);
        service.updateEmployee(employeeDTO);
        return ResponseEntity.ok("Employee updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        service.deleteEmployeeById(id);
        return ResponseEntity.ok("Employee with ID " + id + " deleted successfully");
    }
}
