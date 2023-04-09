package com.microservice.usermanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "USERS")
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = {"userPhones"})
@ToString(exclude = {"userPhones"})
public class User {

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    List<UserPhones> userPhones;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "users_seq", allocationSize = 1)
    private Integer userId;
    @Column(name = "USER_NAME")
    private String username;
    @Column(name = "EMAIL")
    @NotBlank(message = "Email required.")
    private String email;
    @Column(name = "PASSWORD")
    @NotBlank(message = "Password required.")
    private String password;
    @CreationTimestamp
    @Column(name = "CREATED_AT")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "LAST_LOGIN")
    private Date lastLogin;

    @Column(name = "IS_ACTIVE")
    private boolean isActive;

    @Column(name = "JWT_TOKEN")
    private String jwtToken;
}
