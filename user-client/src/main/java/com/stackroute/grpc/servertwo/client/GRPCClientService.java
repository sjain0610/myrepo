package com.stackroute.grpc.servertwo.client;

import com.stackroute.grpc.servertwo.rest.dto.BankClientRequest;
import com.stackroute.grpc.servertwo.rest.dto.BankEmpClientRequest;
import com.stackroute.grpc.servertwo.rest.dto.BranchClientRequest;
import com.stackroute.grpc.servertwo.rest.dto.CustomerClientRequest;
import com.stackroute.grpc.servertwo.rest.exception.InvalidInputException;
import com.stackroute.grpc.servertwo.rest.exception.UserAlreadyExistException;
import com.stackroute.grpc.servertwo.rest.exception.UserDoesNotExistException;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import com.stackroute.grpc.*;

import java.util.Objects;


@Service
public class GRPCClientService {
    @Autowired
    private JavaMailSenderImpl javaMailSender;


    private final static Logger log = LoggerFactory.getLogger(GRPCClientService.class);


    public Customer saveCustomer(CustomerClientRequest request) throws InvalidInputException, UserAlreadyExistException {
        log.info("### Start execute GRPCClientService from server two");
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext().build();
        Customer customer = null;

        //log.info(String.format("## grpcServerOneHost::%s and grpcServerPort::%s", grpcServerOneHost, grpcServerOnePort));
        // ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext().build();
        userServerGrpc.userServerBlockingStub stub = userServerGrpc.newBlockingStub(channel);

        Customer customerBuilder = Customer.newBuilder()
                .setCustId(request.getCustId())
                .setName(request.getName())
                .setEmailId(request.getEmailId())
                .setMobNo(request.getMobNo())
                .setUserName(request.getUserName())
                .setPassword(request.getPassword())
                .setStreetAddress(request.getStreetAddress())
                .setCity(request.getCity())
                .setState(request.getState())
                .setPincode(request.getPincode())
                .build();

        if (customerBuilder.getName().isEmpty() || customerBuilder.getEmailId().isEmpty() || customerBuilder.getMobNo().isEmpty()
                || customerBuilder.getUserName().isEmpty() || customerBuilder.getPassword().isEmpty()) {
            throw new InvalidInputException("Missing required input parameters");
        }

        Customer tempCustomer = customerGetByUserName(customerBuilder.getUserName());


        if (!tempCustomer.getUserName().isEmpty()) {
            throw new UserAlreadyExistException("Username already taken");
        }

        customer = stub.createCustomer(customerBuilder);
        channel.shutdown();
        return customer;
    }


    public Bank saveBank(BankClientRequest request) throws InvalidInputException, UserAlreadyExistException {
        log.info("### Start execute GRPCClientService from server two");
        // log.info(String.format("## grpcServerOneHost::%s and grpcServerPort::%s", grpcServerOneHost, grpcServerOnePort));
        System.out.println(request);
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext().build();
        Bank bank = null;
        userServerGrpc.userServerBlockingStub stub = userServerGrpc.newBlockingStub(channel);
        Bank bankBuilder = Bank.newBuilder()
                .setBankId(request.getBankId())
                .setBankName(request.getBankName())
                .setUsername(request.getUsername())
                .setPassword(request.getPassword())
                .build();


        if (bankBuilder.getBankName().isEmpty() || bankBuilder.getUsername().isEmpty() || bankBuilder.getPassword().isEmpty()) {
            throw new InvalidInputException("Missing required input parameters");
        }
        Bank tempBank = bankGetByUsername(bankBuilder.getUsername());


        if (!tempBank.getUsername().isEmpty()) {
            throw new UserAlreadyExistException("Username already taken");
        }
        bank = stub.createBank(bankBuilder);
        channel.shutdown();
        System.out.println(bank);
        return bank;


    }


