package med.voll.api.domain.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // Essa classe vai fazer o serviço de autenticaçao, mas precisamente de buscar o usuario no banco de dados
public class AutenticacaoService implements UserDetailsService{
	
	@Autowired
	private UsuarioRepository repository;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		
		return repository.findByLogin(login);
	}
	
	// Esta sendo usada pela classe Controller pelo manager por debaixo dos panos

}
