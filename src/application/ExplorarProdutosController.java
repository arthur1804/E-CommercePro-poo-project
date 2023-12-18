package application;

import dominio.ItemCarrinho;
import dominio.Produto;
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
import javafx.stage.Stage;
import persistencia.CarrinhoDao;
import persistencia.ProdutoDao;
import persistencia.UsuarioDao;
import persistencia.itemCarrinhoDao;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ExplorarProdutosController implements Initializable {

    @FXML
    private TableView<Produto> tabelaProdutos;

    @FXML
    private TableColumn<Produto, Integer> idColum;

    @FXML
    private TableColumn<Produto, String> nomeColum;

    @FXML
    private TableColumn<Produto, Double> precoColum;

    @FXML
    private TableColumn<Produto, String> categoriaColum;

    @FXML
    private TableColumn<Produto, Integer> lojaIdColum;

    @FXML
    private Button addProductToKartButton;

    @FXML
    private Button ComprarDiretoButton;

    @FXML
    private Button ProdutoToCarrinhoButton;

    @FXML
    private Button voltarButton;

    private CarrinhoController carrinhoController;
    private int carrinhoId;

    private ProdutoDao produtoDao;
    
    private int criarCarrinho(String usuarioCpf) {
        CarrinhoDao carrinhoDao = new CarrinhoDao();

        int carrinhoIdExistente = carrinhoDao.buscarCarrinhoPorUsuarioCpf(usuarioCpf);
        if (carrinhoIdExistente != -1) {
            return carrinhoIdExistente;
        }

        int novoCarrinhoId = carrinhoDao.inserirCarrinho(usuarioCpf);

        if (novoCarrinhoId != -1) {
            return novoCarrinhoId;
        } else {
            System.err.println("Erro ao criar um novo carrinho para o usuário com CPF: " + usuarioCpf);
            return -1;
        }
    }

    
    @FXML
    void handleAdicionarAoCarrinho(ActionEvent event) {
        Produto produtoSelecionado = tabelaProdutos.getSelectionModel().getSelectedItem();

        if (produtoSelecionado != null) {
            try {
                String usuarioCpf = "12345678901";
                int carrinhoId = obterCarrinhoId(usuarioCpf);

                if (carrinhoId == -1) {
                    carrinhoId = criarCarrinho(usuarioCpf);

                    if (carrinhoId == -1) {
                        System.out.println("Erro ao criar o carrinho para o usuário.");
                        return;
                    }
                }

                if (carrinhoController == null) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLCarrinho.fxml"));
                    Parent carrinhoParent = loader.load();
                    CarrinhoController novoCarrinhoController = loader.getController();

                    novoCarrinhoController.inicializarCarrinho(carrinhoId);
                    setCarrinhoController(novoCarrinhoController);
                }

                itemCarrinhoDao itemCarrinhoDao = new itemCarrinhoDao();

                if (!itemCarrinhoDao.existeItemNoCarrinho(carrinhoId, produtoSelecionado.getId())) {
                    ItemCarrinho itemCarrinho = new ItemCarrinho(carrinhoId, produtoSelecionado.getId(), 1);
                    itemCarrinhoDao.inserirItemCarrinho(itemCarrinho);
                    carrinhoController.atualizarTabela();
                    
                  
                    exibirAlerta(AlertType.INFORMATION, "Sucesso", "Produto adicionado ao carrinho com sucesso.");
                } else {
                   
                    exibirAlerta(AlertType.WARNING, "Aviso", "Este produto já está no carrinho.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Erro ao carregar a tela de carrinho.");
            }
        } else {
            
            exibirAlerta(AlertType.WARNING, "Aviso", "Nenhum produto selecionado.");
        }
    }

    private void exibirAlerta(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    
    public int obterCarrinhoId(String usuarioId) {
        itemCarrinhoDao dao = new itemCarrinhoDao();
        return dao.obterCarrinhoId(usuarioId);
    }
    
    private void setScene(ActionEvent event, String fxmlFileName) {
        try {
            URL fxmlUrl = getClass().getResource(fxmlFileName);

            if (fxmlUrl == null) {
                System.err.println("Arquivo FXML não encontrado!");
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent parent = loader.load();

            if ("FXMLComprar.fxml".equals(fxmlFileName)) {
                ComprarController comprarController = loader.getController();
            }

            Scene scene = new Scene(parent);
            setScene(event, scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleVoltarButton(ActionEvent event) throws IOException {
        if (carrinhoController != null && carrinhoController.getAnchorPaneCarrinho() != null) {
            setScene(event, carrinhoController.getAnchorPaneCarrinho().getScene());
        } else {
            setScene(event, "TelaPrincipal.fxml");
        }
    }

    @FXML
    void handleComprarDireto(ActionEvent event) {
        setScene(event, "FXMLComprar.fxml");
    }

    private void setScene(ActionEvent event, Scene scene) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    
    public void setCarrinhoController(CarrinhoController carrinhoController) {
        System.out.println("setCarrinhoController: " + carrinhoController);
        this.carrinhoController = carrinhoController;
        this.carrinhoController.setProdutoDao(produtoDao);
    }

    public void setCarrinhoId(int carrinhoId) {
        this.carrinhoId = carrinhoId;
    }
    

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        produtoDao = new ProdutoDao();

        idColum.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeColum.setCellValueFactory(new PropertyValueFactory<>("nome"));
        precoColum.setCellValueFactory(new PropertyValueFactory<>("preco"));
        categoriaColum.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        lojaIdColum.setCellValueFactory(new PropertyValueFactory<>("lojaId"));

        List<Produto> produtos = produtoDao.buscarTodosProdutos();
        ObservableList<Produto> listaDeProdutos = FXCollections.observableArrayList(produtos);
        tabelaProdutos.setItems(listaDeProdutos);
    }


    @FXML
    private void handleIrParaCarrinho(ActionEvent event) {
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLCarrinho.fxml"));
            Parent root = loader.load();

            
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
           
        }
    }
}

