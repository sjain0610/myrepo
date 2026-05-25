package com.stackroute.entity;

import javax.persistence.*;

import lombok.*;

import javax.persistence.Entity;

@Data
@Builder
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bank")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bank_id")
    private long bankId;
    @Column(name = "bank_name")
    private String bankName;
   @Column(name = "username")
    private String username;
   @Column(name = "password")
    private String password;
}
