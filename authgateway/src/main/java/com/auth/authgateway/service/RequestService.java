package com.auth.authgateway.service;

import com.auth.authgateway.dto.RequestDto;
import com.auth.authgateway.dto.UserDto;
import com.auth.authgateway.entities.Group;
import com.auth.authgateway.entities.Request;
import com.auth.authgateway.entities.RequestStatus;
import com.auth.authgateway.repository.RequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RequestService {
    final Supplier<Optional<UserDto>> userDtoSupplier = () -> {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            return (auth.getPrincipal() instanceof UserDto) ? Optional.of((UserDto) auth.getPrincipal()) : Optional.empty();
        }
        return Optional.empty();
    };
    @Autowired
    private RequestRepository requestRepository;

    public RequestDto save(RequestDto requestDto) {
        log.info("Save/Update request ID: - {}, status - {}", requestDto.getId(), requestDto.getStatus());
        Optional<UserDto> user = userDtoSupplier.get();
        Request request = requestRepository.save(new Request(requestDto.getId(), requestDto.getStatus(), new Group(requestDto.getGroupName()), requestDto.getPayload().getBytes(), requestDto.getComment(), requestDto.getRequestedBy(), requestDto.getRequestType(), LocalDateTime.now(), LocalDateTime.now()));
        return new RequestDto(request);
    }

    public List<RequestDto> getUsersAllRequests() {
        if (!userDtoSupplier.get().isPresent()) {
            throw new NullPointerException("User Information not found!");
        }
        return requestRepository.findAll().stream().filter(req ->
                userDtoSupplier.get().get().getUserId().equalsIgnoreCase(req.getRequestedBy()))
                .map(RequestDto::new).collect(Collectors.toList());
    }

    public List<RequestDto> getUsersAllPendingRequests() {
        if (!userDtoSupplier.get().isPresent()) {
            throw new NullPointerException("User Information not found!");
        }
        UserDto user = userDtoSupplier.get().get();
        log.info("Getting pending requests by groups - {}, user - {}", user.getGroups(), user.getUserId());
        List<RequestDto> requests = requestRepository.findByStatus(RequestStatus.REQUEST).stream().
                filter(req -> user.getGroups().contains(req.getGroup().getGroupName()))
                .map(RequestDto::new).collect(Collectors.toList());
        return requests;
    }

}
