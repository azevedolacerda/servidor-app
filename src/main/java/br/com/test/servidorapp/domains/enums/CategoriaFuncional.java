package br.com.test.servidorapp.domains.enums;

public enum CategoriaFuncional {

	EFETIVO(0, "Efetivo"), CNE(1, "CNE"), SP(2, "Secretário Parlamentar");

	private String nome;
	private Integer id;

	CategoriaFuncional(Integer id, String nome) {
		this.nome = nome;
		this.id = id;
	}
	
	public java.lang.String getNome() {
		return nome;
	}

	public static CategoriaFuncional getById(Long id) {
		for (CategoriaFuncional e : values()) {
			if (e.id.equals(id))
				return e;
		}
		return null;
	}

}