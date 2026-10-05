package com.medislot.repo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medislot.entity.Doctors;
import com.medislot.entity.SlotStatus;
import com.medislot.entity.Slots;

@Repository
public interface SlotRepo extends JpaRepository<Slots, Integer>{

	boolean existsByDoctorAndSlotdateAndStartTime(Doctors doctor, LocalDate slotdate, LocalTime startTime);
	
	public List<Slots> findByDoctorAndSlotdateAndStatus(Doctors doctor, LocalDate slotdate, SlotStatus status);
}
