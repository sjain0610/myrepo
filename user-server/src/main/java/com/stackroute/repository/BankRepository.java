package com.stackroute.repository;

import com.stackroute.entity.Bank;
import com.stackroute.entity.BankEmp;
import com.stackroute.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public interface BankRepository extends JpaRepository<Bank,String> {
    Bank findByUsernameAndPassword(String username, String password);
    Bank findByUsername(String username);

}
