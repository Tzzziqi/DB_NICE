package com.edu.trip.jpa;

import com.edu.trip.model.StateroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateroomRepository extends JpaRepository<StateroomEntity, Long> {
    List<StateroomEntity> findAllByStatus(String status);

    StateroomEntity findByStateroomIdAndStatus(Long id, String status);


}
