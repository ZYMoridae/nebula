package com.jz.nebula;

import com.jz.nebula.jwt.JwtConfigurer;
import com.jz.nebula.service.TokenService;
import com.jz.nebula.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    /**
     * For API route
     */
    @Order(2)
    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
    class ApiSecurityConfiguration extends WebSecurityConfigurerAdapter {
        private final Logger logger = LogManager.getLogger(CmsSecurityConfiguration.class);
        @Autowired
        TokenService jwtTokenProvider;

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            logger.debug("ApiSecurityConfiguration:: entered");
            //TODO: Set csrf enable in the production environment
            http.antMatcher("/api/**").httpBasic().disable().csrf().disable().sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
                    .antMatchers("/css/**").permitAll()
                    .antMatchers("/js/**").permitAll()
                    .antMatchers("/api/sso/authorize").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/users").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/token/refresh").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/products").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/products/{\\d+}").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/products/{\\d+}/comments").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/home-banners/active").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/news").permitAll()
                    .antMatchers("/swagger-ui.html").permitAll()
                    // Below is for the Swagger path by pass
                    .antMatchers("/v2/api-docs",
                            "/configuration/ui",
                            "/swagger-resources/**",
                            "/configuration/security",
                            "/swagger-ui.html",
                            "/webjars/**").permitAll()
                    .anyRequest().authenticated().and().apply(new JwtConfigurer(jwtTokenProvider));
        }
//
//        @Override
//        public void configure(WebSecurity web) throws Exception {
//            web.ignoring()
//                    .antMatchers("/api/sso/authorize", "/api/users", "/api/token/refresh", "/api/products", "/api/products/{\\d+}", "/api/products/{\\d+}/comments", "/api/home-banners/active", "/api/news");
//        }
    }

    /**
     * Below WebSecurityConfig is for CMS route
     */
    @Order(1)
    @Configuration
    class CmsSecurityConfiguration extends WebSecurityConfigurerAdapter {
        private final Logger logger = LogManager.getLogger(CmsSecurityConfiguration.class);

        @Autowired
        private UserService managerDetailsService;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            logger.debug("CmsSecurityConfiguration:: entered");
            http.antMatcher("/cms/**").csrf().disable()
                    .authorizeRequests().anyRequest().authenticated().and()
                    .formLogin()
                    .loginPage("/cms/login").defaultSuccessUrl("/cms/home", true)
                    .permitAll().and()
                    .logout()
                    .permitAll();
        }


        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring()
                    .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/webfonts/**");
        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(managerDetailsService);
        }

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }

}
