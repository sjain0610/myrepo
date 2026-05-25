
package com.stackroute.service;

import com.stackroute.Entity.Query;


import com.stackroute.bankrush.*;
import com.stackroute.repo.QueryManagementRepo;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@GrpcService
public class queryServiceImpl  extends QueryServiseGrpc.QueryServiseImplBase {
    @Autowired
    private QueryManagementRepo repo;

    @Override
    public void getQueryInfo(QueryRequest request, StreamObserver<QueryResponse> responseObserver) {
        Query query = new Query(request.getCustomerId(), request.getQueryId(), request.getQueryStatus(), request.getQueryDes(),
                request.getQueryCreationDate(), request.getQuerySubject());
        Query query1 = this.repo.save(query);
        QueryResponse queryResponse = QueryResponse.newBuilder()
                .setCustomerId(query.getCustomerId())
                .setQueryId(query.getQueryId())
                .setQueryDes(query.getQueryDes())
                .setQueryCreationDate(query.getQueryCreationDate())
                .setQueryStatus(query.getQueryStatus())
                .setQuerySubject(query.getQuerySubject()).build();
        responseObserver.onNext(queryResponse);
        responseObserver.onCompleted();
    }



    @Override
    public void getbyQueryId(QueryIdRequest request, StreamObserver<QueryResponse> responseObserver) {
        Query query2 = this.repo.findByQueryId(request.getQueryId());

        QueryResponse queryResponse1 = QueryResponse.newBuilder()
                .setQueryId(query2.getQueryId()).setQuerySubject(query2.getQuerySubject()).setQueryDes(query2.getQueryDes()).setQueryCreationDate(query2.getQueryCreationDate())
                .setQueryStatus(query2.getQueryStatus()).setCustomerId(query2.getCustomerId())
                .build();
        responseObserver.onNext(queryResponse1);
        responseObserver.onCompleted();
    }

    @Override
    public void updateQueryStatus(updateRequest request, StreamObserver<updateResponse> responseObserver) {
        String response = null;
        Query query3 = this.repo.findById(request.getQueryId()).get();
        if (query3 != null) {
            query3.setQueryStatus(request.getQueryStatus());

            this.repo.save(query3);
            response = query3.getQueryStatus();
        } else response = "queryIdNotFound";
        updateResponse updatequery = updateResponse.newBuilder().setQueryStatus(response).build();
        responseObserver.onNext(updatequery);
        responseObserver.onCompleted();
    }
}



