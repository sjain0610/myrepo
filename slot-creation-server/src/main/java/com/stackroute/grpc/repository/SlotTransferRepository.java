package com.stackroute.grpc.repository;

import com.stackroute.grpc.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface SlotTransferRepository extends JpaRepository<Slot,String> {
    List<Slot> findAllByIfscCodeAndSlotDateAndStartTimeAndStopTime(String ifscCode, Date slotDate, String startTime, String stopTime);
    Slot findBySlotId(String slotId);
}
