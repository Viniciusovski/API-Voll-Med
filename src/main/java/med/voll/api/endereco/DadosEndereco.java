package med.voll.api.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(
		@NotBlank
		String logradouro,
		
		@NotBlank
		String bairro,
		
		@NotBlank
		@Pattern(regexp = "\\d{8}") // Expressao regular que define em 8 digitos
		String cep,
		
		@NotBlank
		String cidade,
		
		@NotBlank
		String uf,
		
		@NotBlank
		String complemento,

		@NotBlank
		String numero) {
	
}
