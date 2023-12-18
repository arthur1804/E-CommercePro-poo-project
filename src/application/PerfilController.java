package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dominio.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import persistencia.UsuarioDao;

public class PerfilController implements Initializable {
	
	@FXML
    private AnchorPane Perfil;
	
    @FXML
    private Button PerfilEditarButton;

    @FXML
    private Button HistoricoComprasButton;
    
    @FXML
    private Button voltarButton;

    @FXML
    private TableView<Usuario> InformacoesUsuarioTable;

    @FXML
    private TableColumn<Usuario, String> nomeColumn;

    @FXML
    private TableColumn<Usuario, String> emailColumn;

    @FXML
    private TableColumn<Usuario, String> senhaColumn;

    @FXML
    private TableColumn<Usuario, String> cpfColumn;

    private ObservableList<Usuario> usuariosData = FXCollections.observableArrayList();

    private UsuarioDao usuarioDao = new UsuarioDao();
    
    @FXML
    void handleHistoricoComprasButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLHistoricoCompras.fxml"));
            Parent historicoComprasParent = loader.load();

            HistoricoComprasController historicoComprasController = loader.getController();

            Scene historicoComprasScene = new Scene(historicoComprasParent);

            Stage janela = (Stage) ((Node) event.getSource()).getScene().getWindow();

            janela.setScene(historicoComprasScene);
            janela.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void handleVoltarButton(ActionEvent event) throws IOException {
        Parent telaPrincipalParent = FXMLLoader.load(getClass().getResource("TelaPrincipal.fxml"));
        Scene telaPrincipalScene = new Scene(telaPrincipalParent);

        Stage janela = (Stage) ((Node) event.getSource()).getScene().getWindow();

        janela.setScene(telaPrincipalScene);
        janela.show();
    }
    
    @FXML
    void handleEditarPerfilButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLEditarPerfil.fxml"));
            Parent editarPerfilParent = loader.load();

            EditarPerfilController editarPerfilController = loader.getController();

            Usuario usuarioAtual = InformacoesUsuarioTable.getSelectionModel().getSelectedItem();
            editarPerfilController.setUsuarioAtual(usuarioAtual);

            Scene editarPerfilScene = new Scene(editarPerfilParent);
            editarPerfilScene.setUserData(this); 

            Stage janela = new Stage();

            janela.setScene(editarPerfilScene);
            janela.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void atualizarTabelaUsuarios() {
        usuariosData.clear();
        usuariosData.addAll(usuarioDao.buscarTodosUsuarios());
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        senhaColumn.setCellValueFactory(new PropertyValueFactory<>("senha"));
        cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        usuariosData.addAll(usuarioDao.buscarTodosUsuarios());

        InformacoesUsuarioTable.setItems(usuariosData);

        InformacoesUsuarioTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                handleEditarPerfilButton(null);
            }
        });
    }
}
