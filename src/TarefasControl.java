import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.time.LocalDate;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TarefasControl {

    private LongProperty idTarefa = new SimpleLongProperty(0);
    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty descricaoTarefa = new SimpleStringProperty("");
    private StringProperty status = new SimpleStringProperty("");
    private ObjectProperty<LocalDate> dataCriacao = 
            new SimpleObjectProperty<>(LocalDate.now());
    private ObjectProperty<LocalDate> dataConclusao = 
            new SimpleObjectProperty<>(LocalDate.now());

    private ObservableList<Tarefa> lista = FXCollections.observableArrayList();

    private TarefaDAO tarefaDAO;

    private long contador = 0;

    public TarefasControl() throws ProjetoException { 
        tarefaDAO = new TarefaDAOImpl();
    }

    public void excluir( Tarefa c ) throws ProjetoException { 
        tarefaDAO.remover( c );
        pesquisarTodos();
    }

    public void gravar() throws ProjetoException {
        Tarefa tarefa = telaParaEntidade();
        if (idTarefa.get() == 0) { 
            tarefa.setIdTarefa(++contador);
            tarefaDAO.inserir(tarefa);
        } else { 
            tarefaDAO.atualizar(tarefa);
        }
        limparTudo();
        pesquisarTodos();
    }

    public void pesquisar() throws ProjetoException { 
        List<Tarefa> listaTemp = tarefaDAO.pesquisarPorStatus( status.get() );
        lista.clear();
        lista.addAll( listaTemp );
    }

    public void pesquisarTodos() throws ProjetoException { 
        List<Tarefa> listaTemp = tarefaDAO.pesquisarPorStatus( "" );
        lista.clear();
        lista.addAll( listaTemp );
    }

    public void limparTudo() { 
        idTarefa.set( 0 );
        nome.set( "" );
        descricaoTarefa.set( "" );
        dataCriacao.set( LocalDate.now() );
        dataConclusao.set( LocalDate.now() );
    }

    public void entidadeParaTela(Tarefa c) { 
        if (c != null) {
            idTarefa.set( c.getIdTarefa() );
            nome.set( c.getNome() );
            descricaoTarefa.set( c.getDescricaoTarefa() );
            dataCriacao.set( c.getDataCriacao() );
            dataConclusao.set( c.getDataConclusao() );
        }
    }

    public Tarefa telaParaEntidade() { 
        Tarefa tarefa = new Tarefa();
        tarefa.setIdTarefa(idTarefa.get());
        tarefa.setNome(nome.get());
        tarefa.setDescricaoTarefa(descricaoTarefa.get());
        tarefa.setStatus(status.get());
        tarefa.setDataCriacao(dataCriacao.get());
        tarefa.setDataConclusao(dataConclusao.get());
        return tarefa;
    }

    public ObservableList<Tarefa> getLista() { 
        return this.lista;
    }

    public LongProperty idTProperty() { 
        return this.idTarefa;
    }
    public StringProperty nomeProperty() { 
        return this.nome;
    }
    public StringProperty descTProperty() { 
        return this.descricaoTarefa;
    }
    public StringProperty statusProperty() { 
        return this.status;
    }
    public ObjectProperty<LocalDate> dataCriacaoProperty() { 
        return this.dataCriacao;
    }
    public ObjectProperty<LocalDate> dataConclusaoProperty() { 
        return this.dataConclusao;
    }
    
}