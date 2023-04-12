package com.microservice.usermanagement.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;

@Data
@Entity
@Table(name = "PHONES")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_phones_generator")
    @SequenceGenerator(name = "user_phones_generator", sequenceName = "user_phones_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

    @Column(name = "PHONE")
    private Long phone;

    @Column(name = "CITY_CODE")
    private Integer cityCode;

    @Column(name = "COUNTRY_CODE")
    private String countryCode;
}
