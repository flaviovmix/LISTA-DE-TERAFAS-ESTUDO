package app.tarefas;

import app.ConexaoPostGres;
import static app.Utilidades.stringToDate;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {
    private ConexaoPostGres dataBase;
    
    private ConexaoPostGres conexaoBanco;

    public TarefaDAO() {
        dataBase = new ConexaoPostGres();
        dataBase.abrirConexaoJNDI();
    }
    
 
    public int contarTarefas(boolean ativoOuInativo) {
        String sql = "SELECT COUNT(*) FROM tarefas WHERE ativo = ?";
        try (PreparedStatement ps = dataBase.getConexao().prepareStatement(sql)) {
            ps.setBoolean(1, ativoOuInativo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public List<TarefaBean> listaTarefasAtivas(Integer offset) {
        return listarTarefasAtivoEInativas(true, offset);
    }    
    public List<TarefaBean> listaTarefasInativas(Integer offset) {
        return listarTarefasAtivoEInativas(false, offset);
    }
    private List<TarefaBean> listarTarefasAtivoEInativas(boolean ativoOuInativo, Integer offset) {
    List<TarefaBean> lista = new ArrayList<>();

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT tarefas.* , (");

            sql.append("SELECT COUNT(*) ");

                sql.append("FROM detalhes_tarefa ");
                sql.append("WHERE detalhes_tarefa.fk_tarefa = tarefas.id_tarefa ");

            sql.append(") AS quantidade_de_subtarefas ");

        sql.append("FROM tarefas WHERE ativo = ? ");
        
        sql.append("ORDER BY id_tarefa DESC "); 
        
        sql.append("LIMIT 5 OFFSET ?");  

        try (PreparedStatement ps = dataBase.getConexao().prepareStatement(sql.toString())) {

            ps.setBoolean(1, ativoOuInativo);
            ps.setInt(2, offset != null ? offset : 0);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    TarefaBean tarefa = new TarefaBean();

                    tarefa.setId_tarefa(rs.getInt("id_tarefa"));
                    tarefa.setTitulo(rs.getString("titulo"));
                    tarefa.setDescricao(rs.getString("descricao"));
                    tarefa.setStatus(rs.getString("status"));
                    tarefa.setPrioridade(rs.getString("prioridade"));
                    tarefa.setResponsavel(rs.getString("responsavel"));
                    tarefa.setData_criacao(rs.getDate("data_criacao"));
                    tarefa.setData_conclusao(rs.getDate("data_conclusao"));
                    tarefa.setQuantidade_de_subtarefas(rs.getInt("quantidade_de_subtarefas"));
                    
                    lista.add(tarefa);

                }
            }
        } catch (SQLException erro) {
            erro.printStackTrace();
        }

        return lista;

    }
    
    
    public void selectUnico(TarefaBean tarefa) {
        String sql = "SELECT * FROM tarefas WHERE id_tarefa = ?";

        try (PreparedStatement ps = dataBase.getConexao().prepareStatement(sql)) {
            
            ps.setInt(1, tarefa.getId_tarefa()); // Evita SQL Injection

            try (ResultSet rs = ps.executeQuery()) {
                
                if (rs.next()) {
                    tarefa.setTitulo(rs.getString("titulo"));
                    tarefa.setDescricao(rs.getString("descricao"));
                    tarefa.setStatus(rs.getString("status"));
                    tarefa.setPrioridade(rs.getString("prioridade"));
                    tarefa.setResponsavel(rs.getString("responsavel"));
                    tarefa.setData_criacao(rs.getDate("data_criacao"));
                    tarefa.setData_conclusao(stringToDate(rs.getString("data_conclusao"), "yyyy-MM-dd"));

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public void adicionarTarefa(TarefaBean tarefa) {

        String sql = "INSERT INTO tarefas (titulo, descricao, status, prioridade, responsavel, data_conclusao) " +
                     "VALUES (?, ?, ?, ?, ?, ?) RETURNING id_tarefa;";

        try (PreparedStatement ps = dataBase.getConexao().prepareStatement(sql)) {

            ps.setString(1, tarefa.getTitulo());
            ps.setString(2, tarefa.getDescricao());
            ps.setString(3, tarefa.getStatus());
            ps.setString(4, tarefa.getPrioridade());
            ps.setString(5, tarefa.getResponsavel());
            ps.setDate(6, tarefa.getData_conclusao());

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    tarefa.setId_tarefa(rs.getInt("id_tarefa"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void alterarTarefa(TarefaBean tarefa) {
        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tarefas SET ")
           .append("titulo = ?, ")
           .append("descricao = ?, ")
           .append("status = ?, ")
           .append("prioridade = ?, ")
           .append("responsavel = ?, ")
           .append("data_conclusao = ? ")
           .append("WHERE id_tarefa = ?");


        try (PreparedStatement ps = dataBase.getConexao().prepareStatement(sql.toString())) {
            ps.setString(1, tarefa.getTitulo());
            ps.setString(2, tarefa.getDescricao());
            ps.setString(3, tarefa.getStatus());
            ps.setString(4, tarefa.getPrioridade());
            ps.setString(5, tarefa.getResponsavel());
            ps.setDate(6, tarefa.getData_conclusao());
            ps.setInt(7, tarefa.getId_tarefa());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluirTarefa(Integer id_tarefa) {

        String sql = ("DELETE FROM tarefas WHERE id_tarefa = ?");
        
        try (PreparedStatement ps = dataBase.getConexao().prepareStatement(sql);) {
            
            ps.setInt(1, id_tarefa);
            
            ps.executeUpdate();
            
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public TarefaBean buscarPorId(int id) {
        
        String sql = "SELECT * FROM tarefas WHERE id_tarefa = ?";
        
        TarefaBean tarefa = null;
        try (
                PreparedStatement ps = dataBase.getConexao().prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
            ) {
            
            ps.setInt(1, id);
            
            if (rs.next()) {
                tarefa = new TarefaBean();
                tarefa.setId_tarefa(rs.getInt("id_tarefa"));
                tarefa.setTitulo(rs.getString("titulo"));
                tarefa.setDescricao(rs.getString("descricao"));
                tarefa.setStatus(rs.getString("status"));
                tarefa.setPrioridade(rs.getString("prioridade"));
                tarefa.setResponsavel(rs.getString("responsavel"));
                //tarefa.setData_criacao(rs.getDate("data_criacao"));
                //tarefa.setData_conclusao(rs.getDate("data_conclusao"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tarefa;
    }
    
    
    public void alterarAtivoInativo(int id_tarefa, boolean ativo) {
        String sql = "UPDATE tarefas SET ativo = ? WHERE id_tarefa = ?";
        
        try (PreparedStatement ps = dataBase.getConexao().prepareStatement(sql)) {
        
            ps.setBoolean(1, ativo);
            ps.setInt(2, id_tarefa);
            ps.executeUpdate();

            ps.close(); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public void fecharConexao() {
        dataBase.fecharConexao();
    }

}
