package com.medislot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medislot.entity.Slots;

@Repository
public interface SlotRepo extends JpaRepository<Slots, Integer>{

}
