package dominio;

public interface MetodoPagamento {
	void pagarComDebito(double valor);

	void pagarComCredito(double valor);

	void pagarComBoleto(double valor);

	void pagarComPix(double valor);
}