package com.auth.authgateway.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserDto {
    private String firstName;
    private String lastName;
    private String userId;
    private String mail;
    private String country;
    private String language;
    private Set<String> groups;
}
