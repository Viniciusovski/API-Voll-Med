package med.voll.api.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import med.voll.api.domain.usuario.Usuario;

@Service
public class TokenService {
	
	// Ler tags do application.properties
	@Value("${api.security.token.secret}")
	private String secret;
	// Montando token com diversas informações do usuario
	public String gerarToken(Usuario usuario) {
		try {
		    var algoritmo = Algorithm.HMAC256(secret);
		    return JWT.create()
		        .withIssuer("API Voll.med")
		        .withSubject(usuario.getLogin())
		        .withClaim("id", usuario.getId())
		        .withExpiresAt(dataExpiracao()) // <-- Data para expirar o token
		        .sign(algoritmo);
		} catch (JWTCreationException exception){
		    throw new RuntimeException("Erro ao gerar token jwt", exception);
		}
	}

	private Instant dataExpiracao() {
		// Instant faz parte da API de datas do Java 8
		// Defini 2 horas de expiracao baseado no fuso horario brasileiro
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
	
}
