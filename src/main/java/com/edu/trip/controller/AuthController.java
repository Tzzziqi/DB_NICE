package com.edu.trip.controller;

import com.edu.trip.data_view.TokenDataView;
import com.edu.trip.jpa.TokenRepository;
import com.edu.trip.jpa.UserRepository;
import com.edu.trip.model.TokenEntity;
import com.edu.trip.model.UserEntity;
import com.edu.trip.params.CredentialParam;
import com.edu.trip.po.UserRole;
import com.edu.trip.security.JwtTokenUtil;
import com.edu.trip.util.AccountCredentialUtil;
import com.edu.trip.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @RequestMapping("/signUp")
    public ResponseEntity signUp(@RequestBody CredentialParam user) {
        if (!AccountCredentialUtil.validateRegisterInput(user.getUsername(), user.getPassword())) {
            return ResponseUtil.fail("Invalid username or password pattern");
        }
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return ResponseUtil.fail("Username already exists");
        }
        String md5 = AccountCredentialUtil.md5Encrypt(user.getPassword());
        if (md5 == null) {
            return ResponseUtil.fail(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error.");
        }

        UserEntity wrap = new UserEntity(null,
                user.getUsername(), md5, UserRole.CUSTOMER.name(), new Date()
        );
        UserEntity userEntity = userRepository.saveAndFlush(wrap);
        return ResponseUtil.ok(String.valueOf(userEntity.getId()));
    }

    @RequestMapping("/signIn")
    public ResponseEntity signIn(@RequestBody CredentialParam credential) {
        if (!AccountCredentialUtil.validateRegisterInput(credential.getUsername(), credential.getPassword())) {
            return ResponseUtil.fail(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }

        UserEntity userEntity = userRepository.findByUsername(credential.getUsername());
        if (userEntity == null) {
            return ResponseUtil.fail(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
        String md5 = AccountCredentialUtil.md5Encrypt(credential.getPassword());
        if (md5 == null) {
            return ResponseUtil.fail(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error.");
        }
        if (!userEntity.getPassword().equals(md5)) {
            return ResponseUtil.fail(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
        String token = jwtTokenUtil.generateToken(credential.getUsername(), userEntity.getRole());
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setToken(token);
        tokenEntity.setUser(userEntity);
        tokenEntity.setCreateTime(new Date());
        tokenRepository.saveAndFlush(tokenEntity);
        return ResponseUtil.ok(new TokenDataView(token, userEntity.getUsername(), userEntity.getRole()));
    }

    public static void main(String[] args) {
        String md5 = AccountCredentialUtil.md5Encrypt("Worker++");
        System.out.println(md5);
    }

}
