package application;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dominio.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import persistencia.UsuarioDao;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CadastroController implements Initializable{
	
	@FXML
	private AnchorPane AnchorPaneCadastro;
	
	@FXML
    private Button cadastrarButton;

    @FXML
    private TextField cpfField;

    @FXML
    private TextField nomeField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField senhaField;

    @FXML
    private CheckBox primeCheckBox;
    
    @FXML
    private Button VoltarButton;
    
    private AnchorPane root;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		 root = AnchorPaneCadastro;
	}
	
	@FXML
	void cadastrarUsuario(ActionEvent event) {
	    String cpf = cpfField.getText();
	    String nome = nomeField.getText();
	    String email = emailField.getText();
	    String senha = senhaField.getText();
	    boolean isPrime = primeCheckBox.isSelected();

	    if (cpf.isEmpty() || nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
	        exibirAlerta("Por favor, preencha todos os campos");
	        return;
	    }

	    Usuario novoUsuario = new Usuario(cpf, nome, email, senha, isPrime);

	    UsuarioDao usuarioDao = new UsuarioDao();
	    usuarioDao.inserirUsuario(novoUsuario);

	    exibirAlerta("Usuário cadastrado com sucesso!");

	    Stage stage = (Stage) cadastrarButton.getScene().getWindow();
	    stage.close();

	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaPrincipal.fxml"));
	        Parent root = loader.load();

	        Stage newStage = new Stage();
	        newStage.setTitle("Tela Principal");
	        newStage.setScene(new Scene(root));
	        newStage.show();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	
	private void exibirAlerta(String mensagem) {
	    Alert alert = new Alert(AlertType.INFORMATION);
	    alert.setTitle("Informação");
	    alert.setHeaderText(null);
	    alert.setContentText(mensagem);
	    alert.showAndWait();
	}
	
	 @FXML
	    void handleBotaoVoltar(ActionEvent event) throws IOException {
	        Parent telaTelaPrincipalParent = FXMLLoader.load(getClass().getResource("FXMLTela.fxml"));
	        Scene telaTelaPrincipalScene = new Scene(telaTelaPrincipalParent);

	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

	        stage.setScene(telaTelaPrincipalScene);
	        stage.show();
	    }
	}