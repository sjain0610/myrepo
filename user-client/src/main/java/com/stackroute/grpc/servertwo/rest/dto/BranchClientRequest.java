package com.stackroute.grpc.servertwo.rest.dto;


import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchClientRequest {

    private String ifscCode;
    private long bankId;
    private String streetAddress;
    private String city;
    private String state;
    private String pincode;
}
