package com.example.crud_app.security;
import com.example.crud_app.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfigurator{

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf()
                .disable()
                .authorizeRequests(configurer ->
                configurer
                        .antMatchers("/").permitAll()
                        .antMatchers("/posts/create/post").hasAnyAuthority(Constants.ADMIN, Constants.AUTHOR)
                        .antMatchers("/posts/filter").permitAll()
                        .antMatchers("/posts/update/**").hasAnyAuthority(Constants.ADMIN, Constants.AUTHOR)
                        .antMatchers("/posts/delete/**").hasAnyAuthority(Constants.ADMIN, Constants.AUTHOR)
                        .antMatchers("/posts/drafts/**").hasAnyAuthority(Constants.ADMIN, Constants.AUTHOR)
                        .antMatchers("/drafts/read/**").hasAnyAuthority(Constants.ADMIN, Constants.AUTHOR)
                        .antMatchers("/comment/create").permitAll()
                        .antMatchers("/comment/delete/**").hasAnyAuthority(Constants.ADMIN, Constants.AUTHOR)
                        .antMatchers("/comment/update/**").hasAnyAuthority(Constants.ADMIN, Constants.AUTHOR)
                        .antMatchers("/error").permitAll()
                )
                .formLogin()
                .and()

                .logout(LogoutConfigurer::permitAll)
                .build();
    }
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

}
