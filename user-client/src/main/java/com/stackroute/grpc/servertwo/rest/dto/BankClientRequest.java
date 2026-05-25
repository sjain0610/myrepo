package com.stackroute.grpc.servertwo.rest.dto;


import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankClientRequest {

    private long bankId;
    private String bankName;
    private String username;
    private String password;
}
