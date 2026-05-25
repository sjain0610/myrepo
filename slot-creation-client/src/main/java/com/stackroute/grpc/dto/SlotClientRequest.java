package com.stackroute.grpc.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SlotClientRequest {

    @ApiModelProperty(notes = "The unique Id of slot creation which is auto generated")
    private String slotId;
    @ApiModelProperty(notes = "The Employee id assigned to the slot")
    private int empId;
    @ApiModelProperty(notes = "The Date of slot booked for ")
    private Date slotDate;
    @ApiModelProperty(notes = "The Start time of slot")
    private String startTime;
    @ApiModelProperty(notes = "The End time of slot")
    private String stopTime;
    @ApiModelProperty(notes = "The Status of slot (Booked , Available or Cancelled)")
    private String status;
    @ApiModelProperty(notes = "The Ifsc code of bank employee assigned to the slot")
    private String ifscCode;
}
