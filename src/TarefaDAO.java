import java.util.List;

public interface TarefaDAO {
    
    void inserir( Tarefa c ) throws ProjetoException;
    void atualizar( Tarefa c ) throws ProjetoException;
    void remover( Tarefa c ) throws ProjetoException;
    List<Tarefa> pesquisarPorStatus( String status ) throws ProjetoException;
    
}