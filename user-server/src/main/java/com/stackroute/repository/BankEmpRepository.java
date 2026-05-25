package com.stackroute.repository;

import com.stackroute.entity.Bank;
import com.stackroute.entity.BankEmp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankEmpRepository extends JpaRepository<BankEmp,String> {

    BankEmp findByUsernameAndPassword(String username, String password);
    BankEmp findByUsername(String username);


}
