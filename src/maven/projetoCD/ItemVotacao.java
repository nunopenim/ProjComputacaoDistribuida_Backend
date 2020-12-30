package maven.projetoCD;

public class ItemVotacao {
	private String _id;
	private String _rev = null;
	private String nome;
	private int contagem;
	
	public ItemVotacao(String id, String nome) {
		this._id = id;
		this.nome = nome;
		this.contagem = 0;
	}
	
	public String toString() {
		return this._id + "|" + this.nome + "|" + this.contagem;
	}
	
	public void vote() {
		this.contagem++;
	}
}
