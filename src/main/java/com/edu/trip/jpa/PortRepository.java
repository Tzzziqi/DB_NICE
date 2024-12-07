package com.edu.trip.jpa;

import com.edu.trip.model.PortEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortRepository extends ResourceRepository<PortEntity> {

    PortEntity findByNameAndStatus(String name, String status);

    PortEntity findByPortIdAndStatus(Long name, String status);


    @Query("select p from PortEntity p  where p.status='ACTIVE' and (p.name like %:keyword% or p.address like %:keyword%" +
            " or p.country like %:keyword% or p.state like %:keyword%)")
    List<PortEntity> findAllByKeyword(@Param("keyword") String keyword);


}
