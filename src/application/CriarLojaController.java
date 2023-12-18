package application;

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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import dominio.Loja;
import persistencia.LojaDao;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CriarLojaController implements Initializable{

	    @FXML
	    private TextField NomeVendedor;

	    @FXML
	    private TextField CPFVendedor;

	    @FXML
	    private TextField lojaCNPJ;

	    @FXML
	    private TextField NomeLoja;

	    @FXML
	    private TextField lojaEndereço;

	    @FXML
	    private TextField DataCreation;
	    
	    @FXML
	    private TextField EmailLoja;

	    @FXML
	    private Button CriarLojaButton;

	    @FXML
	    private Button voltarButton;
	    
	    @FXML
	    void handleVoltarButton(ActionEvent event) throws IOException {
	    	
	        Parent telaPrincipalParent = FXMLLoader.load(getClass().getResource("TelaPrincipal.fxml"));
	        Scene telaPrincipalScene = new Scene(telaPrincipalParent);

	        Stage janela = (Stage) ((Node) event.getSource()).getScene().getWindow();

	        janela.setScene(telaPrincipalScene);
	        janela.show();
	    }
	    
	    @FXML
	    void handleCriarLojaButton(ActionEvent event) throws IOException {
	        String nomeVendedor = NomeVendedor.getText();
	        String cpfVendedor = CPFVendedor.getText();
	        String cnpjLoja = lojaCNPJ.getText();
	        String nomeLoja = NomeLoja.getText();
	        String enderecoLoja = lojaEndereço.getText();
	        String dataCreation = DataCreation.getText();
	        String emailLoja = EmailLoja.getText();

	        if (nomeVendedor.isEmpty() || cpfVendedor.isEmpty() || cnpjLoja.isEmpty() || nomeLoja.isEmpty()
	                || enderecoLoja.isEmpty() || dataCreation.isEmpty() || emailLoja.isEmpty()) {
	            exibirAlertaErro("Por favor, preencha todos os campos");
	            return;
	        }

	        Loja novaLoja = new Loja(0, nomeVendedor, cpfVendedor, nomeLoja, enderecoLoja, emailLoja, cnpjLoja, null);

	        LojaDao lojaDao = new LojaDao();
	        lojaDao.inserirLoja(novaLoja);

	        exibirAlerta("Loja criada com sucesso!");

	        FXMLLoader loaderMinhaLoja = new FXMLLoader(getClass().getResource("FXMLMinhaLoja.fxml"));
	        Parent minhaLojaParent = loaderMinhaLoja.load();

	        MinhaLojaController minhaLojaController = loaderMinhaLoja.getController();

	        minhaLojaController.carregarDadosTabela();
	        Scene minhaLojaScene = new Scene(minhaLojaParent);

	        Stage janela = (Stage) ((Node) event.getSource()).getScene().getWindow();

	        janela.setScene(minhaLojaScene);
	        janela.show();
	    }

	    private void exibirAlertaErro(String mensagem) {
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Erro");
	        alert.setHeaderText("Por favor, preencha todos os campos");
	        alert.setContentText(mensagem);
	        alert.showAndWait();
	    }
	    
	    private void exibirAlerta(String mensagem) {
	        Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Informação");
	        alert.setHeaderText(null);
	        alert.setContentText(mensagem);
	        alert.showAndWait();
	    }


		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			// TODO Auto-generated method stub
			
		}

}
