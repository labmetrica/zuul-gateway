package com.metrica.formacion.eurekazuulserver.Security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .addFilterAfter(new JwtTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()

                    //Authentication server -  visible para todos
                    .antMatchers(HttpMethod.POST,"/auth").permitAll()

                    //Api Usuarios -
                    .antMatchers(HttpMethod.GET,"/clientes/**").permitAll()
                    .antMatchers(HttpMethod.POST,"/clientes/**").authenticated()
                    .antMatchers(HttpMethod.PUT,"/clientes/**").authenticated()
                    .antMatchers(HttpMethod.DELETE,"/clientes/**").hasRole("ADMIN")

                    //Api Grupos -
                    .antMatchers(HttpMethod.GET,"/grupos/**").permitAll()
                    .antMatchers(HttpMethod.POST,"/grupos/**").authenticated()
                    .antMatchers(HttpMethod.PUT,"/grupos/**").authenticated()
                    .antMatchers(HttpMethod.DELETE,"/grupos/**").hasRole("ADMIN")

                    //Metrica service -
                    .antMatchers(HttpMethod.GET,"/serviceMetrica/**").permitAll()
                    .antMatchers(HttpMethod.PUT,"/serviceMetrica/**").authenticated()
                    .antMatchers(HttpMethod.POST,"/serviceMetrica/**").authenticated()
                    .antMatchers(HttpMethod.DELETE,"/serviceMetrica/**").hasRole("ADMIN")
                    .anyRequest().authenticated();
    }
}
