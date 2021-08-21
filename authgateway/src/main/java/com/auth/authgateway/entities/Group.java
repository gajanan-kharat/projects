package com.auth.authgateway.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "GROUPS")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Group {
    @Id
    @Column(name = "GROUP_NAME")
    private String groupName;

    @NotNull
    @JsonIgnoreProperties("groups")
    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
    private Set<User> users;

    @OneToMany(mappedBy = "groups")
    List<Request> requests;

    public Group(String groupName) {
        this.groupName = groupName;
    }



}
