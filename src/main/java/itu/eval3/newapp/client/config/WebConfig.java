package itu.eval3.newapp.client.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import itu.eval3.newapp.client.interceptors.AuthInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns(
                    "/hr/**",
                    "/home/**", 
                    "/dashboard/**", 
                    "/user/**", 
                    "/api/**")
                ;
    }
}

