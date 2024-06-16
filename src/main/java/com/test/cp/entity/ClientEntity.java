package com.test.cp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clientes")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false , unique = true)
    private Long clientId;

    @Column(name = "nombres")
    private String names;
    @Column(name = "apellidos")
    private String surnames;
    @Column(name = "direccion")
    private String address;


    private boolean deleted;
}
