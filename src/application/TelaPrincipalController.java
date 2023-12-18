package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class TelaPrincipalController implements Initializable {
		@FXML
	    private AnchorPane Principal;

	   @FXML
	    private Button PerfilButton;

	    @FXML
	    private Button CarrinhoButton;

	    @FXML
	    private Button ExplorarButton;

	    @FXML
	    private Button CreateLojaButton;

	    @FXML
	    private Button MinhaLojaButtom;

	    @FXML
	    private Button VerHistóricoComprasButton;
	    
	    private AnchorPane root;
	    
	    @FXML
		public void handlePerfilButton(ActionEvent event) throws IOException {
		        changeScreen("FXMLPerfil.fxml");
		}
	    
	    @FXML
		public void handleCarrinhoButton(ActionEvent event) throws IOException {
		        changeScreen("FXMLCarrinho.fxml");
		}
	    
	    @FXML
	    public void handleExplorarButton(ActionEvent event) throws IOException {
	        	changeScreen("FXMLExplorarProdutos.fxml");
	      
	    }
	    
	    @FXML
		public void handleCriarButton(ActionEvent event) throws IOException {
		        changeScreen("FXMLCriarLoja.fxml");
		}
	    
	    @FXML
		public void handleMinhaLojaButton(ActionEvent event) throws IOException {
		        changeScreen("FXMLMinhaLoja.fxml");
		}
	    
	    @FXML
	  		public void handleHistoricoButton(ActionEvent event) throws IOException {
	  		        changeScreen("FXMLHistoricoCompras.fxml");
	  		}
	    
	    
	    private void changeScreen(String fxml) throws IOException {
	        Parent newContent = FXMLLoader.load(getClass().getResource(fxml));
	        ObservableList<Node> children = Principal.getChildren();
	        children.clear();
	        children.add(newContent);
	    }
	    
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			root = Principal;
			
		}
}
