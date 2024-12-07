package com.edu.trip.jpa;

import com.edu.trip.model.PackageEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends ResourceRepository<PackageEntity> {

    PackageEntity findByPackageIdAndStatus(Long id, String status);


    PackageEntity findByNameAndStatus(String name, String status);

}
