package application;

import dominio.Compra;
import persistencia.CompraDao;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ComprarController implements Initializable {

    @FXML
    private TextField tfUsuarioCpf;

    @FXML
    private TextField tfValorTotal;

    @FXML
    private ComboBox<String> cbTipoPagamento;

    @FXML
    private Button btnRealizarCompra;
    
    @FXML
    private Button VoltarButton;

    @FXML
    private Label lblValorTotal;

    private CompraDao compraDao;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        inicializarComboBoxTipoPagamento();
        compraDao = new CompraDao();
    }

    private void inicializarComboBoxTipoPagamento() {
        ObservableList<String> tiposPagamento = FXCollections.observableArrayList("Débito", "Crédito", "Crédito Parcelado", "Boleto", "PIX");
        cbTipoPagamento.setItems(tiposPagamento);
        cbTipoPagamento.getSelectionModel().selectFirst();
    }

    @FXML
    private void handleBtnRealizarCompra() {
        String usuarioCpf = tfUsuarioCpf.getText();
        BigDecimal valorTotal = new BigDecimal(tfValorTotal.getText());
        String tipoPagamento = cbTipoPagamento.getValue();

        Compra compra = new Compra(0, usuarioCpf, new Date(), valorTotal);
        compra.setTipoPagamento(tipoPagamento);

        compraDao.inserirCompra(compra, tipoPagamento);

        lblValorTotal.setText("Valor Total: " + valorTotal.toString());

        exibirMensagemSucesso("Compra efetuada com sucesso!");
    }

    private void exibirMensagemSucesso(String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
    
    @FXML
    void handleVoltarButton(ActionEvent event) throws IOException {
    	
        Parent telaPrincipalParent = FXMLLoader.load(getClass().getResource("TelaPrincipal.fxml"));
        Scene telaPrincipalScene = new Scene(telaPrincipalParent);

        Stage janela = (Stage) ((Node) event.getSource()).getScene().getWindow();

        janela.setScene(telaPrincipalScene);
        janela.show();
    }
    
}