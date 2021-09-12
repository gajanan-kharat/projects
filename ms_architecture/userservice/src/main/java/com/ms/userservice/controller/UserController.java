package com.ms.userservice.controller;

import com.ms.userservice.entity.User;
import com.ms.userservice.service.UserService;
import com.ms.userservice.vo.ResponseTemplateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public User save(@RequestBody User user) {
        log.info("Inside save() of user controller");
        log.info("User details: {}",user);
        return userService.save(user);

    }

    @GetMapping("/{userId}")
    public ResponseTemplateVO getUserByDepartment(@PathVariable("userId") Long userId) {
        log.info("Inside getUserByDepartment() of User Controller");
        return userService.getUserByDepartment(userId);
    }

}
