package com.stackroute.grpc.serverOne.rest;


import com.stackroute.bankrush.QueryResponse;
import com.stackroute.grpc.serverOne.dto.CustomerQueryIdRequest;
import com.stackroute.grpc.serverOne.dto.CustomerQueryRequet;
import com.stackroute.grpc.serverOne.dto.EmployeeQueryRequset;
import com.stackroute.grpc.serverOne.service.GrpcClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class QueryController {

    @Autowired
    private  QueryController queryController;

    @PostMapping(value = "/querycreate")
    public CustomerQueryRequet queryRequet(@RequestBody CustomerQueryRequet requet){
        QueryResponse br  = GrpcClientService.saveQuery(requet);
        return  CustomerQueryRequet.builder()
                .queryId(requet.getQueryId()).customerId(requet.getCustomerId()).queryStatus(requet.getQueryStatus()).
                queryDes(requet.getQueryDes()).querySubject(requet.getQuerySubject()).queryCreationDate(requet.getQueryCreationDate()).build();


    }
   @PutMapping(value = "/updatequerybyid")
    public String updateQueryStatus(@RequestBody EmployeeQueryRequset requset){
        String successMessage="";
      String str= GrpcClientService.updateQueryStaus(requset);

                return requset.getQueryStatus();
    }
    @GetMapping(value = "/getquerydetailbyid")

    public CustomerQueryRequet getQueryDetail(@RequestBody CustomerQueryIdRequest request){

        String successMessage="";
         CustomerQueryRequet queryDetaile=GrpcClientService.getQueryDetails(request);

        return  CustomerQueryRequet.builder().queryId(queryDetaile.getQueryId()).customerId(queryDetaile.getCustomerId()).queryStatus(queryDetaile.getQueryStatus()).
                querySubject(queryDetaile.getQuerySubject()).queryDes(queryDetaile.getQueryDes()).queryCreationDate(queryDetaile.getQueryCreationDate()).build();



    }




}
