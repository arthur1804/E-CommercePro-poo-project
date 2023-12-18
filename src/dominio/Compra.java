package dominio;

import java.math.BigDecimal;
import java.util.Date;

public class Compra extends Transacao implements MetodoPagamento {
	private Date dataCompra;
	private BigDecimal valorTotal;
	private String tipoPagamento;

	public Compra(int id, String usuarioCpf, Date dataCompra, BigDecimal valorTotal) {
		super(id, usuarioCpf);
		this.dataCompra = dataCompra;
		this.valorTotal = valorTotal;
	}

	public Date getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(Date dataCompra) {
		this.dataCompra = dataCompra;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

	@Override
	public void pagarComDebito(double valor) {
		if (valorTotal.doubleValue() != valor) {
			throw new IllegalArgumentException("Valor do débito incorreto.");
		}
		
	}

	@Override
	public void pagarComCredito(double valor) {
		BigDecimal limiteCredito = obterLimiteCredito();

		if (valorTotal.doubleValue() > limiteCredito.doubleValue() || valorTotal.doubleValue() != valor) {
			throw new IllegalArgumentException(
					"Pagamento com crédito falhou. Valor incorreto ou limite de crédito insuficiente.");
		}

	}

	public void pagarComCreditoParcelado(double valor, int parcelas) {
		validarPagamentoCredito(valor, parcelas);
		
	}

	@Override
	public void pagarComBoleto(double valor) {
		if (valorTotal.doubleValue() != valor) {
			throw new IllegalArgumentException("Valor do boleto incorreto.");
		}
		
	}

	@Override
	public void pagarComPix(double valor) {
		if (valorTotal.doubleValue() != valor) {
			throw new IllegalArgumentException("Valor do PIX incorreto.");
		}
		
	}

	private void validarPagamentoCredito(double valor, int parcelas) {
		BigDecimal limiteCredito = obterLimiteCredito();

		if (valorTotal.doubleValue() > limiteCredito.doubleValue() || valorTotal.doubleValue() != valor) {
			throw new IllegalArgumentException(
					"Pagamento com crédito parcelado falhou. Valor incorreto ou limite de crédito insuficiente.");
		}
	}

	private BigDecimal obterLimiteCredito() {
		return new BigDecimal("1000.00");
		
	}
}
