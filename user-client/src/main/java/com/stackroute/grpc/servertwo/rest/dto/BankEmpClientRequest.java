package com.stackroute.grpc.servertwo.rest.dto;


import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankEmpClientRequest {
    private long empId;
    private String username;
    private String password;
    private String ifscCode;
}
