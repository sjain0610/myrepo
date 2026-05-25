package com.stackroute.service;


import com.stackroute.grpc.*;
import com.stackroute.repository.BankEmpRepository;
import com.stackroute.repository.BankRepository;
import com.stackroute.repository.BranchRepository;
import com.stackroute.repository.CustomerRepository;
import com.sun.net.httpserver.Authenticator;
import io.grpc.stub.StreamObserver;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import java.util.Base64;
import java.util.Objects;


@GrpcService
public class UserServiceServerImpl extends userServerGrpc.userServerImplBase {


    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private BankEmpRepository bankEmpRepository;

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public void createCustomer(Customer request, StreamObserver<Customer> responseObserver) {
        System.out.println(request);

        long custId = Customer.newBuilder(request).getCustId();
        String name = Customer.newBuilder(request).getName();
        String emailId = Customer.newBuilder(request).getEmailId();
        String mobNo = Customer.newBuilder(request).getMobNo();
        String userName = Customer.newBuilder(request).getUserName();
        String password = Customer.newBuilder(request).getPassword();
        String streetAddress = Customer.newBuilder(request).getStreetAddress();
        String city = Customer.newBuilder(request).getCity();
        String state = Customer.newBuilder(request).getState();
        String pincode = Customer.newBuilder(request).getPincode();

        com.stackroute.entity.Customer customerRequest =
                com.stackroute.entity.Customer.builder()
                        .custId(custId)
                        .name(name)
                        .emailId(emailId)
                        .mobNo(mobNo)
                        .username(userName)
                        .password(password)
                        .streetAddress(streetAddress)
                        .city(city)
                        .state(state)
                        .pincode(pincode)
                        .build();

        customerRepository.save(customerRequest);

        responseObserver.onNext(request);
        responseObserver.onCompleted();


    }

    @Override
    public void createBank(Bank request, StreamObserver<Bank> responseObserver) {
        long bankId = Bank.newBuilder(request).getBankId();
        String bankName = Bank.newBuilder(request).getBankName();
        String username = Bank.newBuilder(request).getUsername();
        String password = Bank.newBuilder(request).getPassword();
        com.stackroute.entity.Bank bankRequest =
                com.stackroute.entity.Bank.builder()
                        .bankId(bankId)
                        .bankName(bankName)
                        .username(username)
                        .password(password)
                        .build();
        bankRepository.save(bankRequest);

        responseObserver.onNext(request);
        responseObserver.onCompleted();
    }

    @Override
    public void createBankEmp(BankEmp request, StreamObserver<BankEmp> responseObserver) {

        long empId = BankEmp.newBuilder(request).getEmpId();
        String username = BankEmp.newBuilder(request).getUsername();
        String password = BankEmp.newBuilder(request).getPassword();
        String ifscCode = BankEmp.newBuilder(request).getIfscCode();

        com.stackroute.entity.BankEmp bankEmpRequest =
                com.stackroute.entity.BankEmp.builder()
                        .empId(empId)
                        .username(username)
                        .password(password)
                        .ifscCode(ifscCode)
                        .build();
        bankEmpRepository.save(bankEmpRequest);

        responseObserver.onNext(request);
        responseObserver.onCompleted();
    }

    @Override
    public void createBranch(Branch request, StreamObserver<Branch> responseObserver) {
        String ifscCode = Branch.newBuilder(request).getIfscCode();
        long bankId = Branch.newBuilder(request).getBankId();
        String streetAddress = Branch.newBuilder(request).getStreetAddress();
        String city = Branch.newBuilder(request).getCity();
        String state = Branch.newBuilder(request).getState();
        String pincode = Branch.newBuilder(request).getPincode();

        com.stackroute.entity.Branch branchRequest =
                com.stackroute.entity.Branch.builder()
                        .ifscCode(ifscCode)
                        .bankId(bankId)
                        .streetAddress(streetAddress)
                        .city(city)
                        .state(state)
                        .pincode(pincode)
                        .build();


        branchRepository.save(branchRequest);

        responseObserver.onNext(request);
        responseObserver.onCompleted();


    }

