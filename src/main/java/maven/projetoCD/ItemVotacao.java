package maven.projetoCD;

public class ItemVotacao {
	private String _id;
	private String _rev = null;
	public String nome;
	public int contagem;
	
	public ItemVotacao(String id, String nome) {
		this._id = id;
		this.nome = nome;
		this.contagem = 0;
	}
	
	public String getId() {
		return this._id;
	}
	
	public void vote() {
		this.contagem++;
	}
}