    public BankEmp saveBankEmp(BankEmpClientRequest request) throws InvalidInputException, UserAlreadyExistException {
        //log.info("### Start execute GRPCClientService from server two");
        // log.info(String.format("## grpcServerOneHost::%s and grpcServerPort::%s", grpcServerOneHost, grpcServerOnePort));
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext().build();
        userServerGrpc.userServerBlockingStub stub = userServerGrpc.newBlockingStub(channel);
        BankEmp bankEmp = null;
        BankEmp bankEmpBuilder = BankEmp.newBuilder()
                .setEmpId(request.getEmpId())
                .setUsername(request.getUsername())
                .setPassword(request.getPassword())
                .setIfscCode(request.getIfscCode())
                .build();

        if (bankEmpBuilder.getIfscCode().isEmpty() || bankEmpBuilder.getUsername().isEmpty() || bankEmpBuilder.getPassword().isEmpty()) {
            throw new InvalidInputException("Missing required input parameters");
        }

        BankEmp tempBankEmp = bankEmpGetByUsername(bankEmpBuilder.getUsername());


        if (!tempBankEmp.getUsername().isEmpty()) {
            throw new UserAlreadyExistException("Username already taken");
        }

        bankEmp = stub.createBankEmp(bankEmpBuilder);

        channel.shutdown();

        return bankEmp;
    }

    public Branch saveBranch(BranchClientRequest request) throws InvalidInputException, UserAlreadyExistException {
        log.info("### Start execute GRPCClientService from server two");
        //log.info(String.format("## grpcServerOneHost::%s and grpcServerPort::%s", grpcServerOneHost, grpcServerOnePort));
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext().build();
        userServerGrpc.userServerBlockingStub stub = userServerGrpc.newBlockingStub(channel);
        Branch branch = null;
        Branch branchBuilder = Branch.newBuilder()
                .setIfscCode(request.getIfscCode())
                .setBankId(request.getBankId())
                .setStreetAddress(request.getStreetAddress())
                .setCity(request.getCity())
                .setState(request.getState())
                .setPincode(request.getPincode())
                .build();

        if (branchBuilder.getIfscCode().isEmpty()) {
            throw new InvalidInputException("Missing required input parameters");
        }

        Branch tempBranch = branchGetByIfsc(branchBuilder.getIfscCode());


        if (!tempBranch.getIfscCode().isEmpty()) {
            throw new UserAlreadyExistException("Ifsc already taken");
        }

        branch = stub.createBranch(branchBuilder);
        channel.shutdown();
        return branch;

    }


    public String UpdateBranch(BranchClientRequest request) throws UserDoesNotExistException {
        log.info("### Start execute GRPCClientService from server two");
        //log.info(String.format("## grpcServerOneHost::%s and grpcServerPort::%s", grpcServerOneHost, grpcServerOnePort));
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext().build();
        userServerGrpc.userServerBlockingStub stub = userServerGrpc.newBlockingStub(channel);
        UpdateResponse updateResponse = null;
        updateResponse = stub.updateBranchDetails(Branch.newBuilder()
                .setIfscCode(request.getIfscCode())
                .setBankId(request.getBankId())
                .setStreetAddress(request.getStreetAddress())
                .setCity(request.getCity())
                .setState(request.getState())
                .setPincode(request.getPincode())
                .build());

        if (updateResponse.getResponseMessage().equals("Invalid Username")) {
            throw new UserDoesNotExistException("Invalid Username");
        }
        return updateResponse.getResponseMessage();


    }

    public String UpdateBank(BankClientRequest request) throws UserDoesNotExistException {

        log.info("### Start execute GRPCClientService from server two");
        // log.info(String.format("## grpcServerOneHost::%s and grpcServerPort::%s", grpcServerOneHost, grpcServerOnePort));
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext().build();
        userServerGrpc.userServerBlockingStub stub = userServerGrpc.newBlockingStub(channel);
        UpdateResponse updateResponse = null;


        updateResponse = stub.updateBankPassword(Bank.newBuilder()
                .setBankId(request.getBankId())
                .setBankName(request.getBankName())
                .setUsername(request.getUsername())
                .setPassword(request.getPassword())
                .build());

        if (updateResponse.getResponseMessage().equals("Invalid Username")) {
            throw new UserDoesNotExistException("Invalid Username");
        }

        channel.shutdown();

        return updateResponse.getResponseMessage();
    }

