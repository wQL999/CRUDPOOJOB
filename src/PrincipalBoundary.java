import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PrincipalBoundary extends Application {
   private Map<String, Tela> telas = new HashMap<>();
    @Override
    public void start(Stage stage) throws Exception {
        telas.put("Projeto", new ProjetoBoundary() );
        telas.put("Tarefa", new TarefaBoundary() );
        BorderPane panePrincipal = new BorderPane();
        MenuBar menuBar = new MenuBar();
        Menu mnuCadastro = new Menu("Cadastro");
        Menu mnuAjuda = new Menu("Ajuda");
        MenuItem mnuItemContato = new MenuItem("Projeto");
        mnuItemContato.setOnAction ( e -> 
            panePrincipal.setCenter( telas.get("Projeto").render() )
        );
        MenuItem mnuItemEndereco = new MenuItem("Tarefa");
        mnuItemEndereco.setOnAction( e-> 
            panePrincipal.setCenter( telas.get("Tarefa").render() )
        );
        //MenuItem mnuItemCreditos = new MenuItem("Creditos");
        mnuCadastro.getItems().addAll( mnuItemContato );
        mnuCadastro.getItems().addAll( mnuItemEndereco );
       // mnuAjuda.getItems().add( mnuItemCreditos );
        menuBar.getMenus().addAll( mnuCadastro, mnuAjuda);
        panePrincipal.setTop( menuBar );
        Scene scn = new Scene(panePrincipal, 800, 600);
        stage.setScene(scn);
        stage.setTitle("Gerenciamento de Projetos");
        stage.show();        
    }

    public static void main(String[] args) {
        Application.launch(PrincipalBoundary.class, args);
    }
    
}