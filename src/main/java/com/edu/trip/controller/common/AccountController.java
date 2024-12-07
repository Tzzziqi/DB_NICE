package com.edu.trip.controller.common;

import com.edu.trip.jpa.TokenRepository;
import com.edu.trip.jpa.UserRepository;
import com.edu.trip.model.UserEntity;
import com.edu.trip.params.ChangePasswordParam;
import com.edu.trip.util.AccountCredentialUtil;
import com.edu.trip.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequestMapping("/account")
@RestController
public class AccountController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Transactional
    @RequestMapping("/modifyPassword")
    public ResponseEntity refreshToken(@RequestAttribute(value = "userId", required = false) Long userId,
                                       @RequestBody ChangePasswordParam credentialParam) {
        if (!AccountCredentialUtil.checkPassword(credentialParam.getOldPassword()) || !AccountCredentialUtil.checkPassword(credentialParam.getNewPassword())) {
            return ResponseUtil.fail("Invalid password.");
        }
        String oldPassword = AccountCredentialUtil.md5Encrypt(credentialParam.getOldPassword());
        String newPassword = AccountCredentialUtil.md5Encrypt(credentialParam.getNewPassword());
        if (oldPassword == null || newPassword == null) {
            return ResponseUtil.fail("Invalid password.");
        }

        Optional<UserEntity> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseUtil.fail("Invalid user account"); // impossible
        }
        UserEntity user = userOpt.get();
        if (!user.getPassword().equals(oldPassword)) {
            return ResponseUtil.fail("Password not match");
        }
        user.setPassword(newPassword);
        user = userRepository.saveAndFlush(user);
        tokenRepository.deleteAllByUser(user);
        return ResponseUtil.ok("Change password successfully");
    }




}
