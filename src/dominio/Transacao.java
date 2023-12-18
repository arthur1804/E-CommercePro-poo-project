package dominio;

public abstract class Transacao extends Entidade {
	private String usuarioCpf;

	public Transacao(int id, String usuarioCpf) {
		super(id);
		this.usuarioCpf = usuarioCpf;
	}

	public String getUsuarioCpf() {
		return usuarioCpf;
	}

	public void setUsuarioCpf(String usuarioCpf) {
		this.usuarioCpf = usuarioCpf;
	}

}
