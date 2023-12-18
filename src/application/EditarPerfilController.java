package application;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import persistencia.UsuarioDao;

public class EditarPerfilController implements Initializable {
    @FXML
    private Button VoltarButton;

    @FXML
    private Button confirmarButton;

    @FXML
    private TextField nomeField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField senhaField;

    @FXML
    private TextField CpfField;

    private UsuarioDao usuarioDao;
    private Usuario usuarioAtual; 

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        usuarioDao = new UsuarioDao();
    }
    
    public void setUsuarioAtual(Usuario usuarioAtual) {
        this.usuarioAtual = usuarioAtual;
        if (usuarioAtual != null) {
            exibirDadosUsuario();
        }
    }
    
    private void exibirDadosUsuario() {
        nomeField.setText(usuarioAtual.getNome());
        emailField.setText(usuarioAtual.getEmail());
        senhaField.setText(usuarioAtual.getSenha());
        CpfField.setText(usuarioAtual.getCpf());
    }

    @FXML
    private void handleConfirmarButton() {
        String novoNome = nomeField.getText();
        String novoEmail = emailField.getText();
        String novaSenha = senhaField.getText();
        String novoCpf = CpfField.getText();

        if (usuarioAtual != null) {
            usuarioAtual.setNome(novoNome);
            usuarioAtual.setEmail(novoEmail);
            usuarioAtual.setSenha(novaSenha);
            usuarioAtual.setCpf(novoCpf); 

            usuarioDao.atualizarUsuario(usuarioAtual);

            Stage janela = (Stage) confirmarButton.getScene().getWindow();
            janela.close();

            atualizarTelaPerfil();
        } else {
        	mostrarAlerta("Erro", "Usuário nulo", "Não foi possível atualizar. Usuário atual é nulo.");
        }
    }
    
    private void mostrarAlerta(String titulo, String cabecalho, String conteudo) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(conteudo);
        alert.showAndWait();
    }

    private void atualizarTelaPerfil() {
        Scene cenaPerfil = confirmarButton.getScene();

        PerfilController perfilController = (PerfilController) cenaPerfil.getUserData();

        perfilController.atualizarTabelaUsuarios();
    }
    
    @FXML
    void handleVoltarButton(ActionEvent event) throws IOException {
        Stage janela = (Stage) ((Node) event.getSource()).getScene().getWindow();

        janela.close();
    }
   
}
