package com.stackroute.grpc.entity;


import lombok.*;

import javax.persistence.*;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Slot")
public class Slot {

    @Id
    @Column(name = "slot_id")
    private String slotId;
    @Column(name = "emp_id")
    private int empId;
    @Column(name = "slot_date")
    private Date slotDate;
    @Column(name = "start_time")
    private String startTime;
    @Column(name = "stop_time")
    private String stopTime;
    @Column(name = "status")
    private String status;
    @Column(name = "ifsc_code")
    private String ifscCode;
}
