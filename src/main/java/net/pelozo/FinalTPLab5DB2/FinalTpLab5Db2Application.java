package net.pelozo.FinalTPLab5DB2;

import net.pelozo.FinalTPLab5DB2.filter.JWTAuthorizationFilter;
import net.pelozo.FinalTPLab5DB2.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@SpringBootApplication
//Si se le saca el comentario a la linea de abajo y matás el metodo configure se deshabilita spring security
(exclude = SecurityAutoConfiguration.class)
public class FinalTpLab5Db2Application {
	public static void main(String[] args) {
		SpringApplication.run(FinalTpLab5Db2Application.class, args);
	}

	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter{

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
					.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests()
					.antMatchers(HttpMethod.POST, "/measurements").permitAll()
					.antMatchers(HttpMethod.POST, "/login").permitAll()
					.antMatchers(HttpMethod.POST, "/backoffice/login").permitAll()
					.antMatchers("/console/**").permitAll() //TODO borrar esta linea
					.antMatchers("/tariff/**").hasAuthority(User.TYPE.BACKOFFICE.name())
					.antMatchers("/clients/**").hasAuthority(User.TYPE.BACKOFFICE.name())
					.anyRequest().authenticated();

			http.headers().frameOptions().disable(); //TODO remove this (h2 console won't work w/o this line)
		}
	}

}
