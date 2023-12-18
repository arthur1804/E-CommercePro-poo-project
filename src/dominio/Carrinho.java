package dominio;

public class Carrinho extends Transacao {
    private Usuario usuario;

    public Carrinho() {
        super(0, ""); // Valores fictícios, só pra simular
    }

    public Carrinho(int id, Usuario usuario) {
        super(id, usuario.getCpf());
        this.usuario = usuario;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}