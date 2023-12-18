package persistencia;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import dominio.Compra;

public class CompraDao {
	private Connection conexao;

	public CompraDao() {
		this.conexao = ConexaoBanco.getConnection();
	}

	public void inserirCompra(Compra compra, String tipoPagamentoEscolhido) {
		String sql = "INSERT INTO compras (usuario_cpf, data_compra, valor_total, tipo_pagamento) VALUES (?, ?, ?, ?)";

		try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, compra.getUsuarioCpf());
			stmt.setDate(2, new java.sql.Date(compra.getDataCompra().getTime()));
			stmt.setBigDecimal(3, compra.getValorTotal());
			stmt.setString(4, tipoPagamentoEscolhido);

			stmt.executeUpdate();

			try (ResultSet rs = stmt.getGeneratedKeys()) {
				if (rs.next()) {
					compra.setId(rs.getInt(1));
				}
			}

		} catch (SQLException e) {
			System.out.println("Algo deu errado!");
			e.printStackTrace();
		}
	}

	public Compra buscarCompraPorId(int id) {
	    String sql = "SELECT * FROM compras WHERE id = ?";

	    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
	        stmt.setInt(1, id);

	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                Compra compra = criarCompraAPartirResultSet(rs);
	                buscarInformacoesPagamento(compra);

	                return compra;
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Erro na busca");
	        e.printStackTrace();
	    }

	    return null;
	}

	private void buscarInformacoesPagamento(Compra compra) {
		String sql = "SELECT tipo_pagamento FROM compras WHERE id = ?";

		try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.setInt(1, compra.getId());

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					String tipoPagamento = rs.getString("tipo_pagamento");
					compra.setTipoPagamento(tipoPagamento);
				}
			}
		} catch (SQLException e) {
			System.out.println("Erro ao buscar informações de pagamento");
			e.printStackTrace();
		}
	}

	public List<Compra> buscarTodasCompras() {
		List<Compra> compras = new ArrayList<>();
		String sql = "SELECT * FROM compras";

		try (Statement stmt = conexao.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				Compra compra = criarCompraAPartirResultSet(rs);
				compras.add(compra);
			}
		} catch (SQLException e) {
			System.out.println("erro na busca");
			e.printStackTrace();
		}

		return compras;
	}

	public void atualizarValorTotal(Compra compra) {
		String sql = "UPDATE compras SET valor_total = ? WHERE id = ?";

		try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.setBigDecimal(1, compra.getValorTotal());
			stmt.setInt(2, compra.getId());

			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("algo deu errado ao tentar atualizar");
			e.printStackTrace();
		}
	}

	public void excluirCompra(int id) {
		String sql = "DELETE FROM compras WHERE id = ?";

		try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.setInt(1, id);

			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("erro na exclusão");
			e.printStackTrace();
		}
	}

	private Compra criarCompraAPartirResultSet(ResultSet rs) throws SQLException {
		int id = rs.getInt("id");
		String usuarioCpf = rs.getString("usuario_cpf");
		Date dataCompra = rs.getDate("data_compra");
		BigDecimal valorTotal = rs.getBigDecimal("valor_total");

		return new Compra(id, usuarioCpf, dataCompra, valorTotal);
	}
}
