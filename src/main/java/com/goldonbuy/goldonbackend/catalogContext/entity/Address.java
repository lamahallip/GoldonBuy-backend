package com.goldonbuy.goldonbackend.catalogContext.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String street;

    private String city;
    private String zipCode;
    private String country;

    @OneToMany(mappedBy = "address")
    private List<Store> stores;
}
