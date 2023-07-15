package med.voll.api.infra.security;

import java.io.IOException;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;

@Component // essa notação é utilizada quando queremos que o spring carregue uma classe generica
public class SecurityFilter extends OncePerRequestFilter{
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// Metodo para chamar um filtro a cada reguisição
		// FilterChain representa a cadeia de filtros da aplicação
		
		//Recuperar o token e passalo no cabeçalho da requisição
		var tokenJWT = recuperarToken(request);
		
		if(tokenJWT != null) {
			// Valida o token e recupera o subject
			var subject = tokenService.getSubject(tokenJWT);
			
			// Trazendo o objeto usuario
			var usuario = usuarioRepository.findByLogin(subject);
			
			
			// Fazer o spring considerar o usuario autenticado
			Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication );
		}
		
		// Necessário para chamar os próximos filtros na aplicação
		filterChain.doFilter(request, response);
		
	}

	private String recuperarToken(HttpServletRequest request) {
		var authorizationHeader = request.getHeader("Authorization");
		if(authorizationHeader != null) {
			// Retira o prefixo padrão do Token JWT, o Bearer do token
			return authorizationHeader.replace("Bearer", "");
		}
		
		return null;
		
		
	}

}
