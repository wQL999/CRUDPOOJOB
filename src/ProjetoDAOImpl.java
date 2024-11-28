import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjetoDAOImpl implements ProjetoDAO {

    private final static String DB_CLASS = "org.mariadb.jdbc.Driver";
    private final static String DB_URL = "jdbc:mariadb://localhost:3306/gerenciamentoProjetos?allowPublicKeyRetrieval=true"; // 07
    private final static String DB_USER = "root";
    private final static String DB_PASS = "alunofatec";

    private Connection con = null;

    public ProjetoDAOImpl() throws ProjetoException { 
        try { 
            Class.forName(DB_CLASS);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (ClassNotFoundException | SQLException e) { 
            throw new ProjetoException( e );
        }
    }

    @Override
    public void inserir(Projeto c) throws ProjetoException {
        try { 
            String SQL = """
                    INSERT INTO projeto (id, nome, descricao, dataInit, dataTermino)
                    VALUES (?, ?, ?, ?, ?)
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setLong(1, c.getId());
            stm.setString(2, c.getNome());
            stm.setString(3, c.getDescricao());
            java.sql.Date di = java.sql.Date.valueOf(c.getDataInit());
            java.sql.Date df = java.sql.Date.valueOf(c.getDataTermino());
            stm.setDate(4, di);
            stm.setDate(5, df);
            int i = stm.executeUpdate();
        } catch (SQLException e) { 
            throw new ProjetoException( e );
        }
    }

    @Override
    public void atualizar(Projeto c) throws ProjetoException {
        try { 
            String SQL = """
                    UPDATE projeto SET nome = ?, descricao = ?, dataInit = ?, dataTermino = ?
                    WHERE id = ?
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setString(1, c.getNome());
            stm.setString(2, c.getDescricao());
            java.sql.Date di = java.sql.Date.valueOf(c.getDataInit());
            java.sql.Date df = java.sql.Date.valueOf(c.getDataTermino());
            stm.setDate(3, di);
            stm.setDate(4, df);
            stm.setLong(5, c.getId());
            int i = stm.executeUpdate();
        } catch (SQLException e) { 
            throw new ProjetoException( e );
        }        
    }

    @Override
    public void remover(Projeto c) throws ProjetoException {
        try { 
            String SQL = """
                    DELETE FROM projeto WHERE id = ?
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setLong( 1, c.getId() );
            int i = stm.executeUpdate();
        } catch (SQLException e) { 
            throw new ProjetoException( e );
        }
    }

    @Override
    public List<Projeto> pesquisarPorNome(String nome) throws ProjetoException {
        List<Projeto> lista = new ArrayList<>();
        try { 
            String SQL = """
                    SELECT * FROM projeto WHERE nome LIKE ?
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setString(1, "%" + nome + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) { 
                Projeto c = new Projeto();
                c.setId( rs.getLong("id") );
                c.setNome( rs.getString("nome") );
                c.setDescricao( rs.getString("descricao") );
                c.setDataInit( rs.getDate("dataInit").toLocalDate() );
                c.setDataTermino( rs.getDate("dataTermino").toLocalDate() );

                lista.add( c );
            }
        } catch (SQLException e) { 
            throw new ProjetoException( e );
        }
        return lista;
    }
    
}