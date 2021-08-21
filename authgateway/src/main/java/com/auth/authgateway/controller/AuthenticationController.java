package com.auth.authgateway.controller;

import com.auth.authgateway.dto.UserDto;
import com.auth.authgateway.entities.Group;
import com.auth.authgateway.entities.User;
import com.auth.authgateway.repository.GroupRepository;
import com.auth.authgateway.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RestController
@RequestMapping(AuthenticationController.ENDPOINT)
public class AuthenticationController {
    public static final String ENDPOINT = "/user";

    @Autowired
    private UserService userService;

    @Autowired
    private GroupRepository groupRepository;

    @GetMapping
    public ResponseEntity<UserDto> getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof String) {
            if(principal.toString().equalsIgnoreCase("anonymoususer")) {
                log.warn("Principal {}", principal.toString());
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
        }
        UserDto userDto = (principal instanceof UserDto) ? (UserDto) principal : null;
        saveOrUpdateUser(userDto);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getUsers();
    }

    @PostMapping
    public UserDto updateUser(@RequestBody UserDto userDto) {
        List<Group> groups = new ArrayList<>();
        Group some_group = new Group("some_group");
        Group some_other_group = new Group("some_other_group");
        groups.add(some_group);
        groups.add(some_other_group);
        userService.saveUser(new User("ABC12345", "Gajanan", "Kharat", "abc@gmail.com" ,new HashSet<>(groups)));
        return userDto;
    }

    @GetMapping("/groups")
    public List<Group> getGroup() {
        return groupRepository.findAll();
    }

    private void saveOrUpdateUser(UserDto userDto) {
        Executor saveUserExecutor = Executors.newSingleThreadExecutor();
        Runnable saveUserTask = () -> {
            try {
                Set<Group> groups = new HashSet<>();
                userDto.getGroups().forEach(group -> {
                    groups.add(new Group(group));
                });
                userService.saveUser(new User(userDto.getUserId(), userDto.getFirstName(), userDto.getLastName(),
                        userDto.getMail(), groups));
                log.info("Principal {}", userDto.getFirstName());
            }catch (Exception e){
                e.printStackTrace();
                log.error(e.getMessage());
            }
        };
        try {
            saveUserExecutor.execute(saveUserTask);
        } finally {
            ((ExecutorService)saveUserExecutor).shutdown();
        }
    }

}
