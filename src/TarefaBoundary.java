import java.time.LocalDate;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
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
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

public class TarefaBoundary implements Tela {

    private Label lblId = new Label("");
    private TextField txtNome = new TextField();
    private TextField txtDescricao = new TextField();
    private TextField txtStatus = new TextField();
    private DatePicker dataCriacao = new DatePicker();
    private DatePicker dataConclusao = new DatePicker();

    private TarefasControl control = null;

    private TableView<Tarefa> tableView = new TableView<>();

    @Override
    public Pane render() {
        try { 
            control = new TarefasControl();
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
                new Alert(AlertType.ERROR, "Erro ao gravar o tarefa", ButtonType.OK).showAndWait();
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
        paneForm.add(new Label("Descricao: "), 0, 2);
        paneForm.add(txtDescricao, 1, 2);
        paneForm.add(new Label("Status: "), 0, 3);
        paneForm.add(txtStatus, 1, 3);
        paneForm.add(new Label("Data Inicio: "), 0, 4);
        paneForm.add(dataCriacao, 1, 4);
        paneForm.add(new Label("Data Fim: "), 0, 5);
        paneForm.add(dataConclusao, 1, 5);

        paneForm.add(btnGravar, 0, 8);
        paneForm.add(btnPesquisar, 1, 8);

        ligacoes();
        gerarColunas();

        panePrincipal.setTop( paneForm );
        panePrincipal.setCenter(tableView);

       return panePrincipal;
    }

    public void gerarColunas() { 
        TableColumn<Tarefa, Long> col1 = new TableColumn<>("Id");
        col1.setCellValueFactory( new PropertyValueFactory<Tarefa, Long>("idTarefa") );

        TableColumn<Tarefa, String> col2 = new TableColumn<>("Nome");
        col2.setCellValueFactory( new PropertyValueFactory<Tarefa, String>("nome"));

        TableColumn<Tarefa, String> col3 = new TableColumn<>("Descrição");
        col3.setCellValueFactory( new PropertyValueFactory<Tarefa, String>("descricaoTarefa"));

        TableColumn<Tarefa, String> col4 = new TableColumn<>("Status");
        col4.setCellValueFactory( new PropertyValueFactory<Tarefa, String>("status"));

        TableColumn<Tarefa, LocalDate> col5 = new TableColumn<>("Data Inicio");
        col5.setCellValueFactory( new PropertyValueFactory<Tarefa, LocalDate>("dataCriacao"));

        TableColumn<Tarefa, LocalDate> col6 = new TableColumn<>("Data Fim");
        col6.setCellValueFactory( new PropertyValueFactory<Tarefa, LocalDate>("dataConclusao"));


        tableView.getSelectionModel().selectedItemProperty()
            .addListener( (obs, antigo, novo) -> {
                if (novo != null) { 
                    System.out.println("status: " + novo.getStatus());
                    control.entidadeParaTela(novo);
                }
            });
        Callback<TableColumn<Tarefa, Void>, TableCell<Tarefa, Void>> cb = 
            new Callback<>() {
                @Override
                public TableCell<Tarefa, Void> call(
                    TableColumn<Tarefa, Void> param) {
                    TableCell<Tarefa, Void> celula = new TableCell<>() { 
                        final Button btnApagar = new Button("Apagar");

                        {
                            btnApagar.setOnAction( e -> {
                                Tarefa tarefa = tableView.getItems().get( getIndex() );
                                try { 
                                    control.excluir( tarefa ); 
                                } catch (ProjetoException err) { 
                                    new Alert(AlertType.ERROR, "Erro ao excluir o tarefa", ButtonType.OK).showAndWait();
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

        TableColumn<Tarefa, Void> col9 = new TableColumn<>("Ação");
        col9.setCellFactory(cb);

        tableView.getColumns().addAll(col1, col2, col3, col4, col5, col6, col9);
        tableView.setItems( control.getLista() );
    }

    public void ligacoes() { 
        control.idTProperty().addListener( (obs, antigo, novo) -> {
            lblId.setText( String.valueOf(novo) );
        });

       // IntegerStringConverter integerConverter = new IntegerStringConverter();
        Bindings.bindBidirectional(control.nomeProperty(), txtNome.textProperty());
       // Bindings.bindBidirectional(txtNumero.textProperty(), control.numeroProperty(), (StringConverter) integerConverter);
        Bindings.bindBidirectional(control.descTProperty(), txtDescricao.textProperty());
        Bindings.bindBidirectional(control.statusProperty(), txtStatus.textProperty() );
        Bindings.bindBidirectional(control.dataCriacaoProperty(), dataCriacao.valueProperty() );
        Bindings.bindBidirectional(control.dataConclusaoProperty(), dataConclusao.valueProperty() );
    }
}