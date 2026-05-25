package com.stackroute.repository;

import com.stackroute.entity.Bank;
import com.stackroute.entity.BankEmp;
import com.stackroute.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,String> {
    Customer findByUsernameAndPassword(String username, String password);
    Customer findByUsername(String username);

}
