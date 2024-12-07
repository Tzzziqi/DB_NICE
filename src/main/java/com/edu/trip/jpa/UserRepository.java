package com.edu.trip.jpa;

import com.edu.trip.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);

    List<UserEntity> findAllByOrderByCreateTimeDesc();


}
