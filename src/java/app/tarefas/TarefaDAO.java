package app.tarefas;

import app.ConexaoPostGres;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {

    private ConexaoPostGres conexaoBanco;

    // Construtor da classe TarefaDAO.
    // Ao criar um objeto TarefaDAO, � instanciada uma conex�o com o banco de dados PostgreSQL,
    // usando o nome "lista_tarefas" como identificador (por exemplo, nome do banco).
    // Em seguida, o m�todo abrirConexao() � chamado para estabelecer a conex�o imediatamente.
    public TarefaDAO() {
        conexaoBanco = new ConexaoPostGres("lista_tarefas");
        conexaoBanco.abrirConexao();
    }
    
    // M�todo p�blico que retorna uma lista de tarefas ativas.
    // Ele delega a execu��o para o m�todo listarTarefasAtivoEInativas passando o valor "verdadeiro" como argumento.
    // O retorno � do tipo List<TarefaBean>, sem expor qual implementa��o concreta da lista est� sendo usada.
    // retornando apenas as tarefas ativas.    
    public List<TarefaBean> listaTarefasAtivas() {
        return listarTarefasAtivoEInativas(true);
    }

    //igual o anterior porem passando falso como argumento
    // retornando apenas as tarefas inativas.
    public List<TarefaBean> listaTarefasInativas() {
        return listarTarefasAtivoEInativas(false);
    }
    
    // M�todo privado que retorna uma lista de tarefas do tipo List<TarefaBean>.
    // Recebe um argumento do tipo boolean chamado ativoOuInativo, que determina
    // se o m�todo deve buscar tarefas ativas (true) ou inativas (false).
    // Internamente, ele utiliza a implementa��o ArrayList para armazenar os resultados.
    private List<TarefaBean> listarTarefasAtivoEInativas(boolean ativoOuInativo) {
        
    // Em Java, List � uma interface da biblioteca java.util, ArrayList � uma classe concreta que implementa essa interface.
    // Estamos criando uma lista de TarefaBean usando a implementa��o ArrayList.
    List<TarefaBean> lista = new ArrayList<>();


    
        // Cria um novo objeto StringBuilder chamado sql.
        // StringBuilder � uma classe do Java usada para construir e manipular textos de forma eficiente.
        // Diferente da classe String (que � imut�vel), o StringBuilder permite modificar o conte�do sem criar novos objetos na mem�ria.
        // Isso � especialmente �til quando queremos montar uma string em v�rias partes
        //como � o caso da constru��o de uma consulta SQL longa e complexa.
        StringBuilder sql = new StringBuilder();
        
        //Seleciona todas as colunas da tabela tarefas
        sql.append("SELECT tarefas.* , (");

            // Faz uma subconsulta para contar as subtarefas selecionando a contagem de todos os registros
            sql.append("SELECT COUNT(*) ");

                // Da tabela detalhes_tarefa
                sql.append("FROM detalhes_tarefa ");

                // Onde a chave estrangeira da tabela detalhes_tarefa corresponda ao id da tabela tarefa
                sql.append("WHERE detalhes_tarefa.fk_tarefa = tarefas.id_tarefa ");

            // Nomeia a coluna resultante como numero_de_subtarefas
            sql.append(") AS quantidade_de_subtarefas ");

        // Da tabela principal tarefas
        sql.append("FROM tarefas WHERE ativo = ?;");


        // Bloco try-with-resources que garante o fechamento autom�tico do PreparedStatement ao final da execu��o,
        // mesmo que ocorra uma exce��o.  
        // PreparedStatement � uma interface da API JDBC usada para enviar comandos SQL ao banco de dados
        // de forma pr�-compilada, permitindo definir par�metros e ajudando a evitar ataques de SQL Injection.
        // O m�todo conexaoBanco.getConexao() retorna a conex�o ativa com o banco de dados.
        // O m�todo prepareStatement(sql.toString()) cria um PreparedStatement a partir da instru��o SQL 
        // constru�da no StringBuilder (convertida para String com .toString()).
        try (PreparedStatement ps = conexaoBanco.getConexao().prepareStatement(sql.toString())) {


            // Define o valor do primeiro par�metro (?) da instru��o SQL como um booleano.
            ps.setBoolean(1, ativoOuInativo);

            
            // Bloco try-with-resources que executa a consulta SQL preparada anteriormente (ps.executeQuery())
            // e armazena o resultado no objeto ResultSet "rs".
            // ResultSet � uma interface que representa a tabela de resultados retornada pelo banco de dados,
            // permitindo percorrer linha por linha e acessar os valores de cada coluna.
            // O try-with-resources garante que o ResultSet seja fechado automaticamente ap�s o uso,
            // evitando vazamento de recursos.
            try (ResultSet rs = ps.executeQuery()) {
                
                
                // Loop que avan�a o cursor do ResultSet para a pr�xima linha de resultado.
                // O m�todo next() retorna true se houver mais uma linha para ler, e false quando acabar.
                // Dentro do bloco while, cada chamada a rs.getXXX() acessa o valor das colunas da linha atual.
                while (rs.next()) {
                    
                    // Cria uma nova inst�ncia da classe TarefaBean.
                    // "tarefa" ser� um objeto que representar� uma linha da tabela "tarefas",
                    // permitindo armazenar os dados obtidos do ResultSet em atributos Java.
                    TarefaBean tarefa = new TarefaBean();
                    
                    // Define no objeto "tarefa" o valor do campo vindo do banco de dados.
                    // rs.getInt obt�m o valor do campo atual do ResultSet.
                    // O m�todo setId_tarefa(...) armazena esse valor no atributo correspondente dentro do TarefaBean.
                    // Isso para todos os campos
                    tarefa.setId_tarefa(rs.getInt("id_tarefa"));
                    tarefa.setTitulo(rs.getString("titulo"));
                    tarefa.setDescricao(rs.getString("descricao"));
                    tarefa.setStatus(rs.getString("status"));
                    tarefa.setPrioridade(rs.getString("prioridade"));
                    tarefa.setResponsavel(rs.getString("responsavel"));
                    tarefa.setData_criacao(rs.getDate("data_criacao"));
                    tarefa.setData_conclusao(rs.getDate("data_conclusao"));
                    tarefa.setQuantidade_de_subtarefas(rs.getInt("quantidade_de_subtarefas"));
                    
                    // Em cada itera��o do loop, adiciona o objeto "tarefa" (preenchido com os dados da linha atual do ResultSet)
                    // � lista que conter� todas as tarefas retornadas pela consulta.
                    lista.add(tarefa);

                }
            }
        } catch (SQLException erro) {
            erro.printStackTrace();
        }

        // Retorna a lista contendo todas as tarefas obtidas na consulta ao banco de dados.
        return lista;

    }

    public void selectUnico(TarefaBean tarefa) {
        String sql = "SELECT * FROM tarefas WHERE id_tarefa = ?";

        try (PreparedStatement ps = conexaoBanco.getConexao().prepareStatement(sql)) {
            
            ps.setInt(1, tarefa.getId_tarefa()); // Evita SQL Injection

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    tarefa.setTitulo(rs.getString("titulo"));
                    tarefa.setDescricao(rs.getString("descricao"));
                    tarefa.setStatus(rs.getString("status"));
                    tarefa.setPrioridade(rs.getString("prioridade"));
                    tarefa.setResponsavel(rs.getString("responsavel"));
                    tarefa.setData_criacao(rs.getDate("data_criacao"));
                    tarefa.setData_conclusao(rs.getDate("data_conclusao"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void adicionarTarefa(TarefaBean tarefa) {
    String sql = "INSERT INTO tarefas (titulo, descricao, status, prioridade, responsavel) " +
                 "VALUES (?, ?, ?, ?, ?) RETURNING id_tarefa;";

        try (PreparedStatement ps = conexaoBanco.getConexao().prepareStatement(sql);
             ) {
            ps.setString(1, tarefa.getTitulo());
            ps.setString(2, tarefa.getDescricao());
            ps.setString(3, tarefa.getStatus());
            ps.setString(4, tarefa.getPrioridade());
            ps.setString(5, tarefa.getResponsavel());

            try (ResultSet rs = ps.executeQuery()) { // <- usa executeQuery por causa do RETURNING
                if (rs.next()) {
                    tarefa.setId_tarefa(rs.getInt("id_tarefa"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void alterarTarefa(TarefaBean tarefa) {
        String sql = "UPDATE tarefas SET "
                + "titulo = ?, "
                + "descricao = ?, "
                + "status = ?, "
                + "prioridade = ?, "
                + "responsavel = ? "
                + "WHERE id_tarefa = ?";

        try (PreparedStatement ps = conexaoBanco .getConexao().prepareStatement(sql)) {
            ps.setString(1, tarefa.getTitulo());
            ps.setString(2, tarefa.getDescricao());
            ps.setString(3, tarefa.getStatus());
            ps.setString(4, tarefa.getPrioridade());
            ps.setString(5, tarefa.getResponsavel());
            ps.setInt(6, tarefa.getId_tarefa());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluirTarefa(Integer id_tarefa) {

        String sql = ("DELETE FROM tarefas WHERE id_tarefa = ?");
        
        try (PreparedStatement ps = conexaoBanco .getConexao().prepareStatement(sql);) {
            
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
                PreparedStatement ps = conexaoBanco .getConexao().prepareStatement(sql);
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
        try {
            String sql = "UPDATE tarefas SET ativo = ? WHERE id_tarefa = ?";
            
            PreparedStatement ps = conexaoBanco .getConexao().prepareStatement(sql);

            ps.setBoolean(1, ativo);
            ps.setInt(2, id_tarefa);
            ps.executeUpdate();

            ps.close(); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public void fecharConexao() {
        conexaoBanco .fecharConexao();
    }

}
