

import java.time.LocalDate;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProjetoControl {

    private ObservableList<Projeto> lista = FXCollections.observableArrayList();
    private long contador = 2;

    private LongProperty id = new SimpleLongProperty(0);
    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty descricao = new SimpleStringProperty("");
    private ObjectProperty<LocalDate> dataInit = 
            new SimpleObjectProperty<>(LocalDate.now());
    private ObjectProperty<LocalDate> dataTermino = 
            new SimpleObjectProperty<>(LocalDate.now());

    private ProjetoDAO projetoDAO = null;

    public ProjetoControl() throws ProjetoException { 
        projetoDAO = new ProjetoDAOImpl();
    }

    public Projeto paraEntidade() { 
        Projeto c = new Projeto();
        c.setId( id.get() );
        c.setNome( nome.get() );
        c.setDescricao( descricao.get()  );
        c.setDataInit( dataInit.get() );
        c.setDataTermino( dataTermino.get() );
        return c;
    }

    public void excluir( Projeto c ) throws ProjetoException { 
        projetoDAO.remover( c );
        pesquisarTodos();
    }

    public void limparTudo() { 
        id.set( 0 );
        nome.set( "" );
        descricao.set( "" );
        dataInit.set( LocalDate.now() );
        dataTermino.set( LocalDate.now() );
    }

    public void paraTela(Projeto c) { 
        if (c != null) {
            id.set( c.getId() );
            nome.set( c.getNome() );
            descricao.set( c.getDescricao() );
            dataInit.set( c.getDataInit() );
            dataTermino.set( c.getDataTermino() );
        }
    }


    public void gravar() throws ProjetoException { 
        Projeto c = paraEntidade();
        if (c.getId() == 0 ) { 
            this.contador += 1;
            c.setId( this.contador );
            projetoDAO.inserir( c );
        } else { 
            projetoDAO.atualizar( c );
        }
        pesquisarTodos();
    }

    public void pesquisar() throws ProjetoException { 
        lista.clear();
        lista.addAll( projetoDAO.pesquisarPorNome( nome.get() ) );
    }

    public void pesquisarTodos() throws ProjetoException { 
        lista.clear();
        lista.addAll( projetoDAO.pesquisarPorNome( "" ) );
    }

    public LongProperty idProperty() { 
        return this.id;
    }
    public StringProperty nomeProperty() { 
        return this.nome;
    }
    public StringProperty descricaoProperty() { 
        return this.descricao;
    }
    public ObjectProperty<LocalDate> dataInitProperty() { 
        return this.dataInit;
    }
    public ObjectProperty<LocalDate> dataTerminoProperty() { 
        return this.dataTermino;
    }

    public ObservableList<Projeto> getLista() { 
        return this.lista;
    }
}