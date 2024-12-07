package com.edu.trip.security;


import com.edu.trip.jpa.TokenRepository;
import com.edu.trip.jpa.UserRepository;
import com.edu.trip.model.TokenEntity;
import com.edu.trip.po.UserRole;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtInterceptHandler implements HandlerInterceptor {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    private static Map<String, UserRole> userRoleMap = new HashMap<>() {{
        put("/customer/", UserRole.CUSTOMER);
        put("/employee/", UserRole.EMPLOYEE);
    }};

    private static String[] allowAllRoleSet = {"/token/refreshToken", "/account/modifyPassword", "/view/trip/read", "/view/trip/readSingle"};

    private static String[] authIfSetAuthorizationPrefix = {
            "/view/"
    };


    private void reject(HttpServletResponse response) throws IOException {
        response.getWriter().println("Invalid access credential. Maybe credential expired");
        response.setStatus(401);
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        log.info("JwtInterceptHandler::preHandle() {}", uri);
        boolean skipValidate = false;
        for (String skippable : authIfSetAuthorizationPrefix) {
            if (uri.startsWith(skippable)) {
                skipValidate = true;
                break;
            }
        }
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            if (skipValidate) {
                return true;
            }
            reject(response);
            return false;
        }
        Claims claims = null;
        try {
            claims = jwtTokenUtil.parseTokenClaims(authHeader);
        } catch (Throwable e) {
            e.printStackTrace();
            reject(response);
            return false;
        }

        Date expiration = claims.getExpiration();
        if (expiration.before(new Date())) {
            reject(response);
            return false;
        }
        String username = claims.get(JwtTokenUtil.CLAIM_KEY_USERNAME, String.class);
        String role = claims.get(JwtTokenUtil.CLAIM_KEY_ROLE, String.class);
        UserRole roleRes = null;
        try {
            roleRes = UserRole.valueOf(role);
        } catch (Throwable e) {
            e.printStackTrace();
            reject(response);
            return false;
        }
        TokenEntity token = tokenRepository.findByToken(authHeader);
        if (token == null) {
            reject(response);
            return false;
        }
        boolean skipRoleValidate = false;
        for (String skipUri : allowAllRoleSet) {
            if (uri.equals(skipUri)) {
                skipRoleValidate = true;
                break;
            }
        }
        if (!skipRoleValidate) {
            UserRole validate = null;
            for (String prefix : userRoleMap.keySet()) {
                if (uri.startsWith(prefix)) {
                    validate = userRoleMap.get(prefix);
                    break;
                }
            }
            if (validate == null) {
                response.getWriter().write("Invalid Path. Not Found");
                response.setStatus(404);
                return false;
            }
            if (!validate.name().equalsIgnoreCase(role)) {
                reject(response);
                return false;
            }
            if (!token.getUser().getRole().equals(role)) {
                reject(response);
                return false;
            }
        }

        if (!token.getUser().getUsername().equals(username)) {
            reject(response);
            return false;
        }
        request.setAttribute("userId", token.getUser().getId());
        request.setAttribute("role", roleRes);
        return true;
    }


}
