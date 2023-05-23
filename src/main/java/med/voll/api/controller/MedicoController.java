package med.voll.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.DadosListagemMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
	
	@Autowired
	private MedicoRepository repository;
	
	@PostMapping
	@Transactional // do spring
	public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
		repository.save(new Medico(dados));
	}
	
	@GetMapping // Retorna um Page que tem as informações para paginação quando for trabalhado no frontEnd, por isso foi usado o Page e não o List
	public Page<DadosListagemMedico> listar(Pageable paginacao){
		return repository.findAll(paginacao).map(DadosListagemMedico::new);
	}
	
//	Agora vamos controlar o número de registros exibidos. Para isso, passaremos, na URL, o parâmetro ?size. Se o igualarmos a 1, teremos a exibição de apenas um registro na tela:
//
//		http://localhost:8080/medicos?size=1COPIAR CÓDIGO
//		Obs: Se não passarmos o parâmetro size, o Spring devolverá 20 registros por padrão.
//
//		Para trazermos a página, vamos passar outro parâmetro na URL, após usar um &. Será o parâmetro page. Como a primeira página é representada por page=0, para trazer o próxima, traremos page=1. E assim sucessivamente.
}
