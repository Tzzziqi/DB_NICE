package com.edu.trip.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface ResourceRepository<ResourceClass> extends JpaRepository<ResourceClass, Long> {
    List<ResourceClass> findAllByStatus(String status);

    List<ResourceClass> findAllByNameContainingAndStatus(String name, String status);


}
