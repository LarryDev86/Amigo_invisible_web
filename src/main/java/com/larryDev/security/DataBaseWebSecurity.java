package com.larryDev.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class DataBaseWebSecurity {

    @Bean
    public UserDetailsManager useresCustom(DataSource dataSource){
        //Este es metodo que usa java para comprobar si exites el usuario su contraseña y si esta habilitado ( enable = 0 , 1 )
        JdbcUserDetailsManager user = new JdbcUserDetailsManager(dataSource);
        //En la primera consulta comprueba en BD el nombre contraseña y estado
        user.setUsersByUsernameQuery("select username,password,estatus from Usuarios u where username=?");
        //Si no usamos el cuerpo de bbdd por defecto, entonces debemos definir el nombre de la tabla
        //sino spring buscara author en vez de Usuarios como es la nuestra.
        user.setAuthoritiesByUsernameQuery(
                "select username,perfil " +
                        "from Usuarios where username=?"
        );
        return user;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/admin").authenticated().anyRequest()
                    .permitAll()
            )
            .formLogin(form -> form.permitAll())
            // asi es como se configura el logout de sesion  de spring security
            .logout(logout -> logout
                .logoutSuccessUrl("/") // después de cerrar sesión va a /
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );
        return http.build();
    }
}
