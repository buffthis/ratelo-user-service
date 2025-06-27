package com.ratelo.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:3000", "https://unblind.kr", "https://hyeongjun.me")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Bean
    public FilterRegistrationBean<Filter> sameSiteCookieFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter((ServletRequest request, ServletResponse response, FilterChain chain) -> {
            chain.doFilter(request, response);
            if (response instanceof HttpServletResponse res) {
                for (String header : res.getHeaders("Set-Cookie")) {
                    if (header.startsWith("JSESSIONID")) {
                        res.setHeader("Set-Cookie", header + "; SameSite=None; Secure");
                    }
                }
            }
        });
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
