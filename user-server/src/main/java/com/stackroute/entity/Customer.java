package com.stackroute.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cust_id")
    private long custId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email_id", nullable = false)
    private String emailId;
    @Column(name = "mob_no", nullable = false)
    private String mobNo;
    @Column(name = "user_name", unique = true)
    private String username;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "street_address")
    private String streetAddress;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "pincode")
    private String pincode;


}