    @Override
    public void userLogin(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        System.out.println("Inside server");
        String username = request.getUsername();
        String password = request.getPassword();
        String role = request.getRole();
        String responseMessage;
        LoginResponse response;
        String token;
        System.out.println("Inside server" + username + " " + password + " " + role);
        if (role.equalsIgnoreCase("Customer")) {
            System.out.println("inside customer if");
            com.stackroute.entity.Customer customer = customerRepository.findByUsernameAndPassword(username, password);
            if (customer == null) {
                System.out.println("if null");
                responseMessage = "InvalidUser";
            } else {
                System.out.println(" not null");
                responseMessage = "ValidUser";
            }

        } else if (role.equalsIgnoreCase("Bank")) {
            com.stackroute.entity.Bank bank = bankRepository.findByUsernameAndPassword(username, password);

            if (bank == null)
                responseMessage = "InvalidUser";
            else
                responseMessage = "ValidUser";

        } else if (role.equalsIgnoreCase("BankEmployee")) {
            com.stackroute.entity.BankEmp bankEmp;
            bankEmp = bankEmpRepository.findByUsernameAndPassword(username, password);

            if (bankEmp == null)
                responseMessage = "InvalidUser";
            else
                responseMessage = "ValidUser";

        } else {
            responseMessage = "InvalidRole";
        }

        if(responseMessage.equals("ValidUser")) {

          if(role.equalsIgnoreCase("Customer"))
              token = Jwts.builder().setId(username).setSubject(role).claim("email",customerRepository.findByUsername(username).getEmailId()).signWith(SignatureAlgorithm.HS256, "mysecret").compact();
             else
            token = Jwts.builder().setId(username).setSubject(role).signWith(SignatureAlgorithm.HS256, "mysecret").compact();

            response = LoginResponse.newBuilder().setResponseMessage(token).build();
        }

        else
        {  response = LoginResponse.newBuilder().setResponseMessage(responseMessage).build();}


        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void updateCustomerDetails(Customer request, StreamObserver<UpdateResponse> responseObserver) {

        String emailId = request.getEmailId();
        String mobNo = request.getMobNo();
        String username = request.getUserName();
        String password = request.getPassword();
        String streetAddress = request.getStreetAddress();
        String city = request.getCity();
        String state = request.getState();
        String pincode = request.getPincode();
        String responseMessage = "success";

        com.stackroute.entity.Customer customer = customerRepository.findByUsername(username);

        if (Objects.isNull(customer)) {
            responseMessage = "Invalid Username";

        } else {
            if (!emailId.equals("")) {
                customer.setEmailId(emailId);

            }
            if (!mobNo.equals("")) {
                customer.setMobNo(mobNo);

            }
            if (!password.equals("")) {
                customer.setPassword(password);

            }
            if (!city.equals("")) {
                customer.setCity(city);

            }
            if (!streetAddress.equals("")) {
                customer.setStreetAddress(streetAddress);

            }
            if (!state.equals("")) {
                customer.setState(state);

            }
            if (!pincode.equals("")) {
                customer.setPincode(pincode);

            }

            customerRepository.save(customer);
            responseMessage = "Success";
        }
        UpdateResponse response = UpdateResponse.newBuilder().setResponseMessage(responseMessage).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void updateBranchDetails(Branch request, StreamObserver<UpdateResponse> responseObserver) {
        String ifscCode = request.getIfscCode();
        String streetAddress = request.getStreetAddress();
        String city = request.getCity();
        String state = request.getState();
        String pincode = request.getPincode();
        String responseMessage = "";
        com.stackroute.entity.Branch branch = branchRepository.findByIfscCode(ifscCode);

        if (Objects.isNull(branch)) {
            responseMessage = "Invalid Username";

        } else {

            if (!city.equals("")) {
                branch.setCity(city);

            }
            if (!streetAddress.equals("")) {
                branch.setStreetAddress(streetAddress);

            }
            if (!state.equals("")) {
                branch.setState(state);

            }
            if (!pincode.equals("")) {
                branch.setPincode(pincode);

            }
            branchRepository.save(branch);
            responseMessage = "Success";

        }
        UpdateResponse response = UpdateResponse.newBuilder().setResponseMessage(responseMessage).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void updateBanKEmpPassword(BankEmp request, StreamObserver<UpdateResponse> responseObserver) {
        String username = request.getUsername();
        String password = request.getPassword();
        String responseMessage = "";
        com.stackroute.entity.BankEmp bankEmp = bankEmpRepository.findByUsername(username);
        if (Objects.isNull(bankEmp)) {
            responseMessage = "Invalid Username";

        } else {
            bankEmp.setPassword(password);
            bankEmpRepository.save(bankEmp);
            responseMessage = "Success";
        }

        UpdateResponse response = UpdateResponse.newBuilder().setResponseMessage(responseMessage).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateBankPassword(Bank request, StreamObserver<UpdateResponse> responseObserver) {
        String username = request.getUsername();
        String password = request.getPassword();
        String responseMessage = "";
        com.stackroute.entity.Bank bank = bankRepository.findByUsername(username);
        if (Objects.isNull(bank)) {
            responseMessage = "Invalid Username";

        } else {
            bank.setPassword(password);
            bankRepository.save(bank);
            responseMessage = "Success";

        }
        UpdateResponse response = UpdateResponse.newBuilder().setResponseMessage(responseMessage).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void getCustomer(CustomerByUsername request, StreamObserver<Customer> responseObserver) {
        String username = request.getUsername();
        com.stackroute.entity.Customer customer = customerRepository.findByUsername(username);


        if (customer == null) {
            responseObserver.onNext(null);
            responseObserver.onCompleted();
        } else {
            Customer response = Customer.newBuilder()
                    .setCustId(customer.getCustId())
                    .setName(customer.getName())
                    .setEmailId(customer.getEmailId())
                    .setMobNo(customer.getMobNo())
                    .setUserName(customer.getUsername())
                    .setPassword(customer.getPassword())
                    .setStreetAddress(customer.getStreetAddress())
                    .setCity(customer.getCity())
                    .setState(customer.getState())
                    .setPincode(customer.getPincode())
                    .build();


            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

    }

    @Override
    public void getBank(BankByUsername request, StreamObserver<Bank> responseObserver) {
        String username = request.getUsername();
        com.stackroute.entity.Bank bank = bankRepository.findByUsername(username);

        if (bank == null) {
            responseObserver.onNext(null);
            responseObserver.onCompleted();
        } else {
            Bank response = Bank.newBuilder()
                    .setBankId(bank.getBankId())
                    .setBankName(bank.getBankName())
                    .setUsername(bank.getUsername())
                    .setPassword(bank.getPassword())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }


    @Override
    public void getBranch(BranchByIfscCode request, StreamObserver<Branch> responseObserver) {

        String ifscCode = request.getIfscCode();
        com.stackroute.entity.Branch branch = branchRepository.findByIfscCode(ifscCode);
        if (branch == null) {
            responseObserver.onNext(null);
            responseObserver.onCompleted();
        } else {
            Branch response = Branch.newBuilder()
                    .setIfscCode(branch.getIfscCode())
                    .setBankId(branch.getBankId())
                    .setStreetAddress(branch.getStreetAddress())
                    .setCity(branch.getCity())
                    .setState(branch.getState())
                    .setPincode(branch.getPincode())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }


    }

    @Override
    public void getBankEmp(BankEmpByUsername request, StreamObserver<BankEmp> responseObserver) {
        String username = request.getUsername();
        com.stackroute.entity.BankEmp bankEmp = bankEmpRepository.findByUsername(username);
        if (bankEmp == null) {
            responseObserver.onNext(null);
            responseObserver.onCompleted();
        } else {
            BankEmp response = BankEmp.newBuilder()
                    .setEmpId(bankEmp.getEmpId())
                    .setIfscCode(bankEmp.getIfscCode())
                    .setUsername(bankEmp.getUsername())
                    .setPassword(bankEmp.getPassword())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }


    }


}


