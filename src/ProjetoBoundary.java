import java.time.LocalDate;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

public class ProjetoBoundary implements Tela {

    private Label lblId = new Label("");
    private TextField txtNome = new TextField();
    private TextField txtDescricao = new TextField();
    private DatePicker dataInit = new DatePicker();
    private DatePicker dataTermino = new DatePicker();

    private ProjetoControl control = null;

    private TableView<Projeto> tableView = new TableView<>();

    @Override
    public Pane render() {
        try { 
            control = new ProjetoControl();
        } catch (ProjetoException e ) { 
            new Alert(AlertType.ERROR, "Erro ao iniciar o sistema", ButtonType.OK).showAndWait();
        }
        BorderPane panePrincipal = new BorderPane();
        GridPane paneForm = new GridPane();

        Button btnGravar = new Button("Gravar");
        btnGravar.setOnAction( e -> { 
            try { 
                control.gravar();
            } catch (ProjetoException err) { 
                new Alert(AlertType.ERROR, "Erro ao gravar o contato", ButtonType.OK).showAndWait();
            }
            tableView.refresh();
        });
        Button btnPesquisar = new Button("Pesquisar");
        btnPesquisar.setOnAction( e -> { 
            try { 
                control.pesquisar();
            } catch (ProjetoException err) { 
                new Alert(AlertType.ERROR, "Erro ao pesquisar por nome", ButtonType.OK).showAndWait();
            }});

        Button btnNovo = new Button("*");
        btnNovo.setOnAction( e -> control.limparTudo() );

        paneForm.add(new Label("Id: "), 0, 0);
        paneForm.add(lblId, 1, 0);
        paneForm.add(new Label("Nome: "), 0, 1);
        paneForm.add(txtNome, 1, 1);
        paneForm.add(btnNovo, 2, 1);
        paneForm.add(new Label("Descrição: "), 0, 2);
        paneForm.add(txtDescricao, 1, 2);
        paneForm.add(new Label("Data Inicio Projeto: "), 0, 3);
        paneForm.add(dataInit, 1, 3);
        paneForm.add(new Label("Data Término Projeto: "), 0, 4);
        paneForm.add(dataTermino, 1, 4);

        paneForm.add(btnGravar, 0, 5);
        paneForm.add(btnPesquisar, 1, 5);

        ligacoes();
        gerarColunas();

        panePrincipal.setTop( paneForm );
        panePrincipal.setCenter(tableView);

        return panePrincipal;
    }

    public void gerarColunas() { 
        TableColumn<Projeto, Long> col1 = new TableColumn<>("Id");
        col1.setCellValueFactory( new PropertyValueFactory<Projeto, Long>("id") );

        TableColumn<Projeto, String> col2 = new TableColumn<>("Nome");
        col2.setCellValueFactory( new PropertyValueFactory<Projeto, String>("nome"));

        TableColumn<Projeto, String> col3 = new TableColumn<>("Descrição");
        col3.setCellValueFactory( new PropertyValueFactory<Projeto, String>("descricao"));

        TableColumn<Projeto, LocalDate> col4 = new TableColumn<>("Data Inicio");
        col4.setCellValueFactory( new PropertyValueFactory<Projeto, LocalDate>("dataInit"));

        TableColumn<Projeto, LocalDate> col5 = new TableColumn<>("Data Término");
        col5.setCellValueFactory( new PropertyValueFactory<Projeto, LocalDate>("dataTermino"));

        tableView.getSelectionModel().selectedItemProperty()
            .addListener( (obs, antigo, novo) -> {
                if (novo != null) { 
                    System.out.println("Nome: " + novo.getNome());
                    control.paraTela(novo);
                }
            });
        Callback<TableColumn<Projeto, Void>, TableCell<Projeto, Void>> cb = 
            new Callback<>() {
                @Override
                public TableCell<Projeto, Void> call(
                    TableColumn<Projeto, Void> param) {
                    TableCell<Projeto, Void> celula = new TableCell<>() { 
                        final Button btnApagar = new Button("Apagar");

                        {
                            btnApagar.setOnAction( e -> {
                                Projeto contato = tableView.getItems().get( getIndex() );
                                try { 
                                    control.excluir( contato ); 
                                } catch (ProjetoException err) { 
                                    new Alert(AlertType.ERROR, "Erro ao excluir o projeto", ButtonType.OK).showAndWait();
                                }
                            });
                        }

                        @Override
                        public void updateItem( Void item, boolean empty) {                             
                            if (!empty) { 
                                setGraphic(btnApagar);
                            } else { 
                                setGraphic(null);
                            }
                        }
                        
                    };
                    return celula;            
                } 
            };

        TableColumn<Projeto, Void> col6 = new TableColumn<>("Ação");
        col6.setCellFactory(cb);

        tableView.getColumns().addAll(col1, col2, col3, col4, col5, col6);
        tableView.setItems( control.getLista() );
    }

    public void ligacoes() { 
        control.idProperty().addListener( (obs, antigo, novo) -> {
            lblId.setText( String.valueOf(novo) );
        });
        Bindings.bindBidirectional(control.nomeProperty(), txtNome.textProperty());
        Bindings.bindBidirectional(control.descricaoProperty(), txtDescricao.textProperty());
        Bindings.bindBidirectional(control.dataInitProperty(), dataInit.valueProperty());
        Bindings.bindBidirectional(control.dataTerminoProperty(), dataTermino.valueProperty());
    }
}