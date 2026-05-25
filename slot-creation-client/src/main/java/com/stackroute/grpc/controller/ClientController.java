package com.stackroute.grpc.controller;

import com.google.protobuf.Timestamp;
import com.stackroute.grpc.client.TransferClientService;
import com.stackroute.grpc.dto.SlotClientRequest;
import com.stackroute.grpc.Slot;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;


@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private TransferClientService transferClientService;


    @PostMapping("/transferslot")
    @ApiOperation(value = "transfer",notes = "Cancel slot and Transfer slot to other bank emp based on slot date, slot time and ifsc code")
    public SlotClientRequest transfer(@RequestBody SlotClientRequest slotClientRequest) {
        Slot slot = TransferClientService.slotTransfer(slotClientRequest);
        return SlotClientRequest.builder()
                .slotId(slot.getSlotId())
                .empId(slot.getEmpId())
                .slotDate(getDateFromGoogleTimeStamp(slot.getSlotDate()))
                .startTime(slot.getStartTime())
                .stopTime(slot.getStopTime())
                .status(slot.getStatus())
                .ifscCode(slot.getIfscCode())
                .build();
    }

    public Date getDateFromGoogleTimeStamp(Timestamp date) {

        Instant instant = Instant.from(Instant
                .ofEpochSecond(date.getSeconds(), date.getNanos())
                .atZone(ZoneId.of("Asia/Kolkata"))
                .toLocalDate().atStartOfDay(ZoneId.of("GMT")));
        return Date.from(instant);
    }

}
