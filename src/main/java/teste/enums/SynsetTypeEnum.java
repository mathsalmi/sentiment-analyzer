package teste.enums;

/**
 * Tipos de synset.
 * 
 * @see http://wordnet.princeton.edu/man/wndb.5WN.html#sect3
 * @author Matheus Salmi <mathsalmi@gmail.com>
 *
 */
public enum SynsetTypeEnum {

	NOUN(1, "n", "NOUN"), VERB(2, "v", "VERB"), ADJECTIVE(3, "a", "ADJECTIVE"), ADVERB(4, "r", "ADVERB");

	private final int id;
	private final String codigo;
	private final String nome;

	SynsetTypeEnum(int id, String codigo, String nome) {
		this.id = id;
		this.codigo = codigo;
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}
}
