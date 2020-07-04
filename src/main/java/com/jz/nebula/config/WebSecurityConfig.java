package com.jz.nebula.config;

import com.jz.nebula.config.jwt.JwtConfigurer;
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
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    /**
     * For API route
     */
    @Order(2)
    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
    class ApiSecurityConfiguration extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
//        private final Logger logger = LogManager.getLogger(CmsSecurityConfiguration.class);

        private TokenService jwtTokenProvider;

        @Autowired
        public void setJwtTokenProvider(TokenService jwtTokenProvider) {
            this.jwtTokenProvider = jwtTokenProvider;
        }

        //Swagger Resources
//        @Override
//        public void addResourceHandlers(ResourceHandlerRegistry registry) {
//            registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
//            registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//        }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
//            logger.debug("ApiSecurityConfiguration:: entered");
            //TODO: Set csrf enable in the production environment
            http
                    .authorizeRequests().antMatchers("/v2/api-docs",
                    "/configuration/**",
                    "/swagger*/**", "/webjars/**",
                    "/swagger-ui.html").permitAll().and()

                    .antMatcher("/api/**").httpBasic().disable().csrf().disable().sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
                    .antMatchers("/css/**").permitAll()
                    .antMatchers("/js/**").permitAll()
                    .antMatchers("/api/sso/auth").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/users").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/token/refresh").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/products").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/products/{\\d+}").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/products/{\\d+}/comments").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/home-banners/active").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/news").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/jobs").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/jobs/categories").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/jobs/{\\d+}").permitAll()
//                    .antMatchers("/swagger-ui.html").permitAll()
                    // Below is for the Swagger path by pass

                    .anyRequest().authenticated().and().apply(new JwtConfigurer(jwtTokenProvider));
        }

        //
        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring()
                    .antMatchers("/v2/api-docs",
                            "/swagger*/**", "/webjars/**",
                            "/swagger-resources/**",
                            "/configuration/security",
                            "/swagger-ui.html",
                            "/api/invoices/**");
        }
    }

    /**
     * Below WebSecurityConfig is for CMS route
     */
    @Order(1)
    @Configuration
    class CmsSecurityConfiguration extends WebSecurityConfigurerAdapter {
        private final Logger logger = LogManager.getLogger(CmsSecurityConfiguration.class);

        private UserService managerDetailsService;

        @Autowired
        public void setManagerDetailsService(UserService managerDetailsService) {
            this.managerDetailsService = managerDetailsService;
        }

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
                    .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/webfonts/**", "/invoices/**");
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
