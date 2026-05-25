package com.stackroute.grpc.serverOne.dto;


import lombok.*;


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerQueryRequet {


    private String customerId;
    private String queryId;
    private String  queryStatus;
    private String queryDes;
    private String queryCreationDate;
    private String querySubject;

}
