package com.edu.trip.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.Token;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private String role;

    @JsonIgnore
    private Date createTime;

//    @OneToMany(mappedBy = "user")
//    private List<TokenEntity> tokens;

}
