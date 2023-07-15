package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Habilita a manipulação das configurações de segurança
public class SecurityConfiguration {
	
	@Autowired
	private SecurityFilter securityFilter;
	
	// O objectivo é fazer a aplicação deixar de ser statefull para ser stateless
	// Desabilita o processo padrão do spring para segurança
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    return http.csrf().disable()
	            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	            .and().authorizeRequests()
	            .antMatchers(HttpMethod.POST, "/login").permitAll()
	            .antMatchers(HttpMethod.DELETE, "/medicos").hasRole("ADMIN")
	            .antMatchers(HttpMethod.DELETE, "/pacientes").hasRole("ADMIN")
	            .anyRequest().authenticated()
	            .and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
	            .build();
	}
	
	// @Bean Sem essa anotação no método, o objeto SecurityFilterChain não será exposto como um bean para o Spring.
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	// Algoritmo para decodificar hash de senha
	@Bean
	public PasswordEncoder passwordEnconder() {
		return new BCryptPasswordEncoder();
	}
}
