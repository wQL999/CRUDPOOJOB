
import java.util.List;

public interface ProjetoDAO {

    void inserir( Projeto c ) throws ProjetoException;
    void atualizar( Projeto c ) throws ProjetoException;
    void remover( Projeto c ) throws ProjetoException;
    List<Projeto> pesquisarPorNome( String nome ) throws ProjetoException;
    
}