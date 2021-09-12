package com.ms.userservice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    private Long deptId;

    private String deptCode;

    private String deptName;

    private String deptAddress;
}
