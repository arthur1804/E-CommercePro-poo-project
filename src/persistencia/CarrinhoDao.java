package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import dominio.Carrinho;
import dominio.Usuario;

public class CarrinhoDao {
	private Connection conexao;

	public CarrinhoDao() {
		this.conexao = ConexaoBanco.getConnection();
	}

	public int inserirCarrinho(String usuarioCpf) {
	    try {
	        conexao.setAutoCommit(false);

	        UsuarioDao usuarioDao = new UsuarioDao();
	        Usuario usuario = usuarioDao.buscarUsuarioPorCPF(usuarioCpf);

	        if (usuario == null) {
	            
	            usuario = new Usuario(usuarioCpf, "Nome do Usuário", "usuario@email.com", "senha123", false);
	            usuarioDao.inserirUsuario(usuario);
	        }

	        String sql = "INSERT INTO carrinho (usuario_cpf) VALUES (?) RETURNING id";
	        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
	            stmt.setString(1, usuario.getCpf());
	            ResultSet rs = stmt.executeQuery();

	            if (rs.next()) {
	                int carrinhoId = rs.getInt("id");
	                conexao.commit();
	                return carrinhoId;
	            }
	        }

	    } catch (SQLException e) {
	        try {
	            conexao.rollback();
	        } catch (SQLException rollbackException) {
	            rollbackException.printStackTrace();
	        }
	        e.printStackTrace();
	    } finally {
	        try {
	            conexao.setAutoCommit(true);
	        } catch (SQLException autoCommitException) {
	            autoCommitException.printStackTrace();
	        }
	    }

	    return -1;
	}


	public Carrinho buscarCarrinhoPorId(int id) {
		String sql = "SELECT * FROM carrinho WHERE id = ?";

		try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.setInt(1, id);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return criarCarrinhoAPartirResultSet(rs);
				}
			}
		} catch (SQLException e) {
			System.out.println("Erro na busca");
			e.printStackTrace();
		}

		return null;
	}

	public List<Carrinho> buscarTodosCarrinhos() {
		List<Carrinho> carrinhos = new ArrayList<>();
		String sql = "SELECT * FROM carrinho";

		try (Statement stmt = conexao.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				Carrinho carrinho = criarCarrinhoAPartirResultSet(rs);
				carrinhos.add(carrinho);
			}
		} catch (SQLException e) {
			System.out.println("Erro na busca");
			e.printStackTrace();
		}

		return carrinhos;
	}

	public void atualizarUsuarioCpf(Carrinho carrinho) {
		String sql = "UPDATE carrinho SET usuario_cpf = ? WHERE id = ?";

		try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.setString(1, carrinho.getUsuarioCpf());
			stmt.setInt(2, carrinho.getId());

			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Erro em atualizar");
			e.printStackTrace();
		}
	}

	public void excluirCarrinho(int id) {
		String sql = "DELETE FROM carrinho WHERE id = ?";

		try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.setInt(1, id);

			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Erro na exclusão");
			e.printStackTrace();
		}
	}
	
	
	private Carrinho criarCarrinhoAPartirResultSet(ResultSet rs) throws SQLException {
	    int id = rs.getInt("id");
	    String usuarioCpf = rs.getString("usuario_cpf");

	    
	    UsuarioDao usuarioDao = new UsuarioDao();
	    Usuario usuario = usuarioDao.buscarUsuarioPorCPF(usuarioCpf);

	    if (usuario == null) {
	        throw new RuntimeException("Usuário com o CPF especificado não encontrado.");
	    }

	    return new Carrinho(id, usuario);
	}
	
	public int buscarCarrinhoPorUsuarioCpf(String usuarioCpf) {
        String sql = "SELECT id FROM carrinho WHERE usuario_cpf = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, usuarioCpf);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro na busca do carrinho");
            e.printStackTrace();
        }

        return -1;
    }
}
