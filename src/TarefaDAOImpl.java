import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAOImpl implements TarefaDAO {

    private final static String DB_CLASS = "org.mariadb.jdbc.Driver";
    private final static String DB_URL = "jdbc:mariadb://localhost:3306/gerenciamentoProjetos?allowPublicKeyRetrieval=true";
    private final static String DB_USER = "root";
    private final static String DB_PASS = "alunofatec";

    private Connection con = null;

    public TarefaDAOImpl() throws ProjetoException { 
        try { 
            Class.forName(DB_CLASS);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (ClassNotFoundException | SQLException e) { 
            throw new ProjetoException( e );
        }
    }

    @Override
    public void inserir(Tarefa c) throws ProjetoException {
        try { 
            String sql = """
                    INSERT INTO tarefa (nome, descricao,
                    statusT, dataCriacao, dataConclusao)
                    VALUES (?, ?, ?, ?, ?)
                    """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, c.getNome() );
            stm.setString(2, c.getDescricaoTarefa());
            stm.setString(3, c.getStatus());
            java.sql.Date di = java.sql.Date.valueOf(c.getDataCriacao());
            java.sql.Date df = java.sql.Date.valueOf(c.getDataConclusao());
            stm.setDate(4, di);
            stm.setDate(5, df);
            stm.executeUpdate();
        } catch (SQLException err) {
            throw new ProjetoException(err);
        }
    }

    @Override
    public void atualizar(Tarefa c) throws ProjetoException {
        try { 
            String sql = """
                    UPDATE tarefa SET nome=?, descricao=?,
                    statusT=?, dataCriacao=?, dataConclusao=?
                    WHERE id = ?
                    """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, c.getNome() );
            stm.setString(2, c.getDescricaoTarefa());
            stm.setString(3, c.getStatus());
            java.sql.Date di = java.sql.Date.valueOf(c.getDataCriacao());
            java.sql.Date df = java.sql.Date.valueOf(c.getDataConclusao());
            stm.setDate(4, di);
            stm.setDate(5, df);
            stm.executeUpdate();
        } catch (SQLException err) {
            throw new ProjetoException(err);
        }
    }

    @Override
    public void remover(Tarefa c) throws ProjetoException {
        try { 
            String sql = """
                    DELETE FROM tarefa
                    WHERE id = ?
                    """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setLong(1, c.getIdTarefa());
            stm.executeUpdate();
        } catch (SQLException err) {
            throw new ProjetoException(err);
        }
    }

    @Override
    public List<Tarefa> pesquisarPorStatus(String status) throws ProjetoException {
        List<Tarefa> lista = new ArrayList<>();
        try { 
            String sql = """
                    SELECT * FROM tarefa
                    WHERE statusT LIKE ?
                    """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, "%" + status + "%");
            ResultSet rs = stm.executeQuery();
            while(rs.next()) { 
                Tarefa tarefa = new Tarefa();
                tarefa.setIdTarefa( rs.getLong("id")  );
                tarefa.setNome( rs.getString("nome") );
                tarefa.setDescricaoTarefa( rs.getString("descricao") );
                tarefa.setStatus( rs.getString("statusT" ) );
                tarefa.setDataCriacao( rs.getDate("dataCriacao").toLocalDate() );
                tarefa.setDataConclusao( rs.getDate("dataConclusao" ).toLocalDate());
             
                lista.add(tarefa);
            }
        } catch (SQLException err) {
            throw new ProjetoException(err);
        }
        return lista;
    }
}