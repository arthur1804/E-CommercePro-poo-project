package dominio;

import java.util.Date;

public class Loja extends Entidade {
	private String nomeVendedor;
	private String cpfVendedor;
	private String nomeLoja;
	private String endereco;
	private String email;
	private String cnpj;
	private Date dataCriacao;

	public Loja(int id, String nomeVendedor, String cpfVendedor, String nomeLoja, String endereco, String email,
			String cnpj, Date dataCriacao) {
		super(id);
		this.nomeVendedor = nomeVendedor;
		this.cpfVendedor = cpfVendedor;
		this.nomeLoja = nomeLoja;
		this.endereco = endereco;
		this.email = email;
		this.cnpj = cnpj;
		this.dataCriacao = dataCriacao;
	}

	public String getNomeVendedor() {
		return nomeVendedor;
	}

	public void setNomeVendedor(String nomeVendedor) {
		this.nomeVendedor = nomeVendedor;
	}

	public String getCpfVendedor() {
		return cpfVendedor;
	}

	public void setCpfVendedor(String cpfVendedor) {
		this.cpfVendedor = cpfVendedor;
	}

	public String getNomeLoja() {
		return nomeLoja;
	}

	public void setNomeLoja(String nomeLoja) {
		this.nomeLoja = nomeLoja;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	@Override
	public String toString() {
	    return "Loja: " + nomeLoja; 
	}

}
