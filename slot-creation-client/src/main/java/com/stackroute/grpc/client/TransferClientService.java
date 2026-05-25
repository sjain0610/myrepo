package com.stackroute.grpc.client;

import com.google.protobuf.Timestamp;


import com.stackroute.grpc.dto.SlotClientRequest;

import com.stackroute.grpc.Slot;
import com.stackroute.grpc.slotCreateGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TransferClientService {

    public static Slot slotTransfer(SlotClientRequest request) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",8090).usePlaintext().build();
        slotCreateGrpc.slotCreateBlockingStub stub = slotCreateGrpc.newBlockingStub(channel);
        Date date = request.getSlotDate();

        Slot slot = stub.transferSlot(Slot.newBuilder()
                .setSlotId(request.getSlotId())
                .setEmpId(request.getEmpId())
                .setSlotDate(Timestamp.newBuilder()
                        .setSeconds(date.toInstant().getEpochSecond())
                        .setNanos(date.toInstant().getNano())
                        .build())
                .setStartTime(request.getStartTime())
                .setStopTime(request.getStopTime())
                .setStatus(request.getStatus())
                .setIfscCode(request.getIfscCode())
                .build());
        channel.shutdown();
        return slot;
    }
}
