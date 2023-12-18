package application;

import dominio.ItemCarrinho;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import persistencia.ProdutoDao;
import persistencia.itemCarrinhoDao;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CarrinhoController implements Initializable {
	
	private AnchorPane anchorPaneCarrinho;

    @FXML
    private TableView<ItemCarrinho> itensDoCarrinho;

    @FXML
    private TableColumn<ItemCarrinho, Integer> CarrinhoColum;

    @FXML
    private TableColumn<ItemCarrinho, Integer> ProdutoColum;

    @FXML
    private TableColumn<ItemCarrinho, Integer> QuantidadeColum;

    @FXML
    private Button comprarPorCarrinho;

    @FXML
    private Button continuarComprandoButton;

    @FXML
    private Button removerButton;

    @FXML
    private Button voltarButton;

    @FXML
    private ComboBox<Integer> escolherItemdoCarrinho;

    private int carrinhoId;

    private itemCarrinhoDao itemCarrinhoDao;
    
    private ProdutoDao produtoDao;
    
    public void setProdutoDao(ProdutoDao produtoDao) {
        this.produtoDao = produtoDao;
    }
    
    public AnchorPane getAnchorPaneCarrinho() {
        return anchorPaneCarrinho;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	CarrinhoColum.setCellValueFactory(new PropertyValueFactory<>("carrinhoId"));
    	ProdutoColum.setCellValueFactory(new PropertyValueFactory<>("produtoId"));
    	QuantidadeColum.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        itemCarrinhoDao = new itemCarrinhoDao(); 

        atualizarComboBox();
        atualizarTabela();
    }

    private void atualizarComboBox() {
        List<ItemCarrinho> itens = itemCarrinhoDao.buscarItensPorCarrinhoId(carrinhoId);

        ObservableList<Integer> idsProdutos = FXCollections.observableArrayList();
        for (ItemCarrinho item : itens) {
            idsProdutos.add(item.getProdutoId());
        }

        escolherItemdoCarrinho.setItems(idsProdutos);
    }

    public void inicializarCarrinho(int carrinhoId) {
        this.carrinhoId = carrinhoId;
        atualizarTabela();
        atualizarComboBox();
    }

    public void atualizarTabela() {
        itemCarrinhoDao = new itemCarrinhoDao();
        List<ItemCarrinho> itens = itemCarrinhoDao.buscarItensPorCarrinhoId(carrinhoId);
        ObservableList<ItemCarrinho> listaDeItens = FXCollections.observableArrayList(itens);
        itensDoCarrinho.setItems(listaDeItens);
    }

    @FXML
    void handleComprarDireto(ActionEvent event) {
        try {
            URL fxmlUrl = getClass().getResource("FXMLComprar.fxml");

            if (fxmlUrl == null) {
                System.err.println("Arquivo FXML não encontrado!");
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent comprarParent = loader.load();

            ComprarController comprarController = loader.getController();

            Scene comprarScene = new Scene(comprarParent);
            Stage janela = (Stage) ((Node) event.getSource()).getScene().getWindow();

            janela.setScene(comprarScene);
            janela.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleContinuarComprandoButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLExplorarProdutos.fxml"));
        Parent explorarProdutosParent = loader.load();

        ExplorarProdutosController explorarProdutosController = loader.getController();

        Scene explorarProdutosScene = new Scene(explorarProdutosParent);

        Stage janela = (Stage) ((Node) event.getSource()).getScene().getWindow();

        janela.setScene(explorarProdutosScene);
        janela.show();
    }

    @FXML
    public void handleRemoverItemButton() {
        ItemCarrinho itemSelecionado = itensDoCarrinho.getSelectionModel().getSelectedItem();

        if (itemSelecionado != null) {
            itemCarrinhoDao.excluirItemCarrinho(itemSelecionado.getCarrinhoId(), itemSelecionado.getProdutoId());
            System.out.println("Item removido do carrinho com sucesso.");
            atualizarTabela(); 
        } else {
            System.out.println("Nenhum item selecionado para remover.");
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
}
