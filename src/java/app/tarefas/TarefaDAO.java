package app.Tarefas;

import br.jasap.core.AppManager;
import br.jasap.dao.JasapDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO extends JasapDAO {
    
    public TarefaDAO() {
    }
    
    public TarefaDAO(AppManager manager) {
        this.manager = manager;
    }
    
    public List<TarefaBean> listarTarefas(boolean ativoOuInativo) {
        
        List<TarefaBean> lista = new ArrayList<>();
        String sql = "SELECT * FROM tarefas  WHERE ativo = ? ORDER BY id_tarefa";
        
        try (PreparedStatement instrucaoPreparada = getDataBase().getConn().prepareStatement(sql.toString())) {
            instrucaoPreparada.setBoolean(1, ativoOuInativo);
            try (ResultSet resultado = instrucaoPreparada.executeQuery()) {
                while (resultado.next()) {
                    TarefaBean tarefa = new TarefaBean();
                    
                    tarefa.setId_tarefa(resultado.getInt("id_tarefa"));
                    tarefa.setTitulo(resultado.getString("titulo"));
                    tarefa.setDescricao(resultado.getString("descricao"));
                    tarefa.setStatus(resultado.getString("status"));
                    tarefa.setPrioridade(resultado.getString("prioridade"));
                    tarefa.setResponsavel(resultado.getString("responsavel"));
                    tarefa.setData_criacao(resultado.getDate("data_criacao"));
                    tarefa.setData_conclusao(resultado.getDate("data_conclusao"));
                    tarefa.setAtivo(resultado.getBoolean("ativo"));
                    
                    System.out.println(tarefa.getTitulo());
                    
                    lista.add(tarefa);
                }
            }
        } catch (SQLException erro) {
            erro.printStackTrace();
        }
        return lista;
    }
   
}
