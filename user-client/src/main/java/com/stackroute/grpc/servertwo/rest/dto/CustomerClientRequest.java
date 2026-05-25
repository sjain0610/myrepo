package com.stackroute.grpc.servertwo.rest.dto;

import lombok.*;


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerClientRequest {

    private long custId;
    private String name;
    private String emailId;
    private String mobNo;
    private String userName;
    private String password;
    private String streetAddress;
    private String city;
    private String state;
    private String pincode;
}
