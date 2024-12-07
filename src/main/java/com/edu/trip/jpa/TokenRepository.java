package com.edu.trip.jpa;

import com.edu.trip.model.TokenEntity;
import com.edu.trip.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    TokenEntity findByToken(String token);

    void deleteAllByUser(UserEntity user);

}
