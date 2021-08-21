package com.auth.authgateway.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "REQUEST")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "groups")
public class Request {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long requestId;

    @NotNull
    @Column(name = "STATUS")
    @Enumerated(value = EnumType.STRING)
    private RequestStatus status;

    @NotNull
    @JsonIgnoreProperties("users")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groups")
    private Group group;

    @Lob
    @Column(name = "PAYLOAD", length = 2050)
    private byte[] payload;

    @Column(name = "COMMENT")
    private String comment;

    @NotNull
    private String requestedBy;// todo: See if this can be derived through the joins Users table

    private String requestType;

    @NotNull
    @CreationTimestamp
    private LocalDateTime creationTime;

    @NotNull
    @UpdateTimestamp
    private LocalDateTime updatedTime;
}
