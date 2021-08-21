package com.auth.authgateway.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "USERS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

// For soft Delete
@SQLDelete(sql = "update User set isDeleted=true where userId=?")
@Where(clause = "isDeleted=false") // While fetching records it will include deleted records that is `is_deleted=true`

public class User {
    @Id
    @Column(name = "USER_ID")
    private String userId;

    @NotNull
    @Column(name = "FIRST_NAME")
    private String firstName;

    @NotNull
    @Column(name = "LAST_NAME")
    private String lastName;

    @NotNull
    @Column(name = "MAIL")
    private String mail;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "CREATION_TIME")
    @CreationTimestamp
    private LocalDateTime creationTime;

    @Column(name = "UPDATED_TIME")
    @UpdateTimestamp
    private LocalDateTime updatedTime;

    @NotNull
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USERS_GROUPS",
            joinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "userId", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "GROUP_NAME", referencedColumnName = "groupName", nullable = false, updatable = false)}
    )
    private Set<Group> groups;

    public User(String userId, String firstName, String lastName, String mail, Set<Group> groups) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.groups = groups;

    }

    // Enable soft delete for native queries
    @PreRemove
    private void preRemove() {
        this.isDeleted = true;
    }

}
