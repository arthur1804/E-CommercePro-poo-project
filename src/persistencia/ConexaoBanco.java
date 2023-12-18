package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {
	private static final String URL = "jdbc:postgresql://localhost:5432/MercadoPOO";
	private static final String USUARIO = "postgres";
	private static final String SENHA = "arthur45";
	private static Connection con;

	private ConexaoBanco() {

	}

	public static Connection getConnection() {
		if (con == null) {
			conectar();
		}
		return con;
	}

	private static void conectar() {
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(URL, USUARIO, SENHA);
		} catch (ClassNotFoundException | SQLException e) {
			throw new RuntimeException("Erro na conexao!", e);
		}
	}

	public static void desconectar() {
		try {
			if (con != null && !con.isClosed()) {
				con.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException("Nao foi possivel desconectar", e);
		}
	}
}