package com.example.ccidbackend.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "signature")
public class Signature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "signature")
    private List<LegalizationStamp> legalizationStamps;


    public Signature() {
    }

    public Signature(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Signature(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}