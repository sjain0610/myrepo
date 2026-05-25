package com.stackroute.grpc.servertwo.rest.controller;

import com.stackroute.grpc.Bank;
import com.stackroute.grpc.BankEmp;
import com.stackroute.grpc.Branch;
import com.stackroute.grpc.Customer;
import com.stackroute.grpc.servertwo.client.GRPCClientService;
import com.stackroute.grpc.servertwo.client.UserAuthenticationService;
import com.stackroute.grpc.servertwo.rest.dto.*;
import com.stackroute.grpc.servertwo.rest.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private final GRPCClientService gRPCClientService;

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    private final static Logger log = LoggerFactory.getLogger(ClientController.class);

    public ClientController(GRPCClientService gRPCClientService) {
        this.gRPCClientService = gRPCClientService;
    }

    @PostMapping("/registration/customer")
    public ResponseEntity<ResponseWrapper<?>> createCustomer(@RequestBody CustomerClientRequest customerClientRequest) throws InvalidInputException, UserAlreadyExistException {

        try {
            Customer customer = gRPCClientService.saveCustomer(customerClientRequest);
            CustomerClientRequest reponse = CustomerClientRequest.builder()
                    .custId(customerClientRequest.getCustId())
                    .name(customerClientRequest.getName())
                    .emailId(customerClientRequest.getEmailId())
                    .mobNo(customerClientRequest.getMobNo())
                    .userName(customerClientRequest.getUserName())
                    .password(customerClientRequest.getPassword())
                    .streetAddress(customerClientRequest.getStreetAddress())
                    .city(customerClientRequest.getCity())
                    .state(customerClientRequest.getState())
                    .pincode(customerClientRequest.getPincode())
                    .build();
            return new ResponseEntity<>(new ResponseWrapper<>(true, "", reponse), HttpStatus.CREATED);
        } catch (InvalidInputException e) {
            return new ResponseEntity<>(new ResponseWrapper<>(false, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (UserAlreadyExistException e) {
            return new ResponseEntity<>(new ResponseWrapper<>(false, "Username already taken", null), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseWrapper<>(false, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping("/registration/bank")
    public ResponseEntity<ResponseWrapper<?>> createBank(@RequestBody BankClientRequest bankClientRequest) throws InvalidInputException, UserAlreadyExistException {

        try {
            System.out.println(bankClientRequest);
            Bank bank = gRPCClientService.saveBank(bankClientRequest);
            BankClientRequest response = BankClientRequest.builder()
                    .bankId(bankClientRequest.getBankId())
                    .bankName(bankClientRequest.getBankName())
                    .username(bankClientRequest.getUsername())
                    .password(bankClientRequest.getPassword())
                    .build();
            return new ResponseEntity<>(new ResponseWrapper<>(true, "", response), HttpStatus.CREATED);
        } catch (InvalidInputException e) {
            return new ResponseEntity<>(new ResponseWrapper<>(false, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (UserAlreadyExistException e) {
            return new ResponseEntity<>(new ResponseWrapper<>(false, "Username already taken", null), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseWrapper<>(false, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/registration/bankEmp")
    public ResponseEntity<ResponseWrapper<?>> createBankEmp(@RequestBody BankEmpClientRequest bankEmpClientRequest) throws InvalidInputException, UserAlreadyExistException {

        try {
            BankEmp bankEmp = gRPCClientService.saveBankEmp(bankEmpClientRequest);
            BankEmpClientRequest response = BankEmpClientRequest.builder()
                    .empId(bankEmpClientRequest.getEmpId())
                    .username(bankEmpClientRequest.getUsername())
                    .password(bankEmpClientRequest.getPassword())
                    .ifscCode(bankEmpClientRequest.getIfscCode())
                    .build();
            return new ResponseEntity<>(new ResponseWrapper<>(true, "", response), HttpStatus.CREATED);
        } catch (InvalidInputException e) {
            return new ResponseEntity<>(new ResponseWrapper<>(false, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (UserAlreadyExistException e) {
            return new ResponseEntity<>(new ResponseWrapper<>(false, "Username already taken", null), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseWrapper<>(false, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }

    }


    @PostMapping("/registration/branch")
    public ResponseEntity<ResponseWrapper<?>> createBranch(@RequestBody BranchClientRequest branchClientRequest) throws InvalidInputException, UserAlreadyExistException {
        try {
            Branch branch = gRPCClientService.saveBranch(branchClientRequest);
            BranchClientRequest response = BranchClientRequest.builder()
                    .ifscCode(branchClientRequest.getIfscCode())
                    .bankId(branchClientRequest.getBankId())
                    .streetAddress(branchClientRequest.getStreetAddress())
                    .city(branchClientRequest.getCity())
                    .state(branchClientRequest.getState())
                    .pincode(branchClientRequest.getPincode())
                    .build();
            return new ResponseEntity<>(new ResponseWrapper<>(true, "", response), HttpStatus.CREATED);
        } catch (InvalidInputException e) {
            return new ResponseEntity<>(new ResponseWrapper<>(false, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (UserAlreadyExistException e) {
            return new ResponseEntity<>(new ResponseWrapper<>(false, "Username already taken", null), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseWrapper<>(false, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/login/{username}/{password}/{role}")
    public ResponseEntity<?> loginUser(@PathVariable String username, @PathVariable String
            password, @PathVariable String role) throws InvalidRoleException, InvalidCredentialsException {
        String result;
        System.out.println("In controller");
        result = userAuthenticationService.generateToken(username, password, role);
        if (result.equals("InvalidRole"))
            throw new InvalidRoleException();
        else if (result.equals("InvalidUser"))
            throw new InvalidCredentialsException();
        System.out.println(result);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @PutMapping(value = "/update/bank")
    public ResponseEntity<?> UpdateBank(@RequestBody BankClientRequest bankClientRequest) {

        try {
            String response = gRPCClientService.UpdateBank(bankClientRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserDoesNotExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/Update/customer")
    public ResponseEntity<?> UpdateCustomer(@RequestBody CustomerClientRequest customerClientRequest) {
        try {
            String response = gRPCClientService.UpdateCustomer(customerClientRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserDoesNotExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        }

        @PutMapping(value = "/update/branch")
        public ResponseEntity<?> UpdateBranch (@RequestBody BranchClientRequest branchClientRequest){
            try {
                String response = gRPCClientService.UpdateBranch(branchClientRequest);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (UserDoesNotExistException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }

        @PutMapping(value = "/update/bankEmp")
        public ResponseEntity<?> UpdateBankEmp (@RequestBody BankEmpClientRequest bankEmpClientRequest) throws
        UserDoesNotExistException {
            try {
                String response = gRPCClientService.UpdateBankEmp(bankEmpClientRequest);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (UserDoesNotExistException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }


        @GetMapping(value = "customer/profile/{username}")
        public CustomerClientRequest getCustomer (@PathVariable String username){
            Customer customer = gRPCClientService.customerGetByUserName(username);
            return CustomerClientRequest.builder()
                    .custId(customer.getCustId())
                    .name(customer.getName())
                    .emailId(customer.getEmailId())
                    .mobNo(customer.getMobNo())
                    .userName(customer.getUserName())
                    .password(customer.getPassword())
                    .streetAddress(customer.getStreetAddress())
                    .city(customer.getCity())
                    .state(customer.getState())
                    .pincode(customer.getPincode())
                    .build();

        }

        @GetMapping(value = "bank/profile/{username}")
        public BankClientRequest getBank (@PathVariable String username){
            Bank bank = gRPCClientService.bankGetByUsername(username);

            return BankClientRequest.builder()
                    .bankId(bank.getBankId())
                    .password(bank.getPassword())
                    .username(bank.getUsername())
                    .bankName(bank.getBankName())
                    .build();
        }

        @GetMapping(value = "bankEmp/profile/{username}")
        public BankEmpClientRequest getBankEmo (@PathVariable String username){
            BankEmp bankEmp = gRPCClientService.bankEmpGetByUsername(username);

            return BankEmpClientRequest.builder()
                    .empId(bankEmp.getEmpId())
                    .username(bankEmp.getUsername())
                    .password(bankEmp.getPassword())
                    .ifscCode(bankEmp.getIfscCode())
                    .build();
        }

        @GetMapping(value = "branch/profile/{ifscCode}")
        public BranchClientRequest getBranch (@PathVariable String ifscCode){
            Branch branch = gRPCClientService.branchGetByIfsc(ifscCode);

            return BranchClientRequest.builder()
                    .bankId(branch.getBankId())
                    .ifscCode(branch.getIfscCode())
                    .pincode(branch.getPincode())
                    .state(branch.getState())
                    .pincode(branch.getPincode())
                    .city(branch.getCity())
                    .build();
        }


    }
