package com.stackroute.entity;

import javax.persistence.*;

import lombok.*;

@Data
@Entity
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "branch")
public class Branch {

    @Id
    @Column(name="ifsc_Code")
    private String ifscCode;
    @Column(name="bank_id",nullable = false)
    private long bankId;
    @Column(name = "street_address")
    private String streetAddress;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "pincode")
    private String pincode;


}
