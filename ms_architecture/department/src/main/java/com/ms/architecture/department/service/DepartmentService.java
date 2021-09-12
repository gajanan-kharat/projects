package com.ms.architecture.department.service;

import com.ms.architecture.department.entity.Department;
import com.ms.architecture.department.repository.DepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    public Department save(Department department) {
        log.info("Inside save department method of service");
        return departmentRepository.saveAndFlush(department);
    }

    public Department getDeptById(final Long deptId) {
        log.info("Inside getDeptById() by service");
         return departmentRepository.findById(deptId).get();

    }
}
