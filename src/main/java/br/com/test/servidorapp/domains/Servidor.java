package br.com.test.servidorapp.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.test.servidorapp.domains.enums.CategoriaFuncional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Servidor {

	@Id 
	@NotBlank(message = "A matrícula deve ser preenchida") 
	@Digits(integer = 10, fraction = 0, message = "A matrícula deve conter apenas números")
	@Column(columnDefinition = "Integer")
	private String matricula;
	
	@Column(length = 60) 
	@NotBlank(message = "O nome deve ser preenchido")
	@Length(min = 5, max = 60, message = "O nome deve ter de 5 a 60 caracteres")
	private String nome;
	
	@NotNull(message = "A categoria funcional deve ser selecionada") 
	private CategoriaFuncional categoriaFuncional;
	
	@Column(length = 20) 
	@NotBlank(message = "A lotação deve ser preenchida")	 
	private String lotacao;
	

}