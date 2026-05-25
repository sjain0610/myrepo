package com.stackroute.grpc.serverOne.service;


import com.stackroute.bankrush.*;
import com.stackroute.grpc.serverOne.dto.CustomerQueryIdRequest;
import com.stackroute.grpc.serverOne.dto.CustomerQueryRequet;
import com.stackroute.grpc.serverOne.dto.EmployeeQueryRequset;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GrpcClientService {
    private final static Logger log = LoggerFactory.getLogger(GrpcClientService.class);

    public static QueryResponse saveQuery(CustomerQueryRequet requet) {
        log.info("### Start execute GRPCClientService from server two");
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8082).usePlaintext().build();
        QueryResponse queryRequest = null;
        try {
            QueryServiseGrpc.QueryServiseBlockingStub stub = QueryServiseGrpc.newBlockingStub(channel);
            QueryRequest queryRequestBuilder = QueryRequest.newBuilder().
                    setCustomerId(requet.getCustomerId())
                    .setQueryId(requet.getQueryId())
                    .setQueryDes(requet.getQueryDes())
                    .setQueryCreationDate(requet.getQueryCreationDate())
                    .setQueryStatus(requet.getQueryStatus())
                    .setQuerySubject(requet.getQuerySubject()).build();
            queryRequest = stub.getQueryInfo(queryRequestBuilder);

        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            channel.shutdown();
        }
        return queryRequest;

    }

    public static CustomerQueryRequet getQueryDetails(CustomerQueryIdRequest request) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8082).usePlaintext().build();

        QueryResponse querytrack = null;
        QueryServiseGrpc.QueryServiseBlockingStub stub = QueryServiseGrpc.newBlockingStub(channel);
       CustomerQueryRequet customerQueryData = null;


            querytrack = stub.getbyQueryId(QueryIdRequest.newBuilder().
                    setQueryId(request.getQueryId()).build());

            customerQueryData = CustomerQueryRequet.builder().
                    queryId(querytrack.getQueryId()).
                    customerId(querytrack.getCustomerId()).
                    queryDes(querytrack.getQueryDes()).
                    queryStatus(querytrack.getQueryStatus()).
                    querySubject(querytrack.getQuerySubject()).
                    queryCreationDate(querytrack.getQueryCreationDate()).
                    build();
        channel.shutdown();
        return customerQueryData;



    }

    public static String updateQueryStaus(EmployeeQueryRequset requset) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8082).usePlaintext().build();
        updateResponse queryStatus = null;
        QueryServiseGrpc.QueryServiseBlockingStub stub = QueryServiseGrpc.newBlockingStub(channel);
        try {
            queryStatus = stub.updateQueryStatus(updateRequest.newBuilder()
                    .setQueryId(requset.getQueryId()).setQueryStatus(requset.getQueryStatus()).build());
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            channel.shutdown();
        }

        return queryStatus.getQueryStatus();
    }
}




