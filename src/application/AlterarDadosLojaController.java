package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dominio.Loja;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import persistencia.LojaDao;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlterarDadosLojaController implements Initializable {

    @FXML
    private TextField AlterarNomeField;

    @FXML
    private TextField AlterarCpfField;

    @FXML
    private TextField AlterarCNPJField;
    
    @FXML
    private TextField EmailField;

    @FXML
    private TextField AlterarNomeLojaField;

    @FXML
    private TextField AlterarEnderecoField;

    @FXML
    private Button atualizarDadosButton;

    @FXML
    private Button voltarButton;

    private Loja lojaAtual;
    private MinhaLojaController minhaLojaController; 

    public void setLojaAtual(Loja lojaAtual) {
        this.lojaAtual = lojaAtual;
        if (lojaAtual != null) {
            exibirDadosLoja();
        }
    }

    private void exibirDadosLoja() {
    	
    	if (EmailField != null) {
    	   EmailField.setText(lojaAtual.getEmail());
    	}
    	 
        AlterarNomeField.setText(lojaAtual.getNomeVendedor());
        AlterarCpfField.setText(lojaAtual.getCpfVendedor());
        AlterarCNPJField.setText(lojaAtual.getCnpj());
        AlterarNomeLojaField.setText(lojaAtual.getNomeLoja());
        AlterarEnderecoField.setText(lojaAtual.getEndereco());
        EmailField.setText(lojaAtual.getEmail());
    }

    public void setMinhaLojaController(MinhaLojaController minhaLojaController) {
        this.minhaLojaController = minhaLojaController;
    }

    @FXML
    void handleAtualizarDadosButton(ActionEvent event) {
        if (lojaAtual != null && minhaLojaController != null) {
            TableView<Loja> tabelaLoja = minhaLojaController.getTabelaInfosLoja();

            if (tabelaLoja != null) {
               
                String novoNomeVendedor = AlterarNomeField.getText();
                String novoCpfVendedor = AlterarCpfField.getText();
                String novoCnpj = AlterarCNPJField.getText();
                String novoNomeLoja = AlterarNomeLojaField.getText();
                String novoEndereco = AlterarEnderecoField.getText();
                String novoEmail =  EmailField.getText();

                lojaAtual.setNomeVendedor(novoNomeVendedor);
                lojaAtual.setCpfVendedor(novoCpfVendedor);
                lojaAtual.setCnpj(novoCnpj);
                lojaAtual.setNomeLoja(novoNomeLoja);
                lojaAtual.setEndereco(novoEndereco);
				lojaAtual.setEmail(novoEmail);

               
                LojaDao lojaDao = new LojaDao();
                lojaDao.atualizarLoja(lojaAtual);

                minhaLojaController.carregarDadosTabela();
                
                showAlert("Alterado com sucesso!");

                System.out.println("Dados atualizados na tabela:");
                minhaLojaController.getObservableListaLojas().forEach(loja -> System.out.println(loja));
            }
        }
    }
    
    @FXML
    void handleVoltarButton(ActionEvent event) throws IOException {
    	
        Parent telaPrincipalParent = FXMLLoader.load(getClass().getResource("TelaPrincipal.fxml"));
        Scene telaPrincipalScene = new Scene(telaPrincipalParent);

        Stage janela = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        janela.setScene(telaPrincipalScene);
        janela.show();

        minhaLojaController.carregarDadosTabela();
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
    
}