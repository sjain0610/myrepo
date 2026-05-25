package com.stackroute.grpc.servertwo.client;

import com.stackroute.grpc.LoginRequest;
import com.stackroute.grpc.LoginResponse;
import com.stackroute.grpc.userServerGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService {

    public String generateToken(String username, String password, String role) {

        System.out.println("Inside getToken()");
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext().build();
        userServerGrpc.userServerBlockingStub stub = userServerGrpc.newBlockingStub(channel);


        LoginResponse response = stub.userLogin(LoginRequest.newBuilder().setUsername(username).setPassword(password).setRole(role).build());
        System.out.println("response obtained");
        System.out.println(response.getResponseMessage());
        channel.shutdown();
        return response.getResponseMessage();

    }


}

