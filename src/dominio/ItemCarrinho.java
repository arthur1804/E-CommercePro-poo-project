package dominio;

public class ItemCarrinho {
	private int carrinhoId;
	private int produtoId;
	private int quantidade;

	public ItemCarrinho(int carrinhoId, int produtoId, int quantidade) {
		this.carrinhoId = carrinhoId;
		this.produtoId = produtoId;
		this.quantidade = quantidade;
	}

	public int getCarrinhoId() {
		return carrinhoId;
	}

	public void setCarrinhoId(int carrinhoId) {
		this.carrinhoId = carrinhoId;
	}

	public int getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(int produtoId) {
		this.produtoId = produtoId;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

}
