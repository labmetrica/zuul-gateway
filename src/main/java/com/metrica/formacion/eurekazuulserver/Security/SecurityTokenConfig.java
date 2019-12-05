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
                    .antMatchers(HttpMethod.POST,"/clientes/**").permitAll()
                    .antMatchers(HttpMethod.PUT,"/clientes/**").permitAll()
                    .antMatchers(HttpMethod.DELETE,"/clientes/**").permitAll()

                    //Api Grupos -
                    .antMatchers(HttpMethod.GET,"/grupos/**").permitAll()
                    .antMatchers(HttpMethod.POST,"/grupos/**").permitAll()
                    .antMatchers(HttpMethod.PUT,"/grupos/**").permitAll()
                    .antMatchers(HttpMethod.DELETE,"/grupos/**").permitAll()

                    //Metrica service -
                    .antMatchers(HttpMethod.GET,"/serviceMetrica/**").permitAll()
                    .antMatchers(HttpMethod.PUT,"/serviceMetrica/**").permitAll()
                    .antMatchers(HttpMethod.POST,"/serviceMetrica/**").permitAll()
                    .antMatchers(HttpMethod.DELETE,"/serviceMetrica/**").permitAll()
                    .anyRequest().permitAll();
    }
}
