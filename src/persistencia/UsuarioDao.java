package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import dominio.Usuario;

public class UsuarioDao {
	private Connection conexao;

	public UsuarioDao() {
		this.conexao = ConexaoBanco.getConnection();
	}

	public void inserirUsuario(Usuario usuario) {
	    if (usuarioExiste(usuario.getCpf())) {
	        System.out.println("Usuário já existe. Não é possível inserir novamente.");
	        return;
	    }

	    String sql = "INSERT INTO usuario (cpf, nome, email, senha, prime) VALUES (?, ?, ?, ?, ?)";

	    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
	        stmt.setString(1, usuario.getCpf());
	        stmt.setString(2, usuario.getNome());
	        stmt.setString(3, usuario.getEmail());
	        stmt.setString(4, usuario.getSenha());
	        stmt.setBoolean(5, usuario.isPrime());

	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println("Erro ao tentar inserir");
	        e.printStackTrace();
	    }
	}

	private boolean usuarioExiste(String cpf) {
	    String sql = "SELECT COUNT(*) FROM usuario WHERE cpf = ?";

	    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
	        stmt.setString(1, cpf);

	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1) > 0;
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Erro na verificação de existência do usuário");
	        e.printStackTrace();
	    }

	    return false;
	}

	public Usuario buscarUsuarioPorCPF(String cpf) {
		String sql = "SELECT * FROM usuario WHERE cpf = ?";

		try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.setString(1, cpf);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return criarUsuarioAPartirResultSet(rs);
				}
			}
		} catch (SQLException e) {
			System.out.println("erro na busca");
			e.printStackTrace();
		}

		return null;
	}

	public List<Usuario> buscarTodosUsuarios() {
		List<Usuario> usuarios = new ArrayList<>();
		String sql = "SELECT * FROM usuario";

		try (Statement stmt = conexao.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				Usuario usuario = criarUsuarioAPartirResultSet(rs);
				usuarios.add(usuario);
			}
		} catch (SQLException e) {
			System.out.println("erro na busca de usuario");
			e.printStackTrace();
		}

		return usuarios;
	}

	public void atualizarUsuario(Usuario usuario) {
		String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ?, prime = ? WHERE cpf = ?";

		try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.setString(1, usuario.getNome());
			stmt.setString(2, usuario.getEmail());
			stmt.setString(3, usuario.getSenha());
			stmt.setBoolean(4, usuario.isPrime());
			stmt.setString(5, usuario.getCpf());

			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("erro ao tentar atualizar");
			e.printStackTrace();
		}
	}

	public void excluirUsuario(String cpf) {
		String sql = "DELETE FROM usuario WHERE cpf = ?";

		try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.setString(1, cpf);

			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("erro na exclusão");
			e.printStackTrace();
		}
	}

	private Usuario criarUsuarioAPartirResultSet(ResultSet rs) throws SQLException {
		String cpf = rs.getString("cpf");
		String nome = rs.getString("nome");
		String email = rs.getString("email");
		String senha = rs.getString("senha");
		boolean prime = rs.getBoolean("prime");

		return new Usuario(cpf, nome, email, senha, prime);
	}
}
