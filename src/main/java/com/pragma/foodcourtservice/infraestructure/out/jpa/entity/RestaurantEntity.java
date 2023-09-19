package com.pragma.foodcourtservice.infraestructure.out.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "restaurant")
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false, unique = true)
    private String nit;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String phone;
    @Column(name = "url_logo", nullable = false)
    private String urlLogo;
    @Column(name = "owner_id", nullable = false, unique = true)
    private Long ownerId;

}
