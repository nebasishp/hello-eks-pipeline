package com.example.employee;

import com.example.s3.S3FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;
    private final S3FileService s3FileService;

    public EmployeeService(
            EmployeeRepository repository,
            S3FileService s3FileService) {

        this.repository = repository;
        this.s3FileService = s3FileService;
    }

    public Employee create(Employee employee) {
        return repository.save(employee);
    }

    public List<Employee> getAll() {
        return repository.findAll();
    }

    public Employee getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found: " + id));
    }

    public Employee update(Long id, Employee request) {

        Employee employee = getById(id);

        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(request.getDepartment());
        employee.setSalary(request.getSalary());

        return repository.save(employee);
    }

    public void delete(Long id) {
        Employee employee = getById(id);
        repository.delete(employee);
    }

    public Employee uploadResume(
            Long id,
            MultipartFile file) throws IOException {

        Employee employee = getById(id);

        String key =
                s3FileService.upload(id, file);

        employee.setResumeKey(key);

        return repository.save(employee);
    }
}