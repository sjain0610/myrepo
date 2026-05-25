package com.stackroute.repo;
import com.stackroute.Entity.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueryManagementRepo extends JpaRepository<Query,
        String> {
    Query findByQueryId(String queryId);

    //QueryManagementRepo updateById(String queryId);
    //QueryManagementRepo updateQueryStatus(String queryId);

}