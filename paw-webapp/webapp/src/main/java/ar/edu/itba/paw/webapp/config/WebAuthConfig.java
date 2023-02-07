package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.basic.BasicAuthenticationProvider;
import ar.edu.itba.paw.webapp.auth.JwtFilter;
import ar.edu.itba.paw.webapp.auth.PawUserDetailsService;
import ar.edu.itba.paw.webapp.auth.cors.CorsFilter;
import ar.edu.itba.paw.webapp.auth.voters.AntMatcherVoter;
import ar.edu.itba.paw.webapp.auth.jwt.JwtAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;


import java.util.Arrays;
import java.util.Collections;

@ComponentScan({ "ar.edu.itba.paw.webapp.auth" })
@EnableWebSecurity
@Configuration
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Value("${key}")
    private String key;

    @Autowired
    private PawUserDetailsService userDetailsService;

    @Autowired
    private BasicAuthenticationProvider basicAuthenticationProvider;

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AntMatcherVoter antMatcherVoter() { return new AntMatcherVoter();}

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        auth.authenticationProvider(basicAuthenticationProvider);
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .exceptionHandling()
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                    .headers()
                        .cacheControl().disable()
                .and()
                    .authorizeRequests()
////////////////////////////////////////////////////  auth filters:
//USERS:
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.GET, "/users/auth").authenticated()
                .antMatchers(HttpMethod.GET, "/users/{id}").access("@antMatcherVoter.canAccessUser(authentication, #id)")
                .antMatchers(HttpMethod.PATCH, "/users/{id}").access("@antMatcherVoter.canAccessUser(authentication, #id)")
                .antMatchers(HttpMethod.DELETE, "/users/{id}").access("@antMatcherVoter.canAccessUser(authentication, #id)")

//RESTAURANT:
                .antMatchers(HttpMethod.GET, "/restaurants/{id}").permitAll()
                .antMatchers(HttpMethod.PATCH, "/restaurants/{id}").hasRole("RESTAURANT")//access("@antMatcherVoter.canPatchRestaurant(authentication, #id) or hasRole('RESTAURANT')")
                .antMatchers(HttpMethod.GET, "/restaurants/{id}/").permitAll()
//CUSTOMERS:
                .antMatchers(HttpMethod.GET, "/customers/{id}").access("@antMatcherVoter.canAccessCustomer(authentication, #id)")
                .antMatchers(HttpMethod.PATCH, "/customers/{id}").hasRole("RESTAURANT")
                .antMatchers(HttpMethod.DELETE, "/customers/{id}").hasRole("RESTAURANT")
                .antMatchers(HttpMethod.GET, "/customers").hasRole("RESTAURANT")
                .antMatchers(HttpMethod.POST, "/customers").permitAll()

//RESERVATIONS:
                .regexMatchers(HttpMethod.GET, "\\/reservations\\?.*customerId=[1-9][0-9]*.*").access("@antMatcherVoter.canAccessReservationList(authentication, request.getQueryString()) or hasRole('RESTAURANT')")
                .antMatchers(HttpMethod.POST, "/reservations").access("@antMatcherVoter.canPostReservation(authentication) or hasRole('RESTAURANT')")
                .antMatchers("/reservations/{securityCode}").access("@antMatcherVoter.canAccessReservation(authentication, #securityCode) or hasRole('RESTAURANT')")
                .antMatchers("/reservations").hasRole("RESTAURANT")

//ORDER ITEMS:
                .antMatchers(HttpMethod.GET, "/reservations/{securityCode}/orderItems/{orderItemId}").access("@antMatcherVoter.canAccessOrderItem(authentication, #securityCode, #orderItemId) or hasRole('RESTAURANT')")
                .antMatchers(HttpMethod.PATCH, "/reservations/{securityCode}/orderItems/{orderItemId}").access("@antMatcherVoter.canAccessOrderItem(authentication, #securityCode, #orderItemId) or hasRole('RESTAURANT')")
                .antMatchers(HttpMethod.GET, "/reservations/{securityCode}/orderItems").access("@antMatcherVoter.canAccessOrderItems(authentication, #securityCode) or hasRole('RESTAURANT')")
                .antMatchers(HttpMethod.POST, "/reservations/{securityCode}/orderItems").access("@antMatcherVoter.canAccessOrderItems(authentication, #securityCode) or hasRole('RESTAURANT')")
                .antMatchers(HttpMethod.GET, "/reservations/orderItems").hasRole("RESTAURANT")

//DISHES:
                .antMatchers(HttpMethod.GET, "/restaurants/{restaurantId/dishes/{dishId}").permitAll()
                .antMatchers(HttpMethod.PATCH, "/restaurants/{restaurantId/dishes/{dishId}").hasRole("RESTAURANT")
                .antMatchers(HttpMethod.DELETE, "/restaurants/{restaurantId/dishes/{dishId}").hasRole("RESTAURANT")
                .antMatchers(HttpMethod.GET, "/restaurants/{restaurantId}/dishes").permitAll()
                .antMatchers(HttpMethod.POST, "/restaurants/{restaurantId}/dishes").hasRole("RESTAURANT")

//DISH CATEGORIES:
                .antMatchers(HttpMethod.GET, "/restaurants/{restaurantId}/dishCategories").permitAll()
                .antMatchers(HttpMethod.POST, "/restaurants/{restaurantId}/dishCategories").hasRole("RESTAURANT")
                .antMatchers(HttpMethod.GET, "/restaurants/{restaurantId}/dishCategories/{categoryId}").permitAll()
                .antMatchers(HttpMethod.PATCH, "/restaurants/{restaurantId}/dishCategories/{categoryId}").hasRole("RESTAURANT")
                .antMatchers(HttpMethod.DELETE, "/restaurants/{restaurantId}/dishCategories/{categoryId}").hasRole("RESTAURANT")

//IMAGES:
                .antMatchers(HttpMethod.GET,"/resources/images/{imageId}").permitAll()
                .antMatchers(HttpMethod.PUT,"/resources/images/{dishId}").hasRole("RESTAURANT")

//END
                .antMatchers("**").permitAll()//.denyAll() //todo: define behaviour
//////////////////////////////////////////////////// auth filters^^
                .and().addFilterBefore(new JwtFilter(authenticationManagerBean(), authenticationFailureHandler), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
                .csrf().disable();
        super.configure(http);
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "favicon.ico", "/img/**");
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // todo: Cuidado con esto sacar de produccion
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration cors = new CorsConfiguration();
//        cors.setAllowedOrigins(Collections.singletonList("*"));
//        cors.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
//        cors.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
//        cors.setExposedHeaders(Arrays.asList("x-auth-token", "authorization", "X-Total-Pages", "Content-Disposition"));
//        cors.addAllowedOrigin("http://localhost:3000/");
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", cors);
//        return source;
//    }
}
