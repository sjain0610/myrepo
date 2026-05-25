package com.stackroute.Entity;

import com.google.gson.internal.bind.JsonTreeReader;
import com.stackroute.bankrush.QueryResponse;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Query {

    private String customerId;
    @Id
    private String queryId;
    private String  queryStatus;
    private String queryDes;
    private String queryCreationDate;
    private String querySubject;


}
