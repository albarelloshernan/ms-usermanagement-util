package com.microservice.usermanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "PHONES")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UserPhones {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_phones_generator")
    @SequenceGenerator(name = "user_phones_generator", sequenceName = "user_phones_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "PHONE")
    private Long phone;

    @Column(name = "CITY_CODE")
    private Integer cityCode;

    @Column(name = "COUNTRY_CODE")
    private String countryCode;
}
