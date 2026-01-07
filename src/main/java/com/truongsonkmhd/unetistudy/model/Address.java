package com.truongsonkmhd.unetistudy.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_address")
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Address{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // phù hợp với PostgreSQL
    @Column(name = "id")
    Long id;

    @Column(name = "apartment_number")
    String apartmentNumber;

    @Column(name = "floor")
    String floor;

    @Column(name = "building")
    String building;

    @Column(name = "street_number")
    String streetNumber;

    @Column(name = "street")
    String street;

    @Column(name = "city")
    String city;

    @Column(name = "country")
    String country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "address_type")
    Integer addressType;


    @Column(name = "created_at", length = 255)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    Date createdAt;

    @Column(name = "updated_at", length = 255)
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    Date updatedAt;

}