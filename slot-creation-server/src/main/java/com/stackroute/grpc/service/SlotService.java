package com.stackroute.grpc.service;
import com.google.protobuf.Timestamp;
import com.stackroute.grpc.entity.Slot;
import com.stackroute.grpc.slotCreateGrpc;
import com.stackroute.grpc.repository.SlotTransferRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.*;
import java.util.Date;
import java.util.List;


@GrpcService
public class SlotService extends slotCreateGrpc.slotCreateImplBase {

    @Autowired
    private SlotTransferRepository transferRepository;


    @Override
    public void transferSlot(com.stackroute.grpc.Slot request, StreamObserver<com.stackroute.grpc.Slot> responseObserver) {
        String slotId = request.getSlotId();
        int empId = request.getEmpId();

        Timestamp date = request.getSlotDate();
        Instant instant = Instant.from(Instant
                .ofEpochSecond(date.getSeconds(), date.getNanos())
                        .atZone(ZoneId.of("Europe/London"))
                .toLocalDate().atStartOfDay(ZoneId.of("GMT")));
        
        String startTime = request.getStartTime();
        String stopTime = request.getStopTime();
        String status = request.getStatus();
        String ifscCode = request.getIfscCode();
        
        com.stackroute.grpc.Slot response = null;

        LocalDateTime t = LocalDateTime.ofInstant(instant,ZoneId.of("Iceland"));
        Instant newInstant = t.atZone(ZoneId.systemDefault()).toInstant();
        Date slotDate = Date.from(newInstant);

        if(transferRepository.findBySlotId(slotId).getStatus().equalsIgnoreCase("Booked")) {

            List<Slot> allSlots = transferRepository.findAllByIfscCodeAndSlotDateAndStartTimeAndStopTime(ifscCode, slotDate, startTime, stopTime);

            Slot newSlot = findAvailableSlot(allSlots, empId);

            if (newSlot != null) {
                cancelSlot(slotId);
                Slot newSlotDetails = transferRepository.findBySlotId(newSlot.getSlotId());

                response = com.stackroute.grpc.Slot.newBuilder()
                        .setSlotId(newSlotDetails.getSlotId())
                        .setEmpId(newSlotDetails.getEmpId())
                        .setSlotDate(convertDateToGoogleTimestamp(newSlotDetails.getSlotDate()))
                        .setStartTime(newSlotDetails.getStartTime())
                        .setStopTime(newSlotDetails.getStopTime())
                        .setStatus(newSlotDetails.getStatus())
                        .setIfscCode(newSlotDetails.getIfscCode())
                        .build();
            }
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }



    public Slot findAvailableSlot(List<Slot> allSlots, int empId){
        for (Slot slotDetails : allSlots) {
            if (slotDetails.getStatus().equalsIgnoreCase("Avaliable") && slotDetails.getEmpId() != empId) {
                slotDetails.setStatus("Booked");
                transferRepository.save(slotDetails);
                return  slotDetails;

            }
        }
        return null;
    }

    public void cancelSlot(String slotId){
        Slot s = transferRepository.findBySlotId(slotId);
        s.setStatus("Cancelled");
        transferRepository.save(s);
    }

    protected Timestamp convertDateToGoogleTimestamp(Date date) {
        Instant instant = date.toInstant();

        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }
}
