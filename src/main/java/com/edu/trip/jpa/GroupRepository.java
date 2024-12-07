package com.edu.trip.jpa;

import com.edu.trip.model.GroupEntity;
import com.edu.trip.model.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {


}
