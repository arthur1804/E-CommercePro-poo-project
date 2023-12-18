package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import dominio.Produto;

public class ProdutoDao {
	private Connection conexao;

	public ProdutoDao() {
		this.conexao = ConexaoBanco.getConnection();
	}

	public void inserirProduto(Produto produto) {
		String sql = "INSERT INTO produto (nome, preco, categoria, loja_id) VALUES (?, ?, ?, ?)";

		try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, produto.getNome());
			stmt.setDouble(2, produto.getPreco());
			stmt.setString(3, produto.getCategoria());
			stmt.setInt(4, produto.getLojaId());

			stmt.executeUpdate();

			try (ResultSet rs = stmt.getGeneratedKeys()) {
				if (rs.next()) {
					produto.setId(rs.getInt(1));
				}
			}
		} catch (SQLException e) {
			System.out.println("erro tentar inserir");
			e.printStackTrace();
		}
	}

	public Produto buscarProdutoPorId(int id) {
		String sql = "SELECT * FROM produto WHERE id = ?";

		try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.setInt(1, id);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return criarProdutoAPartirResultSet(rs);
				}
			}
		} catch (SQLException e) {
			System.out.println("erro na busca");
			e.printStackTrace();
		}

		return null;
	}

	public List<Produto> buscarTodosProdutos() {
		List<Produto> produtos = new ArrayList<>();
		String sql = "SELECT * FROM produto";

		try (Statement stmt = conexao.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				Produto produto = criarProdutoAPartirResultSet(rs);
				produtos.add(produto);
			}
		} catch (SQLException e) {
			System.out.println("erro na busca");
			e.printStackTrace();
		}

		return produtos;
	}

	public void atualizarProduto(Produto produto) {
		String sql = "UPDATE produto SET nome = ?, preco = ?, categoria = ?, loja_id = ? WHERE id = ?";

		try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.setString(1, produto.getNome());
			stmt.setDouble(2, produto.getPreco());
			stmt.setString(3, produto.getCategoria());
			stmt.setInt(4, produto.getLojaId());
			stmt.setInt(5, produto.getId());

			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("erro ao tentar atualizar");
			e.printStackTrace();
		}
	}

	public void excluirProduto(int id) {
		String sql = "DELETE FROM produto WHERE id = ?";

		try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.setInt(1, id);

			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("erro na exclusão");
			e.printStackTrace();
		}
	}

	private Produto criarProdutoAPartirResultSet(ResultSet rs) throws SQLException {
		int id = rs.getInt("id");
		String nome = rs.getString("nome");
		double preco = rs.getDouble("preco");
		String categoria = rs.getString("categoria");
		int lojaId = rs.getInt("loja_id");

		return new Produto(id, nome, preco, categoria, lojaId);
	}
}
