package application;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class FXMLTela1Controller implements Initializable{
	
	@FXML
	private AnchorPane painelPrincipal;
	
	@FXML
	private Button loginButton;
	
	@FXML
	private Button cadastroButton;
	
	@FXML
	private AnchorPane root;
	
	@FXML
	public void handleLoginButton(ActionEvent event) throws IOException {
	    changeScreen("FXMLogin.fxml");
	}
	
	
	@FXML
	public void handleCadastroButton(ActionEvent event) throws IOException {
	        changeScreen("FXMLCadastro.fxml");
	}
	
	private void changeScreen(String fxml) throws IOException {
        Parent newContent = FXMLLoader.load(getClass().getResource(fxml));
        ObservableList<Node> children = painelPrincipal.getChildren();
        children.clear();
        children.add(newContent);
    }
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		 root = painelPrincipal;
		
	}

}
