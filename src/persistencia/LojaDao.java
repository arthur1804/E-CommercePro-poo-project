package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import dominio.Loja;

public class LojaDao {
	private Connection conexao;

	public LojaDao() {
		this.conexao = ConexaoBanco.getConnection();
	}

	public void inserirLoja(Loja loja) {
	    String sql = "INSERT INTO loja (nome_vendedor, cpf_vendedor, nome_loja, endereco, email, cnpj, data_criacao) "
	            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

	    try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	        stmt.setString(1, loja.getNomeVendedor());
	        stmt.setString(2, loja.getCpfVendedor());
	        stmt.setString(3, loja.getNomeLoja());
	        stmt.setString(4, loja.getEndereco());
	        stmt.setString(5, loja.getEmail());
	        stmt.setString(6, loja.getCnpj());
	        
	        
	        if (loja.getDataCriacao() != null) {
	            stmt.setDate(7, new java.sql.Date(loja.getDataCriacao().getTime()));
	        } else {
	            stmt.setNull(7, java.sql.Types.DATE);
	        }

	        stmt.executeUpdate();

	        try (ResultSet rs = stmt.getGeneratedKeys()) {
	            if (rs.next()) {
	                loja.setId(rs.getInt(1));
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("erro ao tentar inserir loja");
	        e.printStackTrace();
	    }
	}

	public Loja buscarLojaPorId(int id) {
		String sql = "SELECT * FROM loja WHERE id = ?";

		try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.setInt(1, id);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return criarLojaAPartirResultSet(rs);
				}
			}
		} catch (SQLException e) {
			System.out.println("erro na busca");
			e.printStackTrace();
		}

		return null;
	}

	public List<Loja> buscarTodasLojas() {
		List<Loja> lojas = new ArrayList<>();
		String sql = "SELECT * FROM loja";

		try (Statement stmt = conexao.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				Loja loja = criarLojaAPartirResultSet(rs);
				lojas.add(loja);
			}
		} catch (SQLException e) {
			System.out.println("erro na busca");
			e.printStackTrace();
		}

		return lojas;
	}

	public void atualizarLoja(Loja loja) {
	    String sql = "UPDATE loja SET nome_vendedor = ?, cpf_vendedor = ?, nome_loja = ?, endereco = ?, "
	            + "email = ?, cnpj = ?, data_criacao = ? WHERE id = ?";

	    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
	        stmt.setString(1, loja.getNomeVendedor());
	        stmt.setString(2, loja.getCpfVendedor());
	        stmt.setString(3, loja.getNomeLoja());
	        stmt.setString(4, loja.getEndereco());
	        stmt.setString(5, loja.getEmail());
	        stmt.setString(6, loja.getCnpj());

	      
	        if (loja.getDataCriacao() != null) {
	            stmt.setDate(7, new java.sql.Date(loja.getDataCriacao().getTime()));
	        } else {
	            stmt.setNull(7, java.sql.Types.DATE);
	        }

	        stmt.setInt(8, loja.getId());

	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println("erro ao atualizar");
	        e.printStackTrace();
	    }
	}

	public void excluirLoja(int id) {
		String sql = "DELETE FROM loja WHERE id = ?";

		try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.setInt(1, id);

			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("erro na exclusão");
			e.printStackTrace();
		}
	}

	private Loja criarLojaAPartirResultSet(ResultSet rs) throws SQLException {
		int id = rs.getInt("id");
		String nomeVendedor = rs.getString("nome_vendedor");
		String cpfVendedor = rs.getString("cpf_vendedor");
		String nomeLoja = rs.getString("nome_loja");
		String endereco = rs.getString("endereco");
		String email = rs.getString("email");
		String cnpj = rs.getString("cnpj");
		Date dataCriacao = rs.getDate("data_criacao");

		return new Loja(id, nomeVendedor, cpfVendedor, nomeLoja, endereco, email, cnpj, dataCriacao);
	}

}
