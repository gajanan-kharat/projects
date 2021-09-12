package com.ms.architecture.department.controller;

import com.ms.architecture.department.entity.Department;
import com.ms.architecture.department.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/departments")
@Slf4j
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/save")
    public Department save(@RequestBody Department department) {
        log.info("Inside save department method of controller");
        return departmentService.save(department);
    }

    @GetMapping("/{deptId}")
    public Department getDeptById(@PathVariable("deptId") Long deptId) {
        log.info("Inside getDeptById() in controller");
        return departmentService.getDeptById(deptId);
    }

}
