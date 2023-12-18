package application;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    @FXML
    private AnchorPane AnchorPaneLogin;

    @FXML
    private Button entrar;

    @FXML
    private TextField digitarcpf;

    @FXML
    private Text logincpf;

    @FXML
    private Text loginsenha;

    @FXML
    private Button voltarButton;

    @FXML
    private TextField digitarsenha;

    private AnchorPane root;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        root = AnchorPaneLogin;
    }

    @FXML
    void fazerLogin(ActionEvent event) {
        String cpf = digitarcpf.getText();
        String senha = digitarsenha.getText();

        if (cpf.isEmpty() || senha.isEmpty()) {
        	exibirAlerta("Por favor, preencha todos os campos");
            return;
        }
       
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaPrincipal.fxml"));
            Parent root = loader.load();

            Stage newStage = new Stage();
            newStage.setTitle("Tela Principal");
            newStage.setScene(new Scene(root));
            newStage.show();

            Stage stage = (Stage) entrar.getScene().getWindow();
            stage.close();
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