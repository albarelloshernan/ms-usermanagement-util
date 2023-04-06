package com.microservice.usermanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "PHONES")
@AllArgsConstructor
@NoArgsConstructor
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_phones_generator")
    @SequenceGenerator(name = "user_phones_generator", sequenceName = "user_phones_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "PHONE")
    private Long phone;

    @Column(name = "CITY_CODE")
    private Integer cityCode;

    @Column(name = "COUNTRY_CODE")
    private String countryCode;
}
