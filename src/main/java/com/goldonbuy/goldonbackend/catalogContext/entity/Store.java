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
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String contactName;

    @Enumerated(EnumType.STRING)
    private TypeStore type;

    @Enumerated(EnumType.STRING)
    private SizeStore size = SizeStore.FREE;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    public Store(String name, String contactName, TypeStore type, Address address) {
        this.name = name;
        this.contactName = contactName;
        this.type = type;
        this.address = address;
    }
}
