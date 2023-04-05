package com.microservice.usermanagement.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@Table(name = "PHONES")
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = { "user" })
@ToString(exclude = { "user" })
public class UserPhones {
    @Id
    @Column(name = "PHONE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer phoneId;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    @Column(name = "PHONE")
    private Long phone;
    @Column(name = "CITY_CODE")
    private Integer cityCode;
    @Column(name = "COUNTRY_CODE")
    private String countryCode;
}
