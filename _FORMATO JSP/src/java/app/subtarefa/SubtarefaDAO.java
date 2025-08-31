package app.subtarefa;

import app.ConexaoPostGres;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubtarefaDAO {
    private ConexaoPostGres dataBase;

    public SubtarefaDAO() {
        dataBase = new ConexaoPostGres();
        dataBase.abrirConexaoJNDI();
    }
    
    public void adicionarSubtarefa(SubtarefaBean subtarefa) {
        
        String sql = "INSERT INTO detalhes_tarefa (fk_tarefa, descricao) VALUES (?, ?)";
        
        try ( PreparedStatement ps = dataBase.getConexao().prepareStatement(sql);) {
            
            ps.setInt(1, subtarefa.getFk_tarefa());
            ps.setString(2, subtarefa.getDescricao());

            ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
    public void deletarSubtarefa(SubtarefaBean subtarefa) {
        String sql = "DELETE FROM detalhes_tarefa WHERE id_detalhe = ?;";
        
        try (PreparedStatement ps = dataBase.getConexao().prepareStatement(sql)) {
            
            ps.setInt(1, subtarefa.getFk_tarefa());       
            ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }        
    }    
  
    public List<SubtarefaBean> listaTarefasAtivas(int fk_tarefa) {
        return listarTarefasAtivoEInativas(fk_tarefa, true);
    }
    public List<SubtarefaBean> listaTarefasInativas(int fk_tarefa) {
        return listarTarefasAtivoEInativas(fk_tarefa, false);
    }
    private List<SubtarefaBean> listarTarefasAtivoEInativas(int fk_tarefa, boolean ativoOuInativo) {
        List<SubtarefaBean> lista = new ArrayList<>();
        
        String sql = "SELECT * FROM detalhes_tarefa WHERE fk_tarefa = ? AND ativo = ?";
        
        try (PreparedStatement ps = dataBase.getConexao().prepareStatement(sql);) {
            
            ps.setInt(1, fk_tarefa);
            ps.setBoolean(2, ativoOuInativo);
            
            try (ResultSet rs = ps.executeQuery();) {

                while (rs.next()) {
                    SubtarefaBean subtarefa = new SubtarefaBean();
                    subtarefa.setId_detalhe(rs.getInt("id_detalhe"));
                    subtarefa.setFk_tarefa(rs.getInt("fk_tarefa"));
                    subtarefa.setDescricao(rs.getString("descricao"));
                    subtarefa.setData_conclusao(rs.getDate("data_conclusao"));
                    subtarefa.setAtivo(rs.getBoolean("ativo"));
                    lista.add(subtarefa);
                }
                
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    public void alterarAtivoInativo(int id_detalhe, boolean ativo) {
        try {
            String sql = "UPDATE detalhes_tarefa SET ativo = ? WHERE id_detalhe = ?";
            PreparedStatement ps = dataBase.getConexao().prepareStatement(sql);

            ps.setBoolean(1, ativo);
            ps.setInt(2, id_detalhe);
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
