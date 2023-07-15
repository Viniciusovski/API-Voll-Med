package med.voll.api.infra.security;

import java.io.IOException;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component // essa notação é utilizada quando queremos que o spring carregue uma classe generica
public class SecurityFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// Metodo para chamar um filtro a cada reguisição
		// FilterChain representa a cadeia de filtros da aplicação
		
		//Recuperar o token e passalo no cabeçalho da requisição
		var tokenJWT = recuperarToken(request);
		
		// Necessário para chamar os próximos filtros na aplicação
		filterChain.doFilter(request, response);
		
	}

	private String recuperarToken(HttpServletRequest request) {
		var authorizationHeader = request.getHeader("Authorization");
		if(authorizationHeader == null) {
			throw new RuntimeException("Token JWT não enviado no cabeçalho Authorization!");
		}
		
		return authorizationHeader;
	}

}
