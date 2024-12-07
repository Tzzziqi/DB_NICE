package com.edu.trip.controller.common;

import com.edu.trip.data_view.TokenDataView;
import com.edu.trip.jpa.TokenRepository;
import com.edu.trip.model.TokenEntity;
import com.edu.trip.security.JwtTokenUtil;
import com.edu.trip.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/token")
@RestController
public class TokenController {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping("/refreshToken")
    public ResponseEntity refreshToken(@RequestHeader("Authorization") String authorization) {
        TokenEntity tokenEntity = tokenRepository.findByToken(authorization);
        if (tokenEntity == null) {
            // means login concurrency and leads to refresh lots of time, reject is fine for us;
            return ResponseUtil.fail(HttpStatus.BAD_REQUEST, "Too Fast Access. Rejected");
        }

        String newToken = jwtTokenUtil.generateToken(tokenEntity.getUser().getUsername(), tokenEntity.getUser().getRole());
        tokenEntity.setToken(newToken);
        tokenRepository.save(tokenEntity);
        return ResponseUtil.ok(new TokenDataView(
                newToken, tokenEntity.getUser().getUsername(), tokenEntity.getUser().getRole()
        ));
    }

}
