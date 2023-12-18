package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dominio.Loja;
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
import persistencia.LojaDao;
import application.AlterarDadosLojaController;

public class MinhaLojaController implements Initializable{
	
	    
		@FXML
	    private TableView<Loja> TabelaInfosLoja;
		
		public TableView<Loja> getTabelaInfosLoja() {
	        return TabelaInfosLoja;
	    }
	
	    @FXML
	    private TableColumn<Loja, String> DonoColum;
	
	    @FXML
	    private TableColumn<Loja, String> CPFColum;
	
	    @FXML
	    private TableColumn<Loja, String> CNPjColum;
	
	    @FXML
	    private TableColumn<Loja, String> NomeLojaColum;

    	@FXML
    	private TableColumn<Loja, String> EnderecoColum;

    	@FXML
    	private TableColumn<Loja, String> DataColum;	
    	
    	@FXML
        private TableColumn<Loja, String> EmailColum;

	    @FXML
	    private Button EditarLojaButton;

	    @FXML
	    private Button HistoricoLojaButton;

	    @FXML
	    private Button ListaProdutosButton;

	    @FXML
	    private Button VoltarButton;
	    

	    @FXML
	    private Button AddProdutoButton;
	    
	    public ObservableList<Loja> getObservableListaLojas() {
	        return observableListaLojas;
	    }
	    
	    @FXML
	    void handleEditarLojaButton(ActionEvent event) {
	        try {
	           
	            Loja lojaSelecionada = TabelaInfosLoja.getSelectionModel().getSelectedItem();

	            if (lojaSelecionada != null) {
	                FXMLLoader loader = new FXMLLoader(getClass().getResource("EditarMinhaLoja.fxml"));
	                Parent alterarDadosLojaParent = loader.load();

	                AlterarDadosLojaController alterarDadosLojaController = loader.getController();

	                alterarDadosLojaController.setLojaAtual(lojaSelecionada);
	                alterarDadosLojaController.setMinhaLojaController(this); 

	                Scene alterarDadosLojaScene = new Scene(alterarDadosLojaParent);

	                
	                Stage janela = (Stage) ((Node) event.getSource()).getScene().getWindow();

	              
	                janela.setScene(alterarDadosLojaScene);
	                janela.show();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	            
	        }
	    }

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
	    void handleAdicionarProdutoButton(ActionEvent event) {
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("CadastrarProdutos.fxml"));
	            Parent cadastrarProdutoParent = loader.load();

	           
	            CadastroProdutosController cadastrarProdutoController = loader.getController();
	            cadastrarProdutoController.setMinhaLojaController(this);
	            
	           
	            Loja lojaSelecionada = TabelaInfosLoja.getSelectionModel().getSelectedItem();
	            cadastrarProdutoController.setLojaAtual(lojaSelecionada);

	            Scene cadastrarProdutoScene = new Scene(cadastrarProdutoParent);

	            Stage janela = new Stage();
	            janela.setScene(cadastrarProdutoScene);
	            janela.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    private LojaDao lojaDao;
	    private ObservableList<Loja> observableListaLojas;

	    @Override
	    public void initialize(URL arg0, ResourceBundle arg1) {
	    	 lojaDao = new LojaDao();    

	    	    DonoColum.setCellValueFactory(new PropertyValueFactory<>("nomeVendedor"));
	    	    CPFColum.setCellValueFactory(new PropertyValueFactory<>("cpfVendedor"));
	    	    CNPjColum.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
	    	    NomeLojaColum.setCellValueFactory(new PropertyValueFactory<>("nomeLoja"));
	    	    EnderecoColum.setCellValueFactory(new PropertyValueFactory<>("endereco"));
	    	    DataColum.setCellValueFactory(new PropertyValueFactory<>("dataCriacao"));
	    	    EmailColum.setCellValueFactory(new PropertyValueFactory<>("email")); 
	    	    

	    	    observableListaLojas = FXCollections.observableArrayList();
	    	    TabelaInfosLoja.setItems(observableListaLojas);

	    	    carregarDadosTabela();
	    }
	    
	    @FXML
	    void handleListaProdutosButton(ActionEvent event) {
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLExplorarProdutos.fxml"));
	            Parent explorarProdutosParent = loader.load();

	            Scene explorarProdutosScene = new Scene(explorarProdutosParent);

	            Stage janela = new Stage();
	            janela.setScene(explorarProdutosScene);
	            janela.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
		
	    void carregarDadosTabela() {
	        List<Loja> listaDeLojas = lojaDao.buscarTodasLojas();

	        
	        observableListaLojas.clear();
	        observableListaLojas.addAll(listaDeLojas);

	        TabelaInfosLoja.setItems(observableListaLojas);
	        TabelaInfosLoja.refresh();
	    }
	}
	
