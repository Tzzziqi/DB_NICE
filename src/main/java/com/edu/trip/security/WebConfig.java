package com.edu.trip.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * ensure managed by spring
     * @return
     */
    @Bean
    public JwtInterceptHandler getJwtHandler() {
        return new JwtInterceptHandler();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getJwtHandler())
                .addPathPatterns("/**") // by default, all request should be authorized
                .excludePathPatterns("/static/**")
                .excludePathPatterns("/auth/**"); // auth-free for some open api / static resource
    }

    /**
     * for our ui server is deployed separately, cors is required for http request.
     * @param registry cors config
     */
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
////        registry.addMapping("/**")
////                .allowedOriginPatterns("*")
////                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")
////                .allowCredentials(true)
////                .maxAge(-1)
////                .allowedHeaders("*");
//        registry.addMapping("/**")
//                .allowedMethods("*")
//                .allowedOriginPatterns("*")
//                .allowedHeaders("*")
//                .allowCredentials(false);
//    }

}