package lk.shaanzx.online_auction_system_backend.config;

import lk.shaanzx.online_auction_system_backend.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@EnableWebSecurity
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    @Autowired
    private UserServiceImpl registerService;
    @Autowired
    private JwtFilter jwtFilter;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(registerService).passwordEncoder(passwordEncoder());
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable).
                cors(Customizer.withDefaults()).authorizeHttpRequests(auth -> auth
                /*.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Add CORS configuration
                .authorizeHttpRequests(auth->auth*/
                        .requestMatchers(
                                "/api/v1/bids/**",
                                "/api/v1/bidCart/**",
                                "/api/v1/auth/authenticate",
                                "/api/v1/user/register",
                                "/api/v1/auth/refreshToken",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html")
                                .permitAll()

                                .requestMatchers(HttpMethod.GET,"/api/v1/users/**").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers(HttpMethod.POST,"/api/v1/users/**").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers(HttpMethod.PUT,"/api/v1/users/**").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers(HttpMethod.DELETE,"/api/v1/users/**").hasAnyAuthority("ADMIN", "USER")

                                .requestMatchers(HttpMethod.GET,"/api/v1/categories/**").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers(HttpMethod.POST,"/api/v1/categories/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/api/v1/categories/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/api/v1/categories/**").hasAuthority("ADMIN")

                                .requestMatchers(HttpMethod.GET,"/api/v1/admin/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/api/v1/admin/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/api/v1/admin/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/api/v1/admin/**").hasAuthority("ADMIN")

                                .requestMatchers(HttpMethod.GET,"api/v1/items/**").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers(HttpMethod.POST,"/api/v1/items/**").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers(HttpMethod.PUT,"/api/v1/items/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/api/v1/items/**").hasAuthority("ADMIN")

                                .anyRequest().
                                authenticated()
                )

                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
 /*   @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8082", "http://127.0.0.1:5500", "http://localhost:63342"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "*"));
        configuration.setAllowCredentials(false);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/v1/", configuration);
        return source;}*/
}
