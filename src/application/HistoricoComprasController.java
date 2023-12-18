package application;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import dominio.Compra;
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
import javafx.stage.Stage;
import persistencia.CompraDao;

public class HistoricoComprasController implements Initializable{
	
	@FXML
	private TableView<Compra> HistoricoComprasTable;

	@FXML
	private TableColumn<Compra, Integer> idColumn;

	@FXML
	private TableColumn<Compra, Date> dataColumn;

	@FXML
	private TableColumn<Compra, BigDecimal> valorTotalColumn;


	    @FXML
	    private Button VoltarButton;
	    
	    @FXML
	    void handleVoltarButton(ActionEvent event) throws IOException {
	        Parent telaPrincipalParent = FXMLLoader.load(getClass().getResource("TelaPrincipal.fxml"));
	        Scene telaPrincipalScene = new Scene(telaPrincipalParent);

	        Stage janela = (Stage) ((Node) event.getSource()).getScene().getWindow();

	        janela.setScene(telaPrincipalScene);
	        janela.show();
	    }

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			 idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		     dataColumn.setCellValueFactory(new PropertyValueFactory<>("dataCompra"));
		     valorTotalColumn.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));

		     carregarHistoricoCompras();
			
		}
		
		private void carregarHistoricoCompras() {
		    CompraDao compraDao = new CompraDao();
		    List<Compra> historicoCompras = compraDao.buscarTodasCompras();

		    ObservableList<Compra> observableHistorico = FXCollections.observableArrayList(historicoCompras);

		    HistoricoComprasTable.setItems(observableHistorico);
		}

	

}
