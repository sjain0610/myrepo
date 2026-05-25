package com.stackroute.grpc.serverOne.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class CustomerQueryIdRequest {
    private String customerId;
    private String queryId;
    private String  queryStatus;
    private String queryDes;
    private String queryCreationDate;
    private String querySubject;



}
