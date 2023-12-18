package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dominio.Loja;
import dominio.Produto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import persistencia.ProdutoDao;

public class CadastroProdutosController implements Initializable {
	
	private MinhaLojaController minhaLojaController;
	private Loja lojaAtual;
	
	public void setMinhaLojaController(MinhaLojaController minhaLojaController) {
        this.minhaLojaController = minhaLojaController;
    }
	
	public void setLojaAtual(Loja lojaAtual) {
        this.lojaAtual = lojaAtual;
    }

    @FXML
    private Button voltarButton;

    @FXML
    private Button RegistrarButton;

    @FXML
    private TextField categoriaProdutoField;

    @FXML
    private TextField precoProdutoField;

    @FXML
    private TextField NomeProdutoField;
    
    @FXML
    private void initialize() {
        System.out.println("LojaAtual inicializada como: " + lojaAtual);
    }
    
    @FXML
    void handleCadastrarProdutoButton(ActionEvent event) {
    	 System.out.println("LojaAtual no momento do clique: " + lojaAtual);
    	 
        if (lojaAtual != null) {
           
            String nome = NomeProdutoField.getText();
            String precoText = precoProdutoField.getText();
            String categoria = categoriaProdutoField.getText();

            if (nome.isEmpty() || precoText.isEmpty() || categoria.isEmpty()) {
                System.out.println("Preencha todos os campos obrigatórios.");
                return;
            }

            try {
                double preco = Double.parseDouble(precoText);

                
                Produto novoProduto = new Produto(0, nome, preco, categoria, lojaAtual.getId());

      
	            ProdutoDao produtoDao = new ProdutoDao();
	            produtoDao.inserirProduto(novoProduto);
               
                minhaLojaController.carregarDadosTabela();

                System.out.println("Produto cadastrado com sucesso!");

                showAlert("Produto cadastrado com sucesso!");
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
            } catch (NumberFormatException e) {
               
                System.out.println("O preço deve ser um número válido.");
                e.printStackTrace();
            } catch (Exception e) {
               
                System.out.println("Erro ao cadastrar o produto.");
                e.printStackTrace();
            }
        } else {
            
            System.out.println("A lojaAtual é nula. Verifique a lógica de atribuição da loja atual.");
        }
    }
    
    
    @FXML
    void handleVoltarButton(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
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
