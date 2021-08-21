package com.auth.authgateway.controller;

import com.auth.authgateway.dto.RequestDto;
import com.auth.authgateway.service.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(RequestController.ENDPOINT)
public class RequestController {
    public static final String ENDPOINT = "/LogTask";
    
    @Autowired
    private RequestService requestService;
    
    @PostMapping
    public RequestDto registerRequest(@RequestBody RequestDto requestDto) {
        return requestService.save(requestDto);
    }
    
    @GetMapping("/requests")
    public ResponseEntity<List<RequestDto>> getAllRequests() {
        List<RequestDto> usersAllRequests;
        try {
            usersAllRequests = requestService.getUsersAllRequests();
            usersAllRequests.sort(Comparator.comparing(RequestDto::getDate).reversed());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found or not a JWT token based call");
        }
        return new ResponseEntity<>(usersAllRequests, HttpStatus.OK);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<RequestDto>> getPendingRequests() {
        List<RequestDto> usersAllPendingRequests;
        try {
            usersAllPendingRequests = requestService.getUsersAllPendingRequests();
            usersAllPendingRequests.sort(Comparator.comparing(RequestDto::getDate).reversed());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not found!");
        }
        return new ResponseEntity<>(usersAllPendingRequests, HttpStatus.OK);
    }
}
