package com.auth.authgateway.dto;

import com.auth.authgateway.entities.Request;
import com.auth.authgateway.entities.RequestStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestDto {
    private Long id;
    private RequestStatus status;
    private LocalDateTime date;
    private String groupName;
    private String requestType;
    private String requestedBy;
    private String payload;
    private String comment;

    public RequestDto(Request request) {
        this.id = request.getRequestId();
        this.status = request.getStatus();
        this.date = request.getCreationTime();
        this.groupName = request.getGroup().getGroupName();
        this.requestType = request.getRequestType();
        this.requestedBy = request.getRequestedBy();
        this.payload = new String(request.getPayload());
        this.comment = request.getComment();
    }
}
