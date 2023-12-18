package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import dominio.ItemCarrinho;

public class itemCarrinhoDao {
	private Connection conexao;

	public itemCarrinhoDao() {
		this.conexao = ConexaoBanco.getConnection();
	}

	public void inserirItemCarrinho(ItemCarrinho itemCarrinho) {
		String sql = "INSERT INTO itens_carrinho (carrinho_id, produto_id, quantidade) VALUES (?, ?, ?)";

		try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.setInt(1, itemCarrinho.getCarrinhoId());
			stmt.setInt(2, itemCarrinho.getProdutoId());
			stmt.setInt(3, itemCarrinho.getQuantidade());

			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("erro na inclusão");
			e.printStackTrace();
		}
	}

	public List<ItemCarrinho> buscarItensPorCarrinhoId(int carrinhoId) {
		List<ItemCarrinho> itensCarrinho = new ArrayList<>();
		String sql = "SELECT * FROM itens_carrinho WHERE carrinho_id = ?";

		try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.setInt(1, carrinhoId);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					ItemCarrinho itemCarrinho = criarItemCarrinhoAPartirResultSet(rs);
					itensCarrinho.add(itemCarrinho);
				}
			}
		} catch (SQLException e) {
			System.out.println("erro na busca");
			e.printStackTrace();
		}

		return itensCarrinho;
	}

	public List<ItemCarrinho> buscarItensPorProdutoId(int produtoId) {
		List<ItemCarrinho> itensCarrinho = new ArrayList<>();
		String sql = "SELECT * FROM itens_carrinho WHERE produto_id = ?";

		try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.setInt(1, produtoId);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					ItemCarrinho itemCarrinho = criarItemCarrinhoAPartirResultSet(rs);
					itensCarrinho.add(itemCarrinho);
				}
			}
		} catch (SQLException e) {
			System.out.println("erro na busca");
			e.printStackTrace();
		}

		return itensCarrinho;
	}

	public List<ItemCarrinho> buscarTodosItensCarrinho() {
		List<ItemCarrinho> itensCarrinho = new ArrayList<>();
		String sql = "SELECT * FROM itens_carrinho";

		try (Statement stmt = conexao.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				ItemCarrinho itemCarrinho = criarItemCarrinhoAPartirResultSet(rs);
				itensCarrinho.add(itemCarrinho);
			}
		} catch (SQLException e) {
			System.out.println("erro na busca");
			e.printStackTrace();
		}

		return itensCarrinho;
	}

	public void atualizarQuantidade(ItemCarrinho itemCarrinho) {
		String sql = "UPDATE itens_carrinho SET quantidade = ? WHERE carrinho_id = ? AND produto_id = ?";

		try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.setInt(1, itemCarrinho.getQuantidade());
			stmt.setInt(2, itemCarrinho.getCarrinhoId());
			stmt.setInt(3, itemCarrinho.getProdutoId());

			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("algo deu errado ao tentar atualizar");
			e.printStackTrace();
		}
	}

	public void excluirItemCarrinho(int carrinhoId, int produtoId) {
		String sql = "DELETE FROM itens_carrinho WHERE carrinho_id = ? AND produto_id = ?";

		try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.setInt(1, carrinhoId);
			stmt.setInt(2, produtoId);

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("erro na exclusão");
		}
	}

	private ItemCarrinho criarItemCarrinhoAPartirResultSet(ResultSet rs) throws SQLException {
		int carrinhoId = rs.getInt("carrinho_id");
		int produtoId = rs.getInt("produto_id");
		int quantidade = rs.getInt("quantidade");

		return new ItemCarrinho(carrinhoId, produtoId, quantidade);
	}
	
	public int obterCarrinhoId(String usuarioCpf) {
	    if (!usuarioExiste(usuarioCpf)) {
	        return -1; 
	    }

	    String sqlSelect = "SELECT id FROM carrinho WHERE usuario_cpf = ?";
	    String sqlInsert = "INSERT INTO carrinho (usuario_cpf) VALUES (?) RETURNING id";

	    try (PreparedStatement stmtSelect = conexao.prepareStatement(sqlSelect);
	         PreparedStatement stmtInsert = conexao.prepareStatement(sqlInsert)) {

	        stmtSelect.setString(1, usuarioCpf);

	        try (ResultSet rs = stmtSelect.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1);
	            } else {
	                stmtInsert.setString(1, usuarioCpf);

	                try (ResultSet generatedKeys = stmtInsert.executeQuery()) {
	                    if (generatedKeys.next()) {
	                        return generatedKeys.getInt(1);
	                    } else {
	                        return -1; 
	                    }
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return -1; 
	    }
	}
	
	private boolean usuarioExiste(String usuarioCpf) {   
	     String sql = "SELECT COUNT(*) FROM usuario WHERE cpf = ?";
	     try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
	         stmt.setString(1, usuarioCpf);
	         try (ResultSet rs = stmt.executeQuery()) {
	             return rs.next() && rs.getInt(1) > 0;
	         }
	     } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	     }

}
	public boolean existeItemNoCarrinho(int carrinhoId, int produtoId) {
        String sql = "SELECT COUNT(*) FROM itens_carrinho WHERE carrinho_id = ? AND produto_id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, carrinhoId);
            stmt.setInt(2, produtoId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