    public String UpdateCustomer(CustomerClientRequest request) throws UserDoesNotExistException {

        log.info("### Start execute GRPCClientService from server two");
        // log.info(String.format("## grpcServerOneHost::%s and grpcServerPort::%s", grpcServerOneHost, grpcServerOnePort));
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext().build();
        userServerGrpc.userServerBlockingStub stub = userServerGrpc.newBlockingStub(channel);
        UpdateResponse updateResponse = null;

        updateResponse = stub.updateCustomerDetails(Customer.newBuilder()
                .setCustId(request.getCustId())
                .setName(request.getName())
                .setEmailId(request.getEmailId())
                .setMobNo(request.getMobNo())
                .setUserName(request.getUserName())
                .setPassword(request.getPassword())
                .setStreetAddress(request.getStreetAddress())
                .setCity(request.getCity())
                .setState(request.getState())
                .setPincode(request.getPincode())
                .build());
        if (updateResponse.getResponseMessage().equals("Invalid Username")) {
            throw new UserDoesNotExistException("Invalid Username");
        }


        channel.shutdown();
        return updateResponse.getResponseMessage();
    }

    public String UpdateBankEmp(BankEmpClientRequest request) throws UserDoesNotExistException {

        log.info("### Start execute GRPCClientService from server two");
        // log.info(String.format("## grpcServerOneHost::%s and grpcServerPort::%s", grpcServerOneHost, grpcServerOnePort));
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext().build();
        userServerGrpc.userServerBlockingStub stub = userServerGrpc.newBlockingStub(channel);
        UpdateResponse updateResponse = null;
        updateResponse = stub.updateBanKEmpPassword(BankEmp.newBuilder()
                .setEmpId(request.getEmpId())
                .setUsername(request.getUsername())
                .setPassword(request.getPassword())
                .setIfscCode(request.getIfscCode())
                .build());

        if (updateResponse.getResponseMessage().equals("Invalid Username")) {
            throw new UserDoesNotExistException("Invalid Username");
        }
        channel.shutdown();
        return updateResponse.getResponseMessage();
    }

    public String generateToken(String username,String password,String role) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext().build();
        userServerGrpc.userServerBlockingStub stub = userServerGrpc.newBlockingStub(channel);
        LoginResponse response = stub.userLogin(LoginRequest.newBuilder().setUsername(username).setPassword(password).setRole(role).build());
        Jws<Claims> result= Jwts.parser().setSigningKey("mysecret").parseClaimsJws(response.getResponseMessage());
        channel.shutdown();
        String email = result.getBody().get("email",String.class);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("BankRush- Login");
        mailMessage.setText("You have successfully logged in to BankRush!!");
        mailMessage.setFrom("bankrush.app@gmail.com");
        //   JavaMailSenderImpl javaMailSender=new JavaMailSenderImpl();
        javaMailSender.send(mailMessage);

        return response.getResponseMessage();

    }

    public Customer customerGetByUserName(String request) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext().build();
        userServerGrpc.userServerBlockingStub stub = userServerGrpc.newBlockingStub(channel);

        CustomerByUsername customerBuilder = CustomerByUsername.newBuilder().setUsername(request).build();


        Customer customer = stub.getCustomer(customerBuilder);
        channel.shutdown();

        return customer;


    }

    public Bank bankGetByUsername(String request) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext().build();
        userServerGrpc.userServerBlockingStub stub = userServerGrpc.newBlockingStub(channel);

        BankByUsername bankBuilder = BankByUsername.newBuilder().setUsername(request).build();

        Bank bank = stub.getBank(bankBuilder);
        return bank;

    }

    public BankEmp bankEmpGetByUsername(String request) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext().build();
        userServerGrpc.userServerBlockingStub stub = userServerGrpc.newBlockingStub(channel);

        BankEmpByUsername bankEmpBuilder = BankEmpByUsername.newBuilder().setUsername(request).build();

        BankEmp bankEmp = stub.getBankEmp(bankEmpBuilder);
        return bankEmp;

    }

    public Branch branchGetByIfsc(String request) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext().build();
        userServerGrpc.userServerBlockingStub stub = userServerGrpc.newBlockingStub(channel);
        BranchByIfscCode branchBuilder = BranchByIfscCode.newBuilder().setIfscCode(request).build();

        Branch branch = stub.getBranch(branchBuilder);
        return branch;


    }

}
